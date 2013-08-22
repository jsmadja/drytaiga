package formatters;

import misc.Unity;

public class BytesFormatter {

    public static String format(long size) {
        if (size < Unity.MEGA.asBytes()) {
            return Unity.KILO.format(size);
        }
        if (size < Unity.GIGA.asBytes()) {
            return Unity.MEGA.format(size);
        }
        return Unity.GIGA.format(size);
    }

}
