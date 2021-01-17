import ch.brj.jaxb.xsd.sample1.ObjectFactory;
import ch.brj.jaxb.xsd.sample1.Shiporder;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class TestSample1 {

    @Test
    void test() throws JAXBException {
        ObjectFactory of = new ObjectFactory();

        Shiporder.Shipto shipTo = of.createShiporderShipto();
        shipTo.setCity("The City");
        Shiporder so = of.createShiporder();
        so.setOrderperson("The Person");
        so.setShipto(shipTo);

        JAXBContext jaxbContext = JAXBContext.newInstance(Shiporder.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(so, writer);

        System.out.println(writer.toString());
    }

}
