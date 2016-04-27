package com.jy.exception;

@SuppressWarnings("serial")
public class InvalidObjectInCacheException extends CacheException {
	/**
	 * Construct a <code>InvalidObjectInCacheException</code> with the specified
	 * detail message.
	 *
	 * @param msg the detail message
	 */
	public InvalidObjectInCacheException(String msg) {
		super(msg);
	}
}
