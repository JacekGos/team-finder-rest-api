package com.jacekg.teamfinder.exceptions;

public class SaveGameException extends RuntimeException {

	public SaveGameException(String message, Throwable cause) {
		super(message, cause);
	}

	public SaveGameException(String message) {
		super(message);
	}

	public SaveGameException(Throwable cause) {
		super(cause);
	}
}
