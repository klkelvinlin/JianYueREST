package com.jy.dataaccess.cache.annotations.interceptor;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.metadata.Attributes;

import com.jy.dataaccess.cache.CacheAttribute;
import com.jy.dataaccess.cache.annotations.aop.MetadataCacheAttributeInfo;
import com.jy.dataaccess.cache.annotations.aop.MetadataCacheAttributeInfo.MetadataFinder;
import com.jy.dataaccess.cache.annotations.util.CachingUtils;
import com.jy.utils.CollectionUtils;

/**
 * 
 * @author wdong
 *
 */
public class MetadataCachingAttributeInfo implements CachingAttributeInfo {

	private static final long serialVersionUID = -2871646359478342493L;

	private Attributes attributes;
	
	private final MetadataFinder finder = new MetadataFinder() {
		
		private static final long serialVersionUID = -1910359589169150248L;

		public CacheAttribute find(Method m) {
			return find(attributes.getAttributes(m));
		}

		private CacheAttribute find(Collection<?> methodAttributes) {
			if (CollectionUtils.isEmpty(methodAttributes))
				return null;
			for (Iterator<?> i = methodAttributes.iterator(); i.hasNext();) {
				Object attribute = i.next();
				if (attribute instanceof Cached)
					return (Cached) attribute;
			}
			return null;
		}
	};

	private final MetadataCacheAttributeInfo info;

	public MetadataCachingAttributeInfo() {
		info = new MetadataCacheAttributeInfo(finder);
	}

	public MetadataCachingAttributeInfo(Attributes attributes,
			MetadataCacheAttributeInfo info) {
		this.attributes = attributes;
		this.info = info;
	}
	
	public Cached attribute(Method m, Class<?> t) {
		if (!CachingUtils.isCacheable(m))
			return null;
		return (Cached) info.attribute(m, t);
	}

	public void setAttributes(Attributes a) {
		attributes = a;
	}
}
