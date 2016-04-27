package com.jy.dataaccess.cache.annotations.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author wdong
 *
 */
public interface CachingAttributeInfo extends Serializable {
	/**
	 * Returns an instance of <code>{@link Cached}</code> for the intercepted
	 * method.
	 * 
	 * @param method
	 *            the definition of the intercepted method.
	 * @param targetClass
	 *            the target class. May be <code>null</code>, in which case the
	 *            declaring class of the method must be used.
	 * @return a metadata attribute from the intercepted method.
	 */
	Cached attribute(Method method, Class<?> targetClass);
}
