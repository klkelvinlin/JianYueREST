package com.jy.dataaccess.cache.annotations.aop.proxy;

import org.springframework.aop.framework.ProxyConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * cache proxy factory bean
 * @author wdong
 *
 */
public final class CacheProxyFactoryBean<T> extends ProxyConfig implements
		FactoryBean<T>, InitializingBean {

	private static final long serialVersionUID = 1766530358233845722L;

	@Override
	public void afterPropertiesSet() throws Exception {
		

	}

	@Override
	public T getObject() throws Exception {
		
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		
		return null;
	}

	@Override
	public boolean isSingleton() {
		
		return false;
	}

}
