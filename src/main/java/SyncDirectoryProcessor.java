import java.io.File;
import java.util.ArrayList;

public class SyncDirectoryProcessor<E> extends DirectoryProcessor<E> implements
        EncryptionObservable<E> {

    public SyncDirectoryProcessor(IEncryptionAlgorithm<E> encryptionAlgorithm) {
        super(encryptionAlgorithm);
    }

    public void encrtptDirectory(String directory) throws Exception {
        ArrayList<String> files = prepereEncryption(directory);
        ev_wholeEncryptionStarted(new EncryptionLogEventArgs(directory,
                directory + String.valueOf(File.separatorChar) + "encrypted",
                encryptionAlgorithm, System.currentTimeMillis()));
        for (String file : files) {
            String efile, dfile = getOutputFile(file, 0);
            ev_oneEncryptionStarted(new EncryptionLogEventArgs(file, dfile,
                    encryptionAlgorithm, System.currentTimeMillis()));
            efile = encryptionAlgorithm.encrypt(IO.readFile(file));
            IO.writeFile(dfile, efile);
            ev_oneEncryptionEnded(new EncryptionLogEventArgs(file, dfile,
                    encryptionAlgorithm, System.currentTimeMillis()));
        }
        ev_wholeEncryptionEnded(new EncryptionLogEventArgs(directory, directory
                + String.valueOf(File.separatorChar) + "encrypted",
                encryptionAlgorithm, System.currentTimeMillis()));
    }

    public void decryptDirectory(String directory) throws Exception {
        ArrayList<String> files = prepereDecryption(directory);
        ev_wholeDecryptionStarted(new EncryptionLogEventArgs(directory,
                directory + String.valueOf(File.separatorChar) + "decrypted",
                encryptionAlgorithm, System.currentTimeMillis()));
        for (String file : files) {
            String dfile, efile = getOutputFile(file, 1);
            ev_oneDecryptionStarted(new EncryptionLogEventArgs(file, efile,
                    encryptionAlgorithm, System.currentTimeMillis()));
            dfile = encryptionAlgorithm.decrypt(IO.readFile(file));
            IO.writeFile(efile, dfile);
            ev_oneDecryptionEnded(new EncryptionLogEventArgs(file, efile,
                    encryptionAlgorithm, System.currentTimeMillis()));
        }
        ev_wholeDecryptionEnded(new EncryptionLogEventArgs(directory, directory
                + String.valueOf(File.separatorChar) + "decrypted",
                encryptionAlgorithm, System.currentTimeMillis()));
    }

    public void ev_oneEncryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.encryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_oneEncryptionEnded(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.encryptionEnded(encryptionLogEventArgs);
        }
    }

    public void ev_oneDecryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.decryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_oneDecryptionEnded(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.decryptionEnded(encryptionLogEventArgs);
        }

    }

    public void ev_wholeEncryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.dirEncryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_wholeEncryptionEnded(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.dirEncryptionEnded(encryptionLogEventArgs);
        }
    }

    public void ev_wholeDecryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.dirDecryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_wholeDecryptionEnded(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.dirDecryptionEnded(encryptionLogEventArgs);
        }
    }

    private ArrayList<EncryptionObserver<E>> observers = new ArrayList<EncryptionObserver<E>>();

    public void addObserver(EncryptionObserver<E> o) {
        observers.add(o);
    }

}
