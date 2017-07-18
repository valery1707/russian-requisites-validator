package name.valery1707.validator.russian.kpp;

import org.junit.Test;

import static name.valery1707.validator.russian.kpp.KppInfo.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class KppInfoTest {

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalid_null() throws Exception {
		parse(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalid_length() throws Exception {
		parse("123");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalid_format() throws Exception {
		parse("text");
	}

	@Test
	public void testFormat() throws Exception {
		assertThat(parse("773301001").format()).isEqualTo("773301001");
		assertThat(parse("773399001").format()).isEqualTo("773399001");
		assertThat(parse("7733AZ001").format()).isEqualTo("7733AZ001");
	}
}
