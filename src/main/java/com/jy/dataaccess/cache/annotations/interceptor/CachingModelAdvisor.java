package com.jy.dataaccess.cache.annotations.interceptor;

import java.lang.reflect.Method;

import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import com.jy.dataaccess.cache.CachingModel;

/**
 * 
 * @author wdong
 *
 */
public class CachingModelAdvisor extends StaticMethodMatcherPointcutAdvisor {

	private static final long serialVersionUID = -8002757290679200365L;
	
	/**
	 * Retrieves instances of <code>{@link CachingModel}</code> for
	 * intercepted methods.
	 */
	private CachingModelInfo cachingModelInfo;
	
	/**
	 * Construct a <code>CachingModelSourceAdvisor</code>.
	 * 
	 * @param interceptor
	 *            Advice that caches the returned value of intercepted methods.
	 * @throws AopConfigException
	 *             if the <code>CachingAttributeSource</code> of
	 *             <code>cacheInterceptor</code> is <code>null</code>.
	 */
	public CachingModelAdvisor(NameMatchCachingInterceptor interceptor) {
		super(interceptor);

		CachingModelInfo tempInfo = interceptor.getCachingModelInfo();

		if (tempInfo == null) {
			throw new AopConfigException("<" + interceptor.getClass().getName()
					+ "> has no <" + CachingModelInfo.class.getName()
					+ "> configured");
		}

		cachingModelInfo = tempInfo;
	}

	/**
	 * Returns <code>true</code> if the return value of the intercepted method
	 * should be cached.
	 * 
	 * @param method
	 *            the intercepted method to verify.
	 * @param targetClass
	 *            the class declaring the method.
	 * @return <code>true</code> if the return value of the intercepted method
	 *         should be cached.
	 */
	public final boolean matches(Method method, Class<?> targetClass) {
		CachingModel model = cachingModelInfo.model(method, targetClass);

		boolean matches = (model != null);
		return matches;
	}

}
