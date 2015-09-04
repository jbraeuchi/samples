package ch.brj.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author jakob
 */
public class Tests {

	@Test
	public void testCreate() {
		Stream<String> stream1 = Stream.of("Dagobert", "Donald", "Daisy");
		
		List<String> stringList = Arrays.asList("Daniel", "Dagobert", "Donald", "Daisy");
		Stream<String> stream2 = stringList.stream();
		
//		System.out.println(stream1.count());
//		System.out.println(stream2.count());
		
		Assert.assertTrue(stream1.allMatch(e -> e.startsWith("D")));
		Assert.assertTrue(stream2.anyMatch(e -> e.startsWith("Do")));
	}

	@Test
	public void testGrouping() {
		Stream<String> stream1 = Stream.of("Dagobert", "Donald", "Daisy", "Tick", "Trick", "Track");
		
		Map<Object, List<String>> map = stream1.collect(Collectors.groupingBy(e -> e.charAt(1)));
		System.out.println(map);
	}

	@Test
	public void testFilter() {
		List<String> stringList = Arrays.asList("Dagobert", "Donald", "Daisy");

		stringList.stream()
			.filter(e -> e.startsWith("Da"))
			.forEach(e -> System.out.println(e));
	}

	@Test
	public void testCollect() {
		List<String> stringList = Arrays.asList("Daniel", "Dagobert", "Donald", "Daisy");

		List<String> filteredStrings = stringList.stream()
			.filter(e -> e.startsWith("Da"))
			.map(e -> e.toUpperCase())
			.sorted()
			.collect(Collectors.toList());

		System.out.println(filteredStrings);
	}

	@Test
	public void testMap() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		// multiply even elements > 3 by 2 and calculate sum
		int result = numbers.stream()
			.filter(e -> e > 3)
			.filter(e -> e % 2 == 0)
			.mapToInt(e -> e * 2)
			.sum();

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
		int result = numbers.stream()
			.filter(this::doFilter300)
			.filter(this::doFilterEven)
			.mapToInt(Tests::doMultiply)
			.sum();
		long t2 = System.currentTimeMillis();

		System.out.println("testMapSlow : " + result + " duration : " + (t2-t1));
	}
	
	private boolean doFilter300(Integer e) {
		return (e > 300);
	}
	
	private boolean doFilterEven(Integer e) {
		return (e % 2 == 0);
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
