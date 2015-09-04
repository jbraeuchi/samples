package ch.brj.java8.intf;

import org.junit.Test;

/**
 *
 * @author jakob
 */
public class Tests {

    @Test
    public void testDefault() {
        Service s = new ServiceImpl();

        System.out.println(s.function1("Test 1"));
        System.out.println(s.function2("Test 1"));
    }
    
    @Test
    public void testStatic() {
        Service s = new ServiceImpl();

        System.out.println(s.function1("Test 2"));
        System.out.println(Service.function3("Test 2"));
    }
}
