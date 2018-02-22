package com.etherblood.images.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philipp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mesh {

    private Geometry sharedgeometry;
    private SubMeshes submeshes;

    public Geometry getSharedgeometry() {
        return sharedgeometry;
    }

    public void setSharedgeometry(Geometry sharedgeometry) {
        this.sharedgeometry = sharedgeometry;
    }

    public SubMeshes getSubmeshes() {
        return submeshes;
    }

    public void setSubmeshes(SubMeshes submeshes) {
        this.submeshes = submeshes;
    }
}
