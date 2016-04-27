package com.jy.dataaccess.cache.annotations.aop;

/**
 * 
 * @author wdong
 * 
 */
public class MethodFQN {
	private String className;

	private String methodName;

	public MethodFQN(String fqn) throws IllegalArgumentException {
		int separatorIndex = methodSeparator(fqn);
		className = fqn.substring(0, separatorIndex);
		methodName = fqn.substring(separatorIndex + 1);
	}

	private int methodSeparator(String fqn) {
		int separatorIndex = fqn.lastIndexOf(".");
		if (separatorIndex == -1)
			throw new IllegalArgumentException("'" + fqn
					+ "' is not a fully qualified name");
		return separatorIndex;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}
}
