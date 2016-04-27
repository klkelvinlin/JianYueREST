package com.jy.dataaccess.cache.annotations.provider.ehcache;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
import net.sf.ehcache.constructs.blocking.UpdatingCacheEntryFactory;
import net.sf.ehcache.constructs.blocking.UpdatingSelfPopulatingCache;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.dataaccess.cache.annotations.provider.AbstractCacheProvider;
import com.jy.exception.CacheAccessException;
import com.jy.exception.CacheException;
import com.jy.exception.CacheNotFoundException;
import com.jy.exception.FatalCacheException;
import com.jy.exception.ObjectCannotBeCachedException;

public final class EhCacheProvider extends AbstractCacheProvider {

	/**
	 * Map referencing caches looked up from EHCache CacheManager.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map caches = Collections.synchronizedMap(new HashMap());

	/**
	 * EHCache cache manager.
	 */
	private CacheManager cacheManager;

	/**
	 * Constructor.
	 */
	public EhCacheProvider() {
		super();
	}

	/**
	 * Sets the EHCache cache manager to use.
	 * 
	 * @param newCacheManager
	 *            the new cache manager
	 */
	public void setCacheManager(CacheManager newCacheManager) {
		this.cacheManager = newCacheManager;
	}

	/**
	 * Returns a EHCache cache from the cache manager.
	 * 
	 * @param model
	 *            the model containing the name of the cache to retrieve
	 * @return the cache retrieved from the cache manager
	 * @throws CacheNotFoundException
	 *             if the cache does not exist
	 * @throws CacheAccessException
	 *             wrapping any unexpected exception thrown by the cache
	 */
	protected Ehcache getCache(CachingModel model)
			throws CacheNotFoundException, CacheAccessException {
		EhCacheCachingModel ehCacheCachingModel = (EhCacheCachingModel) model;
		String cacheName = ehCacheCachingModel.getCacheName();

		Ehcache cache = (Cache) caches.get(cacheName);
		if (cache == null) {
			cache = decorateCache(getCache(cacheName), ehCacheCachingModel);
		}
		return cache;
	}

	/**
	 * @param cache
	 *            the raw Cache object, based on the configuration of this
	 *            FactoryBean
	 * @param model
	 *            the model containing the name of the cache to retrieve
	 * @return the (potentially decorated) cache object to be registered with
	 *         the CacheManager
	 */
	protected Ehcache decorateCache(Cache cache, EhCacheCachingModel model) {
		if (model.getCacheEntryFactory() != null) {
			if (model.getCacheEntryFactory() instanceof UpdatingCacheEntryFactory) {
				return new UpdatingSelfPopulatingCache(cache,
						(UpdatingCacheEntryFactory) model
								.getCacheEntryFactory());
			} else {
				return new SelfPopulatingCache(cache,
						model.getCacheEntryFactory());
			}
		}
		if (model.isBlocking()) {
			return new BlockingCache(cache);
		}
		return cache;
	}

	/**
	 * Returns a EHCache cache from the cache manager.
	 * 
	 * @param name
	 *            the name of the cache
	 * @return the cache retrieved from the cache manager
	 * @throws CacheNotFoundException
	 *             if the cache does not exist
	 * @throws CacheAccessException
	 *             wrapping any unexpected exception thrown by the cache
	 */
	protected Cache getCache(String name) throws CacheNotFoundException,
			CacheAccessException {
		Cache cache = null;

		try {
			if (cacheManager.cacheExists(name)) {
				cache = cacheManager.getCache(name);
			}
		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}

		if (cache == null) {
			throw new CacheNotFoundException(name);
		}

		return cache;
	}

	@Override
	protected boolean isSerializableCacheElementRequired() {
		return false;
	}

	/**
	 * @param key   the key of the cache entry
	 * @param model the caching model
	 * @return the object retrieved from the cache.
	 * @throws CacheNotFoundException if the cache specified in the given model cannot be found.
	 * @throws CacheAccessException   wrapping any unexpected exception thrown by the cache.
	 * @see AbstractCacheProviderFacade#onGetFromCache(Serializable,CachingModel)
	 */
	protected Object onGetFromCache(Serializable key, CachingModel model)
			throws CacheException {
		Ehcache cache = getCache(model);
		Object cachedObject = null;

		try {
			Element cacheElement = cache.get(key);
			if (cacheElement != null) {
				cachedObject = cacheElement.getObjectValue();
			}

		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}

		return cachedObject;
	}

	/**
	 * @param key   the key of the cache entry
	 * @param model the caching model
	 * @param obj   the object to store in the cache
	 * @throws ObjectCannotBeCachedException if the object to store is not an implementation of
	 *                                       <code>java.io.Serializable</code>.
	 * @throws CacheNotFoundException		if the cache specified in the given model cannot be found.
	 * @throws CacheAccessException		  wrapping any unexpected exception thrown by the cache.
	 * @see AbstractCacheProviderFacade#onPutInCache(Serializable,CachingModel,
	 *Object)
	 */
	protected void onPutInCache(Serializable key, CachingModel model, Object obj)
			throws CacheException {
		Ehcache cache = getCache(model);
		Element newCacheElement = new Element(key, obj);

		try {
			cache.put(newCacheElement);

		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}
	}

	/**
	 * Removes the object stored under the given key from the cache specified in
	 * the given caching model. The caching model should be an instance of
	 *
	 * @param key   the key of the cache entry
	 * @param model the caching model
	 * @throws CacheNotFoundException if the cache specified in the given model cannot be found.
	 * @throws CacheAccessException   wrapping any unexpected exception thrown by the cache.
	 * @see AbstractCacheProviderFacade#onRemoveFromCache(Serializable,
	 *CachingModel)
	 */
	protected void onRemoveFromCache(Serializable key, CachingModel model)
			throws CacheException {
		Ehcache cache = getCache(model);

		try {
			cache.remove(key);

		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}
	}

	/**
	 * @throws FatalCacheException if the cache manager is <code>null</code>.
	 * @see AbstractCacheProviderFacade#validateCacheManager()
	 */
	protected void validateCacheManager() throws FatalCacheException {
		assertCacheManagerIsNotNull(cacheManager);
	}

	public CachingModel getCachingModel(String properties) {
		EhCacheCachingModel model = new EhCacheCachingModel();
		model.setCacheName(properties);
		return model;
	}

}
