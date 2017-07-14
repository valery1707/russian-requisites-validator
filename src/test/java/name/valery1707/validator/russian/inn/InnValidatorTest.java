package name.valery1707.validator.russian.inn;

import org.junit.Test;

import static name.valery1707.validator.russian.inn.InnValidator.ValidationResult.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InnValidatorTest {
	@Test
	public void testInnValid() throws Exception {
		assertThat(InnValidator.isValid(null)).isEqualByComparingTo(NULL);
		assertThat(InnValidator.isValid("")).isEqualByComparingTo(LENGTH);
		assertThat(InnValidator.isValid("qwe")).isEqualByComparingTo(LENGTH);
		assertThat(InnValidator.isValid("qwerty1111")).isEqualByComparingTo(FORMAT);
		assertThat(InnValidator.isValid("1181111110")).isEqualByComparingTo(OK);
		assertThat(InnValidator.isValid("1191111110")).isEqualByComparingTo(OK);
		assertThat(InnValidator.isValid("1171111110")).isEqualByComparingTo(CRC);
		assertThat(InnValidator.isValid("7713456564")).isEqualByComparingTo(CRC);
		assertThat(InnValidator.isValid("325507450247")).isEqualByComparingTo(OK);
		assertThat(InnValidator.isValid("925507450247")).isEqualByComparingTo(CRC);
	}
}
