package name.valery1707.validator.russian.ogrn;

import name.valery1707.validator.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.RegEx;
import java.util.regex.Pattern;

import static name.valery1707.validator.russian.ogrn.OgrnValidator.ValidationResult.*;

/**
 * ОГРН - Основной Государственный Регистрационный Номер
 * <p>
 * ОГРНИП - Основной Государственный Регистрационный Номер для Индивидуального Предпринимателя
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class OgrnValidator {
	OgrnValidator() {
		throw new IllegalStateException("Instance must not be created");
	}

	/**
	 * <a href="https://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%BD%D1%82%D1%80%D0%BE%D0%BB%D1%8C%D0%BD%D0%BE%D0%B5_%D1%87%D0%B8%D1%81%D0%BB%D0%BE#.D0.9D.D0.BE.D0.BC.D0.B5.D1.80.D0.B0_.D0.9E.D0.93.D0.A0.D0.9D_.D0.B8_.D0.9E.D0.93.D0.A0.D0.9D.D0.98.D0.9F">Код причины постановки на учет</a>
	 */
	@RegEx
	public static final String REGEXP =
			"^" +
			"[1-9]" +//признак отнесения государственного регистрационного номера записи
			"\\d{2}" +//две последние цифры года внесения записи в государственный реестр
			"\\d{2}" +//порядковый номер субъекта Российской Федерации по перечню субъектов Российской Федерации, установленному статьей 65 Конституции Российской Федерации
			"(" +
			"\\d{2}" +//код налоговой инспекции. Присутствует в ОГРН, отсуствует в ОГРНИП
			"\\d{5}" +//номер записи, внесенной в государственный реестр в течение года
			"|" +
			"\\d{9}" +//номер записи, внесенной в государственный реестр в течение года
			")" +
			"\\d" +//Контрольное число
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
		return s.charAt(len - 1) == calcCrc(s.subSequence(0, len - 1));
	}

	public static char calcCrc(CharSequence value) {
		int len = value.length();
		Long longValue = Long.valueOf(value.toString());
		int crc = (int) (longValue % (len - 1) % 10);
		return Character.forDigit(crc, 10);
	}
}
