package com.jacekg.teamfinder.exceptions;

public class SaveVenueException extends RuntimeException {

	public SaveVenueException(String message, Throwable cause) {
		super(message, cause);
	}

	public SaveVenueException(String message) {
		super(message);
	}

	public SaveVenueException(Throwable cause) {
		super(cause);
	}
}
