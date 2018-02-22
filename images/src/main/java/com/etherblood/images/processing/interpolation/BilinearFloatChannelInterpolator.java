package com.etherblood.images.processing.interpolation;

import com.etherblood.images.processing.FloatChannel;

public class BilinearFloatChannelInterpolator implements FloatChannelInterpolator {

    @Override
    public float interpolate(FloatChannel channel, float x, float y) {
        int minX = (int) Math.floor(x);
        int maxX = minX + 1;
        int minY = (int) Math.floor(y);
        int maxY = minY + 1;

        float a = channel.get(minX, minY);
        float b = maxX != channel.width() ? channel.get(maxX, minY) : a;
        float c = maxY != channel.height() ? channel.get(minX, maxY) : a;
        float d = maxX != channel.width() ? channel.get(maxX, maxY) : c;

        float ab = a * (maxX - x) + b * (x - minX);
        float cd = c * (maxX - x) + d * (x - minX);
        float abcd = ab * (maxY - y) + cd * (y - minY);
        return abcd;
    }

}
