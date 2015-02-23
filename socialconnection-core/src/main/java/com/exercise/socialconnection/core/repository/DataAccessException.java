package com.exercise.socialconnection.core.repository;

/**
 * Exception for any data access related errors.
 * 
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class DataAccessException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7417921124343830080L;

    /**
     * Instantiates a new data access exception.
     *
     * @param message the message
     * @param exception the exception
     */
    public DataAccessException(String message, Exception exception) {
        super(message, exception);
    }

    /**
     * Instantiates a new data access exception.
     *
     * @param message the message
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Instantiates a new data access exception.
     *
     * @param cause the cause
     */
    public DataAccessException(Exception cause) {
        super(cause);
    }
}
