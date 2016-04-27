package com.jy.dataaccess.cache.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.jy.dataaccess.cache.CacheAttribute;
import com.jy.dataaccess.cache.annotations.aop.MetadataCacheAttributeInfo;
import com.jy.dataaccess.cache.annotations.aop.MetadataCacheAttributeInfo.MetadataFinder;
import com.jy.dataaccess.cache.annotations.interceptor.Cached;
import com.jy.dataaccess.cache.annotations.interceptor.CachingAttributeInfo;
import com.jy.dataaccess.cache.annotations.util.CachingUtils;

/**
 * 
 * @author wdong
 * 
 */
public class AnnotationCachingAttributeInfo implements CachingAttributeInfo {

	private static final long serialVersionUID = -8613844336706931934L;

	private static final MetadataFinder finder = new MetadataFinder() {

		private static final long serialVersionUID = 3539201058883645662L;

		public CacheAttribute find(Method m) {
			return find(m.getAnnotations());
		}

		private CacheAttribute attribute(Cacheable a) {
			return new Cached(a.modelId());
		}

		private CacheAttribute find(Annotation[] annotations) {
			if (isEmpty(annotations)){
				return null;
			}
			
			for (Annotation a : annotations)
				if (a instanceof Cacheable)
					return attribute((Cacheable) a);
			return null;
		}

		private boolean isEmpty(Annotation[] annotations) {
			return annotations.length > 0 ? false : true;
		}
	};

	private final MetadataCacheAttributeInfo info;

	public AnnotationCachingAttributeInfo() {
		info = new MetadataCacheAttributeInfo(finder);
	}

	public Cached attribute(Method m, Class<?> t) {
		if (CachingUtils.isCacheable(m))
			return (Cached) info.attribute(m, t);
		return null;
	}
}
