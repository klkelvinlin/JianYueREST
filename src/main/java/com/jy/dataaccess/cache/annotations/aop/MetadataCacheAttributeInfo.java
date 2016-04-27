package com.jy.dataaccess.cache.annotations.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.util.Assert;

import com.jy.dataaccess.cache.CacheAttribute;

/**
 * 
 * @author wdong
 *
 */
public class MetadataCacheAttributeInfo implements Serializable{

	private static final long serialVersionUID = 2040281963376937923L;

	/**
	 * Interface to be implemented by Classes that, given a
	 * {@link java.lang.reflect.Method}, return a
	 * {@link com.kingdee.core.cache.CacheAttribute} meta data.
	 */
	public interface MetadataFinder extends Serializable {
		CacheAttribute find(Method m);
	}
	
	public static final Object NULL_ATTRIBUTE = new Object();
	
	private final Map<String, CacheAttribute> attributeMap;
	
	private final MetadataFinder finder;
	
	public MetadataCacheAttributeInfo(MetadataFinder f) {
		Assert.notNull(f, "property 'finder' is required");
		attributeMap = Collections.synchronizedMap(new HashMap<String, CacheAttribute>());
		finder = f;
	}
	
	public CacheAttribute attribute(Method m, Class<?> t) {
		String key = key(m, t);
		CacheAttribute cached = attributeMap.get(key);
		if (cached != null)
			return unmaskNull(cached);
		CacheAttribute attribute = retrieve(m, t);
		attributeMap.put(key, maskNull(attribute));
		return attribute;
	}
	
	private String key(Method m, Class<?> t) {
		return t.toString() + System.identityHashCode(m);
	}
	
	private CacheAttribute retrieve(Method m, Class<?> t) {
		Method specificMethod = AopUtils.getMostSpecificMethod(m, t);
		CacheAttribute attribute = finder.find(specificMethod);
		if (attribute != null)
			return attribute;
		if (specificMethod != m)
			return finder.find(m);
		return null;
	}
	
	private CacheAttribute maskNull(CacheAttribute c) {
		return c == null ? (CacheAttribute)NULL_ATTRIBUTE  :  c;
	}

	private CacheAttribute unmaskNull(Object o) {
		return o == NULL_ATTRIBUTE ? null : (CacheAttribute) o;
	}
}
