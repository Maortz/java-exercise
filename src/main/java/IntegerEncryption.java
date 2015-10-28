import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public abstract class IntegerEncryption implements
        IEncryptionAlgorithm<Integer> {
    int maxLength;
    int key;

    public IntegerEncryption() {
    }

    public IntegerEncryption(String keypath) throws FileNotFoundException {
        String kfile = IO.readFile(keypath);
        key = Integer.parseInt(kfile);
    }

    public Integer genKey(int maxLength) {
        this.maxLength = maxLength;
        int max = (int) Math.pow(10, maxLength);
        key = new Random().nextInt(max);
        return key;
    }

    public String saveKey(String parent_path) {
        String key_path = parent_path + String.valueOf(File.separatorChar)
                + "key.txt";
        IO.writeFile(key_path, Integer.toString(key));
        return key_path;
    }

    public void readKey(String parent_path) throws FileNotFoundException,
            InvalidEncryptionKeyException {
        String key_path = parent_path + String.valueOf(File.separatorChar)
                + "key.txt";
        String str = IO.readFile(key_path);
        key = parseKey(str);
    }

    public int getKeyStrength() {
        return maxLength;
    }

    public void setKey(Integer key1) {
        this.key = key1;
        maxLength = (int) Math.log10(this.key) + 1;
    }

    public Integer parseKey(String str) throws InvalidEncryptionKeyException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidEncryptionKeyException();
        }
    }

    public abstract String encrypt(String msg);

    public abstract String decrypt(String msg);

}
