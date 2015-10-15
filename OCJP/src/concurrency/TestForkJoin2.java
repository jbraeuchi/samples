package concurrency;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * Zahlen im Array verdoppeln
 */
public class TestForkJoin2 extends RecursiveAction {

    final int[] data;
    final int lo;
    final int hi;


    TestForkJoin2(int[] data) {
        this(data, 1, data.length);
     }


    TestForkJoin2(int[] data, int lo, int hi) {
        System.out.println("> " + lo + " - " + hi);
        this.data = data;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected void compute() {
        if (lo == hi) {
            data[hi - 1] = data[hi - 1] * 2;
            return;
        }

        int mid = (lo + hi) / 2;
        //        mid = (lo + hi) >>> 1;
        System.out.println(lo + " - " + hi + " : " + mid);

        TestForkJoin2 f1 = new TestForkJoin2(data, lo, mid);
        TestForkJoin2 f2 = new TestForkJoin2(data, mid + 1, hi);
        invokeAll(f1, f2);
    }

    public static void main(String args[]) {
        int data[] = new int[]{1, 12, 3, 4, 25, 6, 37, 8, 9, 10};
        TestForkJoin2 f = new TestForkJoin2(data);

        long t1 = System.currentTimeMillis();
        f.compute();
        long t2 = System.currentTimeMillis();

        String str = String.format("TestForkJoin2 : %s, time: %d ms",  Arrays.toString(data), (t2 - t1));
        System.out.println(str);
    }

}
