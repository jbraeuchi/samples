package concurrency;

import java.util.concurrent.RecursiveTask;

/**
 * Created by jakob on 28.09.2015.
 */
public class Fibonacci2 extends RecursiveTask<Integer> {

    final int n;

    Fibonacci2(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        Fibonacci2 f1 = new Fibonacci2(n - 1);
        Fibonacci2 f2 = new Fibonacci2(n - 2);
        // invokeAll, join
        invokeAll(f1, f2);
        Integer res1 = f1.join();
        Integer res2 = f2.join();

        return res1 + res2;
    }

    public static void main(String args[]) {
        int count = 40;
        Fibonacci2 f = new Fibonacci2(count);

        long t1 = System.currentTimeMillis();
        Integer res = f.compute();
        long t2 = System.currentTimeMillis();

        String str = String.format("Fibonacci2 %d: %d, time: %d ms", count, res, (t2 - t1));
        System.out.println(str);
    }
}
