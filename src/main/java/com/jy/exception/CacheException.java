package com.jy.exception;

public abstract class CacheException extends RuntimeException {

	private static final long serialVersionUID = 8783675611387039462L;

	public CacheException(String msg) {
		super(msg);
	}

	public CacheException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
