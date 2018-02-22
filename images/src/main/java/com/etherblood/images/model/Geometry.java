package com.etherblood.images.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlElement;

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

    @XmlElement
    public void setVertexbuffer(VertexBuffer vertexbuffer) {
        this.vertexbuffer = vertexbuffer;
    }
}
