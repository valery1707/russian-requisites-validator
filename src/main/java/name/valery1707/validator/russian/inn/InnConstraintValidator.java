package name.valery1707.validator.russian.inn;

import name.valery1707.validator.russian.inn.Inn.CheckMode;

import javax.annotation.Nonnull;
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
		return value == null || value.length() == 0 || (InnValidator.isValid(value).isValid() && isValidType(value, mode));
	}

	@SuppressWarnings("SimplifiableIfStatement")
	private boolean isValidType(@Nonnull CharSequence value, @Nonnull CheckMode mode) {
		if (mode.equals(CheckMode.JURIDICAL)) {
			return value.length() == InnValidator.LEN_JURIDICAL;
		} else if (mode.equals(CheckMode.PHYSICAL)) {
			return value.length() == InnValidator.LEN_PHYSICAL;
		} else {
			return true;
		}
	}
}
