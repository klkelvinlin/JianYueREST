package com.jy.dataaccess.cache.annotations.aop.adatper;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.ProxyMethodInvocation;

/**
 * 
 * @author wdong
 * 
 */
public class MethodInvocationAdapter implements ProxyMethodInvocation {

	ProceedingJoinPoint pjp;

	private Map<String, Object> userAttributes;

	protected Object[] arguments;

	public MethodInvocationAdapter(ProceedingJoinPoint pjp) {
		this.pjp = pjp;
	}

	public Method getMethod() {
		MethodSignature methodSig = (MethodSignature) pjp.getSignature();
		return methodSig.getMethod();
	}

	@Override
	public Object[] getArguments() {
		return pjp.getArgs();
	}

	@Override
	public Object proceed() throws Throwable {
		return pjp.proceed();
	}

	@Override
	public Object getThis() {
		return pjp.getThis();
	}

	@Override
	public AccessibleObject getStaticPart() {
		return this.getMethod();
	}

	@Override
	public Object getProxy() {
		return pjp.getThis();
	}

	@Override
	public MethodInvocation invocableClone() {
		Object[] cloneArguments = null;
		if (this.arguments != null) {
			// Build an independent copy of the arguments array.
			cloneArguments = new Object[this.arguments.length];
			System.arraycopy(this.arguments, 0, cloneArguments, 0,
					this.arguments.length);
		}
		return invocableClone(cloneArguments);
	}

	@Override
	public MethodInvocation invocableClone(Object[] arguments) {
		// Force initialization of the user attributes Map,
		// for having a shared Map reference in the clone.
		if (this.userAttributes == null) {
			this.userAttributes = new HashMap<String, Object>();
		}

		// Create the MethodInvocation clone.
		try {
			MethodInvocationAdapter clone = (MethodInvocationAdapter) clone();
			clone.arguments = arguments;
			return clone;
		} catch (CloneNotSupportedException ex) {
			throw new IllegalStateException(
					"Should be able to clone object of type [" + getClass()
							+ "]: " + ex);
		}
	}

	@Override
	public void setArguments(Object[] arguments) {
		this.arguments = arguments.clone();
	}

	@Override
	public void setUserAttribute(String key, Object value) {
		if (value != null) {
			if (this.userAttributes == null) {
				this.userAttributes = new HashMap<String, Object>();
			}
			this.userAttributes.put(key, value);
		}
		else {
			if (this.userAttributes != null) {
				this.userAttributes.remove(key);
			}
		}
	}

	@Override
	public Object getUserAttribute(String key) {
		return (this.userAttributes != null ? this.userAttributes.get(key) : null);
	}

}
