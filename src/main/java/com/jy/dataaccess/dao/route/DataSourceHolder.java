/*********************************************************************************
*
* <p>
* Perforce File Stats:
* <pre>
* $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/dao/route/DataSourceHolder.java#1 $
* $DateTime: 2012/12/19 03:42:21 $
* $Change: 6681389 $
* </pre>
* </p>
*
* @author $Author: wdong $
* @version $Revision: #1 $
*
* Copyright Notice
*
* This file contains proprietary information of Amazon.com.
* Copying or reproduction without prior written approval is prohibited.
*
* Copyright (c) 2012 Amazon.com.  All rights reserved.
*
*********************************************************************************/
package com.jy.dataaccess.dao.route;

import org.springframework.util.Assert;

/**
 * A thread local to hold Data Source and to support Data Source dynamic change at runtime.
 * If we use ThreadLocal, the JTA transaction manager must be used.
 * @author wdong
 *
 */
public class DataSourceHolder {
    private static final ThreadLocal<DataSourceType> holder = new ThreadLocal<DataSourceType>();
    
    public static void setDataSourceType(DataSourceType type) {
            Assert.notNull(type, "DataSource Type can't be empty.");
            holder.set(type);
    }
    
    public static DataSourceType getDataSourceType() {
            return holder.get();
    }
    
    public static void removeDataSourcetype() {
            holder.remove();
    }
}
