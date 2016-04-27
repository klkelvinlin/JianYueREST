package com.jy.dataaccess.dao.finder.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.jy.dataaccess.dao.AbstractDomainObject;


/**
 * Created with IntelliJ IDEA.
 * User: wdong
 * Date: 13-5-6
 * Time: 下午9:55
 * To change this template use File | Settings | File Templates.
 */
@Aspect
public class DomainObjectModifiedFlagAspect {

    @Pointcut("execution(* com.jy.domain.*.set*(..))")
    public void flag(){}

    @Around(value = "flag() && target(target)", argNames = "pjp,target")
    public Object modified(ProceedingJoinPoint pjp, Object target) throws Throwable {
        Object obj = pjp.getThis();
        if(obj instanceof AbstractDomainObject) {
            if(!((AbstractDomainObject)obj).isModified()){
                ((AbstractDomainObject)obj).setModified(true);
            }
        }
        Object result = pjp.proceed();
        return result;
    }
}
