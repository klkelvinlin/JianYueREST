package com.jy.paypal.request;

import java.util.HashMap;
import java.util.Map;

public final class ManagePendingTransactionStatus implements Request {

	private static final long serialVersionUID = -3925537705456488938L;

	/**
	 * The operation you want to perform on the transaction
	 */
	public enum Action {
		/* accepts the payment */
		ACCEPT,

		/* rejects the payment */
		DENY;
	}

	private static final String METHOD_NAME = "ManagePendingTransactionStatus";

	private final Map<String, String> nvpRequest;

	private Map<String, String> nvpResponse;

	/**
	 * Returns instance of ManagePendingTransaction class. If arguments are not
	 * valid, exception is thrown.
	 * 
	 * @param transactionId
	 *            The transaction ID of the payment transaction.
	 * @param action
	 *            The operation you want to perform on the transaction
	 * @throws IllegalArgumentException
	 */
	public ManagePendingTransactionStatus(String transactionId, Action action)
			throws IllegalArgumentException {

		if (transactionId == null || transactionId.length() > 17) {
			throw new IllegalArgumentException("Transaction id cannot be "
					+ "longer than 17 characters.");
		}
		nvpResponse = new HashMap<String, String>();
		nvpRequest = new HashMap<String, String>();
		nvpRequest.put("METHOD", METHOD_NAME);
		nvpRequest.put("TRANSACTIONID", transactionId);
		nvpRequest.put("ACTION", action.toString());
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

		StringBuffer str = new StringBuffer("instance of ");
		str.append("MangagePendingTransactionStatus class with the vlues: ");
		str.append("nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}

}
