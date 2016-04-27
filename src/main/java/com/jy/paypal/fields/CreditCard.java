package com.jy.paypal.fields;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jy.paypal.utils.FormatFields;
import com.jy.paypal.utils.Validator;

public final class CreditCard implements RequestFields {

	private static final long serialVersionUID = -6924736478431506092L;

	/**
	 * Type of credit card
	 * 
	 * @author wdong
	 * 
	 */
	public enum CreditCardType {
		VISA("Visa"), MASTER_CARD("MasterCard"), AMEX("Amex");

		private String value;

		private CreditCardType(String value) {
			this.value = value;
		}

		/** returns value name for nvp request */
		String getValue() {
			return value;
		}
	}

	/**
	 * map that holds name value pair request values
	 */
	private final Map<String, String> nvpRequest;

	public CreditCard(CreditCardType cardType, String cardNumber)
			throws IllegalArgumentException {

		if (!Validator.isValidLuhn(cardNumber)) {
			throw new IllegalArgumentException("Card number - " + cardNumber
					+ " is not valid.");
		}

		nvpRequest = new HashMap<String, String>();

		nvpRequest.put("CREDITCARDTYPE", cardType.getValue());
		nvpRequest.put("ACCT", cardNumber);
	}

	/**
	 * 
	 * @param expiryDate
	 *            Credit card expiration date. This field is required if you are
	 *            using recurring payments with direct payments. Only month and
	 *            a year is used from supplied Date argument.
	 */
	public void setExpiryDate(Date expiryDate) {
		nvpRequest.put("EXPDATE", FormatFields.getCardDateField(expiryDate));
	}

	/**
	 * 
	 * @param cvv2
	 *            Card Verification Value, version 2. Your Merchant Account
	 *            settings determine whether this field is required. Character
	 *            length for Visa, MasterCard, and Discover: exactly three
	 *            digits.Character length for American Express: exactly four
	 *            digits.To comply with credit card processing regulations, you
	 *            must not store this value after a transaction has been
	 *            completed.
	 * @throws IllegalArgumentException
	 */
	public void setCVV2(int cvv2) throws IllegalArgumentException {

		String cardType = nvpRequest.get("CREDITCARDTYPE");
		int numberLength = String.valueOf(cvv2).length();

		if (cardType.equals("Amex") && numberLength != 4) {
			throw new IllegalArgumentException("Please provide correct cvv2");
		} else if (numberLength != 3) {
			throw new IllegalArgumentException("Please provide correct cvv2");
		}

		nvpRequest.put("CVV2", Integer.toString(cvv2));
	}

	/**
	 * 
	 * @param startDate
	 *            Month and year that Maestro or Solo card was issued. Only
	 *            month and a year is used from supplied Date argument.
	 */
	public void setStartDate(Date startDate) {
		nvpRequest.put("STARTDATE", FormatFields.getCardDateField(startDate));
	}

	/**
	 * 
	 * @param issueNumber
	 *            Issue number of Maestro or Solo card. Character length: two
	 *            numeric digits maximum.
	 * @throws IllegalArgumentException
	 */
	public void setIssueNumber(int issueNumber) throws IllegalArgumentException {

		int numberLength = String.valueOf(issueNumber).length();

		if (numberLength > 2) {
			throw new IllegalArgumentException("Issue number cannot have more "
					+ "than 2 numeric digits");
		}

		nvpRequest.put("ISSUENUMBER", String.valueOf(issueNumber));
	}

	public Map<String, String> getNVPRequest() {
		return new HashMap<String, String>(nvpRequest);
	}

	@Override
	public String toString() {

		return "instance of CreditCard class with the values: "
				+ "nvpRequest: " + nvpRequest.toString();
	}

}
