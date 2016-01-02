import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class DirectoryProcessor<E> implements IDirectoryProcessor<E> {

    IEncryptionAlgorithm<E> encryptionAlgorithm;

    public DirectoryProcessor(IEncryptionAlgorithm<E> encryptionAlgorithm1) {
        this.encryptionAlgorithm = encryptionAlgorithm1;
    }

    public String getOutputFile(String input_file, int c) {
        String s = "";
        switch (c) {
        case 0:
            s = "encrypted";
            break;
        case 1:
            s = "decrypted";
            break;
        }
        return Paths.get(input_file).getParent().toString()
                + String.valueOf(File.separatorChar) + s
                + String.valueOf(File.separatorChar)
                + Paths.get(input_file).getFileName().toString();
    }

    public ArrayList<String> getTxtFiles(String directory) {
        String[] ss = new File(directory).list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt")
                        && name.compareTo("key.txt") != 0;
            }
        });
        for (int i = 0; i < ss.length; i++) {
            ss[i] = directory + String.valueOf(File.separatorChar) + ss[i];
        }
        return new ArrayList<String>(Arrays.asList(ss));
    }

    public void createDirectory(String directory, int c) throws Exception {
        String s = "";
        switch (c) {
        case 0:
            s = "encrypted";
            break;
        case 1:
            s = "decrypted";
            break;
        }
        if (!new File(directory + String.valueOf(File.separatorChar) + s)
                .mkdir())
            throw new Exception("Cannot create directory");
    }

    public ArrayList<String> prepareEncryption(String directory)
            throws Exception {
        createDirectory(directory, 0);
        encryptionAlgorithm.genKey(3);
        encryptionAlgorithm.saveKey(directory
                + String.valueOf(File.separatorChar) + "encrypted");
        return getTxtFiles(directory);
    }

    public ArrayList<String> prepareDecryption(String directory)
            throws Exception {
        createDirectory(directory, 1);
        ArrayList<String> files = getTxtFiles(directory);
        encryptionAlgorithm.readKey(directory);
        return files;
    }

}
