package name.valery1707.validator.russian.inn;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckModeTest {
	@Test
	public void nullValueIsNotValid() throws Exception {
		for (Inn.CheckMode mode : Inn.CheckMode.values()) {
			assertThat(mode.isValid(null)).isFalse();
		}
	}
}