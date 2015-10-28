import java.io.File;
import java.io.FileNotFoundException;

public class DoubleEncryption<E> implements IEncryptionAlgorithm<DoubleKey<E>> {

    DoubleKey<E> key;
    IEncryptionAlgorithm<E> encryptionAlgorithm; // Important. it cannot be
                                                 // another type encryption.

    public DoubleEncryption(IEncryptionAlgorithm<E> ea) {
        encryptionAlgorithm = ea;
    }

    public String encrypt(String msg) {
        encryptionAlgorithm.setKey(key.getKey1());
        String e1 = encryptionAlgorithm.encrypt(msg);
        encryptionAlgorithm.setKey(key.getKey2());
        return encryptionAlgorithm.encrypt(e1);
    }

    public String decrypt(String msg) {
        encryptionAlgorithm.setKey(key.getKey2());
        String d1 = encryptionAlgorithm.decrypt(msg);
        encryptionAlgorithm.setKey(key.getKey1());
        return encryptionAlgorithm.decrypt(d1);
    }

    public String saveKey(String parent_path) {
        String keys = key.getKey1() + "," + key.getKey2();
        String key_path = parent_path + String.valueOf(File.separatorChar)
                + "key.txt";
        IO.writeFile(key_path, keys);
        return key_path;
    }

    public void readKey(String parent_path)
            throws InvalidEncryptionKeyException, FileNotFoundException {
        String key_path = parent_path + String.valueOf(File.separatorChar)
                + "key.txt";
        String keys = IO.readFile(key_path);
        key = parseKey(keys);
    }

    public DoubleKey<E> genKey(int strength) {
        E k1 = encryptionAlgorithm.genKey(strength);
        E k2 = encryptionAlgorithm.genKey(strength);
        key = new DoubleKey<E>(k1, k2);
        return key;
    }

    public int getKeyStrength() {
        encryptionAlgorithm.setKey(key.getKey1());
        int a = encryptionAlgorithm.getKeyStrength();
        encryptionAlgorithm.setKey(key.getKey2());
        int b = encryptionAlgorithm.getKeyStrength();
        return Math.max(a, b);
    }

    public void setKey(DoubleKey<E> key1) {
        this.key.setKey1(key1.getKey1());
        this.key.setKey2(key1.getKey2());

    }

    public DoubleKey<E> parseKey(String str)
            throws InvalidEncryptionKeyException {
        int i = str.indexOf(',');
        DoubleKey<E> key1 = new DoubleKey<E>(encryptionAlgorithm.parseKey(str
                .substring(0, i)), encryptionAlgorithm.parseKey(str
                .substring(i + 1)));
        return key1;
    }

}
