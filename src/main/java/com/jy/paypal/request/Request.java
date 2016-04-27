package com.jy.paypal.request;

import java.io.Serializable;
import java.util.Map;

/**
 * Paypal Request interface
 * @author wdong
 *
 */
public interface Request extends Serializable {
	
	/**
	 * Creates and returns part of the nvp (name value pair) request containing
	 * request values
	 * 
	 * @return part of the nvp request as a Map
	 */
	Map<String, String> getNVPRequest();

	/**
	 * Setter for nvp (name value pair) response
	 * 
	 * @param nvpResponse
	 */
	void setNVPResponse(Map<String, String> nvpResponse);

	/**
	 * Return response from paypal. If response is not set/received returns
	 * empty Map.
	 * 
	 * @return response from paypal as a Map
	 */
	Map<String, String> getNVPResponse();
}
