package com.jy.dataaccess.cache.annotations.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.jy.dataaccess.cache.CachingModel;

/**
 * 
 * @author wdong
 * @copyright hsh.com
 */
public abstract class AbstractModelInfoCachingInterceptor extends
		AbstractCachingInterceptor {

	private static final long serialVersionUID = 4756562260546569810L;
	
	private CachingModelInfo cachingModelInfo;
	
	/**
	 * default constructor
	 */
	public AbstractModelInfoCachingInterceptor() {		
	}
	
	/**
	 * constructor
	 * @param cachingModelInfo @see {@link com.jy.dataaccess.cache.annotations.interceptor.CachingModelInfo}
	 */
	public AbstractModelInfoCachingInterceptor(CachingModelInfo cachingModelInfo) {
		this.cachingModelInfo = cachingModelInfo;
	}
	
	/**
	 * @return the source of caching models for class methods
	 */
	public final CachingModelInfo getCachingModelInfo() {
		return cachingModelInfo;
	}
	
	/**
	 * Sets the source of caching models for class methods.
	 * 
	 * @param newCachingModelInfo
	 *            the new source of caching models
	 */
	public final void setCachingModelInfo(CachingModelInfo newCachingModelInfo) {
		cachingModelInfo = newCachingModelInfo;
	}
	

	/**
	 * @see AbstractCachingInterceptor#model(MethodInvocation)
	 */
	protected final CachingModel model(MethodInvocation methodInvocation) {
		Object thisObject = methodInvocation.getThis();
		Class<?> targetClass = (thisObject != null) ? thisObject.getClass() : null;
		Method method = methodInvocation.getMethod();
		return cachingModelInfo.model(method, targetClass);
	}

}
