import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

    private ArrayList<EncryptionDetails> paramsList = new ArrayList<EncryptionDetails>();

    public ArrayList<EncryptionDetails> getParamsList() {
        return paramsList;
    }

    private Stack<String> elementStack = new Stack<String>();
    private Stack<Object> objectStack = new Stack<Object>();

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        // push element
        elementStack.push(qName);
        // push object
        if ("encryptionDetails".equals(qName)) {
            EncryptionDetails p = new EncryptionDetails();
            p.setIsDirectory("true".equals(attributes.getValue(0)));
            objectStack.push(p);
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        elementStack.pop();

        if ("encryptionDetails".equals(qName)) {
            EncryptionDetails p = (EncryptionDetails) objectStack.pop();
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
        if ("encryptionAlgorithm".equals(currentElement())) {
                ((EncryptionDetails) objectStack.peek()).setEncryptionAlgorithm(value);
        } else if (("sourcePath".equals(currentElement()))) {
            ((EncryptionDetails) objectStack.peek()).setSourcePath(value);
        } else if ("destPath".equals(currentElement())) {
            ((EncryptionDetails) objectStack.peek()).setDestPath(value);

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
