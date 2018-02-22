package com.etherblood.images;

import com.etherblood.images.model.Mesh;
import com.etherblood.images.model.Triangle;
import com.etherblood.images.model.Vertex;
import com.etherblood.images.processing.FloatImage;
import com.etherblood.images.processing.ChannelType;
import com.etherblood.images.processing.ImageConverter;
import com.etherblood.images.processing.FloatChannel;
import com.etherblood.images.processing.blending.MagnitudeBlender;
import com.etherblood.images.processing.filters.WrapMode;
import com.etherblood.images.processing.filters.convolution.DefaultConvolutions;
import com.etherblood.images.processing.interpolation.BilinearFloatImageInterpolator;
import com.etherblood.images.rendering.BufferType;
import com.etherblood.images.rendering.TriRastering;
import com.etherblood.images.rendering.Vector4fImage;
import com.etherblood.images.rendering.VertexBuffers;
import com.etherblood.images.rendering.blending.AlphaBlender;
import com.etherblood.images.rendering.sampling.ExampleTextureSampler;
import com.etherblood.images.rendering.sampling.SimpleTextureSampler;
import com.etherblood.images.rendering.sampling.TextureSampler;
import com.etherblood.images.rendering.shaders.DefaultFragmentShader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jme3.math.Vector3f;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Philipp
 */
public class Main {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        renderOz();
        detectEdges();
//        renderTri();
    }
    
    
    private static void renderOz() throws IOException {
        ObjectMapper objectMapper = new XmlMapper();
        Mesh mesh = objectMapper.readValue(new File("tests/sources/oz/oz.mesh.xml"), Mesh.class);
        
        List<Vertex> vertices = mesh.getSharedgeometry().getVertexbuffer().getVertices();
        Vector3f[] positions = new Vector3f[vertices.size()];
        Vector3f[] normals = new Vector3f[vertices.size()];
        Vector3f[] texCoords = new Vector3f[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            Vertex v = vertices.get(i);
            positions[i] = v.getPosition().mult(new Vector3f(200, -200, -1)).add(500, 900, 0);
            normals[i] = v.getNormal();
            texCoords[i] = v.getTexcoord().toVector3f();
        }
        List<Triangle> faces = mesh.getSubmeshes().getSubmeshes().get(0).getFaces().getFaces();
        int[] indices = new int[3 * faces.size()];
        for (int i = 0; i < faces.size(); i++) {
            Triangle tri = faces.get(i);
            int j = 3 * i;
            indices[j] = tri.getV1();
            indices[j + 1] = tri.getV2();
            indices[j + 2] = tri.getV3();
        }
        VertexBuffers buffers = new VertexBuffers();
        buffers.set(BufferType.INDEX, indices);
        buffers.set(BufferType.NORMAL, normals);
        buffers.set(BufferType.TEXTURE, texCoords);
        buffers.set(BufferType.POSITION, positions);
        
        
        
        int width = 1000;
        int height = 1000;
        FloatImage image = new FloatImage(width, height);
        image.setChannel(ChannelType.ALPHA, new FloatChannel(width, height, 1));
        image.setChannel(ChannelType.RED, new FloatChannel(width, height, 0));
        image.setChannel(ChannelType.GREEN, new FloatChannel(width, height, 0));
        image.setChannel(ChannelType.BLUE, new FloatChannel(width, height, 0));

        Vector4fImage vImage = new ImageConverter().toVector4fImage(image, ChannelType.RED, ChannelType.GREEN, ChannelType.BLUE, ChannelType.ALPHA);

        Vector4fImage texture = new ImageConverter().toVector4fImage(ImageIO.read(new File("tests/sources/oz/resources/skin_oz_junior.png")));
        TextureSampler textureSampler = new SimpleTextureSampler(texture, new BilinearFloatImageInterpolator());
        new TriRastering(new DefaultFragmentShader(textureSampler), new AlphaBlender(), buffers).raster(new FloatChannel(vImage.width(), vImage.height(), 1), vImage);

        image = new ImageConverter().toFloatImage(vImage, ChannelType.RED, ChannelType.GREEN, ChannelType.BLUE, ChannelType.ALPHA);

        BufferedImage bufferedImage = new ImageConverter().toBufferedImage(image);
        ImageIO.write(bufferedImage, "png", new File("tests/results/oz.png"));
    }


    private static void renderTri() throws IOException {
        int width = 100;
        int height = 100;
        FloatImage image = new FloatImage(width, height);
        image.setChannel(ChannelType.ALPHA, new FloatChannel(width, height, 1));
        image.setChannel(ChannelType.RED, new FloatChannel(width, height, 0));
        image.setChannel(ChannelType.GREEN, new FloatChannel(width, height, 0));
        image.setChannel(ChannelType.BLUE, new FloatChannel(width, height, 0));

        Vector4fImage vImage = new ImageConverter().toVector4fImage(image, ChannelType.RED, ChannelType.GREEN, ChannelType.BLUE, ChannelType.ALPHA);

        new TriRastering(new DefaultFragmentShader(new ExampleTextureSampler()), new AlphaBlender(), new VertexBuffers()
                .withBuffer(BufferType.INDEX, IntStream.range(0, 9).toArray())
                .withBuffer(BufferType.POSITION, new Vector3f[]{
            new Vector3f(20, 30, 0), new Vector3f(80, 10, 0), new Vector3f(50, 90, 0),
            new Vector3f(50, 10, 0), new Vector3f(60, 30, 0), new Vector3f(20, 70, 0),
            new Vector3f(80, 10, 0), new Vector3f(20, 30, 0), new Vector3f(-10, -15, 0)})
                .withBuffer(BufferType.TEXTURE, new Vector3f[]{
            Vector3f.UNIT_X, Vector3f.UNIT_Y, Vector3f.UNIT_Z,
            Vector3f.UNIT_X, Vector3f.UNIT_Y, Vector3f.UNIT_Z,
            Vector3f.UNIT_X, Vector3f.UNIT_Y, Vector3f.UNIT_Z
        })).raster(new FloatChannel(vImage.width(), vImage.height(), 1), vImage);

        image = new ImageConverter().toFloatImage(vImage, ChannelType.RED, ChannelType.GREEN, ChannelType.BLUE, ChannelType.ALPHA);

        BufferedImage bufferedImage = new ImageConverter().toBufferedImage(image);
        ImageIO.write(bufferedImage, "png", new File("tri.png"));
    }

    private static void detectEdges() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File("tests/sources/source_image.png"));
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
        ImageIO.write(bufferedImage, "png", new File("tests/results/computed_image.png"));
    }

}
