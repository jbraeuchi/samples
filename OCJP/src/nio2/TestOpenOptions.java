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
        // will NOT overwrite existing contents
        OpenOption[] opts1 = new OpenOption[]{StandardOpenOption.CREATE};

        // java.nio.file.FileAlreadyExistsException if file exists
        OpenOption[] opts2 = new OpenOption[]{StandardOpenOption.CREATE_NEW};

        // java.lang.IllegalArgumentException: READ not allowed
        OpenOption[] opts3 = new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.READ};

        // will overwrite existing contents
        OpenOption[] opts4 = new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};

        Path writeFile = Paths.get("c:\\temp\\test.txt");
        BufferedWriter br = Files.newBufferedWriter(writeFile, Charset.forName("UTF-8"),opts4);
        br.write("This text file is created using Path API 1");
        br.flush();
        br.close();
    }
}
