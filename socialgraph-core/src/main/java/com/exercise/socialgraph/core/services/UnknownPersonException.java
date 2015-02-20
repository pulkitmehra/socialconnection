package com.exercise.socialgraph.core.services;

public class UnknownPersonException extends Exception {

	private static final long serialVersionUID = -135753247996391649L;

	public UnknownPersonException(String message, Exception exception) {
		super(message, exception);
	}

	public UnknownPersonException(String message) {
		super(message);
	}

	public UnknownPersonException(Exception cause) {
		super(cause);
	}
}
