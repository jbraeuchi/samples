package ch.brj.java8.intf;

public class OldServiceImpl implements OldService {

	@Override
	public String function1(String s) {
		return "Old Function 1:" + s;
	}

}
