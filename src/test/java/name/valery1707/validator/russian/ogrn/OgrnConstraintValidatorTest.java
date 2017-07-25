package name.valery1707.validator.russian.ogrn;

import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.TypeFactory;
import name.valery1707.validator.russian.ogrn.Ogrn.CheckMode;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class OgrnConstraintValidatorTest {
	@Test
	public void checkModeEnum() throws Exception {
		for (CheckMode mode : CheckMode.values()) {
			assertThat(CheckMode.valueOf(mode.name())).isEqualByComparingTo(mode);
		}
	}

	private static OgrnConstraintValidator validator(CheckMode mode) throws AnnotationFormatException {
		OgrnConstraintValidator validator = new OgrnConstraintValidator();
		Map<String, Object> annotationParameters = new HashMap<String, Object>();
		annotationParameters.put("mode", mode);
		validator.initialize(TypeFactory.annotation(Ogrn.class, annotationParameters));
		return validator;
	}

	private static OgrnConstraintValidator validator() throws AnnotationFormatException {
		return validator(CheckMode.ANY);
	}

	private ConstraintValidatorContext context() {
		return null;
	}

	private AbstractBooleanAssert<?> assertValue(CheckMode mode, CharSequence value) throws AnnotationFormatException {
		return assertThat(validator(mode).isValid(value, context()))
				.describedAs("%s(%s)"
						, mode.toString()
						, value == null ? "null" : value.toString()
				);
	}

	private AbstractBooleanAssert<?> assertValue(CharSequence value) throws AnnotationFormatException {
		return assertValue(CheckMode.ANY, value);
	}

	@Test
	public void testNull() throws Exception {
		assertValue(null).isTrue();
	}

	@Test
	public void testEmpty() throws Exception {
		assertValue("").isTrue();
	}

	@Test
	public void testBlank() throws Exception {
		assertValue(" ").isFalse();
	}

	@Test
	public void testAny() throws Exception {
		assertValue("1037739010891").isTrue();
		assertValue("1037739010892").isFalse();
		assertValue("304500116000157").isTrue();
		assertValue("304500116000159").isFalse();
	}

	@Test
	public void testJuridical() throws Exception {
		assertValue(CheckMode.JURIDICAL, "1037739010891").isTrue();
		assertValue(CheckMode.JURIDICAL, "1037739010892").isFalse();
		assertValue(CheckMode.JURIDICAL, "304500116000157").isFalse();
		assertValue(CheckMode.JURIDICAL, "304500116000159").isFalse();
	}

	@Test
	public void testIndividual() throws Exception {
		assertValue(CheckMode.INDIVIDUAL, "1037739010891").isFalse();
		assertValue(CheckMode.INDIVIDUAL, "1037739010892").isFalse();
		assertValue(CheckMode.INDIVIDUAL, "304500116000157").isTrue();
		assertValue(CheckMode.INDIVIDUAL, "304500116000159").isFalse();
	}
}
