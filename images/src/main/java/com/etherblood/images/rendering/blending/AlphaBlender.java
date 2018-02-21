package com.etherblood.images.rendering.blending;

import com.jme3.math.Vector4f;

public class AlphaBlender implements Blender {

    @Override
    public void blend(Vector4f top, Vector4f bottom, Vector4f output) {
        output.x = top.w * top.x + (1 - top.w) * bottom.w * bottom.x;
        output.y = top.w * top.y + (1 - top.w) * bottom.w * bottom.y;
        output.z = top.w * top.z + (1 - top.w) * bottom.w * bottom.z;
        output.w = top.w + bottom.w - top.w * bottom.w;
    }

    @Override
    public boolean isOpaque(Vector4f top) {
        return top.w >= 1;
    }

}
