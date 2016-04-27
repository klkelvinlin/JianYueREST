package com.jy.dataaccess.cache.annotations.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import com.jy.dataaccess.cache.CacheModel;
import com.jy.dataaccess.cache.annotations.util.TextMatcher;

public final class NameMatchCacheModelInfo implements Serializable {

	private static final long serialVersionUID = 8912209324581808958L;

	private Map<?,?> models;

	public NameMatchCacheModelInfo() {
	}
	
	public NameMatchCacheModelInfo(Map<String, CacheModel>  m){
		models = m;
	}

	public void setModels(Map<?,?> m) {
		models = m;
	}
	
	public CacheModel model(Method m) {
		String key = m.getName();
		CacheModel model = model(key);

		if (model != null) {
			return model;
		}

		return mostSpecificModel(key);
	}

	private CacheModel mostSpecificModel(String method) {
		CacheModel model = null;
		String bestMatch = null;

		for (@SuppressWarnings("unchecked")
		Iterator<String> iter = (Iterator<String>) models.keySet().iterator(); iter.hasNext();) {
			String mapped = iter.next();
			if (!mostSpecificMethodFound(method, bestMatch, mapped)) {
				continue;
			}
			model = model(mapped);
			bestMatch = mapped;
		}

		return model;
	}

	private boolean mostSpecificMethodFound(String method, String bestMatch, String mapped) {
		return TextMatcher.isMatch(method, mapped)
				&& (bestMatch == null || bestMatch.length() <= mapped.length());
	}

	private CacheModel model(String key) {
		return  (CacheModel) models.get(key);
	}
	
}
