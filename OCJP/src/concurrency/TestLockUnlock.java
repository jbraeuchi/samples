package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jakob on 20.10.2015.
 */

//In a re-entrant lock, you can acquire the same lock again. However,
//you need to release that lock the same number of times
public class TestLockUnlock {
    public static void main(String []args) {
        Lock lock = new ReentrantLock();
        try {
            System.out.println("Lock 1 ");
            lock.lock();
            System.out.println("Critical section 1 ");
            System.out.println("Lock 2 ");
            lock.lock(); // LOCK_2
            System.out.println("Critical section 2 ");
        } finally {
            lock.unlock();
            System.out.println("Unlock 2 ");
            lock.unlock(); // UNLOCK_1
            System.out.println("Unlock 1 ");
//            lock.unlock(); // additional unlock will fail
//            System.out.println("Additional unlock");
        }
    }
}


