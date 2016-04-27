package com.jy.paypal.fields;

import java.util.HashMap;
import java.util.Map;

import com.jy.paypal.utils.Validator;

public final class ShippingOptions implements RequestFields {

	private static final long serialVersionUID = 5265409561072699272L;

	/** map that holds name value pair request values */
	private final Map<String, String> nvpRequest;

	public ShippingOptions() {
		nvpRequest = new HashMap<String, String>();
	}

	/**
	 * Required if specifying the Callback URL. When the value of this flat rate
	 * shipping option is true, PayPal selects it by default for the buyer and
	 * reflects it in the "default" total. Note: There must be ONE and ONLY ONE
	 * default. It is not OK to have no default.
	 * 
	 * @param isDefault
	 */
	public void setDefaultShippingOption(boolean isDefault) {

		String option = (isDefault) ? "true" : "false";
		nvpRequest.put("L_SHIPPINGOPTIONISDEFAULT", option);
	}

	/**
	 * Required if specifying the Callback URL. The internal name of the
	 * shipping option such as Air, Ground, Expedited, and so forth. Character
	 * length and limitations: 50 character-string.
	 * 
	 * @param name
	 * @throws IllegalArgumentException
	 */
	public void setShippingName(String name) throws IllegalArgumentException {

		if (name.length() > 50) {
			throw new IllegalArgumentException("Name cannot exceed 50 "
					+ "characters");
		}
		nvpRequest.put("L_SHIPPINGOPTIONNAME", name);
	}

	/**
	 * Required if specifying the Callback URL. The label for the shipping
	 * option as displayed to the user. Examples include: Air: Next Day,
	 * Expedited: 3-5 days, Ground: 5-7 days, and so forth. Shipping option
	 * labels can be localized based on the buyer��s locale, which PayPal sends
	 * to your website as a parameter value in the callback request. Character
	 * length and limitations: 50 character-string.
	 * 
	 * @param label
	 * @throws IllegalArgumentException
	 */
	public void setShippingLabel(String label) throws IllegalArgumentException {

		if (label.length() > 50) {
			throw new IllegalArgumentException("Label cannot exceed 50 "
					+ "characters");
		}
		nvpRequest.put("L_SHIPPINGOPTIONLABEL", label);
	}

	/**
	 * Required if specifying the Callback URL. The amount of the flat rate
	 * shipping option. Limitations: Must not exceed $10,000 USD in any
	 * currency. No currency symbol. Must have two decimal places, decimal
	 * separator must be a period (.).
	 * 
	 * @param amount
	 * @throws IllegalArgumentException
	 */
	public void setShippingAmount(String amount)
			throws IllegalArgumentException {

		if (!Validator.isValidAmount(amount)) {
			throw new IllegalArgumentException("Amount " + amount
					+ " is not valid. Amount has to have exactly two decimal "
					+ "places seaprated by \".\" - example: \"50.00\"");
		}
		nvpRequest.put("L_SHIPPINGOPTIONAMOUNT", amount);
	}

	public Map<String, String> getNVPRequest() {
		return new HashMap<String, String>(nvpRequest);
	}

	@Override
	public String toString() {

		return "instance of ShippingOptions class with the values: "
				+ "nvpRequest: " + nvpRequest.toString();
	}

}
