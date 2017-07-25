package name.valery1707.validator.russian.kpp;

import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.TypeFactory;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class KppConstraintValidatorTest {
	private static KppConstraintValidator validator() throws AnnotationFormatException {
		KppConstraintValidator validator = new KppConstraintValidator();
		Map<String, Object> annotationParameters = new HashMap<String, Object>();
		validator.initialize(TypeFactory.annotation(Kpp.class, annotationParameters));
		return validator;
	}

	private ConstraintValidatorContext context() {
		return null;
	}

	private AbstractBooleanAssert<?> assertValue(CharSequence value) throws AnnotationFormatException {
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