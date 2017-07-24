package name.valery1707.validator.russian.ogrn;

import javax.annotation.Nonnull;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OgrnConstraintValidator implements ConstraintValidator<Ogrn, CharSequence> {
	private Ogrn.CheckMode mode;

	@Override
	public void initialize(Ogrn constraintAnnotation) {
		mode = constraintAnnotation.mode();
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return value == null || value.length() == 0 || (OgrnValidator.isValid(value).isValid() && isValidType(value, mode));
	}

	private boolean isValidType(@Nonnull CharSequence value, @Nonnull Ogrn.CheckMode mode) {
		if (mode.equals(Ogrn.CheckMode.JURIDICAL)) {
			return value.length() == OgrnValidator.LEN_JURIDICAL;
		} else if (mode.equals(Ogrn.CheckMode.INDIVIDUAL)) {
			return value.length() == OgrnValidator.LEN_INDIVIDUAL;
		} else {
			return true;
		}
	}
}
