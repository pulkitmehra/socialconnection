package com.exercise.socialconnection.services;

/**
 * Exception raised if caller request for an unknown person.
 *  
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class UnknownPersonException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -135753247996391649L;

    /**
     * Instantiates a new unknown person exception.
     *
     * @param message the message
     * @param exception the exception
     */
    public UnknownPersonException(String message, Exception exception) {
        super(message, exception);
    }

    /**
     * Instantiates a new unknown person exception.
     *
     * @param message the message
     */
    public UnknownPersonException(String message) {
        super(message);
    }

    /**
     * Instantiates a new unknown person exception.
     *
     * @param cause the cause
     */
    public UnknownPersonException(Exception cause) {
        super(cause);
    }
}
