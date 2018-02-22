package com.etherblood.images.rendering.sampling;

import com.etherblood.images.rendering.Vector4fImage;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.etherblood.images.processing.interpolation.FloatImageInterpolator;

public class SimpleTextureSampler implements TextureSampler {

    private final Vector4fImage texture;
    private final FloatImageInterpolator interpolator;

    public SimpleTextureSampler(Vector4fImage texture, FloatImageInterpolator interpolator) {
        this.texture = texture;
        this.interpolator = interpolator;
    }

    @Override
    public void sample(Vector3f texPos, Vector4f output) {
        interpolator.interpolate(texture, texPos.x * texture.width(), texPos.y * texture.height(), output);
    }

}
