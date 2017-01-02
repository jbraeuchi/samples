package ch.brj.java8.future;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by jakob on 02.01.2017.
 */
public class Tests {

    @Test
    public void test1() throws Exception {
        CompletableFuture<String> fHello = CompletableFuture.supplyAsync(() -> getHello(1000));
        CompletableFuture<String> fWorld = CompletableFuture.supplyAsync(() -> getWorld(0));

        CompletableFuture<String> future = fHello.thenCombine(fWorld, (s1, s2) -> s1 + " " + s2);

        String result = future.get();
        assertEquals("Hello World", result);
        System.out.println(result);
    }

    @Test
    public void test2() throws Exception {
        CompletableFuture<String> fHello = CompletableFuture.supplyAsync(() -> getHello(0));
        CompletableFuture<String> fWorld = CompletableFuture.supplyAsync(() -> getWorld(2000));

        CompletableFuture future = fHello.thenAcceptBoth(fWorld,
                (s1, s2) -> System.out.println(s1 + " " + s2));

        Object result = future.get();
        System.out.println(result);
    }

    @Test
    public void test3() throws Exception {
        CompletableFuture<String> fHello = CompletableFuture.supplyAsync(() -> getHello(1000));
        CompletableFuture<String> fWorld1 = CompletableFuture.supplyAsync(() -> getWorld(0));
        CompletableFuture<String> fWorld2 = CompletableFuture.supplyAsync(() -> getText("World!", 1000));

        CompletableFuture<Void> future = CompletableFuture.allOf(fHello, fWorld1, fWorld2);

        future.get();

        String result = Stream.of(fHello, fWorld1, fWorld2)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        assertEquals("Hello World World!", result);
        System.out.println(result);
    }


    String getText(String text, long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return text;
    }

    String getHello(long sleep) {
        return getText("Hello", sleep);
    }

    String getWorld(long sleep) {
        return getText("World", sleep);
    }
}