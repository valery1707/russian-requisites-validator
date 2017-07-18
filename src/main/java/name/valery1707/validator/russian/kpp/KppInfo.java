package name.valery1707.validator.russian.kpp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class KppInfo {
	/**
	 * Код налогового органа
	 */
	private final int tax;

	/**
	 * Причина постановки на учет (учёта сведений)
	 */
	private final String purpose;

	/**
	 * Порядковый номер
	 */
	private final int id;

	public KppInfo(int tax, String purpose, int id) {
		this.tax = tax;
		this.purpose = purpose;
		this.id = id;
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

	public int getTax() {
		return tax;
	}

	public String getPurpose() {
		return purpose;
	}

	public int getId() {
		return id;
	}
}
