package com.exercise.socialconnection.services;

/**
 *  Exception for any person service error.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class PersonServiceException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7417921124343830080L;

    /**
     * Instantiates a new person service exception.
     *
     * @param message the message
     * @param exception the exception
     */
    public PersonServiceException(String message, Exception exception) {
        super(message, exception);
    }

    /**
     * Instantiates a new person service exception.
     *
     * @param message the message
     */
    public PersonServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new person service exception.
     *
     * @param cause the cause
     */
    public PersonServiceException(Exception cause) {
        super(cause);
    }
}
