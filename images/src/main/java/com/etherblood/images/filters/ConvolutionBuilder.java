package com.etherblood.images.filters;

/**
 *
 * @author Philipp
 */
public class ConvolutionBuilder {

    private float[] matrix;
    private int width, height, shiftX, shiftY;
    private float baseValue = 0;

    public ConvolutionBuilder withMatrix(float[] matrix) {
        int sqrt = (int) Math.sqrt(matrix.length);
        return withMatrix(matrix, sqrt, sqrt);
    }

    public ConvolutionBuilder withMatrix(float[] matrix, int width, int height) {
        if ((width & height & 1) == 0) {
            throw new IllegalArgumentException("width and height must be odd for centered convolution");
        }
        return withMatrix(matrix, width, height, width / -2, height / -2);
    }

    public ConvolutionBuilder withMatrix(float[] matrix, int width, int height, int shiftX, int shiftY) {
        if (width * height > matrix.length) {
            throw new IllegalArgumentException("float matrix is to small for given width * height");
        }
        this.matrix = matrix;
        this.width = width;
        this.height = height;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        return this;
    }

    public ConvolutionBuilder withBaseValue(float baseValue) {
        this.baseValue = baseValue;
        return this;
    }

    public Convolution build() {
        return new Convolution(matrix, width, height, shiftX, shiftY, baseValue);
    }

}
