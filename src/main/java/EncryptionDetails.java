import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EncryptionDetails {
    
    String encryptionAlgorithm;
    String sourcePath;
    String destPath;
    Boolean isDirectory;

    public EncryptionDetails(String encryptionAlgorithm,
                             String sourcePath, String destPath, Boolean isDirectory) {
        super();
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.sourcePath = sourcePath;
        this.destPath = destPath;
        this.isDirectory = isDirectory;
    }
    public EncryptionDetails() {
        destPath = null;
    }

    public IEncryptionAlgorithm<?> getEncryptionAlgorithmObj() {
        try {
            return XMLHelper.getAlgorithmByName(encryptionAlgorithm);
        } catch (Exception e) {
            return null;
        }
    }
    public void setEncryptionAlgorithm(IEncryptionAlgorithm<?> encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm.getClass().getSimpleName();
    }

    @XmlElement
    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @XmlElement
    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    @XmlAttribute
    public Boolean getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }
    
    @XmlElement
    public String getEncryptionAlgorithm(){
        return  encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm){ this.encryptionAlgorithm = encryptionAlgorithm; }
    
}
