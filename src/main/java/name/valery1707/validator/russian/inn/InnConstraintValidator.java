package name.valery1707.validator.russian.inn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InnConstraintValidator implements ConstraintValidator<Inn, CharSequence> {
	@Override
	public void initialize(Inn constraintAnnotation) {
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return value == null || value.length() == 0 || InnValidator.isValid(value).isValid();
	}
}
