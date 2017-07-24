package name.valery1707.validator.russian.ogrn;

import org.junit.Test;

import static name.valery1707.validator.russian.ogrn.OgrnInfo.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class OgrnInfoTest {

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
}
