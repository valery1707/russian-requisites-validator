package name.valery1707.validator;

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
}
