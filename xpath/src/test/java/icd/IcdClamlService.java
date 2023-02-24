package icd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class IcdClamlService {
    private static final String CLAML_DE_FILEPATH = "/icd/icd10gm2023syst_claml_20220916.xml";
    private static final Logger LOGGER = LoggerFactory.getLogger(IcdClamlService.class);

    private Document clamlDe;

    void parseXml() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // never forget this!

            // dtd nicht lesen, getResourceAsStream() findet ClaML.dtd nicht
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            DocumentBuilder builder = factory.newDocumentBuilder();

            LOGGER.info("Parsing start");
            long t1 = System.currentTimeMillis();

            clamlDe = builder.parse(getClass().getResourceAsStream(CLAML_DE_FILEPATH));

            long t2 = System.currentTimeMillis();
            LOGGER.info("Parsing duration: {} ms", t2 - t1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<IcdClamlDTO> findByRubric(String sprache, String arg) {
        // Select Classes of kind=category where Rubric/Label contains arg.
        // Use translate for lowerCase
        String expression = String.format(
                "ClaML/Class[@kind='category']/Rubric[contains(translate(Label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'%s')]/ancestor::Class",
                arg.toLowerCase());

        return findByExpression(sprache, expression);
    }

    public List<IcdClamlDTO> findByCode(String sprache, String arg) {
        // Select Classes of kind=category where code contains arg.
        String expression = String.format("ClaML/Class[@kind='category' and contains(@code,'%s')]", arg.toUpperCase());

        List<IcdClamlDTO> result = findByExpression(sprache, expression);
        return result;
    }

    public List<IcdClamlDTO> findByExpression(String sprache, String expression) {
        //Create XPath
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        try {
            long t1 = System.currentTimeMillis();
            XPathExpression expr = xpath.compile(expression);

            Object result = expr.evaluate(getDocument(sprache), XPathConstants.NODESET);
            long t2 = System.currentTimeMillis();
            LOGGER.info("Searching duration: {} ms, expresssion='{}'", (t2 - t1), expression);

            return toClamlDTO((NodeList) result);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Document getDocument(String sprache) {
        return clamlDe;
    }

    private List<IcdClamlDTO> toClamlDTO(NodeList nodes) {
        LOGGER.info("Number of Nodes: {}", nodes.getLength());
        List<IcdClamlDTO> result = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            result.add(toClamlDTO(nodes.item(i)));
        }

        return result;
    }

    IcdClamlDTO toClamlDTO(Node classNode) {
        IcdClamlDTO result = new IcdClamlDTO();
        String code = classNode.getAttributes().getNamedItem("code").getNodeValue();
        String usage = "";

        Node usageNode = classNode.getAttributes().getNamedItem("usage");
        if (usageNode != null) {
            if ("aster".equals(usageNode.getNodeValue())) {
                usage = "*";
            } else if ("dagger".equals(usageNode.getNodeValue())) {
                usage = "+";
            } else if ("optional".equals(usageNode.getNodeValue())) {
                usage = "!";
            } else {
                usage = usageNode.getNodeValue();
            }
        }
        result.setCode(code);
        result.setUsage(usage);

        result.setSuperClass(getSuperClass(classNode));
        result.setSubClasses(getSubClasses(classNode));
        result.setRubrics(getRubrics(classNode));

        return result;
    }

    private String getSuperClass(Node classNode) {
        List<Node> nodes = getChildNodes(classNode, "SuperClass");
        if (nodes.isEmpty()) {
            return null;
        }

        return nodes.get(0).getAttributes().getNamedItem("code").getNodeValue();
    }

    private List<String> getSubClasses(Node classNode) {
        List<Node> nodes = getChildNodes(classNode, "SubClass");
        return nodes.stream() //
                .map(n -> n.getAttributes().getNamedItem("code").getNodeValue()) //
                .collect(Collectors.toList());
    }

    private List<IcdClamlRubricDTO> getRubrics(Node classNode) {
        List<Node> nodes = getChildNodes(classNode, "Rubric");
        return nodes.stream() //
                .map(n -> getRubricDTO(n)) //
                .collect(Collectors.toList());
    }

    private IcdClamlRubricDTO getRubricDTO(Node rubricNode) {
        IcdClamlRubricDTO result = new IcdClamlRubricDTO();

        String kind = rubricNode.getAttributes().getNamedItem("kind").getNodeValue();
        List<Node> labels = getChildNodes(rubricNode, "Label");
        String text = labels.stream().map(l -> l.getTextContent()).collect(Collectors.joining("; "));
        // text = rubricNode.getTextContent();

        result.setKind(kind);
        result.setText(text);

        return result;
    }

    private List<Node> getChildNodes(Node node, String nodeName) {
        List<Node> result = new ArrayList<>();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);

            if (nodeName.equals(child.getNodeName())) {
                result.add(child);
            }
        }

        return result;
    }

}
