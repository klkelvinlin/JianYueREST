package com.jy.dataaccess.cache;

import org.springframework.core.NestedRuntimeException;

/**
 * abstract cache exception, extends from NestedRuntimeException
 * @author wdong
 *
 */
public abstract class CacheException extends NestedRuntimeException {

	private static final long serialVersionUID = -3941786097740525155L;
	
	public CacheException(String msg) {
		super(msg);
	}
	
	public CacheException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
