package com.etherblood.images.processing.interpolation;

import com.etherblood.images.rendering.Vector4fImage;
import com.jme3.math.Vector4f;

/**
 *
 * @author Philipp
 */
public interface FloatImageInterpolator {
    void interpolate(Vector4fImage image, float x, float y, Vector4f output);
}
