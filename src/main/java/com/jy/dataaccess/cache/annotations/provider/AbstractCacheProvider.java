package com.jy.dataaccess.cache.annotations.provider;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.dataaccess.cache.annotations.serializable.SerializableFactory;
import com.jy.exception.CacheException;
import com.jy.exception.FatalCacheException;
import com.jy.exception.InvalidObjectInCacheException;
import com.jy.exception.ObjectCannotBeCachedException;
import com.jy.utils.StringUtils;

public abstract class AbstractCacheProvider implements CacheProvider {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private boolean failQuietlyEnabled;
	private SerializableFactory serializableFactory;
	
	public final void setSerializableFactory(
			SerializableFactory newSerializableFactory) {
		serializableFactory = newSerializableFactory;
	}
	
	public final SerializableFactory getSerializableFactory(){
		return this.serializableFactory;
	}
	
	@Override
	public void cancelCacheUpdate(Serializable key) throws CacheException {
		if (logger.isDebugEnabled()) {
			logger.debug("Attempt to cancel a cache update using the key <"
					+ StringUtils.quoteIfString(key) + ">");
		}
		try {
			onCancelCacheUpdate(key);
		} catch (CacheException exception) {
			handleCatchedException(exception);
		}
	}

	@Override
	public Object getFromCache(Serializable key, CachingModel model)
			throws CacheException {
		if (logger.isDebugEnabled()) {
			logger.debug("Attempt to retrieve a cache entry using key <"
					+ StringUtils.quoteIfString(key) + "> and cache model <"
					+ model + ">");
		}

		Object cachedObject = null;

		try {
			if (model != null) {
				cachedObject = onGetFromCache(key, model);

				// deserialize the value if required
				if (cachedObject != null) {
					cachedObject = deserializeValueIfNecessary(cachedObject);
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Retrieved cache element <"
						+ StringUtils.quoteIfString(cachedObject) + ">");
			}

		} catch (CacheException exception) {
			handleCatchedException(exception);
		}
		return cachedObject;
	}

	@Override
	public boolean isFailQuietlyEnabled() {
		return this.failQuietlyEnabled;
	}

	@Override
	public void putInCache(Serializable key, CachingModel model, Object obj)
			throws CacheException {
		if (logger.isDebugEnabled()) {
			logger.debug("Attempt to store the object <" + obj
					+ "> in the cache using key <"
					+ StringUtils.quoteIfString(key) + "> and model <" + model
					+ ">");
		}

		try {
			Object newCacheElement = makeSerializableIfNecessary(obj);

			if (model != null) {
				onPutInCache(key, model, newCacheElement);
				logger.debug("Object was successfully stored in the cache");
			}
		} catch (CacheException exception) {
			handleCatchedException(exception);
		}
	}

	@Override
	public void removeFromCache(Serializable key, CachingModel model)
			throws CacheException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("Attempt to remove an entry from the cache using key <"
							+ StringUtils.quoteIfString(key)
							+ "> and model <"
							+ model + ">");
		}

		if (model != null) {
			try {
				onRemoveFromCache(key, model);
				logger.debug("Object removed from the cache");

			} catch (CacheException exception) {
				handleCatchedException(exception);
			}
		}
	}

	@Override
	public CachingModel getCachingModel(String properties) {
		return null;
	}

	/**
	 * Cancels the update being made to the cache.
	 * 
	 * @param key
	 *            the key being used in the cache update.
	 * @throws CacheException
	 *             if an unexpected error takes place when attempting to cancel
	 *             the update.
	 */
	protected void onCancelCacheUpdate(Serializable key) throws CacheException {
		logger.info("Cache provider does not support cancelation of updates");
	}
	
	/**
	 * Re throws the given exception if "fail quietly" is enabled.
	 * 
	 * @param exception
	 *            Be catched exception to be potentially rethrown.
	 * @throws CacheException
	 *             if this cache provider has not been configured to "fail
	 *             quietly."
	 */
	protected final void handleCatchedException(CacheException exception)
			throws CacheException {
		logger.error(exception.getMessage(), exception);
		if (!isFailQuietlyEnabled()) {
			throw exception;
		}
	}
	
	/**
	 * Deserialize the value from the given object if:
	 * <ul>
	 * <li>The cache can only store serializable objects</li>
	 * <li>The given object does not implement
	 * <code>java.io.Serializable</code> </li>
	 * </ul>
	 * Otherwise, will throw an {@link InvalidObjectInCacheException}.
	 * 
	 * @param obj
	 *            the object to check.
	 * @return the given object as a serializable object if necessary.
	 * @throws InvalidObjectInCacheException
	 *             if the cache requires serializable elements, the given object
	 *             does not implement <code>java.io.Serializable</code> and
	 *             the factory of serializable objects is <code>null</code>.
	 * @see #setSerializableFactory(SerializableFactory)
	 * @see org.springmodules.cache.serializable.SerializableFactory#getOriginalValue(Object)
	 */
	protected final Object deserializeValueIfNecessary(Object obj) {
		if (!isSerializableCacheElementRequired()) {
			return obj;
		}
		if (obj instanceof Serializable) {
			if (serializableFactory != null) {
				return serializableFactory.getOriginalValue(obj);
			}
			return obj;
		}
		throw new InvalidObjectInCacheException(
				"The object retrieved from the cache is not of the required "
						+ "type: java.io.Serializable.");
	}
	
	/**
	 * Makes the given object serializable if:
	 * <ul>
	 * <li>The cache can only store serializable objects</li>
	 * <li>The given object does not implement
	 * <code>java.io.Serializable</code> </li>
	 * </ul>
	 * Otherwise, will return the same object passed as argument.
	 * 
	 * @param obj
	 *            the object to check.
	 * @return the given object as a serializable object if necessary.
	 * @throws ObjectCannotBeCachedException
	 *             if the cache requires serializable elements, the given object
	 *             does not implement <code>java.io.Serializable</code> and
	 *             the factory of serializable objects is <code>null</code>.
	 * @see #setSerializableFactory(SerializableFactory)
	 * @see org.springmodules.cache.serializable.SerializableFactory#makeSerializableIfNecessary(Object)
	 */
	protected final Object makeSerializableIfNecessary(Object obj) {
		if (!isSerializableCacheElementRequired()) {
			return obj;
		}
		if (obj instanceof Serializable) {
			return obj;
		}
		if (serializableFactory != null) {
			return serializableFactory.makeSerializableIfNecessary(obj);
		}
		throw new ObjectCannotBeCachedException(
				"The cache can only store implementations of java.io.Serializable");
	}
	
	/**
	 * Asserts that the given cache manager is not <code>null</code>.
	 * 
	 * @param cacheManager
	 *            the cache manager to check
	 * @throws FatalCacheException
	 *             if the cache manager is <code>null</code>
	 */
	protected final void assertCacheManagerIsNotNull(Object cacheManager)
			throws FatalCacheException {
		if (cacheManager == null) {
			throw new FatalCacheException(
					"The cache manager should not be null");
		}
	}
	
	/**
	 * @return <code>true</code> if the cache used by this facade can only
	 *         store serializable objects.
	 */
	protected abstract boolean isSerializableCacheElementRequired();
	
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
	protected abstract Object onGetFromCache(Serializable key,
			CachingModel model) throws CacheException;
	
	/**
	 * Stores an object in the cache.
	 * 
	 * @param key
	 *            the key used to store the object.
	 * @param model
	 *            the model that specifies how to store an object in the cache.
	 * @param obj
	 *            the object to store in the cache.
	 * @throws CacheException
	 *             if an unexpected error takes place when storing an object in
	 *             the cache.
	 */
	protected abstract void onPutInCache(Serializable key, CachingModel model,
			Object obj) throws CacheException;
	
	/**
	 * Removes an entry from the cache.
	 * 
	 * @param key
	 *            the key the entry to remove is stored under.
	 * @param model
	 *            the model that specifies how to remove the entry from the
	 *            cache.
	 * @throws CacheException
	 *             if an unexpected error takes place when removing an entry
	 *             from the cache.
	 */
	protected abstract void onRemoveFromCache(Serializable key,
			CachingModel model) throws CacheException;

	/**
	 * Validates the cache manager used by this facade.
	 * 
	 * @throws FatalCacheException
	 *             if the cache manager is in an invalid state.
	 */
	protected abstract void validateCacheManager() throws FatalCacheException;
}
