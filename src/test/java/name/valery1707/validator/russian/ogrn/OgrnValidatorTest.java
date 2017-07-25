package name.valery1707.validator.russian.ogrn;

import name.valery1707.validator.russian.ogrn.OgrnValidator.ValidationResult;
import org.junit.Test;

import static name.valery1707.validator.russian.ogrn.OgrnValidator.ValidationResult.*;
import static name.valery1707.validator.russian.ogrn.OgrnValidator.isValid;
import static org.assertj.core.api.Assertions.assertThat;

public class OgrnValidatorTest {
	@Test(expected = IllegalStateException.class)
	public void create() throws Exception {
		new OgrnValidator();
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
		assertThat(isValid("qwerty1234567")).isEqualByComparingTo(FORMAT);
		assertThat(isValid("1234567890123")).isEqualByComparingTo(CRC);
		assertThat(isValid("123456789012345")).isEqualByComparingTo(CRC);
		assertThat(isValid("1037739010891")).isEqualByComparingTo(OK);
		assertThat(isValid("1035006110083")).isEqualByComparingTo(OK);
		assertThat(isValid("1037739010891")).isEqualByComparingTo(OK);
		assertThat(isValid("304500116000157")).isEqualByComparingTo(OK);
		assertThat(isValid("304463210700212")).isEqualByComparingTo(OK);
		assertThat(isValid("304463210790212")).isEqualByComparingTo(CRC);
	}
}
