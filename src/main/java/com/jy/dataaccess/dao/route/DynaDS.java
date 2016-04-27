/*********************************************************************************
*
* <p>
* Perforce File Stats:
* <pre>
* $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/dao/route/DynaDS.java#1 $
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dynamic indicate annotation
 * User can add this annotation to DAO interfaces, and the framework will handle the annotation and replace different data source automatically at runtime.
 * If we use this feature, we must use JTA Transaction Manager.
 * @author wdong
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DynaDS {
    String datasource();
}
