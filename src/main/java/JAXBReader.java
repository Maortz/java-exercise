import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class JAXBReader implements XMLParamsReader{

    EncryptionParamList paramList = new EncryptionParamList();

    public void readXML(String path) {
        JAXBContext jaxbContext = null;
        Unmarshaller jaxbUnmarshaller=null;
        EncryptionParamList list = null;
        try {
            jaxbContext = JAXBContext.newInstance(EncryptionParamList.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            list = (EncryptionParamList) jaxbUnmarshaller.unmarshal(new FileInputStream(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        paramList = list;

    }

    public EncryptionParamList getEncryptionParamList() {
        return  paramList;
    }

    public void writeXML(EncryptionParamList encryptionParamList, String fileName) throws JAXBException, FileNotFoundException {
        JAXBContext contextObj = JAXBContext.newInstance(EncryptionParamList.class);

        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshallerObj.marshal(encryptionParamList, new FileOutputStream(fileName));
    }

}
