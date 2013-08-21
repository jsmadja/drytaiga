package formatters;

import misc.FileSize;

public class BytesFormatter {

    public static String format(long bytes) {
        return new FileSize(bytes).toString();
    }

}
