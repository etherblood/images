package com.etherblood.images.rendering;

import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public class IntSetSparse {

    private final int[] array;
    private int pointer;

    public IntSetSparse(int capacity) {
        this.array = new int[capacity];
        this.pointer = 0;
    }

    public int get(int index) {
        return array[index];
    }

    public int size() {
        return pointer;
    }

    public int capacity() {
        return array.length;
    }

    public void toggle(int value) {
        int index = indexOf(value);
        if (index < 0) {
            insertAt(-index - 1, value);
        } else {
            removeAt(index);
        }
    }

    public boolean contains(int value) {
        return indexOf(value) >= 0;
    }

    private int indexOf(int value) {
        return Arrays.binarySearch(array, 0, pointer, value);
    }

    private void insertAt(int index, int value) {
        System.arraycopy(array, index, array, index + 1, pointer++ - index);
        array[index] = value;
    }

    private void removeAt(int index) {
        System.arraycopy(array, index + 1, array, index, --pointer - index);
    }

    public void clear() {
        pointer = 0;
    }

}
