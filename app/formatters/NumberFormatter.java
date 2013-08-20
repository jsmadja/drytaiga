package formatters;

import java.text.NumberFormat;

import static java.util.Locale.FRENCH;

public class NumberFormatter {

    public static String format(Number number) {
        return NumberFormat.getInstance(FRENCH).format(number);
    }

}
