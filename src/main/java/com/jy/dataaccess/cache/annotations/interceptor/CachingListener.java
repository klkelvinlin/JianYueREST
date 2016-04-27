package com.jy.dataaccess.cache.annotations.interceptor;

import java.io.Serializable;

import com.jy.dataaccess.cache.CachingModel;

/**
 * 
 * @author wdong
 *
 */
public interface CachingListener {
	
	/**
	 * Notification that a new entry was stored in the cache.
	 * @param key 		the key used to store the entry.
	 * @param obj		the object stored in the cache.
	 * @param model	the caching model that specified how to store the object.
	 */
	void onCaching(Serializable key, Object obj, CachingModel model);
}
