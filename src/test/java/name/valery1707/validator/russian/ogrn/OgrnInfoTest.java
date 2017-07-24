package name.valery1707.validator.russian.ogrn;

import name.valery1707.validator.russian.inn.InnInfo;
import org.junit.Test;

import static name.valery1707.validator.russian.ogrn.OgrnInfo.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class OgrnInfoTest {

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
		InnInfo.parse("qwerty1234567");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalid_crc() throws Exception {
		InnInfo.parse("1234567890123");
	}

	@Test
	public void testParseValid_juridical() throws Exception {
		OgrnInfo info = parse("1037739010891");
		assertThat(info).isNotNull();
		assertThat(info.getType()).isEqualTo((byte) 1);
		assertThat(info.getYear()).isEqualTo((byte) 3);
		assertThat(info.getSubject()).isEqualTo((byte) 77);
		assertThat(info.getTax()).isEqualTo((byte) 39);
		assertThat(info.getId()).isEqualTo(1089);
	}

	@Test
	public void testParseValid_individual() throws Exception {
		OgrnInfo info = parse("304500116000157");
		assertThat(info).isNotNull();
		assertThat(info.getType()).isEqualTo((byte) 3);
		assertThat(info.getYear()).isEqualTo((byte) 4);
		assertThat(info.getSubject()).isEqualTo((byte) 50);
		assertThat(info.getTax()).isEqualTo((byte) 0);
		assertThat(info.getId()).isEqualTo(11600015);
	}

	@Test
	public void testFormat() throws Exception {
		assertThat(parse("1037739010891").format()).isEqualTo("1037739010891");
		assertThat(parse("304500116000157").format()).isEqualTo("304500116000157");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_type_min() throws Exception {
		new OgrnInfo(-1, 1, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_type_max() throws Exception {
		new OgrnInfo(100, 1, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_year_min() throws Exception {
		new OgrnInfo(1, -1, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_year_max() throws Exception {
		new OgrnInfo(1, 100, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_subject_min() throws Exception {
		new OgrnInfo(1, 1, -1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_subject_max() throws Exception {
		new OgrnInfo(1, 1, 100, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_tax_min() throws Exception {
		new OgrnInfo(1, 1, 1, -1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_tax_max() throws Exception {
		new OgrnInfo(1, 1, 1, 100, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_id_min() throws Exception {
		new OgrnInfo(1, 1, 1, 1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrn_id_max() throws Exception {
		new OgrnInfo(1, 1, 1, 1, 99999 + 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrnip_type_min() throws Exception {
		new OgrnInfo(-1, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrnip_type_max() throws Exception {
		new OgrnInfo(100, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrnip_year_min() throws Exception {
		new OgrnInfo(1, -1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrnip_year_max() throws Exception {
		new OgrnInfo(1, 100, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrnip_subject_min() throws Exception {
		new OgrnInfo(1, 1, -1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrnip_subject_max() throws Exception {
		new OgrnInfo(1, 1, 100, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrnip_id_min() throws Exception {
		new OgrnInfo(1, 1, 1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOgrnip_id_max() throws Exception {
		new OgrnInfo(1, 1, 1, 999999999 + 1);
	}
}
