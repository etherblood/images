package com.etherblood.images.model;

import com.jme3.math.Vector3f;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Philipp
 */
public class Vector2f {

    private float u, v;

    public float getU() {
        return u;
    }

    @XmlAttribute
    public void setU(float u) {
        this.u = u;
    }

    public float getV() {
        return v;
    }

    @XmlAttribute
    public void setV(float v) {
        this.v = v;
    }

    public Vector3f toVector3f() {
        return new Vector3f(u, v, 0);
    }
}
