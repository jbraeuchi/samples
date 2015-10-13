package nio2;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by jakob on 12.10.2015.
 */
public class DirWatcher {
    public static void main(String[] args) {
        Path path = Paths.get("c:/temp");
        WatchService ws = null;
        try {
            ws = FileSystems.getDefault().newWatchService();
            path.register(ws, ENTRY_CREATE, ENTRY_DELETE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Watching: " + path);

        for (; ; ) {
            WatchKey key = null;

            try {
                key = ws.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WatchEvent<?>> events = key.pollEvents();
            for (WatchEvent<?> event : events) {
                switch (event.kind().name()) {
                    case "OVERFLOW":
                        System.out.println("overflow");
                        break;
                    case "ENTRY_CREATE":
                        System.out.println("Created: " + event.context());
                        break;
                    case "ENTRY_DELETE":
                        System.out.println("Deleted: " + event.context());
                        break;
                }
                key.reset();
             }
        }
    }
}