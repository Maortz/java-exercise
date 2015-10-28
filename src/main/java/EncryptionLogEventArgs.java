public class EncryptionLogEventArgs {

    String sourceName;
    String destName;
    IEncryptionAlgorithm<?> algorithmName;
    long time;

    public EncryptionLogEventArgs(String sourceName, String destName,
            IEncryptionAlgorithm<?> algorithmName, long time) {
        this.sourceName = sourceName;
        this.destName = destName;
        this.algorithmName = algorithmName;
        this.time = time;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getDestName() {
        return destName;
    }

    public IEncryptionAlgorithm<?> getAlgorithmName() {
        return algorithmName;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object obj) {
        boolean a = this.sourceName
                .equals(((EncryptionLogEventArgs) obj).sourceName);
        boolean b = this.destName
                .equals(((EncryptionLogEventArgs) obj).destName);
        boolean c = this.algorithmName
                .equals(((EncryptionLogEventArgs) obj).algorithmName);
        return a && b && c;
    }

    @Override
    public int hashCode() {
        int a = sourceName.hashCode();
        int b = destName.hashCode();
        int c = algorithmName.hashCode();
        return a + b + c;
    }

    @Override
    public String toString() {
        return sourceName + " -> " + destName + ", " + algorithmName;
    }

}
