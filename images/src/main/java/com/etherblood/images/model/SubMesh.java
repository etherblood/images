package com.etherblood.images.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Philipp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubMesh {

    private Triangles faces;

    public Triangles getFaces() {
        return faces;
    }

    public void setFaces(Triangles faces) {
        this.faces = faces;
    }
}
