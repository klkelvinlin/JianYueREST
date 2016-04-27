package com.jy.paypal.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Obtain the available balance for a PayPal account.
 * 
 * @author wdong
 * 
 */
public final class GetBalance implements Request {

	private static final long serialVersionUID = -5798734696973315547L;

	private static final String METHOD_NAME = "GetBalance";

	private final Map<String, String> nvpRequest;

	private Map<String, String> nvpResponse;

	public GetBalance() {
		nvpResponse = new HashMap<String, String>();
		nvpRequest = new HashMap<String, String>();
		nvpRequest.put("METHOD", METHOD_NAME);
	}

	/**
	 * Whether to return all currencies.
	 * 
	 * @param allCurrencies
	 *            true - Return the balance for each currency holding false -
	 *            Return only the balance for the primary currency holding
	 */
	public void setAllCurrencies(boolean allCurrencies) {

		String all = (allCurrencies) ? "1" : "0";
		nvpRequest.put("RETURNALLCURRENCIES", all);
	}

	public Map<String, String> getNVPRequest() {
		return new HashMap<String, String>(nvpRequest);
	}

	public void setNVPResponse(Map<String, String> nvpResponse) {
		this.nvpResponse = new HashMap<String, String>(nvpResponse);
	}

	public Map<String, String> getNVPResponse() {
		return new HashMap<String, String>(nvpResponse);
	}

	@Override
	public String toString() {

		StringBuffer str = new StringBuffer("instance of GetBalance");
		str.append("class with the vlues: nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}

}
