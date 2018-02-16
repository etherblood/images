package com.etherblood.images.interpolation;

import com.etherblood.images.FloatChannel;

/**
 *
 * @author Philipp
 */
public interface Interpolator {
    float interpolate(FloatChannel channel, float x, float y);
}
