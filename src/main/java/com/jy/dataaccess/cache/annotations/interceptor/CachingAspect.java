package com.jy.dataaccess.cache.annotations.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.SoftException;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Caching aspect
 * @author wdong
 *
 */
@Aspect
public abstract class CachingAspect {
	
	/**
	 * setter injection
	 */
	AbstractCachingInterceptor methodInterceptor;
	
	public AbstractCachingInterceptor getMethodInterceptor() {
		return this.methodInterceptor;
	}
	
	public void setMethodInterceptor(AbstractCachingInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}
	
	@Around("methodsToBeIntercept()")
	public Object intercept(ProceedingJoinPoint pjp) {
		AbstractCachingInterceptor mInt = getMethodInterceptor();
		Object result = null;
		
		if(mInt != null) {
			try {
				result = mInt.invokeByAspectj(pjp);
			} catch (Throwable e) {				
				throw new SoftException(e);
			}
		} else {
			try {
				result = pjp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	@Pointcut
	public abstract void methodsToBeIntercept();
}
