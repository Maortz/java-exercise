import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.*;

public class EncryptionLog4JLogger<E> extends EncryptionObserver<E> {

    public EncryptionLog4JLogger(EncryptionObservable<E> fileEncryptor,
            String filenameAppender) {
        super(fileEncryptor);
        logger = Logger.getLogger(this.getClass());
        FileAppender fa;
        try {
            fa = new FileAppender(new PatternLayout(
                    PatternLayout.TTCC_CONVERSION_PATTERN), filenameAppender);
            fa.setName("fa");
            logger.addAppender(fa);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        ConsoleAppender ca;
        ca = new ConsoleAppender(new PatternLayout(
                PatternLayout.TTCC_CONVERSION_PATTERN), "System.out");
        ca.setName("ca");
        logger.addAppender(ca);

    }

    Logger logger;
    ArrayList<EncryptionLogEventArgs> startingArgsList = new ArrayList<EncryptionLogEventArgs>();

    @Override
    public synchronized void encryptionStarted(EncryptionLogEventArgs args) {
        startingArgsList.add(args);
        logger.info("Enc. Started. " + args.toString());
    }

    @Override
    public synchronized void encryptionEnded(EncryptionLogEventArgs args) {
        int i = startingArgsList.indexOf(args);
        if (i == -1)
            logger.error("Enc. Ended without starting! " + args.toString());
        else {
            EncryptionLogEventArgs start_args = startingArgsList.get(i);
            logger.info("Enc. Ended. Took " + (args.time - start_args.time)
                    + "ms. " + args.toString());
            startingArgsList.remove(start_args);
        }
    }

    @Override
    public synchronized void decryptionStarted(EncryptionLogEventArgs args) {
        startingArgsList.add(args);
        logger.info("Dec. Started. " + args.toString());
    }

    @Override
    public synchronized void decryptionEnded(EncryptionLogEventArgs args) {
        int i = startingArgsList.indexOf(args);
        if (i == -1)
            logger.error("Dec. Ended without starting! " + args.toString());
        else {
            EncryptionLogEventArgs start_args = startingArgsList.get(i);
            logger.info("Dec. Ended. Took " + (args.time - start_args.time)
                    + "ms. " + args.toString());
            startingArgsList.remove(start_args);
        }
    }

    @Override
    public synchronized void dirEncryptionEnded(EncryptionLogEventArgs args) {
        int i = startingArgsList.indexOf(args);
        if (i == -1)
            logger.error("Dir Enc. Ended without starting! " + args.toString());
        else {
            EncryptionLogEventArgs start_args = startingArgsList.get(i);
            logger.info("Dir Enc. Ended. Took " + (args.time - start_args.time)
                    + "ms. " + args.toString());
            startingArgsList.remove(start_args);
        }
    }

    @Override
    public synchronized void dirEncryptionStarted(EncryptionLogEventArgs args) {
        startingArgsList.add(args);
        logger.info("Dir Enc. Started. " + args.toString());
    }

    @Override
    public synchronized void dirDecryptionEnded(EncryptionLogEventArgs args) {
        int i = startingArgsList.indexOf(args);
        if (i == -1)
            logger.error("Dir Dec. Ended without starting! " + args.toString());
        else {
            EncryptionLogEventArgs start_args = startingArgsList.get(i);
            logger.info("Dir Dec. Ended. Took " + (args.time - start_args.time)
                    + "ms. " + args.toString());
            startingArgsList.remove(start_args);
        }
    }

    @Override
    public synchronized void dirDecryptionStarted(EncryptionLogEventArgs args) {
        startingArgsList.add(args);
        logger.info("Dir Dec. Started. " + args.toString());
    }

    @Override
    public synchronized void error(Exception exc) {
        logger.error(exc.getMessage(), exc);
    }

}
