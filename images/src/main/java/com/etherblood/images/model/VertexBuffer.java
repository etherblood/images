package com.etherblood.images.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

/**
 *
 * @author Philipp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VertexBuffer {

    private List<Vertex> vertices;

    public List<Vertex> getVertices() {
        return vertices;
    }

    @JacksonXmlProperty(localName = "vertex")
    @JacksonXmlElementWrapper(useWrapping = false)
    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }
}
