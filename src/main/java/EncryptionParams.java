
public class EncryptionParams {
    
    IEncryptionAlgorithm<?> encryptionAlgorithm;
    String sourcePath;
    String destPath;
    Boolean isDirectory;
    public EncryptionParams(IEncryptionAlgorithm<?> encryptionAlgorithm,
            String sourcePath, String destPath, Boolean isDirectory) {
        super();
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.sourcePath = sourcePath;
        this.destPath = destPath;
        this.isDirectory = isDirectory;
    }
    public EncryptionParams() {
        destPath = null;
    }
    public IEncryptionAlgorithm<?> getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }
    public void setEncryptionAlgorithm(IEncryptionAlgorithm<?> encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }
    public String getSourcePath() {
        return sourcePath;
    }
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
    public String getDestPath() {
        return destPath;
    }
    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }
    public Boolean getIsDirectory() {
        return isDirectory;
    }
    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }
    
    
    
}
