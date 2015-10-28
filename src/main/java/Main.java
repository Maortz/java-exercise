import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    /**
     * @param args
     */
    public static void main(String[] args) {

        FileEncryptor<DoubleKey<Integer>> enc = new FileEncryptor<DoubleKey<Integer>>(
                new DoubleEncryption<Integer>(new ShiftUpEncryption()));

        System.out.println("Choose an option:");
        System.out.println("\t1. Encryption\n\t2. Decryption");
        int choose = sc.nextInt();
        switch (choose) {
        case 1:
            encrypt(enc);
            break;
        case 2:
            decrypt(enc);
            break;
        default:
            System.out.println("Error");
            break;
        }
        System.out.println("Have a nice day:)");
    }

    /**
	 * 
	 */
    private static void encrypt(FileEncryptor<?> enc) {

        System.out.println("Enter file to encrypt:");
        String path = sc.next();
        int i = path.lastIndexOf('.');
        String opath = path.substring(0, i) + "_encrypted" + path.substring(i);
        try {
            enc.encryptFile(path, opath, 3);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }

    /**
	 * 
	 */
    private static void decrypt(FileEncryptor<?> enc) {

        System.out.println("Enter file to decrypt:");
        String path = sc.next();
        int i = path.lastIndexOf('.');
        String opath = path.substring(0, i) + "_decrypted" + path.substring(i);
        try {
            enc.decryptFile(path, opath);
        } catch (InvalidEncryptionKeyException exc) {
            System.out.println("Invalid encryption key");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

}
