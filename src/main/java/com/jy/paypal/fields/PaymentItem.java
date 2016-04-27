package com.jy.paypal.fields;

import java.util.HashMap;
import java.util.Map;

import com.jy.paypal.utils.Validator;

/**
 * Payment Details Item Type Fields. You have to set amount for at leas one
 * item. Otherwise the payment will be rejected by paypal, because order will be
 * 0.00
 * 
 * @author wdong
 * 
 */
public class PaymentItem implements RequestFields {

	private static final long serialVersionUID = -7793376963452516611L;

	/**
	 * map that holds name value pair request values
	 */
	private final Map<String, String> nvpRequest;

	public PaymentItem() {
		nvpRequest = new HashMap<String, String>();
	}
	
	/**
     * Item name. Character length and limitations: 127 single-byte characters
     * 
     * @param name
     * @throws IllegalArgumentException
     */
    public void setName(String name) throws IllegalArgumentException {

        if (name.length() > 127) {
            throw new IllegalArgumentException("Name cannot exceed 127 "
                    + "characters");
        }
        nvpRequest.put("L_NAME", name);
//        nvpRequest.put("L_PAYMENTREQUEST_0_NAMEm", name);
    }
    
    /**
     * Item description. Character length and limitations: 127 single-byte
     * characters
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
        nvpRequest.put("L_DESC", description);
    }
    
    /**
     * Cost of item.
     * Set amount to 0 if the transaction does not include a one-time
     * purchase; for example, when you set up a billing agreement for a
     * recurring payment that is not immediately charged.
     *
     * @param amount	Character length and limitations: Must not exceed
	 * 					$10,000 USD in any currency. No currency symbol.
	 * 					Regardless of currency, decimal separator must be a
	 * 					period (.). Equivalent to nine characters maximum for
	 * 					USD.
     * @throws IllegalArgumentException
     */
    public void setAmount(String amount) throws IllegalArgumentException {

        if (!Validator.isValidAmount(amount)) {
            throw new IllegalArgumentException("Amount " + amount +
                    " is not valid. Amount has to have exactly two decimal "
                    + "places seaprated by \".\" - example: \"50.00\"");
        }
        nvpRequest.put("L_AMT", amount);
//        nvpRequest.put("PAYMENTREQUEST_0_AMT", amount);
    }
    
    /**
     * Item number. Character length and limitations: 127 single-byte characters
     *
     * @param itemNumber
     * @throws IllegalArgumentException
     */
    public void setItemNumber(String itemNumber)
            throws IllegalArgumentException {

        if (itemNumber.length() > 127) {
            throw new IllegalArgumentException("Item number cannot exceed 127 "
                    + "characters");
        }
        nvpRequest.put("L_NUMBER", itemNumber);
    }
    
    /**
     * Item quantity. Character length and limitations: Any positive integer
     *
     * @param quantity
     * @throws IllegalArgumentException
     */
    public void setQuantity(int quantity) throws IllegalArgumentException {

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity has to be positive "
                    + "integer");
        }
        nvpRequest.put("L_QTY", Integer.toString(quantity));
    }
    
    /**
     * Item sales tax. Note: Character length and limitations: Must not exceed
     * $10,000 USD in any currency. No currency symbol. Regardless of currency,
     * decimal separator must be a period (.). Equivalent to nine characters
     * maximum for USD.
     *
     * @param amount
     * @throws IllegalArgumentException
     */
    public void setTaxAmount(String amount)
            throws IllegalArgumentException {

        if (!Validator.isValidAmount(amount)) {
            throw new IllegalArgumentException("Amount " + amount +
                    " is not valid. Amount has to have exactly two decimal "
                    + "places seaprated by \".\" - example: \"50.00\"");
        }
        nvpRequest.put("L_TAXAMT", amount);
    }
    
    

    public Map<String, String> getNVPRequest() {
        return new HashMap<String, String>(nvpRequest);
    }

    @Override
    public String toString() {

        return "Instance of PaymentDetailsItem class with the values: "
                + "nvpRequest: " + nvpRequest.toString();
    }

}
