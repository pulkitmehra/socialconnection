package com.exercise.socialconnection.web.model;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The Class ErrorResponse.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@XmlRootElement(name = "ErrorResponse")
@JsonTypeName("ErrorResponse")
public class ErrorResponse {

    /** The error message. */
    protected String errorMessage;

    /** The status code. */
    protected int statusCode;

    /** The requested uri. */
    protected String requestedURI;

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param value the new error message
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the status code.
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code.
     *
     * @param value the new status code
     */
    public void setStatusCode(int value) {
        this.statusCode = value;
    }

    /**
     * Gets the requested uri.
     *
     * @return the requested uri
     */
    public String getRequestedURI() {
        return requestedURI;
    }

    /**
     * Sets the requested uri.
     *
     * @param value the new requested uri
     */
    public void setRequestedURI(String value) {
        this.requestedURI = value;
    }

}
