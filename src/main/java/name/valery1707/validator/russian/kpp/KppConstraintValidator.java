package name.valery1707.validator.russian.kpp;

import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class KppConstraintValidator implements ConstraintValidator<Kpp, CharSequence> {
	@Override
	public void initialize(Kpp annotation) {
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return isEmptyValue(value) || isValidValue(value);
	}

	private boolean isEmptyValue(@Nullable CharSequence value) {
		return value == null || value.length() == 0;
	}

	private boolean isValidValue(CharSequence value) {
		return KppValidator.isValid(value).isValid();
	}
}
