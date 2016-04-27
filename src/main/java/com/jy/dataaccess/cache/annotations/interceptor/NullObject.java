package com.jy.dataaccess.cache.annotations.interceptor;

import java.io.Serializable;

import org.springframework.util.ObjectUtils;

/**
 * 
 * @author wdong
 *
 */
public final class NullObject implements Serializable {

	private static final long serialVersionUID = -8806396264911588335L;
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(!(obj instanceof NullObject)) {
			return false;
		}
		return true;
	}
	
	public int hashCode() {
		//TODO need to decide use what kind of hash code algorithm
		return super.hashCode();
	}
	
	public String toString() {
		String identity = ObjectUtils.getIdentityHexString(this);
		return getClass().getName() + "@" + identity;
	}
}
