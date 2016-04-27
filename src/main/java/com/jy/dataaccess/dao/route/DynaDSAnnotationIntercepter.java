/*********************************************************************************
 * <p>
 * Perforce File Stats:
 * 
 * <pre>
 * $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/dao/route/DynaDSAnnotationIntercepter.java#2 $
 * $DateTime: 2013/01/30 06:17:51 $
 * $Change: 6865744 $
 * </pre>
 * 
 * </p>
 * 
 * @author $Author: wdong $
 * @version $Revision: #2 $ Copyright Notice This file contains proprietary
 * information of Amazon.com. Copying or reproduction without prior written
 * approval is prohibited. Copyright (c) 2012 Amazon.com. All rights reserved.
 *********************************************************************************/
package com.jy.dataaccess.dao.route;

import java.lang.annotation.Annotation;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

import com.jy.dataaccess.dao.GenericDao;

/**
 * Intercepter to parse DynaDS annotation
 * 
 * @author wdong
 */
public class DynaDSAnnotationIntercepter<T> extends DelegatingIntroductionInterceptor {
	private static final long serialVersionUID = 951005792648973308L;

	public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?>[] interfaces = null;
        Class<?> c = AopUtils.getTargetClass(invocation);
        if (ReflectiveMethodInvocation.class.isAssignableFrom(c)) {
            ReflectiveMethodInvocation methodInvocation = (ReflectiveMethodInvocation) invocation;

            if ((AopUtils.isJdkDynamicProxy(methodInvocation.getProxy()) || AopUtils.isCglibProxy(methodInvocation.getProxy()))
                && methodInvocation.getProxy() instanceof Advised) {
                Advised advised = (Advised) methodInvocation.getProxy();
                interfaces = advised.getProxiedInterfaces();
            }

            if (interfaces == null || interfaces.length == 0) {
                DataSourceHolder.setDataSourceType(DataSourceType.toDataSourceType("DEFAULT"));
                return invocation.proceed();
            } else {
                for (Class<?> clazz : interfaces) {
                    if (GenericDao.class.isAssignableFrom(clazz)) {
                        Annotation[] annotations = clazz.getAnnotations();
                        for (Annotation annotation : annotations) {
                            if (annotation instanceof DynaDS) {
                                DataSourceHolder.setDataSourceType(DataSourceType.toDataSourceType(((DynaDS) annotation).datasource()));
                                break;
                            }
                        }
                    }
                }
            }
        }

        return invocation.proceed();
    }

}
