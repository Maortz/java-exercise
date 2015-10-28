import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.*;

public class SAXReader implements XMLParamsReader {

    ArrayList<EncryptionParams> paramList = new ArrayList<EncryptionParams>();

    public void readXML(String path) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            InputStream xmlInput = new FileInputStream("data\\sax-example.xml");

            SAXParser saxParser = factory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            saxParser.parse(xmlInput, handler);

            paramList = handler.getParamsList();
        } catch (Throwable err) {
            err.printStackTrace();
        }
    }

    public ArrayList<EncryptionParams> getEncryptionParamList() {
        return paramList;
    }


}
