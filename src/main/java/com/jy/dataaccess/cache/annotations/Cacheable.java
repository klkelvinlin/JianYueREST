package com.jy.dataaccess.cache.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Cacheable annotation
 * 
 * @author wdong
 * 
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
	/**
	 * The id of the caching model to use.
	 * 
	 * @return caching model id
	 */
	String modelId();
}
