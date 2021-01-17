
import ch.brj.jaxb.xsd.sample2.ObjectFactory;
import ch.brj.jaxb.xsd.sample2.PurchaseOrderType;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBElement;

public class TestSample2 {

    @Test
    void test() {
        ObjectFactory of = new ObjectFactory();
        PurchaseOrderType po = of.createPurchaseOrderType();
    }

}
