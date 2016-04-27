package com.jy.exception;

public class CacheAccessException extends CacheException {
	private static final long serialVersionUID = 2209996521248624784L;

	/**
	 * Construct a <code>CacheException</code> with the specified nested
	 * exception.
	 * 
	 * @param ex
	 *            the nested exception
	 */
	public CacheAccessException(Throwable ex) {
		super("An unexpected error occurred when accessing the cache", ex);
	}
}
