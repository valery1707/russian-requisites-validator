package name.valery1707.validator;

import java.util.regex.Pattern;

public final class Checker {
	Checker() {
		throw new IllegalStateException("Instance must not be created");
	}

	public static byte checkRange(byte value, int min, int max, String name) {
		if (value < min || value > max) {
			throw new IllegalArgumentException(String.format("%s must be between %d and %d", name, min, max));
		} else {
			return value;
		}
	}

	public static int checkRange(int value, int min, int max, String name) {
		if (value < min || value > max) {
			throw new IllegalArgumentException(String.format("%s must be between %d and %d", name, min, max));
		} else {
			return value;
		}
	}

	public static String checkPattern(String value, Pattern pattern, String name) {
		if (value == null || !pattern.matcher(value).matches()) {
			throw new IllegalArgumentException(String.format("%s must match with pattern %s", name, pattern.toString()));
		} else {
			return value;
		}
	}
}
