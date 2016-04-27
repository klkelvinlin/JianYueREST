package com.jy.dataaccess.cache.annotations.util;

import java.lang.reflect.Method;

/**
 * utitility class for caching
 * 
 * @author wdong
 * 
 */
public abstract class CachingUtils {
	/**
	 * Returns <code>true</code> if the return type of a method can be
	 * cacheable. In order to be cacheable, the method should have a return type
	 * (not <code>void</code>).
	 * 
	 * @param method
	 *            the method definition to verify.
	 * @return <code>true</code> if the return type of a method can be
	 *         cacheable.
	 */
	public static boolean isCacheable(Method method) {
		Class<?> returnType = method.getReturnType();
		return !void.class.equals(returnType);
	}
}
