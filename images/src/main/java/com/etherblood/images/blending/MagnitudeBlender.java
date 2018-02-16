package com.etherblood.images.blending;

import com.etherblood.images.FloatChannel;


public class MagnitudeBlender {

    public void process(FloatChannel source1, FloatChannel source2, FloatChannel target) {
        for (int i = 0; i < target.size(); i++) {
            float a = source1.get(i);
            float b = source2.get(i);
            float result = (float) Math.sqrt((a * a + b * b) / 2);
            target.set(i, result);
        }
    }

}
