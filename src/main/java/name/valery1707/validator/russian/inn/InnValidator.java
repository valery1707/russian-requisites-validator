package name.valery1707.validator.russian.inn;

import name.valery1707.validator.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.RegEx;
import java.util.regex.Pattern;

import static name.valery1707.validator.russian.inn.InnValidator.ValidationResult.*;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class InnValidator {
	/**
	 * <a href="https://ru.wikipedia.org/wiki/%D0%98%D0%B4%D0%B5%D0%BD%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D1%8B%D0%B9_%D0%BD%D0%BE%D0%BC%D0%B5%D1%80_%D0%BD%D0%B0%D0%BB%D0%BE%D0%B3%D0%BE%D0%BF%D0%BB%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%D1%89%D0%B8%D0%BA%D0%B0#.D0.92.D0.B8.D0.B4.D1.8B">Идентификационный номер налогоплательщика</a>
	 */
	@RegEx
	public static final String REGEXP =
			"^" +
			"[\\d]{10,12}" +
			"$";
	public static final Pattern PATTERN = Pattern.compile(REGEXP);
	public static final int LEN_JURIDICAL = 10;
	public static final int LEN_PHYSICAL = 12;

	public enum ValidationResult {
		NULL(false),
		LENGTH(false),
		FORMAT(false),
		CRC(false),
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
	public static ValidationResult isValid(@Nullable CharSequence inn) {
		if (inn == null) {
			return NULL;
		} else if (inn.length() != LEN_JURIDICAL && inn.length() != LEN_PHYSICAL) {
			return LENGTH;
		} else if (!StringUtils.isNumeric(inn)) {
			return FORMAT;
		} else if (inn.length() == LEN_JURIDICAL) {
			boolean crc_10 = isValidCrc(inn);
			return crc_10 ? OK : CRC;
		} else {//inn.length() == LEN_PHYSICAL
			boolean crc_11 = isValidCrc(inn.subSequence(0, inn.length() - 1));
			boolean crc_12 = isValidCrc(inn);
			return (crc_11 && crc_12) ? OK : CRC;
		}
	}

	private static boolean isValidCrc(@Nonnull CharSequence s) {
		int len = s.length();
		char last = s.charAt(len - 1);
		char crc = calcCrc(s.subSequence(0, len - 1));
		return last == crc;
	}

	private static final int[] CRC_MODULES = new int[]{8, 6, 4, 9, 5, 3, 10, 4, 2, 7, 3};

	/**
	 * <a href="https://ru.wikipedia.org/wiki/%D0%98%D0%B4%D0%B5%D0%BD%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D1%8B%D0%B9_%D0%BD%D0%BE%D0%BC%D0%B5%D1%80_%D0%BD%D0%B0%D0%BB%D0%BE%D0%B3%D0%BE%D0%BF%D0%BB%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%D1%89%D0%B8%D0%BA%D0%B0#.D0.92.D1.8B.D1.87.D0.B8.D1.81.D0.BB.D0.B5.D0.BD.D0.B8.D0.B5_.D0.BA.D0.BE.D0.BD.D1.82.D1.80.D0.BE.D0.BB.D1.8C.D0.BD.D1.8B.D1.85_.D1.86.D0.B8.D1.84.D1.80">Algorithm description</a>
	 *
	 * @param s Input sequence
	 * @return Calculated CRC code
	 */
	public static char calcCrc(@Nonnull CharSequence s) {
		int len = s.length();
		int crc = 0;
		for (int i = 0; i < len; i++) {
			crc += CRC_MODULES[len - i - 1] * Character.getNumericValue(s.charAt(i));
		}
		crc = (crc % 11) % 10;
		return Character.forDigit(crc, 10);
	}
}
