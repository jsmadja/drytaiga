package misc;

public class FileSize {

    private final long size;

    public FileSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        if (size < Unity.MEGA.asBytes()) {
            return Unity.KILO.format(size);
        }
        if (size < Unity.GIGA.asBytes()) {
            return Unity.MEGA.format(size);
        }
        return Unity.GIGA.format(size);
    }

    public enum Unity {
        KILO {
            @Override
            public long asBytes() {
                return 1024;
            }

            @Override
            public long toBytes(long value) {
                return value * asBytes();
            }

            @Override
            String format(long size) {
                return compute(size, asBytes()) + " Ko";
            }
        },
        MEGA {
            public long asBytes() {
                return KILO.asBytes() * KILO.asBytes();
            }

            @Override
            public long toBytes(long value) {
                return value * asBytes();
            }

            @Override
            String format(long size) {
                return compute(size, asBytes()) + " Mo";
            }
        },

        GIGA {
            public long asBytes() {
                return MEGA.asBytes() * KILO.asBytes();
            }

            @Override
            public long toBytes(long value) {
                return value * asBytes();
            }

            @Override
            String format(long size) {
                return compute(size, asBytes()) + " Go";
            }
        };

        private static double compute(double size, long bytes) {
            double v = size / bytes;
            return ((double) Math.round(v * 10)) / 10;
        }

        abstract String format(long size);

        abstract long asBytes();

        public abstract long toBytes(long value);
    }
}
