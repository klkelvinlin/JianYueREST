package com.jy.exception;

@SuppressWarnings("serial")
public class InvalidAttributesException extends RuntimeException {
	
	public InvalidAttributesException(String msg){
		super(msg);
	}
	
	public InvalidAttributesException(String message, Throwable cause){
		super(message, cause);
	}
	
	public InvalidAttributesException(Throwable cause){
		super(cause);
	}
}
