package name.valery1707.validator.russian.kpp;

import name.valery1707.validator.russian.kpp.KppValidator.ValidationResult;
import org.junit.Test;

import static name.valery1707.validator.russian.kpp.KppValidator.ValidationResult.*;
import static name.valery1707.validator.russian.kpp.KppValidator.isValid;
import static org.assertj.core.api.Assertions.assertThat;

public class KppValidatorTest {
	@Test(expected = IllegalStateException.class)
	public void create() throws Exception {
		new KppValidator();
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
		assertThat(isValid("qwerty123")).isEqualByComparingTo(FORMAT);
		assertThat(isValid("7733az001")).isEqualByComparingTo(FORMAT);
		assertThat(isValid("773301001")).isEqualByComparingTo(OK);
		assertThat(isValid("773399001")).isEqualByComparingTo(OK);
		assertThat(isValid("7733AZ001")).isEqualByComparingTo(OK);
	}
}
