package name.valery1707.validator.russian.kpp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

import static name.valery1707.validator.Checker.checkPattern;
import static name.valery1707.validator.Checker.checkRange;

@SuppressWarnings("WeakerAccess")
public class KppInfo {
	/**
	 * Код налогового органа
	 */
	private final int tax;

	/**
	 * Причина постановки на учет (учёта сведений)
	 */
	@Nonnull
	private final String purpose;

	private final static Pattern PURPOSE = Pattern.compile("^[\\dA-Z]{2}$");

	/**
	 * Порядковый номер
	 */
	private final int id;

	/**
	 * КПП
	 *
	 * @param tax     Код налогового органа
	 * @param purpose Причина постановки на учет (учёта сведений)
	 * @param id      Порядковый номер
	 */
	public KppInfo(int tax, String purpose, int id) {
		this.tax = checkRange(tax, 0, 9999, "Tax");
		this.purpose = checkPattern(purpose, PURPOSE, "Purpose");
		this.id = checkRange(id, 1, 999, "ID");
	}

	@Nonnull
	public static KppInfo parse(@Nullable String value) {
		if (KppValidator.isValid(value).nonValid()) {
			throw new IllegalArgumentException("Can not parse KPP from string: " + value);
		}
		assert value != null;
		int tax = Integer.valueOf(value.substring(0, 4));
		String purpose = value.substring(4, 6);
		int id = Integer.valueOf(value.substring(6));
		return new KppInfo(tax, purpose, id);
	}

	public String format() {
		return String.format("%04d%s%03d", getTax(), getPurpose(), getId());
	}

	/**
	 * @return Код налогового органа
	 */
	public int getTax() {
		return tax;
	}

	/**
	 * @return Причина постановки на учет (учёта сведений)
	 */
	@Nonnull
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @return Порядковый номер
	 */
	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KppInfo)) {
			return false;
		}

		KppInfo that = (KppInfo) other;
		return
				this.getTax() == that.getTax() &&
				this.getId() == that.getId() &&
				this.getPurpose().equals(that.getPurpose())
				;
	}

	@Override
	public int hashCode() {
		int result = getTax();
		result = 31 * result + getPurpose().hashCode();
		result = 31 * result + getId();
		return result;
	}
}
