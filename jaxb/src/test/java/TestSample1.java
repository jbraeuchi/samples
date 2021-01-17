import ch.brj.jaxb.xsd.sample1.ObjectFactory;
import ch.brj.jaxb.xsd.sample1.Shiporder;
import org.junit.jupiter.api.Test;

public class TestSample1 {

    @Test
    void test() {
        ObjectFactory of = new ObjectFactory();
        Shiporder so = of.createShiporder();
    }

}
