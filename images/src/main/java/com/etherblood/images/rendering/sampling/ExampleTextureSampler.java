package com.etherblood.images.rendering.sampling;

import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;


public class ExampleTextureSampler implements TextureSampler {

    @Override
    public void sample(Vector3f texPos, Vector4f output) {
        output.set(texPos.x, texPos.y, texPos.z, 0.5f);
    }

}
