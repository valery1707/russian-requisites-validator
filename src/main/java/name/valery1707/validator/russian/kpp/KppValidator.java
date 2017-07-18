package name.valery1707.validator.russian.kpp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.RegEx;
import java.util.regex.Pattern;

import static name.valery1707.validator.russian.kpp.KppValidator.ValidationResult.*;

@SuppressWarnings({"WeakerAccess", "unused"})
public class KppValidator {
	/**
	 * <a href="https://ru.wikipedia.org/wiki/%D0%98%D0%B4%D0%B5%D0%BD%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D1%8B%D0%B9_%D0%BD%D0%BE%D0%BC%D0%B5%D1%80_%D0%BD%D0%B0%D0%BB%D0%BE%D0%B3%D0%BE%D0%BF%D0%BB%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%D1%89%D0%B8%D0%BA%D0%B0#.D0.9A.D0.BE.D0.B4_.D0.BF.D1.80.D0.B8.D1.87.D0.B8.D0.BD.D1.8B_.D0.BF.D0.BE.D1.81.D1.82.D0.B0.D0.BD.D0.BE.D0.B2.D0.BA.D0.B8_.D0.BD.D0.B0_.D1.83.D1.87.D0.B5.D1.82_.28.D0.9A.D0.9F.D0.9F.29">Код причины постановки на учет</a>
	 */
	@RegEx
	public static final String REGEXP =
			"^" +
			"\\d{4}" +//NNNN
			"[\\dA-Z]{2}" +//PP
			"\\d{3}" +//XXX
			"$";
	public static final Pattern PATTERN = Pattern.compile(REGEXP);
	public static final int LENGTH = 9;

	public enum ValidationResult {
		NULL(false),
		LENGTH(false),
		FORMAT(false),
		OK(true);

		private final boolean valid;

		ValidationResult(boolean valid) {
			this.valid = valid;
		}

		public boolean isValid() {
			return valid;
		}

		public boolean nonValid() {
			return !isValid();
		}
	}

	@Nonnull
	public static ValidationResult isValid(@Nullable CharSequence src) {
		if (src == null) {
			return NULL;
		} else if (src.length() != LENGTH) {
			return ValidationResult.LENGTH;
		} else if (!PATTERN.matcher(src).matches()) {
			return FORMAT;
		} else {
			return OK;
		}
	}
}
