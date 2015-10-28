import java.io.FileNotFoundException;

public interface IEncryptionAlgorithm<E> {

    public String encrypt(String msg);

    public String decrypt(String msg);

    public String saveKey(String parent_path);

    public void readKey(String parent_path) throws FileNotFoundException,
            InvalidEncryptionKeyException;

    public E genKey(int strength);

    public void setKey(E key);

    E parseKey(String str) throws InvalidEncryptionKeyException;

    public int getKeyStrength();
}
