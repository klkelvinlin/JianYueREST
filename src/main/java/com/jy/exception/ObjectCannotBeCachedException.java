package com.jy.exception;

public class ObjectCannotBeCachedException extends CacheException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8736275301339942565L;

	/**
	 * Construct a <code>ObjectCannotBeCachedException</code> with the
	 * specified detail message.
	 * 
	 * @param msg
	 *            the detail message
	 */
	public ObjectCannotBeCachedException(String msg) {
		super(msg);
	}
}
