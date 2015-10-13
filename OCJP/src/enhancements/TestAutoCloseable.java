package enhancements;

import java.io.IOException;

public class TestAutoCloseable implements AutoCloseable {

    private String name;

    public TestAutoCloseable(String name) {
        this.name = name;
    }

    // never called, not required by AutoCloseable !
    public void open() throws IOException {
        System.out.println("Opening: " + name);
        throw new IOException("ex in open: " + name);
    }

    public String read() throws IOException {
        System.out.println("Reading: " + name);
        throw new IOException("ex in read: " + name);
//        return "";
    }

    public void close() {
        System.out.println("Closing: " + name);
        throw new RuntimeException("ex in close: " + name);  // -> Add to Suppressed Exception
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Try with Resources");
        try (
                TestAutoCloseable ac1 = new TestAutoCloseable("AC1");
                TestAutoCloseable ac2 = new TestAutoCloseable("AC2")) {

            ac1.read();
        }
        catch (Exception e) {
            System.out.println("ex: " + e.getMessage());
            System.out.println("suppressed: "+ e.getSuppressed().length);
            throw e;
        }
    }
} 
