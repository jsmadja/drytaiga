package misc;

public class FileSize {

    private final long size;

    public FileSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        if (size < (1024 * 1024)) {
            return Unity.KILO.format(size);
        }
        return Unity.MEGA.format(size);
    }

    private enum Unity {
        KILO {
            private long asBytes() {
                return 1024;
            }

            @Override
            String format(long size) {
                return compute(size, asBytes()) + " Ko";
            }
        },
        MEGA {
            private long asBytes() {
                return 1024 * 1024;
            }

            @Override
            String format(long size) {
                return compute(size, asBytes()) + " Mo";
            }
        };

        private static double compute(double size, long bytes) {
            double v = size / bytes;
            return ((double) Math.round(v * 10)) / 10;
        }

        abstract String format(long size);
    }
}
