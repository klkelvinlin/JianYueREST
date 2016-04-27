package com.jy.dataaccess.cache.annotations.provider.memcached;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.utils.ObjectUtils;
import com.jy.utils.StringUtils;

/**
 * 
 * @author wdong
 * 
 */
public class MemCachedCachingModel implements CachingModel {

	private static final long serialVersionUID = -6207602230525987937L;

	private String node;

	/**
	 * Constructor.
	 */
	public MemCachedCachingModel() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param newNode
	 *            the fully qualified name of the node to use
	 */
	public MemCachedCachingModel(String newNode) {
		this();
		setNode(newNode);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MemCachedCachingModel)) {
			return false;
		}
		MemCachedCachingModel cachingModel = (MemCachedCachingModel) obj;
		if (!ObjectUtils.nullSafeEquals(node, cachingModel.node)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the fully qualified name of the node to use
	 */
	public final String getNode() {
		return node;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int multiplier = 31;
		int hash = 17;
		hash = multiplier * hash + ObjectUtils.nullSafeHashCode(node);
		return hash;
	}

	/**
	 * Sets the fully qualified name of the node to use
	 * 
	 * @param newNodeFqn
	 *            the new node FQN
	 */
	public final void setNode(String newNodeFqn) {
		node = newNodeFqn;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer(
				ObjectUtils.identityToString(this));
		buffer.append("[nodeFqn=" + StringUtils.quote(node) + "]");
		return buffer.toString();
	}

}
