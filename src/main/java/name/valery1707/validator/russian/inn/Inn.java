package name.valery1707.validator.russian.inn;

import javax.annotation.Nullable;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {InnConstraintValidator.class})
public @interface Inn {
	/**
	 * @return the error message template
	 */
	String message() default "{name.valery1707.validator.russian.inn.Inn.message}";

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
		ANY {
			@Override
			public boolean isValid(@Nullable CharSequence value) {
				return value != null;
			}
		},
		/**
		 * Support only juridical values
		 */
		JURIDICAL {
			@Override
			public boolean isValid(@Nullable CharSequence value) {
				return value != null && value.length() == InnValidator.LEN_JURIDICAL;
			}
		},
		/**
		 * Support only physical values
		 */
		PHYSICAL {
			@Override
			public boolean isValid(@Nullable CharSequence value) {
				return value != null && value.length() == InnValidator.LEN_PHYSICAL;
			}
		};

		public abstract boolean isValid(@Nullable CharSequence value);
	}
}
