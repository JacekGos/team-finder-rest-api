package com.jacekg.teamfinder.exceptions;

import java.util.List;

import org.springframework.validation.FieldError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {

		ErrorResponse error = new ErrorResponse();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exception.getMessage());
		error.setErrorCode(0);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException exception) {

		ErrorResponse error = new ErrorResponse();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exception.getMessage());
		error.setErrorCode(1);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	//Forms validation error
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
	public ValidationErrorResponse handleException(MethodArgumentNotValidException exception) {

		 BindingResult result = exception.getBindingResult();
		 List<FieldError> fieldErrors = result.getFieldErrors();
		 return processFieldErrors(fieldErrors);
	}
	
	private ValidationErrorResponse processFieldErrors(List<FieldError> fieldErrors) {
		
		ValidationErrorResponse error = new ValidationErrorResponse
				(HttpStatus.BAD_REQUEST.value(), "validation error", 2);
		
		for (FieldError fieldError : fieldErrors) {
			error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return error; 
	}

	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(UserNotValidException exception) {

		ErrorResponse error = new ErrorResponse();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exception.getMessage());
		error.setErrorCode(3);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
    @ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(HttpRequestMethodNotSupportedException exception) {

		ErrorResponse error = new ErrorResponse();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exception.getMessage());
		error.setErrorCode(4);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(InvalidCredentialsException exc) {

		ErrorResponse error = new ErrorResponse();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setErrorCode(5);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(SaveVenueException exc) {

		ErrorResponse error = new ErrorResponse();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setErrorCode(6);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(SaveGameException exc) {

		ErrorResponse error = new ErrorResponse();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setErrorCode(7);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}






