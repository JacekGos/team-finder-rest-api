package com.jacekg.teamfinder.exceptions;

public class UserNotValidException extends RuntimeException {

	public UserNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotValidException(String message) {
		super(message);
	}

	public UserNotValidException(Throwable cause) {
		super(cause);
	}
}
