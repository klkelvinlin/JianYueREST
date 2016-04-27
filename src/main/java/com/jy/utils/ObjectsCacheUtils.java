package com.jy.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.dataaccess.cache.annotations.provider.ehcache.EhCacheProvider;


public class ObjectsCacheUtils {
	
	private static ObjectsCacheUtils cacheCity = new ObjectsCacheUtils();
	
	private EhCacheProvider provider;

	private CachingModel model;

	private CacheManager cacheManager;

	private Cache cache;
	
	private static final String ALL = "all";
	
	private ObjectsCacheUtils() {
		provider = new EhCacheProvider();
		cache = new Cache(
			     new CacheConfiguration(ALL, Short.MAX_VALUE)
			       .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
			       .overflowToDisk(true)
			       .eternal(false)
			       .timeToLiveSeconds(60*60)
			       .timeToIdleSeconds(60*30)
			       .diskPersistent(false)
			       .diskExpiryThreadIntervalSeconds(0));
		cacheManager = CacheManager.create();
		cacheManager.addCache(cache);
		provider.setCacheManager(cacheManager);
		model = provider.getCachingModel(ALL);
	}
	
	public static ObjectsCacheUtils getInstance() {
		return cacheCity;
	}
	
	public void addObject(Serializable id, Object obj) {
		provider.putInCache(id, model, obj);
	}
	
	public Object getObject(Serializable id) {
		return provider.getFromCache(id, model);
	}
	
	public void removeObject(Serializable id) {
		provider.removeFromCache(id, model);
	}
	
	public void addAllObjects(Serializable key, List<? extends Object> objects) {
		provider.putInCache(key, model, objects);
	}
	
	public void addAllObjectsToMap(Serializable key, Map map) {
		provider.putInCache(key, model, map);
	}
	
	@SuppressWarnings("unchecked")
	public List<? extends Object> getAllObjects(Serializable key) {
		return (List<Object>)provider.getFromCache(key, model);
	}
	
	public Map getAllObjectsFromMap(Serializable key) {
		return (HashMap)provider.getFromCache(key, model);
	}
	
	public void removeAllObjects(Serializable key) {
		provider.removeFromCache(key, model);
	}
}
