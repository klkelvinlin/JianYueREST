package com.jy.paypal.fields;

import java.io.Serializable;

/**
 * Currency type, for now, we just support USD. 
 * We may need to support other currency types in the future.
 * @author wdong
 *
 */
public enum Currency implements Serializable {

	/**
	 * U.S. Dollar
	 */
	USD;
}
