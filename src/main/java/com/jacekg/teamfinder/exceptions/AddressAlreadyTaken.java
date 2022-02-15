package com.jacekg.teamfinder.exceptions;

public class AddressAlreadyTaken extends RuntimeException {

	public AddressAlreadyTaken(String message, Throwable cause) {
		super(message, cause);
	}

	public AddressAlreadyTaken(String message) {
		super(message);
	}

	public AddressAlreadyTaken(Throwable cause) {
		super(cause);
	}
}
