package enhancements;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Created by jakob on 06.10.2015.
 */
public class Tests {
    @Test
    public void testTryWithResources() {
        try (FileInputStream input = new FileInputStream("c:/temp/file.txt");
             BufferedInputStream bufferedInput = new BufferedInputStream(input)
        ) {
            int data = bufferedInput.read();
            while (data != -1) {
                System.out.print((char) data);
                data = bufferedInput.read();
                bufferedInput.close(); // Exception
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
