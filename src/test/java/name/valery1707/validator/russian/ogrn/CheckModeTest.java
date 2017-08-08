package name.valery1707.validator.russian.ogrn;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckModeTest {
	@Test
	public void nullValueIsNotValid() throws Exception {
		for (Ogrn.CheckMode mode : Ogrn.CheckMode.values()) {
			assertThat(mode.isValid(null)).isFalse();
		}
	}
}