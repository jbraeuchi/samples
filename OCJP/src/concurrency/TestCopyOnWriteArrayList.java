package concurrency;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jakob on 11.10.2015.
 */
public class TestCopyOnWriteArrayList {

    public static void main(String[] args) {

        List<String> list = new CopyOnWriteArrayList<>();

        // Elemente addieren
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            new Thread() {
                public void run() {
                    while (true) {
                        list.add("Item-" + finalI);
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(list.size());
                    }
                }
            }.start();
        }

        // Elemente löschen ein wenig langsamer
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            new Thread() {
                public void run() {
                    while (true) {
                        list.remove("Item-" + finalI);
                        try {
                            sleep(110);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(list.size());
                    }
                }
            }.start();
        }

    }

}
