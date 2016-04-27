/*********************************************************************************
*
* <p>
* Perforce File Stats:
* <pre>
* $Id: //brazil/src/appgroup/appgroup/libraries/FBAMICDataAccessLayer/mainline/src/com/amazon/fba/mic/dao/utils/ReflectionUtils.java#1 $
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
package com.jy.dataaccess.dao.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

/**
 * reflection utility class
 * @author wdong
 *
 */
public class ReflectionUtils {
    private static final Logger LOGGER = Logger.getLogger(ReflectionUtils.class);

    public static Object getFieldValue(final Object object, final String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            LOGGER.warn("Could not find field [" + fieldName + "] on target [" + object + "]");
            return;
        }

        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {

        }
    }

    protected static Field getDeclaredField(final Object object, final String fieldName) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {}
        }
        return null;
    }

    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }
}
