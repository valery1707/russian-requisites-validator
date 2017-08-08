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

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_tax_min() throws Exception {
		new KppInfo(-1, "00", 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_tax_max() throws Exception {
		new KppInfo(10000, "00", 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_purpose_null() throws Exception {
		new KppInfo(1, null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_purpose_pattern1() throws Exception {
		new KppInfo(1, "", 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_purpose_pattern2() throws Exception {
		new KppInfo(1, "--", 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_purpose_pattern3() throws Exception {
		new KppInfo(1, " 11 ", 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_id_min() throws Exception {
		new KppInfo(1, "00", 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_id_max() throws Exception {
		new KppInfo(1, "00", 1000);
	}
}
