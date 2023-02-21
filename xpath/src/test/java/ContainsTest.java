package xpath;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

public class ContainsTest {

    @Test
    void test() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(getClass().getResourceAsStream("/example.xml"));

        // Contains findet den Match nur auf dem ersten Departement ???
        String expr1 = "employees/employee[department/name='HR']";
        String expr2 = "employees/employee[contains(department/name,'HR')]";

        String expr3 = "employees/employee[department/name='Special HR']";
        String expr4 = "employees/employee[contains(department/name,'Special HR')]";

        // Contains auf dem untersten Element und dann den Ancestor lesen
        String expr5 = "employees/employee/department[contains(name,'HR')]/ancestor::employee";
        String expr6 = "//department[contains(name,'HR')]/ancestor::employee";

        evaluate(doc, expr1);
        evaluate(doc, expr2);
        evaluate(doc, expr3);
        evaluate(doc, expr4);
        evaluate(doc, expr5);
        evaluate(doc, expr6);
    }

    private void evaluate(Document doc, String query) throws XPathExpressionException {
        System.out.println("Query: " + query);
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        XPathExpression expression = xpath.compile(query);
        Object result = expression.evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList = (NodeList) result;

        System.out.println("Number of Nodes: " + nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            System.out.println("Node= " + n.getTextContent());
        }
    }
}