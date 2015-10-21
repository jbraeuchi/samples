package nio2;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Iterator;

/**
 * Created by jakob on 05.10.2015.
 */
public class Tests {
    @Test
    public void testName() {
        Path p1 = Paths.get("c:/temp/sub/test.txt");

        System.out.println("root: " + p1.getRoot());
        System.out.println("names: " + p1.getNameCount());
        System.out.println("name 0: " + p1.getName(0));
        System.out.println("filename " + p1.getFileName());
    }

    @Test
    public void testSubpath() {
        Path p1 = Paths.get("c:/temp/sub/test.txt");

//        System.out.println("subpath 0, 0: " + p1.subpath(0, 0));
        System.out.println("subpath 0, 1: " + p1.subpath(0, 1));
        System.out.println("subpath 0, 2: " + p1.subpath(0, 2));
    }

    @Test
    public void testNormalize() throws IOException {
        Path p1 = Paths.get("c:\\temp\\.\\photos\\..\\readme.txt");
        Path p2 = Paths.get("c:/temp/readme.txt");
        Path p3 = Paths.get("c:/../temp/../temp/./readme.txt"); // erstes .. ignoriert

        System.out.println("normalize 1: " + p1.normalize());
        System.out.println("normalize 2: " + p2.normalize());
        System.out.println("normalize 3: " + p3.normalize());

        System.out.println("equals: " + p1.equals(p2)); // false
        System.out.println("equals: " + p1.normalize().equals(p2.normalize()));  // true

//      System.out.println("same: " + Files.isSameFile(p1, p2));
//      System.out.println("same: " + Files.isSameFile(p1.normalize(), p2.normalize()));
    }

    @Test
    public void testRoots1() {
        Iterable<Path> roots = FileSystems.getDefault().getRootDirectories();
        System.out.println("FileSystem.getRootDirectories");
        for (Path r : roots) {
            System.out.println(r);
        }
    }

    @Test
    public void testRoots2() {
        File[] roots = File.listRoots();   // nicht Files !
        System.out.println("File.listRoots");
        for (File r : roots) {
            System.out.println(r);
        }
    }

    @Test
    public void testDirectoryStream() {
        Path p1 = Paths.get("c:/temp");

        // prefix glob: nicht notwendig
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(p1, "*.*")) {
            Iterator<Path> it = ds.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testResolve() {
        Path p1 = Paths.get("c:/a/b");
        Path p2 = Paths.get("c/d.txt");

        System.out.println("resolve: " + p1.resolve(p2));
        System.out.println("resolveSibling: " + p1.resolveSibling(p2));
        System.out.println("resolve Parent: " + p1.getParent().resolve(p2));
    }

    @Test
    public void testRelativize() {
        Path p1 = Paths.get("c:/temp/sub/test.txt");
        Path p2 = Paths.get("c:/temp/test.txt");

        System.out.println("relativize 1: " + p1.relativize(p2));
        System.out.println("relativize 2: " + p2.relativize(p1));
    }

    @Test
    public void testRelativize1() {
        Path p1 = Paths.get("c:/test/sub/test.txt");
        Path p2 = Paths.get("c:/temp/test.txt");

        System.out.println("relativize 1: " + p1.relativize(p2));
        System.out.println("relativize 2: " + p2.relativize(p1));
    }

    @Test
    public void testRelativize2() {
        Path p1 = Paths.get("c:\\personal\\.\\photos\\..\\readme.txt");
        Path p2 = Paths.get("c:\\personal\\index.html");

        System.out.println("relativize 1: " + p1.relativize(p2));
        System.out.println("relativize 2: " + p2.relativize(p1));
    }

    @Test
    public void testRelativize3() {
        Path p1 = Paths.get("c:\\personal\\.\\photos\\..\\readme.txt");
        Path p2 = Paths.get("d:\\personal\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println("relativize 1: " + p3);   // IllegalArgumentException: 'other' has different root
    }

    @Test
    public void testCreateFile() {
        try {
            Path p1 = Paths.get("c:/temp/test.txt");
            Files.deleteIfExists(p1);
            Files.createFile(p1);

            BasicFileAttributeView fav = Files.getFileAttributeView(p1, BasicFileAttributeView.class);
            BasicFileAttributes attrs = fav.readAttributes();
            // oder direkt readAttributes
//            BasicFileAttributes attrs = Files.readAttributes(p1, BasicFileAttributes.class);
            System.out.println(attrs.creationTime());
            System.out.println(Files.isWritable(p1));

            // create overwrite
            File f = p1.toFile();
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateHidden() {
        try {
            Path p1 = Paths.get("c:/temp/test.txt");
            Files.deleteIfExists(p1);
            Files.createFile(p1);
            Files.setAttribute(p1, "dos:hidden", true);

            DosFileAttributes attrs = Files.readAttributes(p1, DosFileAttributes.class);
            // oder lesen über FileAtttributesView
//            DosFileAttributeView fav = Files.getFileAttributeView(p1, DosFileAttributeView.class);
//            DosFileAttributes attrs = fav.readAttributes();

            System.out.println(attrs.creationTime());
            System.out.println(Files.isWritable(p1));
            System.out.println(attrs.isHidden());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
