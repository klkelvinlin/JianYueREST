package com.jy.dataaccess.cache.annotations.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.dataaccess.cache.annotations.aop.NameMatchCacheModelInfo;

/**
 * 
 * @author wdong
 * copyright @hsh.com
 */
public final class NameMatchCachingModelInfo implements CachingModelInfo {

	private static final long serialVersionUID = 7001927679140184844L;

	private final NameMatchCacheModelInfo source;

	public NameMatchCachingModelInfo() {
		source = new NameMatchCacheModelInfo();
	}

	/**
	 * @see CachingModelInfo#model(Method, Class)
	 */
	public CachingModel model(Method m, Class<?> t) {
		return (CachingModel) source.model(m);
	}

	public void setCachingModels(Map<?, ?> map) {
		source.setModels(map);
	}

}
