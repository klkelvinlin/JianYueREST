package com.jy.exception;

@SuppressWarnings("serial")
public class DataAccessException extends RuntimeException {
	
	public DataAccessException(String msg){
		super(msg);
	}
	
	public DataAccessException(String message, Throwable cause){
		super(message, cause);
	}
	
	public DataAccessException(Throwable cause){
		super(cause);
	}
}
