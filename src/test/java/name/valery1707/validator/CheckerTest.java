package name.valery1707.validator;

import org.junit.Test;

public class CheckerTest {
	@Test(expected = IllegalStateException.class)
	public void create() throws Exception {
		new Checker();
	}
}
