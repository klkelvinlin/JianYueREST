package com.jy.dataaccess.cache.annotations.serializable;

import java.io.Serializable;

/**
 * 
 * @author wdong
 *
 */
public interface SerializableFactory {

	/**
	 * Makes the given object serializable (if it is not already).
	 * 
	 * @param obj
	 *            the object to make serializable.
	 * @return the given object made serializable (if it was not already
	 *         serializable).
	 */
	Serializable makeSerializableIfNecessary(Object obj);

	/**
	 * Returns the original object that could have been made serializable. The
	 * given object will be returned if it was left intact by
	 * <code>{@link #makeSerializableIfNecessary(Object)}</code>.
	 * 
	 * @param obj
	 *            the object that could have been made serializable.
	 * @return the original object that could have been made serializable
	 *         previously by this factory.
	 */
	Object getOriginalValue(Object obj);
}
