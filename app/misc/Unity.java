package misc;

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
        public String format(long size) {
            return Math.round(size, asBytes()) + " Ko";
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
        public String format(long size) {
            return Math.round(size, asBytes()) + " Mo";
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
        public String format(long size) {
            return Math.round(size, asBytes()) + " Go";
        }
    };

    public abstract String format(long size);

    public abstract long asBytes();

    public abstract long toBytes(long value);
}
