package com.jy.exception;

public class FatalCacheException extends CacheException {
	private static final long serialVersionUID = 1452334422376214381L;

	public FatalCacheException(String msg) {
		super(msg);
	}

	public FatalCacheException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
