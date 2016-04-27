package com.jy.exception;

@SuppressWarnings("serial")
public class AttributeDifferentException extends RuntimeException {
	
	public AttributeDifferentException(String msg){
		super(msg);
	}
	
	public AttributeDifferentException(String message, Throwable cause){
		super(message, cause);
	}
	
	public AttributeDifferentException(Throwable cause){
		super(cause);
	}
}
