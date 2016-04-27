package com.jy.paypal.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Obtain information about a specific transaction.
 * 
 * @author wdong
 * 
 */
public final class GetTransactionDetails implements Request {

	private static final long serialVersionUID = -4845093939068591428L;

	private static final String METHOD_NAME = "GetTransactionDetails";

	private final Map<String, String> nvpRequest;

	private Map<String, String> nvpResponse;

	/**
	 * Unique identifier of a transaction. NOTE: The details for some kinds of
	 * transactions cannot be retrieved with GetTransactionDetails. You cannot
	 * obtain details of bank transfer withdrawals, for example.
	 * 
	 * @param transactionId
	 *            maximum 17 single-byte alphanumeric characters
	 * @throws IllegalArgumentException
	 */
	public GetTransactionDetails(String transactionId)
			throws IllegalArgumentException {

		if (transactionId == null || transactionId.length() > 17) {
			throw new IllegalArgumentException("Transaction id cannot be "
					+ "longer than 17 characters.");
		}
		nvpResponse = new HashMap<String, String>();
		nvpRequest = new HashMap<String, String>();
		nvpRequest.put("METHOD", METHOD_NAME);
		nvpRequest.put("TRANSACTIONID", transactionId);
	}

	public Map<String, String> getNVPRequest() {
		return new HashMap<String, String> (nvpRequest);
	}

	public void setNVPResponse(Map<String, String> nvpResponse) {
		this.nvpResponse = new HashMap<String, String>(nvpResponse);
	}

	public Map<String, String> getNVPResponse() {
		return new HashMap<String, String>(nvpResponse);
	}

	@Override
	public String toString() {

		StringBuffer str = new StringBuffer("instance of ");
		str.append("GetTransactionDetails class with the vlues: nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}

}
