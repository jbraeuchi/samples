package concurrency;

import java.util.concurrent.RecursiveTask;

/**
 * Zahlen von lo bis hi addieren
 */
public class TestForkJoin1 extends RecursiveTask<Integer> {

    final int lo;
    final int hi;

    TestForkJoin1(int lo, int hi) {
        System.out.println("> " + lo + " - " + hi);
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected Integer compute() {
        if (lo == hi) {
            return lo;
        }

        int mid = (lo + hi) / 2;
//        mid = (lo + hi) >>> 1;
        System.out.println(lo + " - " + hi + " : " + mid);

        TestForkJoin1 f1 = new TestForkJoin1(lo, mid);
        TestForkJoin1 f2 = new TestForkJoin1(mid + 1, hi);
        // fork, compute, join !
        f1.fork();
        Integer res2 = f2.compute();
        Integer res1 = f1.join();

        return res1 + res2;
    }

    public static void main(String args[]) {
        int from = 1;
        int to = 10;
        TestForkJoin1 f = new TestForkJoin1(from, to);

        long t1 = System.currentTimeMillis();
        Integer res = f.compute();
        long t2 = System.currentTimeMillis();

        String str = String.format("TestForkJoin1 %d, %d: %d, time: %d ms", from, to, res, (t2 - t1));
        System.out.println(str);
    }
}
