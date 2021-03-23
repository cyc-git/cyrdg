package exercise.number;

public class NumberFormatter {

	/**
	 * More than six decimal places where be round up.
	 */
	public String formatWithComma(double number) {
		boolean isNegative = number < 0;
		String numberString = String.format("%6f", isNegative ? -number : number);
		int pointIndex = numberString.indexOf(".");
		char[] chars = numberString.toCharArray();
		StringBuilder builder = new StringBuilder(chars.length + chars.length / 3 + 2);

		for (int i = chars.length - 1, c = 0; i >= 0; i--) {
			if (i > pointIndex) {
				if (builder.length() > 0 || chars[i] != '0') {
					builder.append(chars[i]);
				}
			} else if (i == pointIndex) {
				if (builder.length() > 0) {
					builder.append('.');
				}
			} else {
				builder.append(chars[i]);
				c++;
				if (c == 3 && i != 0) {
					c = 0;
					builder.append(',');
				}
			}
		}
		if (isNegative) {
			builder.append('-');
		}

		return builder.reverse().toString();
	}
}
