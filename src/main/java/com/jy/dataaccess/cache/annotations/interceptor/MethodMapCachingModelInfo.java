package com.jy.dataaccess.cache.annotations.interceptor;

import java.lang.reflect.Method;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.dataaccess.cache.annotations.aop.MethodMapCacheModelInfo;

/**
 * 
 * @author wdong
 *  copyright @hsh.com
 */
public final class MethodMapCachingModelInfo implements CachingModelInfo {

	private static final long serialVersionUID = -8793055035411538671L;
	
	private final MethodMapCacheModelInfo source;

	public MethodMapCachingModelInfo() {
		source = new MethodMapCacheModelInfo();
	}
	
	public void addModel(CachingModel m, String fullyQualifiedMethodName) {
		source.addModel(m, fullyQualifiedMethodName);
	}
	
	@Override
	public CachingModel model(Method m, Class<?> c) {
		return (CachingModel) source.model(m);
	}

}
