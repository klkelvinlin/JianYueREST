package com.jy.paypal.fields;

import java.util.HashMap;
import java.util.Map;

public final class BillingAgreement implements RequestFields {

	private static final long serialVersionUID = 1668242423273895870L;

	/** PayPal payment you require for the billing agreement */
	public enum PaymentType {

		ANY("Any"), INSTANT_ONLY("InstantOnly");

		private String name;

		private PaymentType(String name) {
			this.name = name;
		}

		/** returns key name for nvp request */
		String getName() {
			return name;
		}
	}

	/** map that holds name value pair request values */
	private final Map<String, String> nvpRequest;

	public BillingAgreement() {
		nvpRequest = new HashMap<String, String>();
	}

	/**
	 * Type of billing agreement. For recurring payments, this field must be set
	 * to <b>RecurringPayments</b> and description (<b>setDescription</b>) MUST
	 * be set as well. In this case, you can specify up to ten billing
	 * agreements. Note: Other defined values are not valid.
	 * 
	 * @param billingType
	 */
	public void setBillingType(String billingType) {
		nvpRequest.put("L_BILLINGTYPE0", billingType);
	}

	/**
	 * Description of goods or services associated with the billing agreement,
	 * which is required for each recurring payment billing agreement. PayPal
	 * recommends that the description contain a brief summary of the billing
	 * agreement terms and conditions. For example, customer will be billed at
	 * "9.99 per month for 2 years". Character length and limitations: 127
	 * single-byte alphanumeric bytes.
	 * 
	 * @param description
	 * @throws IllegalArgumentException
	 */
	public void setDescription(String description)
			throws IllegalArgumentException {

		if (description.length() > 127) {
			throw new IllegalArgumentException("Description cannot exceed 127 "
					+ "characters");
		}
		nvpRequest.put("L_BILLINGAGREEMENTDESCRIPTION0", description);
	}

	/**
	 * Specifies type of PayPal payment you require for the billing agreement.
	 * Note: For recurring payments, this field is ignored.
	 * 
	 * @param paymentType
	 */
	public void setPaymentType(PaymentType paymentType) {
		nvpRequest.put("L_PAYMENTTYPE0", paymentType.getName());
	}

	/**
	 * Custom annotation field for your own use. Note: For recurring payments,
	 * this field is ignored. Character length and limitations: 256 single-byte
	 * alphanumeric bytes.
	 * 
	 * @param field
	 * @throws IllegalArgumentException
	 */
	public void setCustomField(String field) throws IllegalArgumentException {

		if (field.length() > 256) {
			throw new IllegalArgumentException("Field cannot exceed 256 "
					+ "characters");
		}
		nvpRequest.put("L_BILLINGAGREEMENTCUSTOM", field);
	}

	public Map<String, String> getNVPRequest() {
		return new HashMap<String, String>(nvpRequest);
	}

	@Override
	public String toString() {

		return "instance of BillingAgreement class with the values: "
				+ "nvpRequest: " + nvpRequest.toString();
	}

}
