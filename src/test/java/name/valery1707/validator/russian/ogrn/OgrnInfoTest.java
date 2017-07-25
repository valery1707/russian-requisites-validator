package name.valery1707.validator.russian.ogrn;

import org.junit.Test;

import static name.valery1707.validator.russian.ogrn.OgrnInfo.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class OgrnInfoTest {

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
		parse("qwerty1234567");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalid_crc() throws Exception {
		parse("1234567890123");
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
		assertThat(info.isJuridical()).isTrue();
		assertThat(info.isIndividual()).isFalse();
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
		assertThat(info.isJuridical()).isFalse();
		assertThat(info.isIndividual()).isTrue();
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

	@Test
	public void testEquals() throws Exception {
		OgrnInfo info = new OgrnInfo(1, 2, 3, 4, 5);
		assertThat(info).isEqualTo(info);
		assertThat(info).isNotEqualTo(new OgrnInfo(9, 2, 3, 4, 5));
		assertThat(info).isNotEqualTo(new OgrnInfo(1, 20, 3, 4, 5));
		assertThat(info).isNotEqualTo(new OgrnInfo(1, 2, 30, 4, 5));
		assertThat(info).isNotEqualTo(new OgrnInfo(1, 2, 3, 40, 5));
		assertThat(info).isNotEqualTo(new OgrnInfo(1, 2, 3, 4, 50));
		assertThat(info).isNotEqualTo(info.format());
		assertThat(info.format()).isEqualTo(info.format());
		assertThat(info).isEqualTo(new OgrnInfo(1, 2, 3, 4, 5));
		assertThat(new OgrnInfo(1, 2, 3, 0, 5)).isNotEqualTo(new OgrnInfo(1, 2, 3, 5));
	}

	@Test
	@SuppressWarnings("RedundantCast")
	public void testHashCode() throws Exception {
		assertThat(new OgrnInfo((int) 1, 1, 1, 0, 1).hashCode()).isNotEqualTo(new OgrnInfo((int) 1, 1, 1, 1).hashCode());
	}
}
