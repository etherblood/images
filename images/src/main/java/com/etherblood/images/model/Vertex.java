package com.etherblood.images.model;

import com.jme3.math.Vector3f;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Philipp
 */
public class Vertex {
    private Vector3f position, normal;
    private Vector2f texcoord;

    public Vector3f getPosition() {
        return position;
    }

    @XmlElement
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getNormal() {
        return normal;
    }

    @XmlElement
    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    public Vector2f getTexcoord() {
        return texcoord;
    }

    @XmlElement
    public void setTexcoord(Vector2f texcoord) {
        this.texcoord = texcoord;
    }
}
