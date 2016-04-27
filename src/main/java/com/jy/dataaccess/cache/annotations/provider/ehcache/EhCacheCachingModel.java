package com.jy.dataaccess.cache.annotations.provider.ehcache;

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.utils.ObjectUtils;
import com.jy.utils.StringUtils;

public class EhCacheCachingModel implements CachingModel {

	private static final long serialVersionUID = -3482678792180230481L;

	private String cacheName;

	private boolean blocking;

	private transient CacheEntryFactory cacheEntryFactory;

	public EhCacheCachingModel() {

	}

	public EhCacheCachingModel(String cacheName) {
		setCacheName(cacheName);
	}

	public final String getCacheName() {
		return cacheName;
	}

	public final void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	/**
	 * Set whether to use a blocking cache that lets read attempts block until
	 * the requested element is created.
	 * <p>
	 * If you intend to build a self-populating blocking cache, consider
	 * specifying a {@link #setCacheEntryFactory CacheEntryFactory}.
	 * 
	 * @see net.sf.ehcache.constructs.blocking.BlockingCache
	 * @see #setCacheEntryFactory
	 * @see org.springframework.cache.ehcache.EhCacheFactoryBean#setBlocking(boolean)
	 */
	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	public boolean isBlocking() {
		return blocking;
	}

	public void setCacheEntryFactory(CacheEntryFactory cacheEntryFactory) {
		this.cacheEntryFactory = cacheEntryFactory;
	}

	public CacheEntryFactory getCacheEntryFactory() {
		return cacheEntryFactory;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EhCacheCachingModel)) {
			return false;
		}

		EhCacheCachingModel cachingModel = (EhCacheCachingModel) obj;

		if (!ObjectUtils.nullSafeEquals(cacheName, cachingModel.cacheName)) {
			return false;
		}

		return true;
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		int multiplier = 31;
		int hash = 7;
		hash = multiplier * hash + (ObjectUtils.nullSafeHashCode(cacheName));
		return hash;
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer(
				ObjectUtils.identityToString(this));
		buffer.append("[cacheName=")
				.append(StringUtils.quote(cacheName))
				.append(", blocking=")
				.append(blocking)
				.append(", cacheEntryFactory=")
				.append((cacheEntryFactory != null) ? cacheEntryFactory
						.getClass().getName() : null).append("]");
		return buffer.toString();
	}
}
