package com.jy.paypal.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Get information about a recurring payments profile.
 * 
 * @author wdong
 * 
 */
public final class GetRecurringPaymentsProfileDetails implements Request {

	private static final String METHOD_NAME = "GetRecurringPaymentsProfileDetails";

	private final Map<String, String> nvpRequest;

	private Map<String, String> nvpResponse;

	private static final long serialVersionUID = 8287725619774015446L;

	/**
	 * 
	 * @param profileId
	 *            Recurring payments profile ID returned in the
	 *            CreateRecurringPaymentsProfile response. Character length and
	 *            limitations: 14 single-byte alphanumeric characters. 19
	 *            character profile IDs are supported for compatibility with
	 *            previous versions of the PayPal API.
	 * @throws IllegalArgumentException
	 */
	public GetRecurringPaymentsProfileDetails(String profileId)
			throws IllegalArgumentException {

		if (profileId.length() != 14 || profileId.length() != 19) {
			throw new IllegalArgumentException("profileId has to be 14 or 19 "
					+ "characters long");
		}

		nvpResponse = new HashMap<String, String>();
		nvpRequest = new HashMap<String, String>();

		nvpRequest.put("METHOD", METHOD_NAME);
		nvpRequest.put("PROFILEID", profileId);
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

		StringBuffer str = new StringBuffer(
				"instance of GetRecurringPaymentsProfileDetails");
		str.append("class with the vlues: nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}

}
