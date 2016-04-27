package com.jy.paypal.fields;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jy.paypal.fields.BillingAgreement.PaymentType;
import com.jy.paypal.utils.Validator;

/**
 * Payment Details Type Fields. For simple paymets use constructor with amount
 * field. If you want to set tax, or more options, use Constructor that takes
 * PaymentItem array.
 * 
 * We ignored the shipping options for the paypal paymentItem, because the
 * products of this website are all virtual ones.
 * 
 * @author wdong
 * 
 */
public final class Payment implements RequestFields {

	private static final long serialVersionUID = -3989348801943494232L;

	/**
	 * map that holds name value pair request values
	 */
	private final Map<String, String> nvpRequest;

	/**
	 * items that belong to this payment, empty if no items are added
	 */
	private List<Map<String, String>> items;

	/* same for all consturctors */
	{
		nvpRequest = new HashMap<String, String>();
		items = new LinkedList<Map<String, String>>();
	}

	/**
	 * You are adviced to use Payment(PaymentItem[] itmes) contructor, where you
	 * can specify all items individually, add individual descriptions,
	 * recurring payments etc.
	 * 
	 * @param amount
	 *            Limitations: Must not exceed $10,000 USD in any currency. No
	 *            currency symbol. Must have two decimal places, decimal
	 *            separator must be a period (.), and no thousands separator.
	 * @throws IllegalArgumentException
	 */
	public Payment(String amount) throws IllegalArgumentException {

		/* can be "0" as well */
		if (!Validator.isValidAmount(amount)) {
			throw new IllegalArgumentException(
					"Amount has to have exactly two "
							+ "decimal places seaprated by \".\" - example: \"50.00\"");
		}

		/* values for this request */
		nvpRequest.put("AMT", amount);
//		nvpRequest.put("PAYMENTREQUEST_0_AMT", amount);
//		nvpRequest.put("PAYMENTREQUEST_0_ITEMAMT", amount);
	}

	/**
	 * Create Payment from supplied items. Amount is calculated automatically.
	 * 
	 * @param items
	 * @throws IllegalArgumentException
	 */
	public Payment(PaymentItem[] items) throws IllegalArgumentException {

		/* check items */
		if (items == null || items.length == 0) {
			throw new IllegalArgumentException("You have to supply items.");
		}

		/* iterate supplied array */
		int x = 0; // this is only for exception message
		for (PaymentItem item : items) {
			/* item cannot be null */
			if (item == null) {
				throw new IllegalArgumentException("Itme at index " + x
						+ " is not set.");
			}
			this.items.add(new HashMap<String, String>(item.getNVPRequest()));
			x++;
		}
	}

	/**
	 * A three-character currency code. Default: USD
	 * 
	 * @param currency
	 */
	public void setCurrency(Currency currency) {
		nvpRequest.put("CURRENCYCODE", currency.toString());
	}

	/**
	 * Total shipping costs for this order. Note: Character length and
	 * limitations: Must not exceed $10,000 USD in any currency. No currency
	 * symbol. Regardless of currency, decimal separator must be a period (.)
	 * Equivalent to nine characters maximum for USD.
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
		nvpRequest.put("SHIPPINGAMT", amount);
	}

	/**
	 * Total shipping insurance costs for this order. The value must be a
	 * non-negative currency amount or null if insurance options are offered.
	 * Note: Character length and limitations: Must not exceed $10,000 USD in
	 * any currency. No currency symbol. Regardless of currency, decimal
	 * separator must be a period (.). Equivalent to nine characters maximum for
	 * USD.
	 * 
	 * @param amount
	 * @throws IllegalArgumentException
	 */
	public void setInsuranceAmount(String amount)
			throws IllegalArgumentException {

		if (amount == null) {
			nvpRequest.put("INSURANCEAMT", "null");
			return;
		}

		if (!Validator.isValidAmount(amount)) {
			throw new IllegalArgumentException("Amount " + amount
					+ " is not valid. Amount has to have exactly two decimal "
					+ "places seaprated by \".\" - example: \"50.00\"");
		}
		nvpRequest.put("INSURANCEAMT", amount);
	}

	/**
	 * Total shipping insurance costs for this order. The value must be a
	 * non-negative currency amount or null if insurance options are offered.
	 * Note: Character length and limitations: Must not exceed $10,000 USD in
	 * any currency. No currency symbol. Regardless of currency, decimal
	 * separator must be a period (.). Equivalent to nine characters maximum for
	 * USD.
	 * 
	 * Insurance option displays drop-down on the PayPal Review page.
	 * 
	 * @param amount
	 * @param insuranceOption
	 *            If true, the Insurance drop-down on the PayPal Review page
	 *            displays the string "Yes" and the insurance amount.
	 * @throws IllegalArgumentException
	 */
	public void setInsuranceAmount(String amount, boolean insuranceOption)
			throws IllegalArgumentException {

		/* amount */
		setInsuranceAmount(amount);
		/* option */
		if (insuranceOption) {
			nvpRequest.put("INSURANCEOPTIONOFFERED", "true");
		} else {
			nvpRequest.put("INSURANCEOPTIONOFFERED", "false");
		}
	}

	/**
	 * Shipping discount for this order, specified as a negative number. Note:
	 * Character length and limitations: Must not exceed $10,000 USD in any
	 * currency. No currency symbol. Regardless of currency, decimal separator
	 * must be a period (.). Equivalent to nine characters maximum for USD.
	 * 
	 * @param discount
	 * @throws IllegalArgumentException
	 */
	public void setShippingDiscount(String discount)
			throws IllegalArgumentException {

		/* amount is number with exactly two decimal places */
		if (!Validator.isValidAmount(discount)) {
			throw new IllegalArgumentException("Amount " + discount
					+ " is not valid. Amount has to have exactly two decimal "
					+ "places seaprated by \".\" - example: \"50.00\"");
		}
		nvpRequest.put("SHIPPINGDISCOUNT", discount);
	}

	/**
	 * Total handling costs for this order. Note: Character length and
	 * limitations: Must not exceed $10,000 USD in any currency. No currency
	 * symbol. Regardless of currency, decimal separator must be a period (.).
	 * Equivalent to nine characters maximum for USD.
	 * 
	 * @param amount
	 * @throws IllegalArgumentException
	 */
	public void setHandlingAmount(String amount)
			throws IllegalArgumentException {

		if (!Validator.isValidAmount(amount)) {
			throw new IllegalArgumentException("Amount " + amount
					+ " is not valid. Amount has to have exactly two decimal "
					+ "places seaprated by \".\" - example: \"50.00\"");
		}
		nvpRequest.put("HANDLINGAMT", amount);
	}

	/**
	 * Description of items the customer is purchasing. Character length and
	 * limitations: 127 single-byte alphanumeric characters
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
		nvpRequest.put("DESC", description);
	}

	/**
	 * A free-form field for your own use. Character length and limitations: 256
	 * single-byte alphanumeric characters
	 * 
	 * @param field
	 * @throws IllegalArgumentException
	 */
	public void setCustomField(String field) throws IllegalArgumentException {

		if (field.length() > 256) {
			throw new IllegalArgumentException("Field cannot exceed 256 "
					+ "characters");
		}
//		nvpRequest.put("PAYMENTREQUEST_0_CUSTOM", field);
		nvpRequest.put("CUSTOM", field);
	}

	/**
	 * Your own invoice or tracking number. Character length and limitations:
	 * 127 single-byte alphanumeric characters; I'm not sure if our website will
	 * provide the invoice to the customers.
	 * 
	 * @param invoiceNumber
	 * @throws IllegalArgumentException
	 */
	@Deprecated
	public void setInvoiceNumber(String invoiceNumber)
			throws IllegalArgumentException {

		if (invoiceNumber.length() > 127) {
			throw new IllegalArgumentException("Invoice number cannot exceed "
					+ "127 characters");
		}
		nvpRequest.put("INVNUM", invoiceNumber);
	}

	/**
	 * Your URL for receiving Instant Payment Notification (IPN) about this
	 * transaction. If you do not specify this value in the request, the
	 * notification URL from your Merchant Profile is used, if one exists.
	 * Important: The notify URL only applies to
	 * <code>DoExpressCheckoutPayment</code>. This value is ignored when set in
	 * <code>SetExpressCheckout</code> or <code>GetExpressCheckoutDetails</code>
	 * . Character length and limitations: 2,048 single-byte alphanumeric
	 * characters
	 * 
	 * @param url
	 * @throws IllegalArgumentException
	 */
	public void setNotifyUrl(String url) throws IllegalArgumentException {

		if (url.length() > 2048) {
			throw new IllegalArgumentException("Url cannot exceed 2048 "
					+ "characters");
		}
		nvpRequest.put("NOTIFYURL", url);
	}

	/**
	 * Note to the seller. Character length and limitations: 255 single-byte
	 * characters
	 * 
	 * @param note
	 * @throws IllegalArgumentException
	 */
	public void setNote(String note) throws IllegalArgumentException {

		if (note.length() > 255) {
			throw new IllegalArgumentException("Note cannot exceed 255 "
					+ "characters");
		}
		nvpRequest.put("NOTETEXT", note);
	}

	/**
	 * Transaction identification number of the transaction that was created.
	 * 
	 * @param transactionId
	 */
	public void setTransactionId(String transactionId) {
		nvpRequest.put("TRANSACTIONID", transactionId);
	}

	/**
	 * The payment method type. Specify the value
	 * <code>InstantPaymentOnly.</code>
	 * 
	 * @param method
	 */
	public void setAllowedPaymentMethod(String method) {
		nvpRequest.put("ALLOWEDPAYMENTMETHOD", method);
	}

	@Override
	public Map<String, String> getNVPRequest() {
		/* hash map holding response */
		HashMap<String, String> nvp = new HashMap<String, String>(nvpRequest);

		int itemAmt = 0;
		int itemTax = 0;

		/* items */
		for (int i = 0; i < items.size(); i++) {
			for (Map.Entry<String, String> entry : items.get(i).entrySet()) {

				/* KEYn VALUE */
				nvp.put(entry.getKey() + i, entry.getValue());

				/* item amount */
				if (entry.getKey().equals("L_AMT")) {
					/* remove decimal point and parse to int */
					itemAmt += Integer.parseInt(entry.getValue().replace(".",
							""));
				}

				/* tax amount */
				if (entry.getKey().equals("L_TAXAMT")) {
					/* remove decimal point and parse to int */
					itemTax += Integer.parseInt(entry.getValue().replace(".",
							""));
				}
			}
		}

		/* format to two decimal places */
		DecimalFormat currency = new DecimalFormat("#0.00");

		/* set ITEMAMT */
		if (itemAmt > 0) {
			/* convert back to two decimals */
			nvp.put("ITEMAMT", currency.format(itemAmt / 100d));
		}

		/* set TAXAMT */
		if (itemTax > 0) {
			/* convert back to two decimals */
			nvp.put("TAXAMT", currency.format(itemTax / 100d));
		}

		/* set AMT if not set */
		if (!nvp.containsKey("AMT")) {

			/* calculate total - tax, shipping etc. */
			int total = itemAmt + itemTax;

			if (nvp.containsKey("HANDLINGAMT")) {
				total += Integer.parseInt(nvp.get("HANDLINGAMT").replace(".",
						""));
			}
			if (nvp.containsKey("SHIPPINGAMT")) {
				total += Integer.parseInt(nvp.get("SHIPPINGAMT").replace(".",
						""));
			}

			/* convert bakc to two decimals */
			nvp.put("AMT", currency.format(total / 100d));
		}

		/* handling or shipping amount is set but item amount is not set */
		if ((nvp.containsKey("HANDLINGAMT") || nvp.containsKey("SHIPPINGAMT"))
				&& !nvp.containsKey("ITEMAMT")) {

			/* set the amount for itemamt - because itemamt is required */
			/* when handling amount is set */
			nvp.put("ITEMAMT", nvp.get("AMT"));
		}

		/* return nvp request */
		return nvp;
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
	public void setBillingDescription(String description)
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

	@Override
	public String toString() {
		return "Instance of PaymentDetails class with the values: nvpRequest: "
				+ nvpRequest.toString();
	}
}
