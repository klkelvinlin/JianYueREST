package com.jy.paypal.fields;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author wdong
 *
 */
public interface RequestFields extends Serializable {
	
	/**
	 * Creates and returns part of the nvp (name value pair) request containing
     * request values
	 * @return
	 */
	Map<String, String> getNVPRequest();
}
 