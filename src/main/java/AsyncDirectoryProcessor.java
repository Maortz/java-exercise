import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class AsyncDirectoryProcessor<E> extends Thread implements
        IDirectoryProcessor<E>, EncryptionObservable<E> {
    
    private IEncryptionAlgorithm<E> encryptionAlgorithm;

    private class AsyncEncryption implements Runnable {
        private String workfile;

        public AsyncEncryption(String string) {
            workfile = string;
        }

        private class SplitAsyncEncryption extends Thread {

            private String part;

            public SplitAsyncEncryption(String part1) {
                this.part = part1;
            }

            public void run() {
                part = encryptionAlgorithm.encrypt(part);
            }

            public String getPart() {
                return part;
            }
        }

        public void run() {
            String efile = null, dfile = getOutputFile(workfile, 0);
            ev_oneEncryptionStarted(new EncryptionLogEventArgs(workfile,
                    dfile, encryptionAlgorithm, System.currentTimeMillis()));
            try {
                efile = IO.readFile(workfile);
            } catch (FileNotFoundException e) {
                ev_error(e);
            }
            String[] parts = efile.split("(?<=\\G.{1000})");
            SplitAsyncEncryption t;
            ArrayList<SplitAsyncEncryption> threads = new ArrayList<SplitAsyncEncryption>();
            for (String part : parts) {
                t = new SplitAsyncEncryption(part);
                t.start();
                threads.add(t);
            }
            for (int i = 0; i < parts.length; i++) {
                while (threads.get(i).isAlive())
                    yield();
            }
            ev_oneEncryptionStarted(new EncryptionLogEventArgs(workfile,
                    dfile, encryptionAlgorithm, System.currentTimeMillis()));
            efile = "";
            for (int i = 0; i < parts.length; i++) {
                efile = efile + threads.get(i).getPart();
            }
            IO.writeFile(dfile, efile);
            ev_oneEncryptionEnded(new EncryptionLogEventArgs(workfile, dfile,
                    encryptionAlgorithm, System.currentTimeMillis()));
        }

    }

    private class AsyncDecryption implements Runnable {
        private String workfile;

        public AsyncDecryption(String string) {
            workfile = string;
        }

        public void run() {
            String dfile, efile = getOutputFile(workfile, 1);
            ev_oneDecryptionStarted(new EncryptionLogEventArgs(workfile,
                    efile, encryptionAlgorithm, System.currentTimeMillis()));
            try {
                dfile = encryptionAlgorithm.decrypt(IO.readFile(workfile));
                IO.writeFile(efile, dfile);
            } catch (FileNotFoundException e) {
                ev_error(e);
            }
            ev_oneDecryptionEnded(new EncryptionLogEventArgs(workfile, efile,
                    encryptionAlgorithm, System.currentTimeMillis()));
        }

    }

    public AsyncDirectoryProcessor(IEncryptionAlgorithm<E> encryptionAlgorithm1) {
        this.encryptionAlgorithm = encryptionAlgorithm1;
    }

    public void encryptDirectory(String directory) throws Exception {
        ArrayList<String> files = prepareEncryption(directory);
        ArrayList<Thread> threads = new ArrayList<Thread>();
        Thread t;
        ev_wholeEncryptionStarted(new EncryptionLogEventArgs(directory,
                directory + String.valueOf(File.separatorChar) + "encrypted",
                encryptionAlgorithm, System.currentTimeMillis()));
        for (String file : files) {
            t = new Thread(new AsyncEncryption(file));
            t.start();
            threads.add(t);
        }
        // Waiting
        for (int i = 0; i < files.size(); i++) {
            while (threads.get(i).isAlive())
                yield();
        }
        ev_wholeEncryptionEnded(new EncryptionLogEventArgs(directory, directory
                + String.valueOf(File.separatorChar) + "encrypted",
                encryptionAlgorithm, System.currentTimeMillis()));
    }

    public void decryptDirectory(String directory) throws Exception {
        ArrayList<String> files = prepareDecryption(directory);
        ArrayList<Thread> threads = new ArrayList<Thread>();
        Thread t;
        ev_wholeDecryptionStarted(new EncryptionLogEventArgs(directory,
                directory + String.valueOf(File.separatorChar) + "decrypted",
                encryptionAlgorithm, System.currentTimeMillis()));
        for (String file : files) {
            t = new Thread(new AsyncDecryption(file));
            t.start();
            threads.add(t);
        }
        // Waiting
        for (int i = 0; i < files.size(); i++) {
            while (threads.get(i).isAlive())
                yield();
        }
        ev_wholeDecryptionEnded(new EncryptionLogEventArgs(directory, directory
                + String.valueOf(File.separatorChar) + "decrypted",
                encryptionAlgorithm, System.currentTimeMillis()));
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

    private ArrayList<EncryptionObserver<E>> observers = new ArrayList<EncryptionObserver<E>>();

    public void addObserver(EncryptionObserver<E> o) {
        observers.add(o);
    }

    public void ev_oneEncryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.encryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_oneEncryptionEnded(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.encryptionEnded(encryptionLogEventArgs);
        }
    }

    public void ev_oneDecryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.decryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_oneDecryptionEnded(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.decryptionEnded(encryptionLogEventArgs);
        }

    }

    public void ev_wholeEncryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.dirEncryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_wholeEncryptionEnded(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.dirEncryptionEnded(encryptionLogEventArgs);
        }
    }

    public void ev_wholeDecryptionStarted(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.dirDecryptionStarted(encryptionLogEventArgs);
        }
    }

    public void ev_wholeDecryptionEnded(
            EncryptionLogEventArgs encryptionLogEventArgs) {
        for (EncryptionObserver<E> obs : observers) {
            obs.dirDecryptionEnded(encryptionLogEventArgs);
        }
    }

    public void ev_error(Exception exc) {
        for (EncryptionObserver<E> obs : observers) {
            obs.error(exc);
        }
    }

}
