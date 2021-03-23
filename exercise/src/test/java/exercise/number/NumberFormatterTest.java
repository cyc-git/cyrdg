package exercise.number;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumberFormatterTest {
	@Test
	public void testFormatWithComma() {
		NumberFormatter formatter = new NumberFormatter();
		assertEquals("9,527", formatter.formatWithComma(9527));
		assertEquals("3,345,678", formatter.formatWithComma(3345678));
		assertEquals("-1,234.45", formatter.formatWithComma(-1234.45));
		assertEquals("-9,527", formatter.formatWithComma(-9527));
		assertEquals("234.45", formatter.formatWithComma(234.45));
		assertEquals("-234.45", formatter.formatWithComma(-234.45));
		assertEquals("0", formatter.formatWithComma(0));
		assertEquals("0", formatter.formatWithComma(-0));
		assertEquals("-0.123", formatter.formatWithComma(-0.1230));
		assertEquals("-0.1203", formatter.formatWithComma(-0.1203));
		assertEquals("-1,111,110.1203", formatter.formatWithComma(-01111110.1203));
	}
}
