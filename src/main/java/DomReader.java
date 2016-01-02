import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DomReader implements XMLParamsReader {

    EncryptionParamList paramList = new EncryptionParamList();

    public void readXML(String path) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        Element elem = document.getDocumentElement();
        NodeList nodes = elem.getChildNodes();

        NodeList processes;
        EncryptionDetails param = null;
        Boolean validElem;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            validElem = false;

            if (node instanceof Element) {
                Element child = (Element) node;
                if ("encryptionDetails".equals(child.getNodeName())) {
                    param = new EncryptionDetails();
                    param.setIsDirectory("true".equals(child.getAttribute("isDirectory")));
                    validElem = true;
                }
                if (validElem) {
                    processes = child.getChildNodes();
                    for (int j = 0; j < processes.getLength(); j++) {
                        try {
                            if ("encryptionAlgorithm".equals(processes.item(j)
                                    .getNodeName())) {
                                param.setEncryptionAlgorithm(processes.item(j)
                                        .getFirstChild().getNodeValue());
                            } else if ("sourcePath".equals(processes.item(
                                    j).getNodeName())) {
                                param.setSourcePath(processes.item(j)
                                        .getFirstChild().getNodeValue());
                            } else if ("destPath".equals(processes.item(
                                    j).getNodeName())) {
                                param.setDestPath(processes.item(j)
                                        .getFirstChild().getNodeValue());
                            }
                        } catch (DOMException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //
                    paramList.getEncryptionDetails().add(param);
                }
            }

        }

    }

    public EncryptionParamList getEncryptionParamList() {
        return paramList;
    }

}
