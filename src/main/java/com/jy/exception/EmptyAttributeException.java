package com.jy.exception;

@SuppressWarnings("serial")
public class EmptyAttributeException extends RuntimeException {
	
	public EmptyAttributeException(String msg){
		super(msg);
	}
	
	public EmptyAttributeException(String message, Throwable cause){
		super(message, cause);
	}
	
	public EmptyAttributeException(Throwable cause){
		super(cause);
	}
}
