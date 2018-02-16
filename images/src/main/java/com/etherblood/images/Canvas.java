package com.etherblood.images;

/**
 *
 * @author Philipp
 */
public interface Canvas {
    int width();
    int height();
    default int index(int x, int y) {
        return x + width() * y;
    }
    default boolean contains(int x, int y) {
        return 0 <= x && x < width() && 0 <= y && y < height();
    }
}
