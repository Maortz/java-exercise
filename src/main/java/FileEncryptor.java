import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileEncryptor<E> implements EncryptionObservable<E> {

    IEncryptionAlgorithm<E> encryptionAlgorithm;

    public FileEncryptor(IEncryptionAlgorithm<E> ea) {
        encryptionAlgorithm = ea;
    }

    public void encryptFile(String input_path, String output_path, int maxLength)
            throws FileNotFoundException {
        ev_encryptionStarted(new EncryptionLogEventArgs(input_path,
                output_path, encryptionAlgorithm, System.currentTimeMillis()));

        encryptionAlgorithm.genKey(maxLength);
        String efile = encryptionAlgorithm.encrypt(IO.readFile(input_path));
        encryptionAlgorithm.saveKey(Paths.get(output_path).getParent()
                .toString());
        IO.writeFile(output_path, efile);

        ev_encryptionEnded(new EncryptionLogEventArgs(input_path, output_path,
                encryptionAlgorithm, System.currentTimeMillis()));
    }

    public void decryptFile(String input_path, String output_path)
            throws FileNotFoundException, InvalidEncryptionKeyException {
        ev_decryptionStarted(new EncryptionLogEventArgs(input_path,
                output_path, encryptionAlgorithm, System.currentTimeMillis()));

        encryptionAlgorithm.readKey(Paths.get(output_path).getParent()
                .toString());
        String dfile = encryptionAlgorithm.decrypt(IO.readFile(input_path));
        IO.writeFile(output_path, dfile);

        ev_decryptionEnded(new EncryptionLogEventArgs(input_path, output_path,
                encryptionAlgorithm, System.currentTimeMillis()));
    }

    private ArrayList<EncryptionObserver<E>> observers = new ArrayList<EncryptionObserver<E>>();

    public void addObserver(EncryptionObserver<E> o) {
        observers.add(o);
    }

    public void ev_encryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.encryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_encryptionEnded(EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.encryptionEnded(encryptionLogEventArgs);
        }
    }

    public void ev_decryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.decryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_decryptionEnded(EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.decryptionEnded(encryptionLogEventArgs);
        }
    }

}
