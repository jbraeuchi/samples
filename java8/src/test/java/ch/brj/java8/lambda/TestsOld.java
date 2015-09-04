package ch.brj.java8.lambda;

import org.junit.Test;

/**
 * @author jakob
 */
public class TestsOld {

	@Test
	public void test1() {

		MyFunction add = new MyFunction() {
			@Override
			public int op(int a, int b) {
				return a + b;
			}
		};

		MyFunction subtract = new MyFunction() {
			@Override
			public int op(int a, int b) {
				return a - b;
			}
		};

		MyFunction multiply = new MyFunction() {
			@Override
			public int op(int a, int b) {
				return a * b;
			}
		};

		MyFunction divide = new MyFunction() {
			@Override
			public int op(int a, int b) {
				return a / b;
			}
		};

		MyFunction magic = new MyFunction() {
			@Override
			public int op(int a, int b) {
				return a * a + b * b;
			}
		};

		System.out.println(exec(2, 3, add));
		System.out.println(exec(2, 3, subtract));
		System.out.println(exec(2, 3, multiply));
		System.out.println(exec(12, 3, divide));
		System.out.println(exec(2, 3, magic));
	}

	private int exec(int a, int b, MyFunction f) {
		return f.op(a, b);
	}

	public interface MyFunction {
		int op(int a, int b);
	}
}
