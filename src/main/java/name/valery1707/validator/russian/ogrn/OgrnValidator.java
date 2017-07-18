package name.valery1707.validator.russian.ogrn;

import name.valery1707.validator.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.RegEx;
import java.util.regex.Pattern;

import static name.valery1707.validator.russian.ogrn.OgrnValidator.ValidationResult.*;

/**
 * ОГРН - Основной Государственный Регистрационный Номер
 * <p/>
 * ОГРНИП - Основной Государственный Регистрационный Номер для Индивидуального Предпринимателя
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class OgrnValidator {
	/**
	 * <a href="https://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%BD%D1%82%D1%80%D0%BE%D0%BB%D1%8C%D0%BD%D0%BE%D0%B5_%D1%87%D0%B8%D1%81%D0%BB%D0%BE#.D0.9D.D0.BE.D0.BC.D0.B5.D1.80.D0.B0_.D0.9E.D0.93.D0.A0.D0.9D_.D0.B8_.D0.9E.D0.93.D0.A0.D0.9D.D0.98.D0.9F">Код причины постановки на учет</a>
	 */
	@RegEx
	public static final String REGEXP =
			"^" +
			"\\d{13,15}" +
			"$";
	public static final Pattern PATTERN = Pattern.compile(REGEXP);
	public static final int LEN_JURIDICAL = 13;
	public static final int LEN_INDIVIDUAL = 15;

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
	public static ValidationResult isValid(@Nullable CharSequence src) {
		if (src == null) {
			return NULL;
		} else if (src.length() != LEN_JURIDICAL && src.length() != LEN_INDIVIDUAL) {
			return LENGTH;
		} else if (!StringUtils.isNumeric(src)) {
			return FORMAT;
		} else {
			return isValidCrc(src) ? OK : CRC;
		}
	}

	/**
	 * <a href="https://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%BD%D1%82%D1%80%D0%BE%D0%BB%D1%8C%D0%BD%D0%BE%D0%B5_%D1%87%D0%B8%D1%81%D0%BB%D0%BE#.D0.9D.D0.BE.D0.BC.D0.B5.D1.80.D0.B0_.D0.9E.D0.93.D0.A0.D0.9D_.D0.B8_.D0.9E.D0.93.D0.A0.D0.9D.D0.98.D0.9F">Номера ОГРН и ОГРНИП</a>
	 */
	private static boolean isValidCrc(@Nonnull CharSequence s) {
		int len = s.length();
		Long longValue = Long.valueOf(s.subSequence(0, len - 1).toString());
		int crc = (int) (longValue % (len - 2) % 10);
		return s.charAt(len - 1) == Character.forDigit(crc, 10);
	}
}
