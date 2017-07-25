package name.valery1707.validator.russian.ogrn;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {OgrnConstraintValidator.class})
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
