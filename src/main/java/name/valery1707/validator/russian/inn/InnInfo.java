package name.valery1707.validator.russian.inn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static name.valery1707.validator.Checker.checkRange;

@SuppressWarnings("WeakerAccess")
public class InnInfo {
	/**
	 * <a href="https://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%B4%D1%8B_%D1%81%D1%83%D0%B1%D1%8A%D0%B5%D0%BA%D1%82%D0%BE%D0%B2_%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D0%B9%D1%81%D0%BA%D0%BE%D0%B9_%D0%A4%D0%B5%D0%B4%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D0%B8">Код субъекта Российской Федерации</a> согласно ст. 65 Конституции
	 */
	private final byte subject;

	/**
	 * Номер местной налоговой инспекции
	 */
	private final byte localTax;

	/**
	 * Номер налоговой записи налогоплательщика
	 */
	private final int id;

	/**
	 * Юридическое Лицо
	 */
	private final boolean juridical;

	private String crc;

	public InnInfo(int subject, int localTax, int id, boolean juridical) {
		this((byte) subject, (byte) localTax, id, juridical);
	}

	public InnInfo(byte subject, byte localTax, int id, boolean juridical) {
		this.subject = checkRange(subject, 0, 99, "Subject");
		this.localTax = checkRange(localTax, 0, 99, "Local tax");
		this.id = checkRange(id, 0, juridical ? 99999 : 999999, "ID");
		this.juridical = juridical;
	}

	@Nonnull
	public static InnInfo parse(@Nullable String value) {
		if (InnValidator.isValid(value).nonValid()) {
			throw new IllegalArgumentException("Can not parse INN from string: " + value);
		}
		assert value != null;
		boolean juridical = value.length() == 10;
		byte subject = Byte.valueOf(value.substring(0, 2));
		byte localTax = Byte.valueOf(value.substring(2, 4));
		int id = Integer.valueOf(value.substring(4, value.length() - (juridical ? 1 : 2)));
		return new InnInfo(subject, localTax, id, juridical);
	}

	public String format() {
		String s = String.format(
				isJuridical()
						? "%02d%02d%05d"
						: "%02d%02d%06d"
				, getSubject(), getLocalTax(), getId());
		if (crc == null) {
			crc = String.valueOf(InnValidator.calcCrc(s));
			if (isPhysical()) {
				crc += String.valueOf(InnValidator.calcCrc(s + crc));
			}
		}
		return s + crc;
	}

	public byte getSubject() {
		return subject;
	}

	public byte getLocalTax() {
		return localTax;
	}

	public int getId() {
		return id;
	}

	public boolean isJuridical() {
		return juridical;
	}

	public boolean isPhysical() {
		return !isJuridical();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InnInfo)) {
			return false;
		}

		InnInfo that = (InnInfo) other;
		return
				this.getSubject() == that.getSubject() &&
				this.getLocalTax() == that.getLocalTax() &&
				this.getId() == that.getId() &&
				this.isJuridical() == that.isJuridical()
				;
	}

	@Override
	public int hashCode() {
		int result = (int) getSubject();
		result = 31 * result + (int) getLocalTax();
		result = 31 * result + getId();
		result = 31 * result + (isJuridical() ? 1 : 0);
		return result;
	}
}
