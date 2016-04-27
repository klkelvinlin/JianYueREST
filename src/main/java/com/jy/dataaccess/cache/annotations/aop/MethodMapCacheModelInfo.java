package com.jy.dataaccess.cache.annotations.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.jy.dataaccess.cache.CacheModel;

public final class MethodMapCacheModelInfo implements Serializable {

	private static final long serialVersionUID = -3850048660350912288L;

	private final MethodMatcher matcher;
	
	private final Map<Method, String> methodMap;
	
	private final Map<Method, CacheModel> models;
	
	public MethodMapCacheModelInfo() {
		matcher = new MethodMatcher	();
		methodMap = new HashMap<Method, String>();
		models = new HashMap<Method, CacheModel>();
	}
	
	public void addModel(CacheModel model, String fullyQualifiedMethodName) throws IllegalArgumentException {
		Collection<Method> matches = matcher.matchingMethods(fullyQualifiedMethodName);
		notEmpty(matches, fullyQualifiedMethodName);
		registerMostSpecificMethod(matches, model, fullyQualifiedMethodName);
	}
	
	public CacheModel model(Method m) {
		return models.get(m);
	}
	
	private void registerMostSpecificMethod(Collection<Method> matches,
			CacheModel model, String fullyQualifiedMethodName) {
		for (Iterator<Method> i = matches.iterator(); i.hasNext();) {
			Method method = (Method) i.next();
			if (methodNotRegisteredOrMoreSpecificMethodFound(method,fullyQualifiedMethodName)) {
				addModel(model, method, fullyQualifiedMethodName);
			}
		}
	}
	
	private boolean methodNotRegisteredOrMoreSpecificMethodFound(Method m,
			String target) {
		String registered = (String) methodMap.get(m);
		if (registered == null)
			return true;
		return !target.equals(registered)
				&& target.length() <= registered.length();
	}
	
	private void notEmpty(Collection<Method> matchingMethods, String targetMethodName)
			throws IllegalArgumentException {
		if (!matchingMethods.isEmpty())
			return;
		throw new IllegalArgumentException(
				"Couldn't find any method matching '" + targetMethodName + "'");
	}
	
	private void addModel(CacheModel c, Method m,
			String fullyQualifiedMethodName) {
		methodMap.put(m, fullyQualifiedMethodName);
		models.put(m, c);
	}
}
