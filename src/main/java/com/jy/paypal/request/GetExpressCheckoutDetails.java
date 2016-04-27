package com.jy.paypal.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Obtain information about an Express Checkout transaction.
 * 
 * @author wdong
 * 
 */
public final class GetExpressCheckoutDetails implements Request {

	private static final long serialVersionUID = -413147023254401454L;

	private static final String METHOD_NAME = "GetExpressCheckoutDetails";

	private final Map<String, String> nvpRequest;

	private Map<String, String> nvpResponse;

	public GetExpressCheckoutDetails(String token)
			throws IllegalArgumentException {
		if (token.length() != 20) {
			throw new IllegalArgumentException("Invalid token argument");
		}

		nvpResponse = new HashMap<String, String>();
		nvpRequest = new HashMap<String, String>();

		nvpRequest.put("METHOD", METHOD_NAME);
		nvpRequest.put("TOKEN", token);
	}

	@Override
	public Map<String, String> getNVPRequest() {
		return new HashMap<String, String>(nvpRequest);
	}

	@Override
	public void setNVPResponse(Map<String, String> nvpResponse) {
		this.nvpResponse = new HashMap<String, String>(nvpResponse);
	}

	@Override
	public Map<String, String> getNVPResponse() {
		return new HashMap<String, String>(nvpResponse);
	}

	@Override
	public String toString() {

		StringBuffer str = new StringBuffer(
				"instance of GetExpressCheckoutDetails ");
		str.append("class with the values: nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}

}
