import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class EncryptionParamList {
    private ArrayList<EncryptionDetails> encryptionDetails;

    public EncryptionParamList() {
        encryptionDetails = new ArrayList<EncryptionDetails>();
    }

    public EncryptionParamList(ArrayList<EncryptionDetails> paramList) {
        this.encryptionDetails = paramList;
    }

    @XmlElement
    public ArrayList<EncryptionDetails> getEncryptionDetails() {
        return encryptionDetails;
    }

    public void setEncryptionDetails(ArrayList<EncryptionDetails> encryptionDetails) {
        this.encryptionDetails = encryptionDetails;
    }
}
