package com.jy.dataaccess.cache.annotations.key;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 
 * @author wdong
 * 
 */
public interface CacheKeyGenerator {

	/**
	 * Generates the key for a cache entry.
	 * 
	 * @param methodInvocation
	 *            the description of an invocation to the intercepted method.
	 * @return the created key.
	 */
	Serializable generateKey(MethodInvocation methodInvocation);
}
