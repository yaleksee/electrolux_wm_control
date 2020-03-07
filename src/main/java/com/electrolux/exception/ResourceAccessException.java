package com.electrolux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAccessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceAccessException(String message){
    	super(message);
    }

	public ResourceAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}