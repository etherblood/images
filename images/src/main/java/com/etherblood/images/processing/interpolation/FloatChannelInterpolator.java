package com.etherblood.images.processing.interpolation;

import com.etherblood.images.processing.FloatChannel;

/**
 *
 * @author Philipp
 */
public interface FloatChannelInterpolator {
    float interpolate(FloatChannel channel, float x, float y);
}
