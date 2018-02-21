package com.etherblood.images.rendering.shaders;

import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;

/**
 *
 * @author Philipp
 */
public interface Shader {
    void process(Vector3f position, Vector3f normal, Vector4f color, Vector3f texture, Vector4f result);
}
