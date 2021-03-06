package com.etherblood.images.processing.interpolation;

import com.etherblood.images.processing.FloatChannel;


public class NearestNeighborFloatChannelInterpolator implements FloatChannelInterpolator {

    @Override
    public float interpolate(FloatChannel channel, float x, float y) {
        return channel.get(Math.round(x), Math.round(y));
    }

}
