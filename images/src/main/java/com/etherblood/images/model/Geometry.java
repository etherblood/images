package com.etherblood.images.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Philipp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {

    private VertexBuffer vertexbuffer;

    public VertexBuffer getVertexbuffer() {
        return vertexbuffer;
    }

    public void setVertexbuffer(VertexBuffer vertexbuffer) {
        this.vertexbuffer = vertexbuffer;
    }
}
