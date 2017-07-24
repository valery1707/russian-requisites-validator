package name.valery1707.validator.russian.ogrn;

import javax.validation.Payload;

public @interface Ogrn {
	/**
	 * @return the error message template
	 */
	String message() default "{name.valery1707.validator.russian.ogrn.Ogrn.message}";

	/**
	 * @return the check mode
	 */
	CheckMode mode() default CheckMode.ANY;

	/**
	 * @return the groups the constraint belongs to
	 */
	Class<?>[] groups() default {};

	/**
	 * @return the payload associated to the constraint
	 */
	Class<? extends Payload>[] payload() default {};

	enum CheckMode {
		/**
		 * Support any variant
		 */
		ANY,
		/**
		 * Support only juridical values
		 */
		JURIDICAL,
		/**
		 * Support only individual values
		 */
		INDIVIDUAL;
	}
}
