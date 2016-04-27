package com.jy.dataaccess.cache.annotations.provider;

import java.io.Serializable;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.exception.CacheException;

public interface CacheProvider {
	/**
	 * Cancels the update being made to the cache.
	 * 
	 * @param key
	 *            the key being used in the cache update.
	 * @throws CacheException
	 *             if an unexpected error takes place when attempting to cancel
	 *             the update.
	 */
	void cancelCacheUpdate(Serializable key) throws CacheException;

	/**
	 * Retrieves an entry from the cache.
	 * 
	 * @param key
	 *            the key under which the entry is stored.
	 * @param model
	 *            the model that specifies how to retrieve an entry.
	 * @return the cached entry.
	 * @throws CacheException
	 *             if an unexpected error takes place when retrieving the entry
	 *             from the cache.
	 */
	Object getFromCache(Serializable key, CachingModel model)
			throws CacheException;

	/**
	 * @return <code>true</code> if no exception should be thrown if an error
	 *         takes place when the cache provider is being configured or
	 *         accessed.
	 */
	boolean isFailQuietlyEnabled();

	/**
	 * Stores an object in the cache.
	 * 
	 * @param key
	 *            the key under which the object will be stored.
	 * @param model
	 *            the model that specifies how to store an object.
	 * @param obj
	 *            the object to store in the cache.
	 * @throws CacheException
	 *             if an unexpected error takes place when storing an object in
	 *             the cache.
	 */
	void putInCache(Serializable key, CachingModel model, Object obj)
			throws CacheException;

	/**
	 * Removes an object from the cache.
	 * 
	 * @param key
	 *            the key under which the object is stored.
	 * @param model
	 *            the model that specifies how to store an object.
	 * @throws CacheException
	 *             if an unexpected error takes place when removing an object
	 *             from the cache.
	 */
	void removeFromCache(Serializable key, CachingModel model)
			throws CacheException;

	CachingModel getCachingModel(String properties);
}
