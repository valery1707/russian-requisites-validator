package name.valery1707.validator;

import org.junit.Test;

public class StringUtilsTest {
	@Test(expected = IllegalStateException.class)
	public void create() throws Exception {
		new StringUtils();
	}
}
