package name.valery1707.validator.russian.inn;

import name.valery1707.validator.russian.inn.InnValidator.ValidationResult;
import org.junit.Test;

import static name.valery1707.validator.russian.inn.InnValidator.ValidationResult.*;
import static name.valery1707.validator.russian.inn.InnValidator.isValid;
import static org.assertj.core.api.Assertions.assertThat;

public class InnValidatorTest {
	@Test(expected = IllegalStateException.class)
	public void create() throws Exception {
		new InnValidator();
	}

	@Test
	public void validationResultEnum() throws Exception {
		for (ValidationResult result : ValidationResult.values()) {
			assertThat(ValidationResult.valueOf(result.name())).isEqualByComparingTo(result);
		}
	}

	@Test
	public void testIsValid() throws Exception {
		assertThat(isValid(null)).isEqualByComparingTo(NULL);
		assertThat(isValid("")).isEqualByComparingTo(LENGTH);
		assertThat(isValid("qwe")).isEqualByComparingTo(LENGTH);
		assertThat(isValid("qwerty1111")).isEqualByComparingTo(FORMAT);
		assertThat(isValid("1181111110")).isEqualByComparingTo(OK);
		assertThat(isValid("1191111110")).isEqualByComparingTo(OK);
		assertThat(isValid("1171111110")).isEqualByComparingTo(CRC);
		assertThat(isValid("7713456564")).isEqualByComparingTo(CRC);
		assertThat(isValid("325507450247")).isEqualByComparingTo(OK);
		assertThat(isValid("325507450257")).isEqualByComparingTo(CRC);
		assertThat(isValid("325507450248")).isEqualByComparingTo(CRC);
		assertThat(isValid("925507450247")).isEqualByComparingTo(CRC);
	}
}
