package ch.brj.java8.lambda;

import org.junit.Test;

/**
 * @author jakob
 */
public class Tests {

	@Test
	public void test1() {

		MyFunction add = (a, b) -> a + b;
		MyFunction subtract = (a, b) -> a - b;
		MyFunction multiply = (a, b) -> a * b;
		MyFunction divide = (a, b) -> a / b;
		MyFunction magic = (a, b) -> a * a + b * b;
		MyFunction magic2 = (a, b) -> {
			return (a * a + b * b);
		};

		System.out.println(exec(2, 3, add));
		System.out.println(exec(2, 3, subtract));
		System.out.println(exec(2, 3, multiply));
		System.out.println(exec(12, 3, divide));
		System.out.println(exec(2, 3, magic));
		System.out.println(exec(2, 3, magic2));
	}

	private int exec(int a, int b, MyFunction f) {
		return f.op(a, b);
	}

	@FunctionalInterface
	public interface MyFunction {
		int op(int a, int b);
	}
}
