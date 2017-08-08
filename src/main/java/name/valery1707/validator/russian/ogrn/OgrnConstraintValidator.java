package name.valery1707.validator.russian.ogrn;

import name.valery1707.validator.russian.ogrn.Ogrn.CheckMode;

import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OgrnConstraintValidator implements ConstraintValidator<Ogrn, CharSequence> {
	private CheckMode mode;

	@Override
	public void initialize(Ogrn annotation) {
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
		return OgrnValidator.isValid(value).isValid() && mode.isValid(value);
	}
}
