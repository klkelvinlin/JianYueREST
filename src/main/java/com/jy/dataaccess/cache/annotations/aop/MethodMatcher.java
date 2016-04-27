package com.jy.dataaccess.cache.annotations.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.ClassUtils;

import com.jy.dataaccess.cache.annotations.util.TextMatcher;

/**
 * 
 * @author wdong
 *
 */
public class MethodMatcher  implements Serializable{

	private static final long serialVersionUID = -6948946872975447191L;

	Collection<Method> matchingMethods(String fullyQualifiedMethodName) throws IllegalArgumentException{
		MethodFQN parser = new MethodFQN(fullyQualifiedMethodName);
		List<Method> matchingMethods = new ArrayList<Method>();
		Method[] methods = methods(parser.getClassName());
		for(int i = 0; i < methods.length; i++) {
			if (matchingMethod(methods[i], parser.getMethodName())){
				matchingMethods.add(methods[i]);
			}
		}
		return matchingMethods;
	}
	
	private Class<?> load(String className) throws IllegalArgumentException {
		Class<?> declaringClass = null;
		try {
			declaringClass = ClassUtils.forName(className, getDefaultClassLoader());
		} catch (ClassNotFoundException exception) {
			throw new IllegalArgumentException("Class '" + className
					+ "' not found");
		}
		return declaringClass;
	}

	private boolean matchingMethod(Method method, String target) {
		String n = method.getName();
		return n.equals(target) || TextMatcher.isMatch(n, target);
	}
	
	private Method[] methods(String className) throws IllegalArgumentException {
		Class<?> declaringClass = load(className);
		return declaringClass.getDeclaredMethods();
	}
	
	private ClassLoader getDefaultClassLoader() {
		return ClassUtils.getDefaultClassLoader();
	}
}
