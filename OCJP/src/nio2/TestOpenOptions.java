package nio2;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.*;

/**
 * Created by jakob on 18.10.2015.
 */
public class TestOpenOptions {
    public static void main(String args[]) throws Exception
    {
        OpenOption[] opts1 = new OpenOption[]{StandardOpenOption.CREATE};
        OpenOption[] opts2 = new OpenOption[]{StandardOpenOption.CREATE_NEW}; //  java.nio.file.FileAlreadyExistsException

        OpenOption[] opts3 = new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.READ}; // java.lang.IllegalArgumentException: READ not allowed

        Path writeFile = Paths.get("c:\\temp\\test.txt");
        BufferedWriter br = Files.newBufferedWriter(writeFile, Charset.forName("UTF-8"),opts2);
        br.write("This text file is created using Path API 1");
        br.flush();
        br.close();
    }
}
