package com.etherblood.images.rendering.sampling;

import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;

/**
 *
 * @author Philipp
 */
public interface TextureSampler {
    void sample(Vector3f texPos, Vector4f output);
}
