import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class IO {

    public static String readFile(String path) throws FileNotFoundException {
        // Opening
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw e;
        }
        // Reading
        java.util.Scanner scanner = new java.util.Scanner(reader);
        java.util.Scanner s = scanner.useDelimiter("\\A");
        String ret = s.hasNext() ? s.next() : "";
        // Closing
        scanner.close();
        try {
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return ret;
    }

    public static void writeFile(String path, String msg) {
        // Opening
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Writing
        try {
            writer.write(msg);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // Closing
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
