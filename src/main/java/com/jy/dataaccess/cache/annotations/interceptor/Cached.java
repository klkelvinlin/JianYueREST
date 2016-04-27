package com.jy.dataaccess.cache.annotations.interceptor;

import com.jy.dataaccess.cache.CacheAttribute;
import com.jy.utils.ObjectUtils;
import com.jy.utils.StringUtils;

/**
 * 
 * @author wdong
 *
 */
public class Cached implements CacheAttribute {

	private static final long serialVersionUID = -4037159301572447671L;

	private String modelId;
	
	public Cached() {
		
	}
	
	public Cached(String newModelId) {
		this();
		setModelId(newModelId);
	}
	
	public final String getModelId() {
		return modelId;
	}
	
	/**
	 * Sets the id of the caching model to associate to this caching attribute.
	 * 
	 * @param newModelId
	 *            the new caching model id
	 */
	public final void setModelId(String newModelId) {
		modelId = newModelId;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Cached)) {
			return false;
		}

		Cached cached = (Cached) obj;

		if (!ObjectUtils.nullSafeEquals(modelId, cached.modelId)) {
			return false;
		}

		return true;
	}
	
	public int hashCode() {
		int multiplier = 31;
		int hash = 7;
		hash = multiplier * hash + (ObjectUtils.nullSafeHashCode(modelId));
		return hash;
	}
	
	public String toString() {
		return new StringBuffer(ObjectUtils.identityToString(this)).append(
				"[modelId=").append(StringUtils.quote(modelId)).append("]")
				.toString();
	}
}
