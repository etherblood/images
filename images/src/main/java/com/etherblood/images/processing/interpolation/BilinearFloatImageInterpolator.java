package com.etherblood.images.processing.interpolation;

import com.etherblood.images.rendering.Vector4fImage;
import com.jme3.math.Vector4f;

public class BilinearFloatImageInterpolator implements FloatImageInterpolator {

    @Override
    public void interpolate(Vector4fImage image, float x, float y, Vector4f output) {
        int minX = (int) Math.floor(x);
        int maxX = minX + 1;
        int minY = (int) Math.floor(y);
        int maxY = minY + 1;

        Vector4f a = image.get(minX, minY);
        Vector4f b = maxX != image.width() ? image.get(maxX, minY) : a;
        Vector4f c = maxY != image.height() ? image.get(minX, maxY) : a;
        Vector4f d = maxX != image.width() ? image.get(maxX, maxY) : c;

        Vector4f ab = a.mult(maxX - x).addLocal(b.mult(x - minX));
        Vector4f cd = c.mult(maxX - x).addLocal(d.mult(x - minX));
        Vector4f abcd = ab.multLocal(maxY - y).addLocal(cd.multLocal(y - minY));
        output.set(abcd);
    }

}
