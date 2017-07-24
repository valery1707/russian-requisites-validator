package name.valery1707.validator.russian.ogrn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * ОГРН - Основной Государственный Регистрационный Номер
 * <p/>
 * ОГРНИП - Основной Государственный Регистрационный Номер для Индивидуального Предпринимателя
 */
@SuppressWarnings("WeakerAccess")
public class OgrnInfo {
	/**
	 * Признак отнесения государственного регистрационного номера записи
	 */
	private final byte type;

	/**
	 * Две последние цифры года внесения записи в государственный реестр
	 */
	private final byte year;

	/**
	 * Субъект Российской Федерации по перечню субъектов Российской Федерации, установленному статьей 65 Конституции Российской Федерации
	 */
	private final byte subject;

	/**
	 * Код налоговой инспекции
	 */
	private final byte tax;

	/**
	 * Номер записи, внесенной в государственный реестр в течение года
	 */
	private final int id;

	/**
	 * TRUE => ОГРН
	 * <p>
	 * FALSE => ОГРНИП
	 */
	private final boolean juridical;

	private char crc = '-';

	/**
	 * @param type      Признак отнесения государственного регистрационного номера записи
	 * @param year      Две последние цифры года внесения записи в государственный реестр
	 * @param subject   Субъект Российской Федерации по перечню субъектов Российской Федерации, установленному статьей 65 Конституции Российской Федерации
	 * @param tax       Код налоговой инспекции
	 * @param id        Номер записи, внесенной в государственный реестр в течение года
	 * @param juridical ОГРН/ОГРНИП
	 */
	private OgrnInfo(byte type, byte year, byte subject, byte tax, int id, boolean juridical) {
		this.type = type;
		this.year = year;
		this.subject = subject;
		this.tax = tax;
		this.id = id;
		this.juridical = juridical;
	}

	/**
	 * @param type    Признак отнесения государственного регистрационного номера записи
	 * @param year    Две последние цифры года внесения записи в государственный реестр
	 * @param subject Субъект Российской Федерации по перечню субъектов Российской Федерации, установленному статьей 65 Конституции Российской Федерации
	 * @param tax     Код налоговой инспекции
	 * @param id      Номер записи, внесенной в государственный реестр в течение года
	 */
	public OgrnInfo(byte type, byte year, byte subject, byte tax, int id) {
		this(type, year, subject, tax, id, true);
	}

	/**
	 * @param type    Признак отнесения государственного регистрационного номера записи
	 * @param year    Две последние цифры года внесения записи в государственный реестр
	 * @param subject Субъект Российской Федерации по перечню субъектов Российской Федерации, установленному статьей 65 Конституции Российской Федерации
	 * @param tax     Код налоговой инспекции
	 * @param id      Номер записи, внесенной в государственный реестр в течение года
	 */
	public OgrnInfo(int type, int year, int subject, int tax, int id) {
		this((byte) type, (byte) year, (byte) subject, (byte) tax, id);
	}

	/**
	 * @param type    Признак отнесения государственного регистрационного номера записи
	 * @param year    Две последние цифры года внесения записи в государственный реестр
	 * @param subject Субъект Российской Федерации по перечню субъектов Российской Федерации, установленному статьей 65 Конституции Российской Федерации
	 * @param id      Номер записи, внесенной в государственный реестр в течение года
	 */
	public OgrnInfo(byte type, byte year, byte subject, int id) {
		this(type, year, subject, (byte) 0, id, false);
	}

	/**
	 * @param type    Признак отнесения государственного регистрационного номера записи
	 * @param year    Две последние цифры года внесения записи в государственный реестр
	 * @param subject Субъект Российской Федерации по перечню субъектов Российской Федерации, установленному статьей 65 Конституции Российской Федерации
	 * @param id      Номер записи, внесенной в государственный реестр в течение года
	 */
	public OgrnInfo(int type, int year, int subject, int id) {
		this((byte) type, (byte) year, (byte) subject, id);
	}

	@Nonnull
	public static OgrnInfo parse(@Nullable CharSequence value) {
		if (OgrnValidator.isValid(value).nonValid()) {
			throw new IllegalArgumentException("Can not parse OGRN from string: " + value);
		}
		assert value != null;
		byte type = Byte.valueOf(value.subSequence(0, 1).toString());
		byte year = Byte.valueOf(value.subSequence(1, 3).toString());
		byte subject = Byte.valueOf(value.subSequence(3, 5).toString());
		int length = value.length();
		if (length == OgrnValidator.LEN_JURIDICAL) {
			byte tax = Byte.valueOf(value.subSequence(5, 7).toString());
			int id = Integer.valueOf(value.subSequence(7, length - 1).toString());
			return new OgrnInfo(type, year, subject, tax, id);
		} else {
			int id = Integer.valueOf(value.subSequence(5, length - 1).toString());
			return new OgrnInfo(type, year, subject, id);
		}
	}

	public String format() {
		String s;
		if (isJuridical()) {
			s = String.format("%01d%02d%02d%02d%05d", getType(), getYear(), getSubject(), getTax(), getId());
		} else {
			s = String.format("%01d%02d%02d%09d", getType(), getYear(), getSubject(), getId());
		}
		if (crc == '-') {
			crc = OgrnValidator.calcCrc(s);
		}
		return s + crc;
	}

	/**
	 * @return Признак отнесения государственного регистрационного номера записи
	 */
	public byte getType() {
		return type;
	}

	/**
	 * @return Две последние цифры года внесения записи в государственный реестр
	 */
	public byte getYear() {
		return year;
	}

	/**
	 * @return Субъект Российской Федерации по перечню субъектов Российской Федерации, установленному статьей 65 Конституции Российской Федерации
	 */
	public byte getSubject() {
		return subject;
	}

	/**
	 * @return Код налоговой инспекции
	 */
	public byte getTax() {
		return tax;
	}

	/**
	 * @return Номер записи, внесенной в государственный реестр в течение года
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return ОГРН
	 */
	public boolean isJuridical() {
		return juridical;
	}

	/**
	 * @return ОГРНИП
	 */
	public boolean isIndividual() {
		return !isJuridical();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OgrnInfo)) {
			return false;
		}

		OgrnInfo that = (OgrnInfo) other;
		return
				this.getType() == that.getType() &&
				this.getYear() == that.getYear() &&
				this.getSubject() == that.getSubject() &&
				this.getTax() == that.getTax() &&
				this.getId() == that.getId() &&
				this.isJuridical() == that.isJuridical();
	}

	@Override
	public int hashCode() {
		int result = (int) getType();
		result = 31 * result + (int) getYear();
		result = 31 * result + (int) getSubject();
		result = 31 * result + (int) getTax();
		result = 31 * result + getId();
		result = 31 * result + (isJuridical() ? 1 : 0);
		return result;
	}
}
