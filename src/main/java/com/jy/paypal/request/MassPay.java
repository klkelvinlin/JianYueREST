package com.jy.paypal.request;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jy.paypal.fields.Currency;
import com.jy.paypal.fields.MassPayItem;

/**
 * Make a payment to one or more PayPal account holders.
 * 
 * @author wdong
 * 
 */
public final class MassPay implements Request {

	private static final long serialVersionUID = -8858086421165928270L;

	/**
	 * Indicates how you identify the recipients of payments in this call to
	 * MassPay.
	 */
	public enum ReceiverType {

		EMAIL_ADDRESS("EmailAddress"),
		USER_ID("UserID");

		private String value;

		private ReceiverType(String value) {
			this.value = value;
		}

		/**
		 * @return string value for nvp request
		 */
		String getValue() {
			return value;
		}
	}
	
	private static final String METHOD_NAME = "MassPay";
	
	private final Map<String, String> nvpRequest;
	
	private Map<String, String> nvpResponse;
	
	private List<Map<String, String>> items;
	
	public MassPay(Currency currency, MassPayItem[] items) {

        nvpResponse = new HashMap<String, String>();
        nvpRequest = new HashMap<String, String>();
        this.items = new LinkedList<Map<String, String>>();

        nvpRequest.put("METHOD", METHOD_NAME);
        nvpRequest.put("CURRENCYCODE", currency.toString());

        /* check items */
        if (items == null || items.length == 0) {
            throw new IllegalArgumentException("You did not supply any items.");
        }

        /* iterate supplied array */
        int x = 0;  // this is only for exception message
        for (MassPayItem item : items) {
            /* item cannot be null */
            if (item == null) {
                throw new IllegalArgumentException("Item at index " + x
                        + " is not set.");
            }
            this.items.add(new HashMap<String, String>(item.getNVPRequest()));
            x++;
        }

    }

    /**
     *
     * @param subject   The subject line of the email that PayPal sends when
     *                  the transaction is completed. The subject line is the
     *                  same for all recipients. Character length and
     *                  limitations: 255 single-byte alphanumeric characters.
     * @throws IllegalArgumentException
     */
    public void setEmailSubject(String subject)
            throws IllegalArgumentException {

        if (subject.length() > 255) {
            throw new IllegalArgumentException("Subject cannot be longer "
                    + "than 255 characters.");
        }
        nvpRequest.put("EMAILSUBJECT", subject);
    }

    /**
     *
     * @param receiver  Indicates how you identify the recipients of payments
     *                  in this call to MassPay.
     */
    public void setReceiverType(ReceiverType receiver) {
        nvpRequest.put("RECEIVERTYPE", receiver.getValue());
    }

    public Map<String, String> getNVPRequest() {

		/* hash map holding response */
		HashMap<String, String> nvp = new HashMap<String, String>(nvpRequest);

        /* mass pay items */
		for (int i = 0; i < items.size(); i++) {
			for (Map.Entry<String, String> entry
					: items.get(i).entrySet()) {

				/* KEYn VALUE */
				nvp.put(entry.getKey() + i, entry.getValue());
			}
		}

        return nvp;
    }

    public void setNVPResponse(Map<String, String> nvpResponse) {
        this.nvpResponse = new HashMap<String, String>(nvpResponse);
    }

    public Map<String, String> getNVPResponse() {
        return new HashMap<String, String>(nvpResponse);
    }

    @Override
    public String toString() {

        StringBuffer str = new StringBuffer("instance of MassPay");
        str.append("class with the vlues: nvpRequest - ");
        str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
    }

}
