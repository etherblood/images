package com.etherblood.images.processing.filters.convolution;

import com.etherblood.images.processing.FloatChannel;
import com.etherblood.images.processing.filters.WrapMode;
import java.util.Objects;

/**
 *
 * @author Philipp
 */
public class Convolution {

    private final float[] matrix;
    private final int width, height, shiftX, shiftY;
    private final float baseValue;

    Convolution(float[] matrix, int width, int height, int shiftX, int shiftY, float baseValue) {
        Objects.requireNonNull(matrix);
        this.matrix = matrix;
        this.width = width;
        this.height = height;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        this.baseValue = baseValue;
    }

    public void process(FloatChannel source, FloatChannel target, WrapMode wrap) {
        for (int targetY = 0; targetY < target.height(); targetY++) {
            for (int targetX = 0; targetX < target.width(); targetX++) {
                float value = baseValue;
                for (int matrixY = 0; matrixY < height; matrixY++) {
                    for (int matrixX = 0; matrixX < width; matrixX++) {
                        int sourceX = wrap.wrap(targetX + shiftX + matrixX, source.width());
                        int sourceY = wrap.wrap(targetY + shiftY + matrixY, source.height());
                        assert source.contains(sourceX, sourceY);
                        value += matrix[matrixX + width * matrixY] * source.get(sourceX, sourceY);
                    }
                }
                target.set(targetX, targetY, value);
            }
        }
    }
}
