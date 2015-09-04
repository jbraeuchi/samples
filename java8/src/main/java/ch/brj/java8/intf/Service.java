package ch.brj.java8.intf;

public interface Service {

	String function1(String s);

	default String function2(String s) {
		return "Default " + s;
	}
	
	static String function3(String s) {
		return "Static " + s;
	}
}
