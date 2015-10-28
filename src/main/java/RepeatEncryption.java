import java.io.FileNotFoundException;

public class RepeatEncryption<E> implements IEncryptionAlgorithm<E> {

    int repeat;
    IEncryptionAlgorithm<E> encryptionAlgorithm;

    public RepeatEncryption(IEncryptionAlgorithm<E> ea, int repeat1) {
        repeat = repeat1;
        encryptionAlgorithm = ea;
    }

    public String encrypt(String msg) {
        String e = msg;
        for (int i = 0; i < repeat; i++) {
            e = encryptionAlgorithm.encrypt(e);
        }
        return e;
    }

    public String decrypt(String msg) {
        String d = msg;
        for (int i = 0; i < repeat; i++) {
            d = encryptionAlgorithm.decrypt(d);
        }
        return d;
    }

    public E genKey(int maxLength) {
        return encryptionAlgorithm.genKey(maxLength);
    }

    public String saveKey(String parent_path) {
        return encryptionAlgorithm.saveKey(parent_path);
    }

    public void readKey(String parent_path) throws FileNotFoundException,
            InvalidEncryptionKeyException {
        encryptionAlgorithm.readKey(parent_path);
    }

    public int getKeyStrength() {
        return encryptionAlgorithm.getKeyStrength();
    }

    public void setKey(E key) {
        encryptionAlgorithm.setKey(key);
    }

    public E parseKey(String str) throws InvalidEncryptionKeyException {
        return encryptionAlgorithm.parseKey(str);
    }
}
