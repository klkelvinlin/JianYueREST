package com.jy.dataaccess.cache.annotations.interceptor;

import com.jy.exception.FatalCacheException;

/**
 * 
 * @author wdong
 * 
 */
public final class NameMatchCachingInterceptor extends
		AbstractModelInfoCachingInterceptor {

	private static final long serialVersionUID = -6447524140060311132L;

	/**
	 * @see AbstractCachingInterceptor#onAfterPropertiesSet()
	 */
	protected void onAfterPropertiesSet() throws FatalCacheException {
		CachingModelInfo cachingModelSource = getCachingModelInfo();
		if (cachingModelSource == null) {
			NameMatchCachingModelInfo newSource = new NameMatchCachingModelInfo();
			newSource.setCachingModels(models());
			setCachingModelInfo(newSource);
		}
	}

}
