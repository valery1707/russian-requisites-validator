package name.valery1707.validator.russian.kpp;

import com.googlecode.miyamoto.AnnotationProxyBuilder;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

public class KppConstraintValidatorTest {
	private static KppConstraintValidator validator() {
		KppConstraintValidator validator = new KppConstraintValidator();
		AnnotationProxyBuilder<Kpp> builder = AnnotationProxyBuilder.newBuilder(Kpp.class);
		validator.initialize(builder.getProxedAnnotation());
		return validator;
	}

	private ConstraintValidatorContext context() {
		return null;
	}

	private AbstractBooleanAssert<?> assertValue(CharSequence value) {
		return assertThat(validator().isValid(value, context()))
				.describedAs("%s"
						, value == null ? "null" : value.toString()
				);
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
	public void testValue() throws Exception {
		assertValue("773301001").isTrue();
	}
}