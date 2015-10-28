import java.util.ArrayList;

public interface IDirectoryProcessor<E> {

    ArrayList<String> getTxtFiles(String directory);

    void encrtptDirectory(String directory) throws Exception;

    ArrayList<String> prepereEncryption(String directory) throws Exception;

    void decryptDirectory(String directory) throws Exception;

    ArrayList<String> prepereDecryption(String directory) throws Exception;

    void createDirectory(String directory, int c) throws Exception;

    String getOutputFile(String input_file, int c);
    
}
