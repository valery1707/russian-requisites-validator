package name.valery1707.validator;

import org.junit.Test;

import static name.valery1707.validator.StringUtils.isEmpty;
import static name.valery1707.validator.StringUtils.isNumeric;
import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {
	@Test(expected = IllegalStateException.class)
	public void create() throws Exception {
		new StringUtils();
	}

	@Test
	public void testIsEmpty() throws Exception {
		assertThat(isEmpty(null)).isTrue();
		assertThat(isEmpty("")).isTrue();
		assertThat(isEmpty(" ")).isFalse();
		assertThat(isEmpty("bob")).isFalse();
		assertThat(isEmpty("  bob  ")).isFalse();
	}

	@Test
	public void testIsNumeric() throws Exception {
		assertThat(isNumeric(null)).isFalse();
		assertThat(isNumeric("")).isFalse();
		assertThat(isNumeric("  ")).isFalse();
		assertThat(isNumeric("123")).isTrue();
		assertThat(isNumeric("\u0967\u0968\u0969")).isTrue();
		assertThat(isNumeric("12 3")).isFalse();
		assertThat(isNumeric("ab2c")).isFalse();
		assertThat(isNumeric("12-3")).isFalse();
		assertThat(isNumeric("12.3")).isFalse();
		assertThat(isNumeric("-123")).isFalse();
		assertThat(isNumeric("+123")).isFalse();
	}
}
