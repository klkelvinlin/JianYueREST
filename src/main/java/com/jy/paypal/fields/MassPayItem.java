package com.jy.paypal.fields;

import java.util.HashMap;
import java.util.Map;

import com.jy.paypal.utils.Validator;

public final class MassPayItem implements RequestFields {

	private static final long serialVersionUID = -6409016219067623714L;

	private final Map<String, String> nvpRequest;

	/**
	 * 
	 * @param key
	 *            key in nvp request string
	 * @param value
	 *            value in nvp request string
	 * @param amount
	 *            payment amount
	 * @throws IllegalArgumentException
	 */
	private MassPayItem(String key, String value, String amount)
			throws IllegalArgumentException {

		if (!Validator.isValidAmount(amount)) {
			throw new IllegalArgumentException("amount - " + amount
					+ " is not valid");
		}

		nvpRequest = new HashMap<String, String>();
		nvpRequest.put(key, value);
		nvpRequest.put("L_AMT", amount);
	}

	/**
	 * 
	 * @param email
	 *            Email address of recipient. Character length and limitations:
	 *            127 single-byte characters maximum.
	 * @param amount
	 *            Payment amount.
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static MassPayItem getNewEmailMassPayItem(String email, String amount)
			throws IllegalArgumentException {

		if (!Validator.isValidEmail(email)) {
			throw new IllegalArgumentException("email is not valid");
		}
		if (email.length() > 127) {
			throw new IllegalArgumentException("email cannot be longer than "
					+ "127 characters");
		}
		return new MassPayItem("L_EMAIL", email, amount);
	}

	/**
	 * 
	 * @param receiverId
	 *            Unique PayPal customer account number. This value corresponds
	 *            to the value of PayerID returned by GetTransactionDetails.
	 *            Character length and limitations: 17 single-byte characters
	 *            maximum.
	 * @param amount
	 *            Payment amount.
	 * @return
	 */
	public static MassPayItem getNewReceiverIdMassPayItem(String receiverId,
			String amount) throws IllegalArgumentException {

		if (receiverId.length() > 17) {
			throw new IllegalArgumentException("receiverId cannot be longer "
					+ "than 17 characters");
		}
		return new MassPayItem("L_RECEIVERID", receiverId, amount);
	}

	/**
	 * 
	 * @param id
	 *            Transaction-specific identification number for tracking in an
	 *            accounting system. Character length and limitations: 30
	 *            single-byte characters. No whitespace allowed.
	 * @throws IllegalArgumentException
	 */
	public void setUniqueId(String id) throws IllegalArgumentException {

		if (id.contains(" ")) {
			throw new IllegalArgumentException("id cannot contain white space");
		}
		if (id.length() > 30) {
			throw new IllegalArgumentException("id cannot be longer than 30"
					+ " characters");
		}
		nvpRequest.put("L_UNIQUEID", id);
	}

	/**
	 * 
	 * @param note
	 *            Custom note for each recipient. Character length and
	 *            limitations: 4,000 single-byte alphanumeric characters.
	 * @throws IllegalArgumentException
	 */
	public void setNote(String note) throws IllegalArgumentException {

		if (note.length() > 4000) {
			throw new IllegalArgumentException(
					"note cannot be longer than 4000" + " characters");
		}
		nvpRequest.put("L_NOTE", note);
	}

	public Map<String, String> getNVPRequest() {
		return new HashMap<String, String>(nvpRequest);
	}

	@Override
	public String toString() {

		return "instance of MassPayItem class with the values: "
				+ "nvpRequest: " + nvpRequest.toString();
	}

}
