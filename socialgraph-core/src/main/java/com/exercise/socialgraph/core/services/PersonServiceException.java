package com.exercise.socialgraph.core.services;

public class PersonServiceException extends Exception {

	private static final long serialVersionUID = 7417921124343830080L;

	public PersonServiceException(String message, Exception exception) {
		super(message, exception);
	}

	public PersonServiceException(String message) {
		super(message);
	}

	public PersonServiceException(Exception cause) {
		super(cause);
	}
}
