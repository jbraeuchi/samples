package ch.brj.java8.intf;

public class ServiceImpl implements Service {

	@Override
	public String function1(String s) {
		return "Function 1:" + s;
	}

//	@Override
//	public String function2(String s) {
//		return "override 2 : " + s;
//	}
}
