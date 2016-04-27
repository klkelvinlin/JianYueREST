/*********************************************************************************
 * <p>
 * Perforce File Stats:
 * 
 * <pre>
 * $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/mybatis/plugins/optmistic/MyBatisOptimisticLockInterceptor.java#1 $
 * $DateTime: 2012/12/26 05:52:14 $
 * $Change: 6702674 $
 * </pre>
 * 
 * </p>
 * 
 * @author $Author: wdong $
 * @version $Revision: #1 $ Copyright Notice This file contains proprietary
 * information of Amazon.com. Copying or reproduction without prior written
 * approval is prohibited. Copyright (c) 2012 Amazon.com. All rights reserved.
 *********************************************************************************/
package com.jy.dataaccess.mybatis.plugins.optmistic;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;

/**
 * The optimistic lock implementation for MyBatis
 * 
 * @author wdong
 */
//TODO:to be completed.
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) }) 
public class MyBatisOptimisticLockInterceptor implements Interceptor {
    
    private static final Logger LOGGER = Logger.getLogger(MyBatisOptimisticLockInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = null;
        Executor executor = (Executor) invocation.getTarget();
        Connection connection = executor.getTransaction().getConnection();

        if (invocation.getArgs() != null && invocation.getArgs().length > 0 && invocation.getArgs()[0] != null
            && invocation.getArgs()[0] instanceof MappedStatement) {
            ms = (MappedStatement) invocation.getArgs()[0];
            Object obj = invocation.getArgs()[1];
            if (this.hasOptimisticLockAnnotation(obj)) {
                if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
                    update(connection, obj);
                } else if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
                    initVersion(connection, obj);
                } else if (ms.getSqlCommandType() == SqlCommandType.DELETE) {
                    delete(connection, obj);
                }
            } else {
                if (ms.getSqlCommandType() == SqlCommandType.UPDATE || ms.getSqlCommandType() == SqlCommandType.INSERT
                    || ms.getSqlCommandType() == SqlCommandType.DELETE) {
                    if (obj == null) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Optimistic Lock can not be set! Parameter is null.");
                        }
                    } else {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Optimistic Lock can not be set! Parameter type '" + obj.getClass().getName()
                                + "' does not have the annotation @OptimisticLocking.");
                        }
                    }
                }
            }
        }

        return invocation.proceed();
    }
    
    /**
     * To adjust if the Domain Object has OptimisticLock annotation
     * 
     * @param instance
     * @return
     */
    private boolean hasOptimisticLockAnnotation(Object instance) {
        if (instance == null) {
            LOGGER.debug("Object is null, can not check @OptimisticLock Annotation!");
            return false;
        }
        return instance.getClass().isAnnotationPresent(OptimisticLock.class);
    }

    private void delete(Connection connection, Object obj) {

    }

    private void initVersion(Connection connection, Object obj) {

    }

    private void update(Connection connection, Object obj) {

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //nothing to do now.
    }
}
