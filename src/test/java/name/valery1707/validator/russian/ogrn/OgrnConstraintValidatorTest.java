package name.valery1707.validator.russian.ogrn;

import com.googlecode.miyamoto.AnnotationProxyBuilder;
import name.valery1707.validator.russian.ogrn.Ogrn.CheckMode;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

public class OgrnConstraintValidatorTest {
	@Test
	public void checkModeEnum() throws Exception {
		for (CheckMode mode : CheckMode.values()) {
			assertThat(CheckMode.valueOf(mode.name())).isEqualByComparingTo(mode);
		}
	}

	private static OgrnConstraintValidator validator(CheckMode mode) {
		OgrnConstraintValidator validator = new OgrnConstraintValidator();
		AnnotationProxyBuilder<Ogrn> builder = AnnotationProxyBuilder.newBuilder(Ogrn.class);
		builder.setProperty("mode", mode);
		validator.initialize(builder.getProxedAnnotation());
		return validator;
	}

	private static OgrnConstraintValidator validator() {
		return validator(CheckMode.ANY);
	}

	private ConstraintValidatorContext context() {
		return null;
	}

	private AbstractBooleanAssert<?> assertValue(CheckMode mode, CharSequence value) {
		return assertThat(validator(mode).isValid(value, context()))
				.describedAs("%s(%s)"
						, mode.toString()
						, value == null ? "null" : value.toString()
				);
	}

	private AbstractBooleanAssert<?> assertValue(CharSequence value) {
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
