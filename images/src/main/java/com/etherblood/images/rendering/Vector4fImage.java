package com.etherblood.images.rendering;

import com.etherblood.images.processing.Canvas;
import com.jme3.math.Vector4f;
import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public class Vector4fImage implements Canvas {

    private final Vector4f[] data;
    private final int width, height;

    public Vector4fImage(int width, int height, Vector4f fillValue) {
        this(width, height);
        Arrays.fill(data, fillValue);
    }

    public Vector4fImage(int width, int height) {
        this(new Vector4f[width * height], width, height);
    }

    public Vector4fImage(Vector4f[] data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public Vector4f[] data() {
        return data;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int size() {
        return width * height;
    }

    public Vector4f get(int index) {
        return data[index];
    }

    public Vector4f get(int x, int y) {
        return get(index(x, y));
    }

    public void set(int index, Vector4f value) {
        data[index] = value;
    }

    public void set(int x, int y, Vector4f value) {
        set(index(x, y), value);
    }

}
