package com.jy.mail.domain;

public class AbstractIdentityObject<T> {
	protected T id;

	public AbstractIdentityObject() {

	}

	public AbstractIdentityObject(T id) {
		this.id = id;
	}

	@SuppressWarnings("rawtypes")
	protected final boolean idEquals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		
		final AbstractIdentityObject that = (AbstractIdentityObject) o;
		if (this.id != null ? !this.id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	protected final int idHashCode() {
		int result = super.hashCode();
		result = 31 * result + (this.id != null ? this.id.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(final Object o) {
		return idEquals(o);
	}

	@Override
	public int hashCode() {
		return idHashCode();
	}

	public T getId() {
		return id;
	}

	public void setId(final T id) {
		this.id = id;
	}
}
