package name.valery1707.validator.russian.inn;

import name.valery1707.validator.russian.inn.Inn.CheckMode;

import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InnConstraintValidator implements ConstraintValidator<Inn, CharSequence> {
	private CheckMode mode;

	@Override
	public void initialize(Inn annotation) {
		mode = annotation.mode();
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return isEmptyValue(value) || isValidValue(value);
	}

	private boolean isEmptyValue(@Nullable CharSequence value) {
		return value == null || value.length() == 0;
	}

	private boolean isValidValue(CharSequence value) {
		return InnValidator.isValid(value).isValid() && mode.isValid(value);
	}
}
