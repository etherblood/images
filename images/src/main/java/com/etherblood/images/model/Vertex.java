package com.etherblood.images.model;

import com.jme3.math.Vector3f;

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

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    public Vector2f getTexcoord() {
        return texcoord;
    }

    public void setTexcoord(Vector2f texcoord) {
        this.texcoord = texcoord;
    }
}
