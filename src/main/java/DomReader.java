import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DomReader implements XMLParamsReader {

    ArrayList<EncryptionParams> paramList = new ArrayList<EncryptionParams>();

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

        NodeList processes = null;
        EncryptionParams param = null;
        Boolean validElem = false;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            validElem = false;

            if (node instanceof Element) {
                Element child = (Element) node;
                if ("DirectoryProcess".equals(child.getNodeName())) {
                    param = new EncryptionParams();
                    param.setIsDirectory(true);
                    validElem = true;
                } else if ("FileProcess".equals(child.getNodeName())) {
                    param = new EncryptionParams();
                    param.setIsDirectory(false);
                    validElem = true;
                }
                if (validElem) {
                    processes = child.getChildNodes();
                    for (int j = 0; j < processes.getLength(); j++) {
                        try {
                            if ("Algorithm".equals(processes.item(j)
                                    .getNodeName())) {
                                param.setEncryptionAlgorithm(XMLHelper
                                        .getAlgorithmByName(processes.item(j)
                                                .getNodeValue()));
                            } else if ("SourceDirectory".equals(processes.item(
                                    j).getNodeName())) {
                                param.setSourcePath(processes.item(j)
                                        .getNodeValue());
                            } else if ("SourceFile".equals(processes.item(j)
                                    .getNodeName())) {
                                param.setSourcePath(processes.item(j)
                                        .getNodeValue());
                            } else if ("DestinationFile".equals(processes.item(
                                    j).getNodeName())) {
                                param.setDestPath(processes.item(j)
                                        .getNodeValue());
                            }
                        } catch (DOMException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //
                    paramList.add(param);
                }
            }

        }

    }

    public ArrayList<EncryptionParams> getEncryptionParamList() {
        return paramList;
    }

}
