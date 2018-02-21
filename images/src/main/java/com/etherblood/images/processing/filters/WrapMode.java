package com.etherblood.images.processing.filters;

/**
 *
 * @author Philipp
 */
public enum WrapMode {
    WRAP, MIRROR, CLAMP;

    public int wrap(int value, int max) {
        switch (this) {
            case CLAMP:
                if (value >= max) {
                    return max - 1;
                }
                if (value < 0) {
                    return 0;
                }
                return value;
            case WRAP:
                return Math.floorMod(value, max);
            case MIRROR:
                if (value < 0) {
                    return wrap(-value, max);
                }
                if (value >= max) {
                    return wrap(value - 2 * max, max);
                }
                return value;
            default:
                throw new UnsupportedOperationException(name());
        }
    }
}
