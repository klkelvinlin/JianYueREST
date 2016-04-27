package com.jy.dataaccess.cache.annotations.provider;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/**
 * abstract factorybean for cachemanager
 * @author wdong
 *
 * @param <T>
 */
public abstract class AbstractCacheManagerFactoryBean<T> implements
		CacheManagerFactoryBean<T> {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private String cacheProviderName;
	
	/**
	 * Location of the cache manager configuration file.
	 */
	private Resource configLocation;
	
	public AbstractCacheManagerFactoryBean() {
		super();
	}

	/**
	 * Notifies the Spring container that this factory is a singleton bean.
	 * 
	 * @return <code>true</code>.
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}
	
	/**
	 * Sets the location of the cache manager configuration file.
	 * 
	 * @param newConfigLocation
	 *            the new location of the cache manager configuration file
	 */
	public final void setConfigLocation(Resource newConfigLocation) {
		configLocation = newConfigLocation;
	}

	@Override
	public final void afterPropertiesSet() throws Exception {
		this.cacheProviderName = getCacheProviderName();
		logger.info("Creating the " + cacheProviderName + " cache manager.");
		createCacheManager();
		logger.info("The cache manager " + cacheProviderName + " was created.");
	}

	@Override
	public void destroy() throws Exception {
		if (getObject() != null) {
			logger.info("Shutting down the " + cacheProviderName
					+ " cache manager.");
			destroyCacheManager();

		} else {
			logger.info("The " + cacheProviderName
					+ " cache manager was not built. No need to shut it down.");
		}
	}
	
	/**
	 * Creates the cache manager.
	 * 
	 * @throws Exception
	 *             any exception thrown when creating the cache manager
	 */
	protected abstract void createCacheManager() throws Exception;
	
	/**
	 * Shuts down the cache manager (if it was previously created.)
	 * 
	 * @throws Exception
	 *             any exception thrown when shutting down the cache manager
	 */
	protected abstract void destroyCacheManager() throws Exception;
	
	/**
	 * @return the name of the cache provider whose cache manager is created by
	 *         this factory
	 */
	protected abstract String getCacheProviderName();
	
	/**
	 * @return the location of the cache manager configuration file
	 */
	protected final Resource getConfigLocation() {
		return configLocation;
	}
	
	/**
	 * @return the configuration resource as a <code>java.util.Properties</code>.
	 * @throws IOException
	 *             thrown if there is any I/O error when reading the
	 *             configuration resource.
	 */
	protected final Properties getConfigProperties() throws IOException {
		Properties properties = null;

		if (configLocation != null) {
			properties = new Properties();
			properties.load(configLocation.getInputStream());
		}

		return properties;
	}

}
