import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

    private ArrayList<EncryptionParams> paramsList = new ArrayList<EncryptionParams>();

    public ArrayList<EncryptionParams> getParamsList() {
        return paramsList;
    }

    private Stack<String> elementStack = new Stack<String>();
    private Stack<Object> objectStack = new Stack<Object>();

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        // push element
        elementStack.push(qName);
        // push object
        if ("DirectoryProcess".equals(qName) || "FileProcess".equals(qName)) {
            EncryptionParams p = new EncryptionParams();
            p.isDirectory = "DirectoryProcess".equals(qName);
            objectStack.push(p);
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        elementStack.pop();

        if ("DirectoryProcess".equals(qName) || "FileProcess".equals(qName)) {
            EncryptionParams p = (EncryptionParams) objectStack.pop();
            paramsList.add(p);
        }
    }

    public void characters(char ch[], int start, int length)
            throws SAXException {

        // getting value
        String value = new String(ch, start, length).trim();
        if (value.length() == 0)
            return;

        // processing info.
        if ("Algorithm".equals(currentElement())) {
            try {
                ((EncryptionParams) objectStack.peek()).encryptionAlgorithm = XMLHelper.getAlgorithmByName(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (("SourceDirectory".equals(currentElement()))
                || ("SourceFile".equals(currentElement()))) {
            ((EncryptionParams) objectStack.peek()).sourcePath = value;
        } else if ("DestinationFile".equals(currentElement())) {
            ((EncryptionParams) objectStack.peek()).destPath = value;

        }
    }

    private String currentElement() {
        return this.elementStack.peek();
    }

    private String currentElementParent() {
        if (this.elementStack.size() < 2)
            return null;
        return this.elementStack.get(this.elementStack.size() - 2);
    }

}
