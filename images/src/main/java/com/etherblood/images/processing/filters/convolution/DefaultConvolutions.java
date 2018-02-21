package com.etherblood.images.processing.filters.convolution;

/**
 *
 * @author Philipp
 */
public class DefaultConvolutions {

    public final static Convolution SOBEL_X = new ConvolutionBuilder().withMatrix(new float[]{
        -1 / 4f, 0, 1 / 4f,
        -2 / 4f, 0, 2 / 4f,
        -1 / 4f, 0, 1 / 4f}).build();
    public final static Convolution SOBEL_Y = new ConvolutionBuilder().withMatrix(new float[]{
        -1 / 4f, -2 / 4f, -1 / 4f,
        0, 0, 0,
        1 / 4f, 2 / 4f, 1 / 4f}).build();
    public final static Convolution GAUSS_1 = new ConvolutionBuilder().withMatrix(new float[]{
        1 / 16f, 2 / 16f, 1 / 16f,
        2 / 16f, 4 / 16f, 2 / 16f,
        1 / 16f, 2 / 16f, 1 / 16f}).build();
    public final static Convolution LAPLACE_1 = new ConvolutionBuilder().withMatrix(new float[]{
        1 / 12f, 2 / 12f, 1 / 12f,
        2 / 12f, -12 / 12f, 2 / 12f,
        1 / 12f, 2 / 12f, 1 / 12f}).build();
    public final static Convolution IDENTITY = new ConvolutionBuilder().withMatrix(new float[]{1}).build();

}
