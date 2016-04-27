package com.jy.dataaccess.cache.annotations.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.metadata.Attributes;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.utils.StringUtils;

/**
 * 
 * @author wdong
 *
 */
@Aspect
public class MetadataCachingInterceptor extends AbstractCachingInterceptor {

	private static final long serialVersionUID = -8244290873717615985L;
	
	/**
	 * Retrieves meta data attributes from class methods.
	 */
	private CachingAttributeInfo cachingAttributeInfo;
	
	public MetadataCachingInterceptor() {
		super();
	}

	public MetadataCachingInterceptor(CachingAttributeInfo cachingAttributeInfo) {
		super();
		this.cachingAttributeInfo = cachingAttributeInfo;
	}
	
	/**
	 * @return the source of caching metadata attributes for class methods
	 */
	public final CachingAttributeInfo getCachingAttributeInfo() {
		return cachingAttributeInfo;
	}
	
	/**
	 * Sets the underlying implementation of attributes to use.
	 * 
	 * @param attributes
	 *            the new implementation of attributes to use.
	 */
	public final void setAttributes(Attributes attributes) {
		MetadataCachingAttributeInfo info = new MetadataCachingAttributeInfo();
		info.setAttributes(attributes);
		//setCachingAttributeInfo(info);
	}
	
	@Override
	protected final CachingModel model(MethodInvocation invocation) {
		Cached attribute = getCachingAttribute(invocation);
		if (attribute == null)
			return null;
		String modelId = attribute.getModelId();
		if (!StringUtils.hasText(modelId))
			return null;
		return (CachingModel) models().get(modelId);
	}

	/**
	 * Returns the metadata attribute of the intercepted method.
	 * 
	 * @param methodInvocation
	 *            the description of an invocation to the method.
	 * @return the metadata attribute of the intercepted method.
	 */
	protected Cached getCachingAttribute(MethodInvocation methodInvocation) {
		Object thisObject = methodInvocation.getThis();
		Class<?> targetClass = (thisObject != null) ? thisObject.getClass() : null;
		Method method = methodInvocation.getMethod();
		Cached attribute = cachingAttributeInfo
				.attribute(method, targetClass);
		return attribute;
	}

}
