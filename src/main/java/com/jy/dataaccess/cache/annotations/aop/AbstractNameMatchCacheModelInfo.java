package com.jy.dataaccess.cache.annotations.aop;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jy.dataaccess.cache.CacheModel;
import com.jy.dataaccess.cache.annotations.util.TextMatcher;

/**
 * AbstractNameMatchCacheModelInfo
 * @author wdong
 * 
 */
public abstract class AbstractNameMatchCacheModelInfo {
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Stores instances of <code>{@link CacheModel}</code> implementations under
	 * the name of the method associated to it.
	 */
	private Map<String, CacheModel> cacheModels;

	/**
	 * Returns the cache model bound to the intercepted method.
	 * 
	 * @param method
	 *            the definition of the intercepted method
	 * @return the model bound to the intercepted method
	 */
	protected final CacheModel getCacheModel(Method method) {
		String methodName = method.getName();
		CacheModel model = cacheModels.get(methodName);

		if (model == null) {
			// look up most specific name match
			String bestNameMatch = null;

			for (Iterator<String> iter = cacheModels.keySet().iterator(); iter
					.hasNext();) {
				String mappedMethodName = iter.next();
				if (isMatch(methodName, mappedMethodName) && (bestNameMatch.length() <= mappedMethodName.length())) {
					model = cacheModels.get(mappedMethodName);
					bestNameMatch = mappedMethodName;
				}
			}
		}
		
		return model;
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the given method name matches the mapped
	 * name. The default implementation checks for "xxx*" and "*xxx" matches.
	 * </p>
	 * <p>
	 * For example, this method will return <code>true</code> if the given
	 * method name is "getUser" and the mapped name is
	 * "get*"
	 * </p>
	 * 
	 * @param methodName
	 *            the method name
	 * @param mappedName
	 *            the name in the descriptor
	 * @return <code>true</code> if the names match
	 */
	private boolean isMatch(String methodName, String mappedMethodName) {
		return TextMatcher.isMatch(methodName, mappedMethodName);
	}
	
	protected final void setCacheModels(Map<String, CacheModel> newCacheModels) {
		cacheModels = newCacheModels;
	}
}
