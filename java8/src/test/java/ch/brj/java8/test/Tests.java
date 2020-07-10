package ch.brj.java8.test;

import org.junit.Test;

import java.util.*;

public class Tests {
    @Test
    public void testSort1() {
        MyList<String> list = new MyList();
        list.addAll(Arrays.asList("Daniel", "Dagobert", "Donald", "Daisy"));

        Collections.sort(list);
        System.out.println(list);

        Collections.sort(list);
        System.out.println(list);

        Collections.sort(list);
        System.out.println(list);
    }

    @Test
    public void testSort2() {
        MyList<String> list = new MyList();
        list.addAll(Arrays.asList("Daniel", "Dagobert", "Donald", "Daisy"));
        Comparator comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };

        list.sort(comparator);
        System.out.println(list);

        list.sort(comparator);
        System.out.println(list);

        list.sort(comparator);
        System.out.println(list);

        list.sort(null);
        System.out.println(list);
    }

    public static class MyList<E> extends ArrayList<E> {
        @Override
        public String toString() {
            return super.toString() + " modCount=" + modCount;
        }
    }
}
