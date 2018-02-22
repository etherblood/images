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
public class SubMeshes {

    private List<SubMesh> submeshes;

    @JacksonXmlProperty(localName = "submesh")
    @JacksonXmlElementWrapper(useWrapping = false)
    public void setSubmeshes(List<SubMesh> submeshes) {
        this.submeshes = submeshes;
    }

    public List<SubMesh> getSubmeshes() {
        return submeshes;
    }
}
