/*********************************************************************************
*
* <p>
* Perforce File Stats:
* <pre>
* $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/dao/finder/impl/FinderIntroductionAdvisor.java#2 $
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
package com.jy.dataaccess.dao.finder.impl;

import org.springframework.aop.support.DefaultIntroductionAdvisor;

import com.jy.dataaccess.dao.impl.GenericDaoImpl;

public class FinderIntroductionAdvisor<T> extends DefaultIntroductionAdvisor {

    private static final long serialVersionUID = -2232695352601606218L;

    public FinderIntroductionAdvisor() {
        super(new FinderIntroductionInterceptor<T>());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean matches(Class clazz) {
        return GenericDaoImpl.class.isAssignableFrom(clazz);
    }

}