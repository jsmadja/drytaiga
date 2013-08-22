package misc;

import java.util.Random;

public class Math {

    public static int percent(Integer up, Integer down) {
        return (int) (((double) up / down) * 100);
    }

    public static double round(double size, long bytes) {
        double v = size / bytes;
        return ((double) java.lang.Math.round(v * 10)) / 10;
    }

    public static int randomInt(int max) {
        if(max == 0) {
            return 0;
        }
        return new Random().nextInt(max);
    }
}
