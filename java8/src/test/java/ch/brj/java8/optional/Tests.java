package ch.brj.java8.optional;

import java.util.Optional;
import java.util.function.Consumer;

import org.junit.Test;

/**
 * @author jakob
 */
public class Tests {

	@Test
	public void testOrElse() {

		Optional<String> o1 = Optional.of("Test Optional");
		Optional<String> o2 = Optional.empty();
		Optional<Integer> o3 = Optional.ofNullable(4284);  // nullable
		Optional<Integer> o4 = Optional.empty();

		System.out.println(o1.orElse("o1 was Null"));
		System.out.println(o2.orElse("o2 was Null"));
		System.out.println(o3.orElse(-42));
		System.out.println(o4.orElse(-42));
	}

	@Test
	public void testIsPresent() {

		Optional<String> o1 = Optional.of("Test Optional");
		Optional<String> o2 = Optional.empty();

		System.out.println("o1 present: " + o1.isPresent());
		System.out.println("o2 present: " + o2.isPresent());
	}
	
	@Test
	public void testIfPresent() {

		Optional<String> o1 = Optional.of("Test Optional");
		Optional<String> o2 = Optional.empty();

		Consumer<String> consumer = new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println("Consumed: " + t);
			}
		};

		o1.ifPresent(consumer);
		o2.ifPresent(consumer);		
	}
	
	@Test
	public void testIfPresentLambda() {

		Optional<String> o1 = Optional.of("Test Optional");
		Optional<String> o2 = Optional.empty();

		o1.ifPresent(x -> System.out.println("Lambda Consumed: " + x));
		o2.ifPresent(x -> System.out.println("Lambda Consumed: " + x));
	}
}
