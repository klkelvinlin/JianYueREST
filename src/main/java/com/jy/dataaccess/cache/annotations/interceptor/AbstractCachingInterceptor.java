package com.jy.dataaccess.cache.annotations.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ObjectUtils;

import com.jy.dataaccess.cache.CachingModel;
import com.jy.dataaccess.cache.annotations.aop.adatper.MethodInvocationAdapter;
import com.jy.dataaccess.cache.annotations.key.CacheKeyGenerator;
import com.jy.dataaccess.cache.annotations.key.HashCodeCacheKeyGenerator;
import com.jy.dataaccess.cache.annotations.provider.CacheProvider;
import com.jy.dataaccess.cache.annotations.util.CachingUtils;
import com.jy.exception.FatalCacheException;

/**
 * 
 * @author wdong
 * 
 */
public abstract class AbstractCachingInterceptor implements MethodInterceptor,
		InitializingBean, Serializable {

	private static final long serialVersionUID = -5860797181654542168L;

	protected final Log logger = LogFactory.getLog(getClass());

	public static final NullObject NULL_ENTRY = new NullObject();

	private CacheProvider cacheProvider;

	private CacheKeyGenerator cacheKeyGenerator;

	private CachingListener[] listeners;

	private Map<?, ?> modelMap; // properties mapping

	@Override
	public final void afterPropertiesSet() throws Exception {
		validateCacheProvider();
		if (modelMap instanceof Properties) {
			setCachingModels(propertiesToModels());
		}

		if (cacheKeyGenerator == null) {
			setCacheKeyGenerator(defaultKeyGenerator());
		}

		onAfterPropertiesSet();
	}

	@Override
	public final Object invoke(MethodInvocation invocation) throws Throwable {
		MethodInvocationProceedingJoinPoint jp = new MethodInvocationProceedingJoinPoint(
				(ProxyMethodInvocation) invocation);
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		Method method = methodSignature.getMethod();

		if (!CachingUtils.isCacheable(method)) {
			return methodNotCacheable(invocation, method);
		}

		CachingModel model = model(invocation);
		if (model == null) {
			return noModelFound(invocation, method);
		}

		Serializable key = this.cacheKeyGenerator.generateKey(invocation);
		Object cached = cacheProvider.getFromCache(key, model);

		if (null == cached) {
			return cachedValueFromSource(invocation, key, model);
		}

		return unmaskNull(cached);
	}
	
	public final Object invokeByAspectj(ProceedingJoinPoint pjp) throws Throwable {
		MethodInvocationAdapter adapter = new MethodInvocationAdapter(pjp);
		Method method = adapter.getMethod();
		
		if(!CachingUtils.isCacheable(method)){
			return this.noModelFound(adapter, method);
		}
		
		CachingModel model = model(adapter);
		if(model == null) {
			return this.noModelFound(adapter, method);
		}
		
		Serializable key = cacheKeyGenerator.generateKey(adapter);
		Object cached = cacheProvider.getFromCache(key, model);

		if (null == cached) {
			return cachedValueFromSource(adapter, key, model);
		}
		
		return unmaskNull(cached);
	}

	public final void setCachingModels(Map<Object, Object> m) {
		modelMap = m;
	}

	public final CacheKeyGenerator cacheKeyGenerator() {
		return this.cacheKeyGenerator;
	}

	public final void setCacheKeyGenerator(CacheKeyGenerator k) {
		cacheKeyGenerator = k;
	}
	
	public final void setCacheProvider(CacheProvider c) {
		this.cacheProvider = c;
	}
	
	public final void setCachingListeners(CachingListener[] cachingListeners) {
		listeners = cachingListeners.clone();
	}

	protected void onAfterPropertiesSet() throws FatalCacheException {
		// TODO: no implementation.
	}

	protected abstract CachingModel model(MethodInvocation invocation);
	
	protected final Map<?,?> models() {
		return modelMap;
	}

	private void validateCacheProvider() throws FatalCacheException {
		if (cacheProvider == null) {
			throw new FatalCacheException(
					"The cache provider facade should not be null");
		}
	}

	private Map<Object, Object> propertiesToModels() {
		Properties properties = (Properties) modelMap;
		Map<Object, Object> m = new HashMap<Object, Object>();

		for (Iterator<Object> i = properties.keySet().iterator(); i.hasNext();) {
			String id = (String) i.next();
			CachingModel cm = cacheProvider.getCachingModel(id);
			m.put(id, cm);
		}

		return m;
	}

	private CacheKeyGenerator defaultKeyGenerator() {
		return new HashCodeCacheKeyGenerator(true);
	}

	private Object methodNotCacheable(MethodInvocation invocation, Method m)
			throws Throwable {
		return logAndProceed("Unable to perform caching. Intercepted method <"
				+ m + "> does not return a value", invocation);
	}

	private Object logAndProceed(String message, MethodInvocation invocation)
			throws Throwable {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
		return invocation.proceed();
	}

	private Object noModelFound(MethodInvocation invocation, Method m)
			throws Throwable {
		return logAndProceed("Unable to perform caching. "
				+ "No model is associated to the method <" + m + ">",
				invocation);
	}

	private Object cachedValueFromSource(MethodInvocation invocation,
			Serializable key, CachingModel m) throws Throwable {
		boolean successful = true;
		try {
			Object value = invocation.proceed();
			putInCache(key, m, value);
			return value;
		} catch (Throwable t) {
			successful = false;
			if (logger.isDebugEnabled()) {
				logger.debug("method " + invocation.getMethod().getName()
						+ " throwed a exception", t);
			}
			throw t;
		} finally {
			if (!successful)
				cacheProvider.cancelCacheUpdate(key);
		}
	}
	
	private void putInCache(Serializable key, CachingModel m, Object o) {
		cacheProvider.putInCache(key, m, maskNull(o));
		notifyListeners(key, o, m);
	}
	
	private Object maskNull(Object o) {
		return o != null ? o : NULL_ENTRY;
	}
	
	private Object unmaskNull(Object obj) {
		return NULL_ENTRY.equals(obj) ? null : obj;
	}
	
	private void notifyListeners(Serializable key, Object cachedObject,
			CachingModel m) {
		if (ObjectUtils.isEmpty(listeners))
			return;
		for (int i = 0; i < listeners.length; i++)
			listeners[i].onCaching(key, cachedObject, m);
	}
}
