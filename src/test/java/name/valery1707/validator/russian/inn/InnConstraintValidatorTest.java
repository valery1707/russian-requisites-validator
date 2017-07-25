package name.valery1707.validator.russian.inn;

import com.googlecode.miyamoto.AnnotationProxyBuilder;
import name.valery1707.validator.russian.inn.Inn.CheckMode;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

public class InnConstraintValidatorTest {
	@Test
	public void checkModeEnum() throws Exception {
		for (CheckMode mode : CheckMode.values()) {
			assertThat(CheckMode.valueOf(mode.name())).isEqualByComparingTo(mode);
		}
	}

	private static InnConstraintValidator validator(CheckMode mode) {
		InnConstraintValidator validator = new InnConstraintValidator();
		AnnotationProxyBuilder<Inn> builder = AnnotationProxyBuilder.newBuilder(Inn.class);
		builder.setProperty("mode", mode);
		validator.initialize(builder.getProxedAnnotation());
		return validator;
	}

	private static InnConstraintValidator validator() {
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
		assertValue("1181111110").isTrue();
		assertValue("1181111111").isFalse();
		assertValue("325507450247").isTrue();
		assertValue("325507450249").isFalse();
	}

	@Test
	public void testJuridical() throws Exception {
		assertValue(CheckMode.JURIDICAL, "1181111110").isTrue();
		assertValue(CheckMode.JURIDICAL, "1181111111").isFalse();
		assertValue(CheckMode.JURIDICAL, "325507450247").isFalse();
		assertValue(CheckMode.JURIDICAL, "325507450249").isFalse();
	}

	@Test
	public void testPhysical() throws Exception {
		assertValue(CheckMode.PHYSICAL, "1181111110").isFalse();
		assertValue(CheckMode.PHYSICAL, "1181111111").isFalse();
		assertValue(CheckMode.PHYSICAL, "325507450247").isTrue();
		assertValue(CheckMode.PHYSICAL, "325507450249").isFalse();
	}
}
