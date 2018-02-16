package com.etherblood.images.interpolation;

import com.etherblood.images.FloatChannel;


public class NearestNeighborInterpolator implements Interpolator {

    @Override
    public float interpolate(FloatChannel channel, float x, float y) {
        return channel.get(Math.round(x), Math.round(y));
    }

}
