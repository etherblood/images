package com.etherblood.images.rendering.blending;

import com.jme3.math.Vector4f;

/**
 *
 * @author Philipp
 */
public interface Blender {
    void blend(Vector4f top, Vector4f bottom, Vector4f output);
    boolean isOpaque(Vector4f top);
}
