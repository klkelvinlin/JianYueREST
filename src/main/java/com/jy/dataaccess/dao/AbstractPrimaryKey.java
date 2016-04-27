/*********************************************************************************
*
* <p>
* Perforce File Stats:
* <pre>
* $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/dao/AbstractPrimaryKey.java#2 $
* $DateTime: 2012/12/26 05:52:14 $
* $Change: 6702674 $
* </pre>
* </p>
*
* @author $Author: wdong $
* @version $Revision: #2 $
*
* Copyright Notice
*
* This file contains proprietary information of Amazon.com.
* Copying or reproduction without prior written approval is prohibited.
*
* Copyright (c) 2012 Amazon.com.  All rights reserved.
*
*********************************************************************************/
package com.jy.dataaccess.dao;

/**
 * Abstract class for Primary Key, all the composite Primary Keys should extend this class. If the domain POJO just has one primary key, it also must 
 * extend AbstractPrimaryKey class.
 * for instance:
 *   class PK extends AbstractPrimaryKey{
 *      //primary keys getter and setter methods.
 *   };
 *   
 *   class POJO extends PK {
 *   }
 * @author wdong
 *
 */
public abstract class AbstractPrimaryKey {
}
