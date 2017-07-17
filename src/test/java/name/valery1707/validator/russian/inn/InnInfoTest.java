package name.valery1707.validator.russian.inn;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InnInfoTest {

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalid_null() throws Exception {
		InnInfo.parse(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalid_length() throws Exception {
		InnInfo.parse("123");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalid_format() throws Exception {
		InnInfo.parse("text");
	}

	@Test
	public void testParseValid_juridical() throws Exception {
		InnInfo info = InnInfo.parse("1181111110");
		assertThat(info).isNotNull();
		assertThat(info.getSubject()).isEqualTo((byte) 11);
		assertThat(info.getLocalTax()).isEqualTo((byte) 81);
		assertThat(info.getId()).isEqualTo(11111);
	}

	@Test
	public void testParseValid_physical() throws Exception {
		InnInfo info = InnInfo.parse("325507450247");
		assertThat(info).isNotNull();
		assertThat(info.getSubject()).isEqualTo((byte) 32);
		assertThat(info.getLocalTax()).isEqualTo((byte) 55);
		assertThat(info.getId()).isEqualTo(74502);
	}

	@Test
	public void testFormat() throws Exception {
		assertThat(InnInfo.parse("1181111110").format()).isEqualTo("1181111110");
		assertThat(InnInfo.parse("325507450247").format()).isEqualTo("325507450247");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_max_id_juridical() throws Exception {
		new InnInfo(1, 1, 99999 + 1, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreate_max_id_physical() throws Exception {
		new InnInfo(1, 1, 999999 + 1, false);
	}

	@Test
	public void testEquals() throws Exception {
		assertThat(new InnInfo(1, 1, 1, true)).isNotEqualTo(new InnInfo(2, 1, 1, true));
		assertThat(new InnInfo(1, 1, 1, true)).isNotEqualTo(new InnInfo(1, 2, 1, true));
		assertThat(new InnInfo(1, 1, 1, true)).isNotEqualTo(new InnInfo(1, 1, 2, true));
		assertThat(new InnInfo(1, 1, 1, true)).isNotEqualTo(new InnInfo(1, 1, 1, false));
	}
}