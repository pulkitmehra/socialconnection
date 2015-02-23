package com.exercise.socialconnection.web.model;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The Class InfoResponse.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@XmlRootElement(name = "InfoResponse")
@JsonTypeName("InfoResponse")
public class InfoResponse {

    /** The value. */
    private Integer value;

    /**
     * Instantiates a new info response.
     */
    public InfoResponse() {

    }

    /**
     * Instantiates a new info response.
     *
     * @param value the value
     */
    public InfoResponse(Integer value) {
        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

}
