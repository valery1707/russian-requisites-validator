package name.valery1707.validator.russian.kpp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class KppConstraintValidator implements ConstraintValidator<Kpp, CharSequence> {
	@Override
	public void initialize(Kpp constraintAnnotation) {
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return value == null || value.length() == 0 || KppValidator.isValid(value).isValid();
	}
}
