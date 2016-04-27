package com.jy.dataaccess.cache.annotations.key;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInvocation;

/**
 * generate cache key from method return type.
 * @author wdong
 *
 */
public class ArgumentsCacheKeyGenerator implements CacheKeyGenerator {

	@Override
	public Serializable generateKey(MethodInvocation methodInvocation) {
		return methodInvocation.getMethod().getReturnType().getName();
	}

}
