import java.util.ArrayList;

public interface XMLParamsReader {
    
    void readXML(String path);
    
    ArrayList<EncryptionParams> getEncryptionParamList();
}
