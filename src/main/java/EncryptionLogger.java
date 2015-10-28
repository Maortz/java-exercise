public class EncryptionLogger<E> extends EncryptionObserver<E> {

    long startTime;
    long endTime;
    String sourcename;
    String algorithm;
    String destname;

    public EncryptionLogger(EncryptionObservable<E> fileEncryptor) {
        super(fileEncryptor);
    }

    @Override
    public synchronized void encryptionStarted(EncryptionLogEventArgs args) {
        startTime = args.time;
        sourcename = args.sourceName;
        destname = args.destName;
        algorithm = args.algorithmName.getClass().getSimpleName();
    }

    @Override
    public synchronized void encryptionEnded(EncryptionLogEventArgs args) {
        endTime = args.time;
        System.out
                .printf("The encryption for file %s with algorithm %s took %d miliseconds. The encrypted file is located in file %s.%n",
                        sourcename, algorithm, (endTime - startTime),
                        destname);
    }

    @Override
    public synchronized void decryptionStarted(EncryptionLogEventArgs args) {
        startTime = args.time;
        sourcename = args.sourceName;
        destname = args.destName;
        algorithm = args.algorithmName.getClass().getSimpleName();
    }

    @Override
    public synchronized void decryptionEnded(EncryptionLogEventArgs args) {
        endTime = args.time;
        System.out
                .printf("The decryption for file %s with algorithm %s took %f miliseconds. The decrypted file is located in file %s.%n",
                        sourcename, algorithm, (endTime - startTime),
                        destname);
    }

    @Override
    public synchronized void dirEncryptionEnded(EncryptionLogEventArgs args) {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void dirEncryptionStarted(EncryptionLogEventArgs args) {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void dirDecryptionEnded(EncryptionLogEventArgs args) {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void dirDecryptionStarted(EncryptionLogEventArgs args) {
        // TODO Auto-generated method stub

    }

    @Override
    public void error(Exception exc) {
        // TODO Auto-generated method stub

    }

}
