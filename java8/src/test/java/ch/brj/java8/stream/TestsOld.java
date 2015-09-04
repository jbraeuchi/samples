package ch.brj.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 *
 * @author jakob
 */
public class TestsOld {

	@Test
	public void testFilter() {
		List<String> stringList = Arrays.asList("Dagobert", "Donald", "Daisy");

		for (String s : stringList) {
			if (s.startsWith("Da")) {
				System.out.println(s);
			}
		}
	}

	@Test
	public void testCollect() {
		List<String> stringList = Arrays.asList("Daniel", "Dagobert", "Donald", "Daisy");
		List<String> filteredStrings = new ArrayList<String>();

		for (String s : stringList) {
			if (s.startsWith("Da")) {
				filteredStrings.add(s.toUpperCase());
			}
		}
		
		// Sorting ?
		
		System.out.println(filteredStrings);
	}

	@Test
	public void testMap() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		// multiply even elements > 3 by 2 and calculate sum
		int result = 0;
		for (Integer n : numbers) {
			if ((n > 3) && (n % 2 == 0)) {
				result = result + (2 * n);
			}
		}

		System.out.println("testMap : " + result);
	}
	
	@Test
	public void testMapSlow() {
		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = 1; i < 1000; i++) {
			numbers.add(i);
		}

		long t1 = System.currentTimeMillis();
		// multiply even elements > 300 by 2 and calculate sum
		int result = 0;
		for (Integer n : numbers) {
			if ((n > 300) && (n % 2 == 0)) {
				result = result + doMultiply(n);
			}
		}
		long t2 = System.currentTimeMillis();

		System.out.println("testMapSlow : " + result + " duration : " + (t2-t1));
	}
	
	private static Integer doMultiply(Integer e) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return (e * 2);
	}
}
