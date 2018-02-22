package com.etherblood.images.rendering.shaders;

import com.etherblood.images.rendering.sampling.TextureSampler;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;


public class DefaultFragmentShader implements FragmentShader {

    private final TextureSampler sampler;

    public DefaultFragmentShader(TextureSampler sampler) {
        this.sampler = sampler;
    }
    
    @Override
    public void process(Vector3f position, Vector3f normal, Vector4f color, Vector3f texture, Vector4f result) {
        if(sampler != null && texture != null) {
            sampler.sample(texture, result);
        } else {
            result.set(Vector4f.UNIT_XYZW);
        }
        
        if(color != null) {
            result.multLocal(color);
        }
    }

}
