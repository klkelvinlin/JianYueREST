/*********************************************************************************
*
* <p>
* Perforce File Stats:
* <pre>
* $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/dao/AbstractDomainObject.java#2 $
* $DateTime: 2013/01/30 06:17:51 $
* $Change: 6865744 $
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

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Abstract Domain Object, some shared properties should be put here. And we prefer every POJO extends this
 * class and then the framework can help to identify the Object persisted or not.
 * @author wdong
 *
 * @param <PK>
 */
public abstract class AbstractDomainObject {
    
    private AtomicBoolean persisted = new AtomicBoolean(false);
    private AtomicBoolean modified = new AtomicBoolean(false);

    private Long recordVersion = null;
    
    public boolean isPersisted() {
        return this.persisted.get();
    } 
    
    public void setPersisted(boolean isPersisted) {
        this.persisted.set(isPersisted);
    }

	public Long getRecordVersion() {
		return recordVersion;
	}

	public void setRecordVersion(Long recordVersion) {
		this.recordVersion = recordVersion;
	}

    public boolean isModified() {
        return this.modified.get();
    }

    public void setModified(boolean modified) {
        this.modified.set(modified);
    }
}
