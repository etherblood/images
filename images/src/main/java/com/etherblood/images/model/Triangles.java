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
public class Triangles {

    private List<Triangle> faces;

    @JacksonXmlProperty(localName = "face")
    @JacksonXmlElementWrapper(useWrapping = false)
    public void setFaces(List<Triangle> faces) {
        this.faces = faces;
    }

    public List<Triangle> getFaces() {
        return faces;
    }

}
