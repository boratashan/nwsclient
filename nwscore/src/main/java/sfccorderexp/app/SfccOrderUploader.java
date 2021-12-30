package sfccorderexp.app;

import org.apache.commons.cli.*;
import sfccorderexp.app.uploader.OrderUploaderTask;
import sfccorderexp.app.utils.ConsoleUtils;
import sfccorderexp.app.utils.TaskRunnable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.*;

public class SfccOrderUploader {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final int EXITCODE_SUCCESS = 0;
    private static final int EXITCODE_ARGUMENT_ERROR = 1;
    private static final int EXITCODE_FILE_NOT_FOUND = 2;
    private static final int EXITCODE_USER_INTERRUPTED = 3;


    public static void main(String[] args) throws IOException, ParseException {
        LOGGER.setLevel(Level.INFO);
        LOGGER.addHandler(new ConsoleHandler());

        FileHandler handler = new FileHandler("orders-log.%u.%g.txt", true);
        handler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(handler);


        // create Options object
        Options options = new Options();

// add t option
        options.addOption("help", false, "Print help");
        options.addOption("tenant", true, "Tenant");
        options.addOption("env", true, "Environment");
        options.addOption("u", true, "Username");
        options.addOption("p", true, "Password");
        options.addOption("source", true, "Source folder");
        options.addOption("invload", true, "Run as inventory loader");



        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( options, args);

        if (cmd.hasOption("help")) {
            LOGGER.info("Print commandline usage...");
            HelpFormatter formatter = new HelpFormatter();

            final PrintWriter writer = new PrintWriter(System.out);
            formatter.printUsage(writer,80,"Newstore uploader.", options);
            writer.flush();
            System.exit(0);
        }
        ApplicationParams params = new ApplicationParams();
        params.setTenant(cmd.getOptionValue("tenant"));
        params.setEnvironment(cmd.getOptionValue("env"));
        params.setUsername(cmd.getOptionValue("u"));
        params.setPassword(cmd.getOptionValue("p"));
        params.setSourceFolder(cmd.getOptionValue("source"));
        params.setInvLoad(cmd.getOptionValue("invload"));

            //.withCredentials(new Credential("btashan@newstore.com", "bORA@911!s"))
        //"/Users/btashan/Desktop/OrdersInput/"

       // ConsoleUtils.println(params.toString());

        //System.exit(0);
        int exitCode = EXITCODE_SUCCESS;

   //p
        // ConsoleUtils.println("Running Newstore SFCC order uploader...");
        LOGGER.info("Running Newstore SFCC order uploader...");
        try {
            try { //"/Users/btashan/Desktop/OrdersInput/"
                OrderUploaderTask task = new OrderUploaderTask(params);
                task.run();
                boolean isInterrupted = waitTaskOrKeyToFinish(task, 'q');
                if (isInterrupted) {
                    exitCode = EXITCODE_SUCCESS;
                  //  ConsoleUtils.println("Process is interrupted by the user");
                    LOGGER.info("Process is interrupted by the user");
                    return;
                }
            } catch (IOException e) {
                if (e instanceof FileNotFoundException)
                    exitCode = EXITCODE_FILE_NOT_FOUND;
                else if (e instanceof IOException)
                    exitCode = EXITCODE_ARGUMENT_ERROR;
               // ConsoleUtils.println(String.format("ERROR : %s, Internal error : %s", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "---"));
                LOGGER.info(String.format("ERROR : %s, Internal error : %s", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "---"));

            } catch (Exception e) {
               // ConsoleUtils.println(String.format("UNKNOWN ERROR : %s", e.getMessage()));
                LOGGER.info(String.format("UNKNOWN ERROR : %s", e.getMessage()));
                exitCode = EXITCODE_ARGUMENT_ERROR;
            }
        } finally {
            //ConsoleUtils.println("Uploader ENDS.");
            LOGGER.info("Uploader ENDS.");

            System.exit(exitCode);
        }
    }

    private static boolean waitTaskOrKeyToFinish(TaskRunnable task, char keyToQuit) throws IOException {
        boolean isInterrupted = false;
        while (!(task.isFinished())) {
            if (System.in.available() > 0) {
                int c = System.in.read();
                if (c == 'q') {
                    task.interrupt();
                    isInterrupted = true;
                }
            }
        }
        if (isInterrupted)
            task.join();
        return isInterrupted;
    }



}
