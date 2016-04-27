package com.jy.dataaccess.mybatis.plugins.persist;

import java.sql.Statement;
import java.util.Collection;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.jy.dataaccess.dao.AbstractDomainObject;

/**
 * This interceptor is for ResultSetHandler to add persist property value of
 * DomainObject, it is bining FBA MIC business domain model,should not be used
 * in general case.
 * 
 * @author wdong
 * 
 */
@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {
		Statement.class}) })
public class LazyLoadingIdentifyInterceptor implements Interceptor {
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {		
		Object result = invocation.proceed();
		
		if(result instanceof AbstractDomainObject) {
			((AbstractDomainObject)result).setPersisted(true);
		} else if (result instanceof Collection) {
			if(result != null && ((Collection<?>)result).size() > 0) {
				for(Object r : (Collection<?>)result) {
					if(r instanceof AbstractDomainObject) {
						((AbstractDomainObject)r).setPersisted(true);
                        ((AbstractDomainObject)r).setModified(false);//tricky, we need to change the modified as false here, but there will be some issues.
					}
				}
			}
		}
		
		return result;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		//nothing to do
	}

}
