package com.etherblood.images;

import com.etherblood.images.blending.MagnitudeBlender;
import com.etherblood.images.filters.WrapMode;
import com.etherblood.images.filters.Convolution;
import com.etherblood.images.filters.ConvolutionBuilder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author Philipp
 */
public class Main {

    private static final float MAX_VALUE = Float.intBitsToFloat(Float.floatToIntBits(1) - 1);
    private static final String TEST_IMAGE_FILE = "image.jpg";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(TEST_IMAGE_FILE));
        FloatImage image = new ImageConverter().fromBufferedImage(bufferedImage);

        Convolution sobelX = new ConvolutionBuilder().withMatrix(new float[]{
            -1 / 4f, 0, 1 / 4f,
            -2 / 4f, 0, 2 / 4f,
            -1 / 4f, 0, 1 / 4f}).build();
        Convolution sobelY = new ConvolutionBuilder().withMatrix(new float[]{
            -1 / 4f, -2 / 4f, -1 / 4f,
            0, 0, 0,
            1 / 4f, 2 / 4f, 1 / 4f}).build();
        Convolution gauss = new ConvolutionBuilder().withMatrix(new float[]{
            1 / 16f, 2 / 16f, 1 / 16f,
            2 / 16f, 4 / 16f, 2 / 16f,
            1 / 16f, 2 / 16f, 1 / 16f}).build();
        Convolution laplace = new ConvolutionBuilder().withMatrix(new float[]{
            1 / 12f, 2 / 12f, 1 / 12f,
            2 / 12f, -12 / 12f, 2 / 12f,
            1 / 12f, 2 / 12f, 1 / 12f}).build();
        Convolution identity = new ConvolutionBuilder().withMatrix(new float[]{1}).build();

        FloatChannel tmp = new FloatChannel(image.width(), image.height());
        FloatChannel tmp2 = new FloatChannel(image.width(), image.height());
        for (ChannelType channelKey : Arrays.asList(ChannelType.RED, ChannelType.GREEN, ChannelType.BLUE)) {
            FloatChannel source = image.getChannel(channelKey);

            sobelY.process(source, tmp, WrapMode.CLAMP);
            sobelX.process(source, tmp2, WrapMode.CLAMP);

            new MagnitudeBlender().process(tmp, tmp2, source);
        }
        Arrays.fill(image.getChannel(ChannelType.ALPHA).data(), MAX_VALUE);

        bufferedImage = new ImageConverter().toBufferedImage(image);
        ImageIO.write(bufferedImage, "png", new File("computed_" + TEST_IMAGE_FILE.replace(".jpg", ".png")));
    }

}
