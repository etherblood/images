package com.etherblood.images;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Philipp
 */
public class ImageConverter {

    public BufferedImage toBufferedImage(FloatImage image) {
        BufferedImage bufferImg = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                float alpha = image.getChannel(ChannelType.ALPHA).get(x, y);
                float red = image.getChannel(ChannelType.RED).get(x, y);
                float green = image.getChannel(ChannelType.GREEN).get(x, y);
                float blue = image.getChannel(ChannelType.BLUE).get(x, y);
                int argb = argb(alpha, red, green, blue);
                bufferImg.setRGB(x, y, argb);
            }
        }
        return bufferImg;
    }

    public FloatImage fromBufferedImage(BufferedImage image) {
        if (image.getType() != BufferedImage.TYPE_INT_ARGB) {
            BufferedImage convertedImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = convertedImg.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            image = convertedImg;
        }
        FloatImage result = new FloatImage(image.getWidth(), image.getHeight());
        result.setChannel(ChannelType.ALPHA, new FloatChannel(result.width(), result.height()));
        result.setChannel(ChannelType.RED, new FloatChannel(result.width(), result.height()));
        result.setChannel(ChannelType.GREEN, new FloatChannel(result.width(), result.height()));
        result.setChannel(ChannelType.BLUE, new FloatChannel(result.width(), result.height()));
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = image.getRGB(x, y);
                result.getChannel(ChannelType.ALPHA).set(x, y, alpha(argb));
                result.getChannel(ChannelType.RED).set(x, y, red(argb));
                result.getChannel(ChannelType.GREEN).set(x, y, green(argb));
                result.getChannel(ChannelType.BLUE).set(x, y, blue(argb));
            }
        }
        return result;
    }
    
    private float alpha(int argb) {
        return ((argb >>> 24) & 0xff) / 256f;
    }
    
    private float red(int argb) {
        return ((argb >>> 16) & 0xff) / 256f;
    }
    
    private float green(int argb) {
        return ((argb >>> 8) & 0xff) / 256f;
    }
    
    private float blue(int argb) {
        return ((argb >>> 0) & 0xff) / 256f;
    }
    
    private int argb(float alpha, float red, float green, float blue) {
        assert 0 <= alpha && alpha < 1;
        assert 0 <= red && red < 1;
        assert 0 <= green && green < 1;
        assert 0 <= blue && blue < 1;
        int result = 0;
        result |= (int)(256 * alpha);
        result <<= 8;
        result |= (int)(256 * red);
        result <<= 8;
        result |= (int)(256 * green);
        result <<= 8;
        result |= (int)(256 * blue);
        return result;
    }
}
