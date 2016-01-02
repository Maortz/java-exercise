import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

import org.junit.*;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBException;

public class TestJunit {
    static String orgPath = "/home/maor/doc.txt";
    static String decPath;
    static String encPath;
    static String encDirAsync = "/home/maor/Async_docs";
    static String decDirAsync = "/home/maor/Async_docs/encrypted";
    static String encDirSync = "/home/maor/Sync_docs";
    static String decDirSync = "/home/maor/sync_docs/encrypted";

    @BeforeClass
    public static void pathSetup() {
        int i = orgPath.lastIndexOf('.');
        encPath = orgPath.substring(0, i) + "_encrypted"
                + orgPath.substring(i);
        i = orgPath.lastIndexOf('.');
        decPath = orgPath.substring(0, i) + "_decrypted"
                + orgPath.substring(i);
    }

    // @AfterClass
    public static void deleteFiles() {
        try {
            Files.delete(Paths.get(encPath));
            Files.delete(Paths.get(decPath));
            Files.delete(Paths.get(Paths.get(orgPath).getParent().toString()
                    + String.valueOf(File.separatorChar) + "key.txt"));
        } catch (NoSuchFileException x) {
            // System.err.format("no such file or directory%n");
        } catch (DirectoryNotEmptyException x) {
            // System.err.format("not empty%n");
        } catch (IOException x) {
            // File permission problems are caught here.
            // System.err.println(x);
        }
    }

    // @Test
    public void testLogging() {
        FileEncryptor<Integer> f = new FileEncryptor<Integer>(
                new ShiftUpEncryption());
        EncryptionLogger<Integer> logger = new EncryptionLogger<Integer>(f);
        try {
            f.encryptFile(orgPath, encPath, 3);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // @Test
    public void test4JLogging() {
        FileEncryptor<Integer> f = new FileEncryptor<Integer>(
                new ShiftUpEncryption());
        EncryptionLog4JLogger<Integer> logger = new EncryptionLog4JLogger<Integer>(
                f, "log4J.txt");
        try {
            f.encryptFile(orgPath, encPath, 3);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // @Test
    public void test4JLoggingWithAsync() {
        AsyncDirectoryProcessor<Integer> adp = new AsyncDirectoryProcessor<Integer>(
                new ShiftUpEncryption());

        EncryptionLog4JLogger<Integer> logger = new EncryptionLog4JLogger<Integer>(
                adp, "log4J_async.txt");
        try {
            adp.encryptDirectory(encDirAsync);
            adp.decryptDirectory(decDirAsync);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //@Test
    public void test4JLoggingWithSync() {
        SyncDirectoryProcessor<Integer> adp = new SyncDirectoryProcessor<Integer>(
                new ShiftUpEncryption());

        EncryptionLog4JLogger<Integer> logger = new EncryptionLog4JLogger<Integer>(
                adp, "log4J_sync.txt");
        try {
            adp.encryptDirectory(encDirSync);
            adp.decryptDirectory(decDirSync);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testWriteXml() throws JAXBException, FileNotFoundException {
        XMLParamsReader r = new JAXBReader();
        r.readXML("EncryptionDetails.xml");
        EncryptionDetails ed = r.getEncryptionParamList().getEncryptionDetails().get(0);
        System.out.println(ed.getEncryptionAlgorithm());
        System.out.println(ed.getSourcePath());
        System.out.println(ed.getDestPath());

    }
}