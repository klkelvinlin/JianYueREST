package com.jy.dataaccess.cache.annotations.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

/**
 * 
 * @author wdong
 * 
 */
public class CachingAttributeAdvisor extends StaticMethodMatcherPointcutAdvisor
		implements Advice {

	private static final long serialVersionUID = 8897127912147930873L;

	/**
	 * Retrieves instances of <code>{@link Cached}</code> for intercepted
	 * methods.
	 */
	private CachingAttributeInfo cachingAttributeInfo;

	public CachingAttributeAdvisor(MetadataCachingInterceptor interceptor) {
		super(interceptor);

		CachingAttributeInfo info = interceptor.getCachingAttributeInfo();
		if (info == null) {
			throw new AopConfigException("<" + interceptor.getClass().getName()
					+ "> has no <" + CachingAttributeInfo.class.getName()
					+ "> configured");
		}
		this.cachingAttributeInfo = info;
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		Cached attribute = cachingAttributeInfo
				.attribute(method, targetClass);
		return (attribute != null);
	}

}
