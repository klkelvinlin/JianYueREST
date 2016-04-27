package com.jy.paypal.fields;

import java.util.HashMap;
import java.util.Map;

/**
 * Schedule Details Fields
 * @author wdong
 */
public final class ScheduleDetails implements RequestFields {

	private static final long serialVersionUID = -1085963919561513597L;

	private final Map<String, String> nvpRequest;

	/**
	 * @param description
	 *            Description of the recurring payment. Note: This field must
	 *            match the corresponding billing agreement description included
	 *            in the SetExpressCheckout request. Character length and
	 *            limitations: 127 single-byte alphanumeric characters
	 * @throws IllegalArgumentException
	 */
	public ScheduleDetails(String description) throws IllegalArgumentException {
		if (description.length() > 127) {
			throw new IllegalArgumentException(
					"Description cannot be longer than 127 characters");
		}

		nvpRequest = new HashMap<String, String>();
		nvpRequest.put("DESC", description);
	}

	/**
	 * 
	 * @param number
	 *            The number of scheduled payments that can fail before the
	 *            profile is automatically suspended. An IPN message is sent to
	 *            the merchant when the specified number of failed payments is
	 *            reached.
	 */
	public void setMaxFailedPayments(int number) {
		nvpRequest.put("MAXFAILEDPAYMENTS", Integer.toString(number));
	}

	/**
	 * This field indicates whether you would like PayPal to automatically bill
	 * the outstanding balance amount in the next billing cycle. The outstanding
	 * balance is the total amount of any previously failed scheduled payments
	 * that have yet to be successfully paid.
	 * 
	 * @param autoBill
	 */
	public void setAutoBillAmount(boolean autoBill) {
		String value = (autoBill) ? "AddToNextBilling" : "NoAutoBill";
		nvpRequest.put("AUTOBILLAMT", value);
	}

	@Override
	public Map<String, String> getNVPRequest() {
		return new HashMap<String, String>(nvpRequest);
	}

	@Override
	public String toString() {
		return "Instance of ScheduleDetails class with the values: nvpRequest: "
				+ nvpRequest.toString();
	}

}
