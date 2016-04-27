package com.jy.exception;

public class CacheNotFoundException extends CacheException {
	private static final long serialVersionUID = 6601590278654078802L;

	/**
	 * Creates a <code>CacheNotFoundException</code>.
	 * 
	 * @param cacheName
	 *            the name of the cache that could be found
	 */
	public CacheNotFoundException(String cacheName) {
		super("Unable to find cache '" + cacheName + "'");
	}

}
