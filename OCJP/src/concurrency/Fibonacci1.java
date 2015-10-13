package concurrency;

import java.util.concurrent.RecursiveTask;

/**
 * Created by jakob on 28.09.2015.
 */
public class Fibonacci1 extends RecursiveTask<Integer> {

    final int n;

    Fibonacci1(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        Fibonacci1 f1 = new Fibonacci1(n - 1);
        Fibonacci1 f2 = new Fibonacci1(n - 2);
        // fork, compute, join !
        f1.fork();
        Integer res2 = f2.compute();
        Integer res1 = f1.join();

        return res1 + res2;
    }

    public static void main(String args[]) {
        int count = 40;
        Fibonacci1 f = new Fibonacci1(count);

        long t1 = System.currentTimeMillis();
        Integer res = f.compute();
        long t2 = System.currentTimeMillis();

        String str = String.format("Fibonacci1 %d: %d, time: %d ms", count, res, (t2 - t1));
        System.out.println(str);
    }
}
