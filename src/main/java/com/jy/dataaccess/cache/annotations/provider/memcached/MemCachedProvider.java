package com.jy.dataaccess.cache.annotations.provider.memcached;

import java.io.Serializable;

import com.danga.MemCached.MemCachedClient;
import com.jy.dataaccess.cache.CachingModel;
import com.jy.dataaccess.cache.annotations.provider.AbstractCacheProvider;
import com.jy.exception.CacheAccessException;
import com.jy.exception.CacheException;
import com.jy.exception.FatalCacheException;

/**
 * memcached provider
 * 
 * @author wdong
 * 
 */
public class MemCachedProvider extends AbstractCacheProvider {

	private MemCachedClient cacheManager;

	/**
	 * default constructor
	 */
	public MemCachedProvider() {

	}

	/**
	 * constructor
	 * 
	 * @param cacheManager
	 *            memcached client object
	 */
	public MemCachedProvider(MemCachedClient cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setCacheManager(MemCachedClient newCacheManager) {
		cacheManager = newCacheManager;
	}

	public MemCachedClient getCacheManager() {
		return this.cacheManager;
	}

	@Override
	protected boolean isSerializableCacheElementRequired() {
		return true;
	}

	@Override
	protected Object onGetFromCache(Serializable key, CachingModel model)
			throws CacheException {
		Object cachedObject = null;
		try {
			cachedObject = cacheManager.get(key.toString());
		} catch (Exception ex) {
			throw new CacheAccessException(ex);
		}
		return cachedObject;
	}

	@Override
	protected void onPutInCache(Serializable key, CachingModel model, Object obj)
			throws CacheException {
		try {
			// cacheManager.put(Fqn.fromString(cachingModel.getNode()),
			// (Object)key, (Object)obj);
			cacheManager.add(key.toString(), obj);
		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}
	}

	@Override
	protected void onRemoveFromCache(Serializable key, CachingModel model)
			throws CacheException {
		try {
			// cacheManager.remove(Fqn.fromString(cachingModel.getNode()),
			// (Object)key);
			cacheManager.delete(key.toString());
		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}
	}

	@Override
	protected void validateCacheManager() throws FatalCacheException {
		assertCacheManagerIsNotNull(cacheManager);
	}

	public CachingModel getCachingModel(String nodeName ) {
		MemCachedCachingModel memCachedCachingModel = new MemCachedCachingModel();	
		memCachedCachingModel.setNode(nodeName);
		return memCachedCachingModel;
	}
}
