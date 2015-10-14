package concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jakob on 14.10.2015.
 */
public class TestCondition {
    AtomicInteger count_put = new AtomicInteger();
    AtomicInteger count_take = new AtomicInteger();

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, size;

    public void put(Object x) throws InterruptedException {
        count_put.incrementAndGet();

        lock.lock();
        try {
            while (size == items.length) {
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++size;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        count_take.incrementAndGet();

        lock.lock();
        try {
            while (size == 0) {
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --size;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final TestCondition tc = new TestCondition();

        // Elemente addieren
        Thread put = new Thread() {
            public void run() {
                try {
                    while (true) {
                        tc.put(new Object());
                        sleep(100);
                        System.out.println(tc.size);
                    }
                } catch (InterruptedException e) {
                }
            }

        };
        put.start();

        // Elemente löschen ein wenig langsamer
        Thread take = new Thread() {
            public void run() {
                try {
                    while (true) {
                        tc.take();
                        sleep(110);
                        System.out.println(tc.size);
                    }
                } catch (InterruptedException e) {
                }
            }

        };
        take.start();

        try {
            Thread.sleep(10_000L);
            put.interrupt();
            take.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("count put: " + tc.count_put);
        System.out.println("count take: " + tc.count_take);
    }
}
