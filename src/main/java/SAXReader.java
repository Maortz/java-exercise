import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class SAXReader implements XMLParamsReader {

    EncryptionParamList paramList = new EncryptionParamList();

    public void readXML(String path) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            InputStream xmlInput = new FileInputStream(path);

            SAXParser saxParser = factory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            saxParser.parse(xmlInput, handler);

            paramList.getEncryptionDetails().addAll(handler.getParamsList());
        } catch (Throwable err) {
            err.printStackTrace();
        }
    }

    public EncryptionParamList getEncryptionParamList() {
        return paramList;
    }


}
