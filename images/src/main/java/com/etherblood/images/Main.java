package com.etherblood.images;

import com.etherblood.images.processing.FloatImage;
import com.etherblood.images.processing.ChannelType;
import com.etherblood.images.processing.ImageConverter;
import com.etherblood.images.processing.FloatChannel;
import com.etherblood.images.processing.blending.MagnitudeBlender;
import com.etherblood.images.processing.filters.WrapMode;
import com.etherblood.images.processing.filters.convolution.DefaultConvolutions;
import com.etherblood.images.rendering.BufferType;
import com.etherblood.images.rendering.TriRastering;
import com.etherblood.images.rendering.shaders.Shader;
import com.etherblood.images.rendering.Vector4fImage;
import com.etherblood.images.rendering.VertexBuffers;
import com.etherblood.images.rendering.blending.AlphaBlender;
import com.etherblood.images.rendering.sampling.ExampleTextureSampler;
import com.etherblood.images.rendering.shaders.DefaultShader;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Philipp
 */
public class Main {

    private static final String TEST_IMAGE_FILE = "image.jpg";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        detectEdges();
        renderTri();
    }

    private static void renderTri() throws IOException {
        int width = 100;
        int height = 100;
        FloatImage image = new FloatImage(width, height);
        image.setChannel(ChannelType.ALPHA, new FloatChannel(width, height, 1));
        image.setChannel(ChannelType.RED, new FloatChannel(width, height, 0));
        image.setChannel(ChannelType.GREEN, new FloatChannel(width, height, 0));
        image.setChannel(ChannelType.BLUE, new FloatChannel(width, height, 0));
        Shader shaderRed = (Vector3f position, Vector3f normal, Vector4f color, Vector3f texture, Vector4f output) -> {
            output.setY(1);
        };
        Shader shaderGreen = (Vector3f position, Vector3f normal, Vector4f color, Vector3f texture, Vector4f output) -> {
            output.setZ(1);
        };
        Shader shaderBlue = (Vector3f position, Vector3f normal, Vector4f color, Vector3f texture, Vector4f output) -> {
            output.setW(1);
        };

        Vector4fImage vImage = new ImageConverter().toVector4fImage(image, ChannelType.RED, ChannelType.GREEN, ChannelType.BLUE, ChannelType.ALPHA);

        new TriRastering(new DefaultShader(new ExampleTextureSampler()), new AlphaBlender(), new VertexBuffers()
                .withBuffer(BufferType.INDEX, IntStream.range(0, 9).toArray())
                .withBuffer(BufferType.VERTEX, new Vector3f[]{
            new Vector3f(20, 30, 0), new Vector3f(80, 10, 0), new Vector3f(50, 90, 0),
            new Vector3f(50, 10, 0), new Vector3f(60, 30, 0), new Vector3f(20, 70, 0),
            new Vector3f(80, 10, 0), new Vector3f(20, 30, 0), new Vector3f(-10, -15, 0)})
                .withBuffer(BufferType.TEXTURE, new Vector3f[]{
            Vector3f.UNIT_X, Vector3f.UNIT_Y, Vector3f.UNIT_Z,
            Vector3f.UNIT_X, Vector3f.UNIT_Y, Vector3f.UNIT_Z,
            Vector3f.UNIT_X, Vector3f.UNIT_Y, Vector3f.UNIT_Z
        })).rasterTo(vImage);

        image = new ImageConverter().toFloatImage(vImage, ChannelType.RED, ChannelType.GREEN, ChannelType.BLUE, ChannelType.ALPHA);

        BufferedImage bufferedImage = new ImageConverter().toBufferedImage(image);
        ImageIO.write(bufferedImage, "png", new File("tri.png"));
    }

    private static void detectEdges() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(TEST_IMAGE_FILE));
        FloatImage image = new ImageConverter().toFloatImageImage(bufferedImage);

        FloatChannel tmp = new FloatChannel(image.width(), image.height());
        FloatChannel tmp2 = new FloatChannel(image.width(), image.height());
        for (ChannelType channelKey : Arrays.asList(ChannelType.RED, ChannelType.GREEN, ChannelType.BLUE)) {
            FloatChannel source = image.getChannel(channelKey);

            DefaultConvolutions.SOBEL_Y.process(source, tmp, WrapMode.CLAMP);
            DefaultConvolutions.SOBEL_X.process(source, tmp2, WrapMode.CLAMP);

            new MagnitudeBlender().process(tmp, tmp2, source);
        }
        Arrays.fill(image.getChannel(ChannelType.ALPHA).data(), 1);

        bufferedImage = new ImageConverter().toBufferedImage(image);
        ImageIO.write(bufferedImage, "png", new File("computed_" + TEST_IMAGE_FILE.replace(".jpg", ".png")));
    }

}
