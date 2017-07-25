package name.valery1707.validator.russian.ogrn;

import name.valery1707.validator.russian.ogrn.Ogrn.CheckMode;

import javax.annotation.Nonnull;
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
		return value == null || value.length() == 0 || (OgrnValidator.isValid(value).isValid() && isValidType(value, mode));
	}

	@SuppressWarnings("SimplifiableIfStatement")
	private boolean isValidType(@Nonnull CharSequence value, @Nonnull CheckMode mode) {
		if (mode.equals(CheckMode.JURIDICAL)) {
			return value.length() == OgrnValidator.LEN_JURIDICAL;
		} else if (mode.equals(CheckMode.INDIVIDUAL)) {
			return value.length() == OgrnValidator.LEN_INDIVIDUAL;
		} else {
			return true;
		}
	}
}
