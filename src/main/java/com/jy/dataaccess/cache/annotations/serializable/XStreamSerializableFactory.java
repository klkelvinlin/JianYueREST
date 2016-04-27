package com.jy.dataaccess.cache.annotations.serializable;

import java.io.Serializable;

import com.jy.utils.ObjectUtils;
import com.jy.utils.StringUtils;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author wdong
 *
 */
public class XStreamSerializableFactory implements SerializableFactory {

	/**
	 * Wraps an XML-serialized object.
	 */
	public static class ObjectWrapper implements Serializable {

		private static final long serialVersionUID = -869605824186073532L;
		private Serializable value;

		/**
		 * Constructor.
		 */
		public ObjectWrapper() {
		}

		/**
		 * Constructor.
		 * 
		 * @param value
		 *            the new value to wrap
		 */
		public ObjectWrapper(final Serializable value) {
			this.value = value;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof ObjectWrapper)) {
				return false;
			}

			ObjectWrapper wrapper = (ObjectWrapper) obj;
			if (!ObjectUtils.nullSafeEquals(value, wrapper.value)) {
				return false;
			}

			return true;
		}

		/**
		 * @return the wrapped value
		 */
		public Serializable getValue() {
			return value;
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			int multiplier = 31;
			int hash = 17;

			// hash = multiplier * hash + Objects.nullSafeHashCode(value);
			hash = multiplier * hash + value.hashCode();
			return hash;
		}

		/**
		 * Sets the new value to wrap
		 * 
		 * @param value
		 *            the new value
		 */
		public void setValue(Serializable value) {
			this.value = value;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return this.identityToString(this).append("[value=").append(
					StringUtils.quoteIfString(value)).append(']').toString();
		}

		public StringBuffer identityToString(Object obj) {
			StringBuffer buffer = new StringBuffer();
			if (obj != null) {
				buffer.append(obj.getClass().getName());
				buffer.append("@");
				buffer.append(ObjectUtils.getIdentityHexString(obj));
			}
			return buffer;
		}

	}

	private XStream xstream;

	/**
	 * Constructor.
	 */
	public XStreamSerializableFactory() {
		this.xstream = new XStream();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof XStreamSerializableFactory)) {
			return false;
		}
		return true;
	}

	/**
	 * @see SerializableFactory#getOriginalValue(Object)
	 */
	public Object getOriginalValue(Object obj) {
		if (!(obj instanceof ObjectWrapper)) {
			return obj;
		}

		String value = (String) ((ObjectWrapper) obj).getValue();
		return xstream.fromXML(value);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return 3982;
	}

	/**
	 * @see SerializableFactory#makeSerializableIfNecessary(Object)
	 */
	public Serializable makeSerializableIfNecessary(Object obj) {
		if (obj == null || obj instanceof Serializable) {
			return (Serializable) obj;
		}

		String value = xstream.toXML(obj);
		return new ObjectWrapper(value);
	}

}