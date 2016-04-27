/*********************************************************************************
*
* <p>
* Perforce File Stats:
* <pre>
* $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/dao/route/DynamicDataSource.java#1 $
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

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    
}
