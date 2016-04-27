package com.jy.paypal.core;

import java.io.Serializable;
import java.net.MalformedURLException;

/**
 * Used for sending request and receving response
 * @author wdong
 *
 */
public interface Transport extends Serializable {
	
	/**
	 * Sends request (msg attribute) to the specified url and returns response as a string
	 * @param urlString url where to send the request
	 * @param msg		request message to be sent
	 * @return			response message
	 * @throws MalformedURLException
	 */
	String getResponse(String urlString, String msg) throws MalformedURLException;
}
