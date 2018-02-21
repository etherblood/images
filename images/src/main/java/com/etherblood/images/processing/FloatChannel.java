package com.etherblood.images.processing;

import com.etherblood.images.processing.filters.ValueTransform;
import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public class FloatChannel implements Canvas {

    private final float[] data;
    private final int width, height;

    public FloatChannel(int width, int height, float fillValue) {
        this(width, height);
        Arrays.fill(data, fillValue);
    }

    public FloatChannel(int width, int height) {
        this(new float[width * height], width, height);
    }

    public FloatChannel(float[] data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public void mapEach(ValueTransform transform) {
        for (int i = 0; i < size(); i++) {
            set(i, transform.process(get(i)));
        }
    }

    public float[] data() {
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

    public float get(int index) {
        return data[index];
    }

    public float get(int x, int y) {
        return get(index(x, y));
    }

    public void set(int index, float value) {
        data[index] = value;
    }

    public void set(int x, int y, float value) {
        set(index(x, y), value);
    }
}
