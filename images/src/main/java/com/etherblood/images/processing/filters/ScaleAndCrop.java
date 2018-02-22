package com.etherblood.images.processing.filters;

import com.etherblood.images.processing.FloatChannel;
import java.awt.Rectangle;
import com.etherblood.images.processing.interpolation.FloatChannelInterpolator;

/**
 *
 * @author Philipp
 */
public class ScaleAndCrop {

    private final FloatChannelInterpolator interpolator;

    public ScaleAndCrop(FloatChannelInterpolator interpolator) {
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
