package khanhnqph27525.fpoly.assignment_nangcao.Activity_News;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PhanTichXML {
    public Document getDocument(String str) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }
        InputSource inputSource = new InputSource();
        inputSource.setCharacterStream(new StringReader(str));
        inputSource.setEncoding("UTF-8");
        document = builder.parse(inputSource);
        return document;
    }
    public String getValue(Element node, String name){
        String kq = "";
        NodeList nodeList = node.getElementsByTagName(name);
        kq = getTextOfNode(nodeList.item(0));
        return kq;
    }

    private String getTextOfNode(Node item) {
        Node nodeChild;
        if (item != null){
            if (item.hasChildNodes()){
                for (nodeChild = item.getFirstChild();nodeChild!=null;nodeChild = nodeChild.getNextSibling()){
                    if (nodeChild.getNodeType() == Node.TEXT_NODE) {
                        return nodeChild.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
