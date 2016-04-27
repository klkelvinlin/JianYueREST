package com.jy.dataaccess.cache.annotations.aop.adatper;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.SoftException;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 
 * @author wdong
 * 
 */
@Aspect
public abstract class AOPAllianceAdapter {

	MethodInterceptor methodInterceptor;

	/**
	 * sub-aspects override this method to tell the adapter which interceptor to
	 * drive.
	 * 
	 * @return
	 */
	protected abstract MethodInterceptor getMethodInterceptor();

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}

	/**
	 * constructor interceptors are less often used, so we provide a default
	 * implementation
	 * 
	 * @return
	 */
	protected ConstructorInterceptor getConstructorInterceptor() {
		return null;
	}
	
	/**
	 * sub-aspects define this point cut to determine the join points at which
	 * the interceptor(s) will be invoked.
	 */
	@Pointcut
	protected abstract void targetJoinPoint();
	
	
	/**
	 * method interceptors should only be triggered at method-execution join 
	 * points, and constructor interceptors at constructor-execution join points
	 */
	@Pointcut("execution(* *(..))")
	public void methodExecution() {
	}
	
	@Pointcut("execution(new(..))")
	public void constructorExecution() {
	}
	
	@Around("targetJoinPoint() && methodExecution()")
	public Object around(ProceedingJoinPoint pjp) {
		MethodInvocationAdapter mic = new MethodInvocationAdapter(pjp);
		MethodInterceptor mInt = getMethodInterceptor();
		Object obj = null;
		if (mInt != null) {
			try {
				obj = mInt.invoke(mic);
			} catch (Throwable t) {
				throw new SoftException(t);
			}
		} else {
			try {
				obj = pjp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
