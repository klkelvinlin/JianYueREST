package com.jy.dataaccess.cache.annotations.provider;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @author wdong
 *
 * @param <T>
 */
public interface CacheManagerFactoryBean<T> extends FactoryBean<T>,
		InitializingBean, DisposableBean {
	
}
