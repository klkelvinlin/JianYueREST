/*********************************************************************************
*
* <p>
* Perforce File Stats:
* <pre>
* $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/mybatis/plugins/optmistic/OptimisticLock.java#1 $
* $DateTime: 2012/12/26 05:52:14 $
* $Change: 6702674 $
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
package com.jy.dataaccess.mybatis.plugins.optmistic;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation to indicate if the POJO should be added Optimistic Lock support.
 * @author wdong
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OptimisticLock {
    /**
     * Table name
     * @return
     */
    String table();
    
    /**
     * Field that be used as optimistic lock field
     * @return
     */
    String field();    
}
