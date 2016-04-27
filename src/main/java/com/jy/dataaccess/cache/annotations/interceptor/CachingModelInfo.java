package com.jy.dataaccess.cache.annotations.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.jy.dataaccess.cache.CachingModel;

/**
 * 
 * @author wdong
 * @copyright hsh.com
 */
public interface CachingModelInfo extends Serializable {
	/**
	 * Returns the caching model bound to the intercepted method.
	 * 
	 * @param m
	 *            the definition of the intercepted method.
	 * @param c
	 *            the target class. May be <code>null</code>, in which case
	 *            the declaring class of the method must be used.
	 * @return a caching model bound to the intercepted method.
	 */
	CachingModel model(Method m, Class<?> c);
}
