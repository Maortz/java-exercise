public abstract class EncryptionObserver<E> {

    public EncryptionObserver(EncryptionObservable<E> fileEncryptor) {
        this.encryptionObservalbe = fileEncryptor;
        fileEncryptor.addObserver(this);
    }

    protected EncryptionObservable<E> encryptionObservalbe;

    public abstract void encryptionStarted(EncryptionLogEventArgs args);

    public abstract void encryptionEnded(EncryptionLogEventArgs args);

    public abstract void decryptionStarted(EncryptionLogEventArgs args);

    public abstract void decryptionEnded(EncryptionLogEventArgs args);

    public abstract void dirEncryptionEnded(EncryptionLogEventArgs args);

    public abstract void dirEncryptionStarted(EncryptionLogEventArgs args);

    public abstract void dirDecryptionEnded(EncryptionLogEventArgs args);

    public abstract void dirDecryptionStarted(EncryptionLogEventArgs args);

    public abstract void error(Exception exc);

}
