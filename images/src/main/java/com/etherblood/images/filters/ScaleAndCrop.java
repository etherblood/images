package com.etherblood.images.filters;

import com.etherblood.images.FloatChannel;
import com.etherblood.images.interpolation.Interpolator;
import java.awt.Rectangle;

/**
 *
 * @author Philipp
 */
public class ScaleAndCrop {

    private final Interpolator interpolator;

    public ScaleAndCrop(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void apply(FloatChannel source, Rectangle sourceView, FloatChannel target, Rectangle targetView) {
        float stepSizeX = (float) sourceView.width / targetView.width;
        float stepSizeY = (float) sourceView.height / targetView.height;

        for (int y = 0; y < targetView.height; y++) {
            float sourceY = sourceView.y + y * stepSizeY;
            int targetY = targetView.y + y;
            for (int x = 0; x < targetView.width; x++) {
                float sourceX = sourceView.x + x * stepSizeX;
                int targetX = targetView.x + x;
                for (int i = 0; i < 4; i++) {
                    float value = interpolator.interpolate(source, sourceX, sourceY);
                    target.set(targetX, targetY, value);
                }
            }
        }
    }
}
