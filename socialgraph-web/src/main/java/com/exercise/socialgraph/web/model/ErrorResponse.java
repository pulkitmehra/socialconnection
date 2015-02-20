package com.exercise.socialgraph.web.model;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonTypeName;

@XmlRootElement(name = "ErrorResponse")
@JsonTypeName("ErrorResponse")
public class ErrorResponse {

    protected String errorMessage;
    protected int statusCode;
    protected String requestedURI;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int value) {
        this.statusCode = value;
    }

    public String getRequestedURI() {
        return requestedURI;
    }

    public void setRequestedURI(String value) {
        this.requestedURI = value;
    }

}
