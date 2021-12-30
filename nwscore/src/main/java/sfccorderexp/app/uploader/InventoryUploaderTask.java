package sfccorderexp.app.uploader;

import kong.unirest.HttpResponse;
import nwscore.Credential;
import nwscore.NwsContext;
import nwscore.NwsEnvironment;
import nwscore.io.DefaultRestClient;
import nwscore.io.InvalidCredentialsException;
import nwscore.io.RestClient;
import nwscore.io.RestClientException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import sfccorderexp.app.ApplicationParams;
import sfccorderexp.app.utils.ConsoleUtils;
import sfccorderexp.app.utils.SingleTaskExecutor;
import sfccorderexp.app.utils.TaskRunnable;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class InventoryUploaderTask implements TaskRunnable {
    //private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private SingleTaskExecutor executor;
    private String inputFolder;

    private static final int DEFAULT_SLEEP = 1000;

    private String activeTaskUuid;
    private boolean isInProgress  = false;
    private NwsContext context;
    private RestClient restClient;
    private String inputInvFile;
    private String fulfillmentLocationId;
    private String stockLocationId;
    private int chunkSize;
    private String nameOfUploadTask;
    private int idle;

    List<CSVRecord> listOfRecords;

    public InventoryUploaderTask(ApplicationParams params) {
        String username = params.getUsername();
        String password = params.getPassword();
        String tenant = params.getTenant();
        String environment = params.getEnvironment();
        this.inputInvFile = params.getInvLoad();
        this.fulfillmentLocationId = params.getFullFillmentLocation();
        this.stockLocationId = params.getStockLocation();
        this.chunkSize = params.getChunkSize();
        this.nameOfUploadTask = params.getNameOfInvLoad();
        this.idle = params.getIdle();
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
            ConsoleUtils.println("Start uploading inventory");
        //    LOGGER.info("Start uploading inventory");
            try {
                 boolean keepRunning = true;
                 listInputCsv(this.inputInvFile);
                 int totalNrOfLines = listOfRecords.size() - 1;
                 ConsoleUtils.println(String.format("Total lines to upload %d", totalNrOfLines));
             //    LOGGER.info(String.format("Total lines to upload %d", totalNrOfLines));
                 int chunkSize = this.chunkSize;
                int nrOfChunks = 1;
                 if (totalNrOfLines < chunkSize) {
                      nrOfChunks = 1;
                 } else {
                     nrOfChunks = (int) Math.ceil((double)totalNrOfLines / chunkSize);
                 }
                ConsoleUtils.println(String.format("Number of chunks(size %d) to be uploaded : %d",chunkSize, nrOfChunks));
             //   LOGGER.info(String.format("Number of chunks(size %d) to be uploaded : %d",chunkSize, nrOfChunks));
                for (int i = 1; i<= nrOfChunks; i++)
                {
                    if (status.isInterrupted()) {
                        break;
                    }
                  String s =  newChunk(i, chunkSize);
                    ConsoleUtils.println(String.format("Uploading chunk to Newstore : %d / %d", i, nrOfChunks));
                  Optional<HttpResponse<String>> response = this.uploadInventoryChunk(s);
                  if (response.isEmpty()) {
                      ConsoleUtils.println(String.format("ERROR Uploading chunk  to Newstore : %d / %d", i, nrOfChunks));
                      ConsoleUtils.println("EXITING...");
                      break;
                  }
                  else {
                      if (response.get().isSuccess()) {
                          ConsoleUtils.println(String.format("SUCCESSFULLY Uploaded chunk  to Newstore : %d / %d", i, nrOfChunks));
                          ConsoleUtils.println(String.format("Not found products : %s", response.get().getBody()));
                      } else {
                          ConsoleUtils.println(String.format("SERVER responded error while uploading chunk  to Newstore : %d / %d", i, nrOfChunks));
                          ConsoleUtils.println(String.format("Status code - text : %d - %s", response.get().getStatus(), response.get().getStatusText()));
                      }
                  }
                    ConsoleUtils.printlnLine();
                    ConsoleUtils.printlnLine();
                    ConsoleUtils.printlnLine();
                    Thread.sleep(1000 * this.idle);
                };

            } catch (Exception e)   {
                ConsoleUtils.printLnException(e);
                ConsoleUtils.println("EXITING, Problem while uploading the chunk, please check error message!");
     //           LOGGER.info(String.format("ERROR : %s, Internal error : %s", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "---"));
            }
        });
    }


    public void listInputCsv(String csvFileName) throws IOException {
        Reader in = new FileReader(csvFileName);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        listOfRecords = new ArrayList<>();
        for (CSVRecord record : records) {
            listOfRecords.add(record);
        }
    }

    public String newChunk(int  chunkNr, int chunkSize) throws IOException {
        if (chunkNr == 0) {
            throw new IllegalArgumentException("ChunkNr should start from 1");
        }
        int  begin = 0;
        int end = 0;
        if (chunkNr == 1) {
            begin = 1;
            end = chunkSize;
        } else {
            begin = (chunkSize * (chunkNr-1)) + 1;
            end = (chunkSize * (chunkNr-1)) + chunkSize;
        }
        if (begin > listOfRecords.size()) {
            throw new IllegalStateException(String.format("Begin bigger than size,  begin : %d,  end %d,  size of the list : %d", begin, end, listOfRecords.size()));
        }
        if (end > listOfRecords.size()) {
            end = begin + listOfRecords.size() - begin - 1;
        }

        ConsoleUtils.println(String.format("Preparing Chunk %d, Row range [begin, end] : %d - %d", chunkNr, begin, end));
 //       LOGGER.info(String.format("Preparing Chunk %d, Row range [begin, end] : %d - %d", chunkNr, begin, end));



        StringWriter writer = new StringWriter();
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
        printer.printRecord(listOfRecords.get(0).get(0), listOfRecords.get(0).get(1));
        for (int i = begin; i<end; i++) {
            printer.print(listOfRecords.get(i).get(0));
            printer.print(listOfRecords.get(i).get(1));
            printer.println();
        }
        writer.flush();
        //ConsoleUtils.println(String.format("DONE.....Preparing Chunk %d, Row range [begin, end] : %d - %d", chunkNr, begin, end));
       // LOGGER.info(String.format("DONE.....Preparing Chunk %d, Row range [begin, end] : %d - %d", chunkNr, begin, end));
        return writer.toString();
    }


public Optional<HttpResponse<String>> uploadInventoryChunk(String chunk) {
    try {
        //ConsoleUtils.println("Uploading  chunk to Newstore");
   //     LOGGER.info("Uploading  chunk to Newstore");
        Reader input = new StringReader(chunk);
        InputStream targetStream = IOUtils.toInputStream(IOUtils.toString(input), Charsets.UTF_8);


        String path = String.format("/v0/i/inventory/stores/%s/stock_locations/%s/stock_import", this.fulfillmentLocationId, this.stockLocationId);
//        Map<String, Object> fields = Map.of("file", input, "count_type","full_count", "external_id", "external_id");
        Map<String, Object> fields = Map.of("file", targetStream, "count_type","full_count", "external_id", this.nameOfUploadTask);


        HttpResponse<String> response = this.restClient.post(path, fields);
        return Optional.of(response);
    } catch (RestClientException e) {
        ConsoleUtils.printLnException(e);
   //     LOGGER.info(String.format("ERROR : %s, Internal error : %s", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "---"));
    } catch (InvalidCredentialsException e) {
        ConsoleUtils.printLnException(e);
    //    LOGGER.info(String.format("ERROR : %s, Internal error : %s", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "---"));
    } catch (IOException e) {
        ConsoleUtils.printLnException(e);
    }
    return Optional.empty();
}






}
