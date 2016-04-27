package com.jy.dataaccess.cache.annotations.key;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.jy.dataaccess.cache.annotations.util.Reflections;
import com.jy.utils.ObjectUtils;

/**
 * cache key generator using hash arlgorithm
 * 
 * @author wdong
 * 
 */
public class HashCodeCacheKeyGenerator implements CacheKeyGenerator {

	/**
	 * Flag that indicates if this generator should generate the hash code of
	 * the arguments passed to the method to apply caching to. If
	 * <code>false</code>, this generator uses the default hash code of the
	 * arguments.
	 */
	private boolean generateArgumentHashCode = false;

	/**
	 * Default constructor
	 */
	public HashCodeCacheKeyGenerator() {
	}

	/**
	 * Construct a <code>HashCodeCacheKeyGenerator</code>.
	 * 
	 * @param generateArgumentHashCode
	 *            the new value for the flag that indicates if this generator
	 *            should generate the hash code of the arguments passed to the
	 *            method to apply caching to. If <code>false</code>, this
	 *            generator uses the default hash code of the arguments.
	 */
	public HashCodeCacheKeyGenerator(boolean generateArgumentHashCode) {
		this.generateArgumentHashCode = generateArgumentHashCode;
	}

	/**
	 * @see CacheKeyGenerator#generateKey(MethodInvocation)
	 */
	public Serializable generateKey(MethodInvocation methodInvocation) {

		HashCodeCalculator hashCodeCalculator = new HashCodeCalculator();
		Method method = methodInvocation.getMethod();
		hashCodeCalculator.append(System.identityHashCode(method));

		Object[] methodArguments = methodInvocation.getArguments();

		if (methodArguments != null) {
			for (Object methodArgument : methodArguments) {
				int hash = 0;

				if (this.generateArgumentHashCode) {
					hash = Reflections.reflectionHashCode(methodArgument);
				} else {
					hash = ObjectUtils.nullSafeHashCode(methodArgument);
				}

				hashCodeCalculator.append(hash);
			}
		}

		long checkSum = hashCodeCalculator.getCheckSum();
		int hashCode = hashCodeCalculator.getHashCode();

		Serializable cacheKey = new HashCodeCacheKey(checkSum, hashCode);
		return cacheKey;
	}

	/**
	 * Sets the flag that indicates if this generator should generate the hash
	 * code of the arguments passed to the method to apply caching to. If
	 * <code>false</code>, this generator uses the default hash code of the
	 * arguments.
	 * 
	 * @param newGenerateArgumentHashCode
	 *            the new value of the flag
	 */
	public final void setGenerateArgumentHashCode(
			boolean newGenerateArgumentHashCode) {
		generateArgumentHashCode = newGenerateArgumentHashCode;
	}

}
