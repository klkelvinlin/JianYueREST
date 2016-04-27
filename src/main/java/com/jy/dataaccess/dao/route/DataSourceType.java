/*********************************************************************************
*
* <p>
* Perforce File Stats:
* <pre>
* $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/dao/route/DataSourceType.java#1 $
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

/**
 * Dynamic Data Source key enumeration, it will be replaced by configuration files.
 * @author wdong
 *
 */
public enum DataSourceType {
    FBA1DS("FBA1DS"),
    MRPDS("MRPDS");

    private String type;

    private DataSourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static DataSourceType toDataSourceType(String type) {
        for (DataSourceType dsType : DataSourceType.values()) {
            if (type.equals(dsType.getType())) {
                return dsType;
            }
        }
        return null;
    }
}
