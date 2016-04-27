package com.jy.exception;

@SuppressWarnings("serial")
public class AlreadyExistsException extends RuntimeException {
	
	public AlreadyExistsException(String msg){
		super(msg);
	}
	
	public AlreadyExistsException(String message, Throwable cause){
		super(message, cause);
	}
	
	public AlreadyExistsException(Throwable cause){
		super(cause);
	}
}
