package sfccorderexp.app.uploader;

import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;

import nwscore.Credential;
import nwscore.NwsContext;
import nwscore.NwsEnvironment;
import nwscore.io.DefaultRestClient;
import nwscore.io.InvalidCredentialsException;
import nwscore.io.RestClient;
import nwscore.io.RestClientException;
import nwscore.utils.IOUtils;
import org.apache.http.ParseException;
import org.apache.http.client.HttpResponseException;
import sfccorderexp.app.ApplicationParams;
import sfccorderexp.app.utils.ConsoleUtils;
import sfccorderexp.app.utils.SingleTaskExecutor;
import sfccorderexp.app.utils.TaskRunnable;

import java.io.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class OrderUploaderTask implements TaskRunnable {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private SingleTaskExecutor executor;
    private String inputFolder;
    private static final String FOLDER_PROCESSED = "processed";
    private static final String FOLDER_FAILED = "failed";
    private static final String FOLDER_TEMP = "temp";

    private final static String EP_JOBQUERY = "/sfcc-api/v1/job_history/";
    private final static String EP_JOBTASKQUERY = "/sfcc-api/v1/job_history/%s/";
    private final static String EP_GETUPLOADURL = "/sfcc-api/v1/job_config/order";
    private static final int DEFAULT_SLEEP = 1000;

    private String activeTaskUuid;
    private boolean isInProgress  = false;
    private NwsContext context;
    private RestClient restClient;

    public OrderUploaderTask(ApplicationParams params) {
        this.inputFolder = params.getSourceFolder();
        String username = params.getUsername();
        String password = params.getPassword();
        String tenant = params.getTenant();
        String environment = params.getEnvironment();
        this.context = NwsContext.Builder
                .start()
                .withCredentials(new Credential(username, password))
                .withTenant(tenant)
                .withEnvironment(NwsEnvironment.fromString(environment))
                .withNwsUrl("newstore.net")
                .build();
        this.restClient = new DefaultRestClient(context);
        executor = new SingleTaskExecutor();

    }

    @Override
    public boolean isFinished() {
        return executor.isFinished();
    }

    @Override
    public void interrupt() {
        executor.interrupt();
    }

    @Override
    public void join() {
        executor.join();
    }


    @Override
    public void run() {
        executor.execute(status -> {
            ConsoleUtils.printlnLine();
            ConsoleUtils.println("Starting...");
            try {
                ConsoleUtils.println("Creating folder structure...");
                 this.initFolders();
                 boolean keepRunning = true;
                 while (keepRunning) {
                     keepRunning = this.runNext(() -> status.isInterrupted());
                     Thread.sleep(1000);
                 }
            } catch (FileNotFoundException e)   {
                ConsoleUtils.printLnException(e);
            }
        });
    }




    private Optional<File> getNextOrderFile() {
        //   File f = new File("/Users/btashan/Desktop/OrdersInput");
        File f = new File(this.inputFolder);
        Optional<String> file =  Arrays.stream(f.list((dir, name) -> {
            return name.endsWith(".xml");
        })).sorted(Comparator.reverseOrder()).findFirst();
        if (file.isPresent()) {
            String fileFullPath = IOUtils.joinPaths(inputFolder, file.get());
            return Optional.of(new File(fileFullPath));
        }
        else {
            return Optional.empty();
        }
    }


    public  void initFolders() throws FileNotFoundException {
        File f = new File(inputFolder);
        if (!f.exists())
            throw new FileNotFoundException(String.format("Input folder is not found %s", inputFolder));
        String processed = IOUtils.joinPaths(inputFolder, FOLDER_PROCESSED);
        String failed = IOUtils.joinPaths(inputFolder, FOLDER_FAILED);
        String temp = IOUtils.joinPaths(inputFolder, FOLDER_TEMP);

        File fd = new File(processed);
        if (!fd.exists())
            fd.mkdir();
        fd = new File(failed);
        if (!fd.exists())
            fd.mkdir();
        fd = new File(temp);
        if (!fd.exists())
            fd.mkdir();
    }

    private void moveFileToProcessed(String orderFile) throws IOException {
        File source = new File(orderFile);
        File target = new File(IOUtils.joinPaths(inputFolder, FOLDER_PROCESSED, source.getName()));
        Files.move(source, target);
    }

    private void moveFileToFailed(String orderFile) throws IOException {
        File source = new File(orderFile);
        File target = new File(IOUtils.joinPaths(inputFolder, FOLDER_FAILED, source.getName()));
        Files.move(source, target);
    }

    private String zipFile (String orderFile) throws IOException {
        File source = new File(orderFile);
        String uuid = UUID. randomUUID().toString();
        String target = IOUtils.joinPaths(inputFolder, FOLDER_TEMP, source.getName().concat(".zip"));
        FileOutputStream fos = new FileOutputStream(target);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(orderFile);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
        return target;
    }


    public boolean runNext(Supplier<Boolean> interruptHandler) {
        Optional<File> file = this.getNextOrderFile();
        if (interruptHandler.get()) {
            return false;
        }
        if (file.isEmpty())  {
            ConsoleUtils.println("No file to process...");
            LOGGER.info("No file to process...");
            return false;
        }
        String fileName = file.get().getName();

        ConsoleUtils.println (String.format("Start processing file %s", fileName));
        LOGGER.info(String.format("Start processing file %s", fileName ));
        try {
            String zippedFile = zipFile(file.get().getAbsolutePath());
            ConsoleUtils.println (String.format("Zipped file %s to  %s", fileName, zippedFile));
            LOGGER.info(String.format("Zipped file %s to  %s", fileName, zippedFile));
            this.startImportingOrder(new File(zippedFile));
            ConsoleUtils.println (String.format("Uploaded file %s", fileName));
            LOGGER.info(String.format("Uploaded file %s", fileName));
            Thread.sleep(1000);
            ConsoleUtils.println(String.format("Start checking status of file %s", fileName));
            LOGGER.info(String.format("Start checking status of file %s", fileName));
            if (this.isInProgress) {
                boolean keepChecking = true;
                while (keepChecking) {
                    Thread.sleep(1000*10);
                    ImportTaskStatus taskStatus = this.getActiveImportTaskStatus(this.activeTaskUuid);
                    String logLocation;
                    String logToSave;
                    switch (taskStatus) {
                        case PENDING:
                            LOGGER.info(".");
                            keepChecking = true;
                            break;
                        case INPROGRESS:
                            LOGGER.info(">");
                            keepChecking = true;
                            break;
                        case COMPLETED:
                            LOGGER.info(String.format("COMPLETED  file %s", fileName));
                            this.moveFileToProcessed(file.get().getAbsolutePath());
                            keepChecking = false;
                            isInProgress = false;
                            break;
                        case OTHER:
                            isInProgress = false;
                            LOGGER.info(String.format("Unknown status  file %s", fileName));
                            logLocation = getTaskLogFile(this.activeTaskUuid);
                            LOGGER.info(String.format("Log location : %s", logLocation));
                            logToSave = IOUtils.joinPaths(inputFolder, FOLDER_FAILED, fileName+".log");
                            LOGGER.info(String.format("Log to save : %s", logToSave));
                            downloadLogFile(logLocation, logToSave );
                            LOGGER.info(String.format("Log file downloaded : %s", logToSave));
                            this.moveFileToFailed(file.get().getAbsolutePath());
                            LOGGER.info(String.format("File moved to failed : %s", fileName));
                            keepChecking = false;
                            break;
                        case FAILED:
                            isInProgress = false;
                            LOGGER.info(String.format("FAILED  file %s", fileName));
                            logLocation = getTaskLogFile(this.activeTaskUuid);
                            LOGGER.info(String.format("Log location : %s", logLocation));
                            logToSave = IOUtils.joinPaths(inputFolder, FOLDER_FAILED, fileName+".log");
                            LOGGER.info(String.format("Log to save : %s", logToSave));
                            downloadLogFile(logLocation, logToSave );
                            LOGGER.info(String.format("Log file downloaded : %s", logToSave));
                            this.moveFileToFailed(file.get().getAbsolutePath());
                            LOGGER.info(String.format("File moved to failed : %s", fileName));
                            keepChecking = false;
                            break;
                    }
                }
            }
        } catch (InvalidCredentialsException | RestClientException | IOException | OperationNotAllowedException | InterruptedException e) {
            LOGGER.info(String.format("EXCEPTION %s", e.getMessage()));
        }
        isInProgress = false;
        return true;
    }


    public String getFirstJobUuid() throws InvalidCredentialsException, RestClientException, HttpResponseException {
        String uuid = null;

        HttpResponse<String> result  =  restClient.get(EP_JOBQUERY, Map.of("entity", "order"), null);
        if (result.isSuccess()) {
            String body = result.getBody();
            try {
                JsonElement element = JsonParser.parseString(body);
                uuid = ((JsonObject) element).get("jobs").getAsJsonArray().get(0).getAsJsonObject().get("uuid").getAsString();
                return uuid;
            }
            catch (Exception e) {
                throw new ParseException(String.format("Can not parse list of tasks response, Result is :"+body));
            }
        } else {
            throw new HttpResponseException(result.getStatus(), result.getStatusText());
        }
    }


    public ImportTaskStatus getActiveImportTaskStatus(String taskUuid) throws InvalidCredentialsException, RestClientException, HttpResponseException {
        JsonObject object = getTaskStatus(taskUuid);
   //     LOGGER.info(String.format("Status check result  %s", object.toString()));
        return ImportTaskStatus.fromString(object.get("status").getAsString());
    }

    public String getTaskLogFile(String taskUuid) throws InvalidCredentialsException, RestClientException, HttpResponseException {
        JsonObject object = getTaskStatus(taskUuid);
        String log = object.get("links").getAsJsonObject().get("logs").getAsString();
        return log;
    }


    public void downloadLogFile(String logUrl, String logFileOutput) throws InvalidCredentialsException, RestClientException, IOException {
        HttpResponse<String> result  =  restClient.get(logUrl, null, null);
        if (result.isSuccess()) {
            FileWriter fileWriter = new FileWriter(logFileOutput);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            try {
                printWriter.print(result.getBody());
            }
            finally {
                printWriter.close();
            }
        }
    }


    public JsonObject getTaskStatus(String taskUuid) throws InvalidCredentialsException, RestClientException, HttpResponseException {
        HttpResponse<String> result  =  restClient.get(String.format(EP_JOBTASKQUERY, taskUuid), null, null);
        if (result.isSuccess()) {
            String body = result.getBody();
            try {
                JsonElement element = JsonParser.parseString(body);
                return (JsonObject) element;
            }
            catch (Exception e) {
                throw new ParseException(String.format("Can not parse list of tasks response, Result is :"+body));
            }
        } else {
            throw new HttpResponseException(result.getStatus(), result.getStatusText());
        }
    }


    public String getUploadUrl() throws InvalidCredentialsException, RestClientException, HttpResponseException {
        String requestBody ="{\n" +
                "    \"cartridgeVersion\": \"20.1.0\",\n" +
                "    \"executionID\": \"NewStoreConnectorOrdersExport\",\n" +
                "    \"jobID\": \"680107\",\n" +
                "    \"siteId\": \"RefArch\"\n" +
                "}";
        HttpResponse<String> result  =  restClient.post(EP_GETUPLOADURL, requestBody);
        if (result.isSuccess()) {
            String body = result.getBody();
            try {
                JsonElement element = JsonParser.parseString(body);
                String uploadUrl =  element.getAsJsonObject().get("upload_url").getAsString();
                return uploadUrl;
            }
            catch (Exception e) {
                throw new ParseException(String.format("Can not get upload url, Result is :"+body));
            }
        } else {
            throw new HttpResponseException(result.getStatus(), result.getStatusText());
        }
    }


    public boolean uploadFileToUrl(byte[] bytes, String uploadUrl) throws InvalidCredentialsException, RestClientException, HttpResponseException {

        HttpResponse<String> result  =  restClient.put(uploadUrl, bytes);
        if (result.isSuccess()) {
            String body = result.getBody();
            try {
                return true;
            }
            catch (Exception e) {
                throw new ParseException(String.format("Can not get upload url, Result is :"+body));
            }
        } else {
            throw new HttpResponseException(result.getStatus(), result.getStatusText());
        }
    }




    public void startImportingOrder(File orderFile) throws InvalidCredentialsException, RestClientException, IOException, OperationNotAllowedException, InterruptedException {
        if (isInProgress) {
            throw new OperationNotAllowedException("There is a ongoing upload, can not start a new one");
        }
        isInProgress = true;
        try {
            String uploadUrl = this.getUploadUrl();
            FileInputStream fs = new FileInputStream(orderFile);
            byte[] input = fs.readAllBytes();
            this.uploadFileToUrl(input, uploadUrl);
            Thread.sleep(DEFAULT_SLEEP);
            this.activeTaskUuid = this.getFirstJobUuid();
        } catch (Exception e) {
            isInProgress = false;
            throw e;
        }
    }


    public ImportTaskStatus getImportStatus() throws InvalidCredentialsException, RestClientException, HttpResponseException {
        JsonObject object =  getTaskStatus(this.activeTaskUuid);
        ImportTaskStatus status = ImportTaskStatus.valueOf("pending");
        return status;
    }




}
