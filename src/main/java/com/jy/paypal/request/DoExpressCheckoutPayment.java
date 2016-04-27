package com.jy.paypal.request;

import java.util.HashMap;
import java.util.Map;

import com.jy.paypal.fields.Address;
import com.jy.paypal.fields.Payment;
import com.jy.paypal.fields.PaymentAction;

/**
 * 
 * @author wdong
 *
 */
public final class DoExpressCheckoutPayment implements Request {

	private static final long serialVersionUID = -4897239424417752721L;
	
	private static final String METHOD_NAME = "DoExpressCheckoutPayment";
	
	private final Map<String, String> nvpRequest;
	
	private Map<String, String> nvpResponse;
	
	/**
     * 
	 * @param payment		Should be the same as for SetExpressCheckout
     * @param token         PayPal token
     * @param paymentAction How you want to obtain payment
     * @param payerId       Unique PayPal customer account identification 
     *                      number as returned by GetExpressCheckoutDetails 
     *                      response
     * @throws IllegalArgumentException
     */
    public DoExpressCheckoutPayment(Payment payment, String token,
            PaymentAction paymentAction, String payerId)
            throws IllegalArgumentException {

        if (token.length() != 20) {
            throw new IllegalArgumentException("Invalid token argument");
        }
        if (payerId.length() != 13) {
            throw new IllegalArgumentException("Invalid payer id");
        }

        nvpResponse = new HashMap<String, String>();
        nvpRequest  = new HashMap<String, String>();

        nvpRequest.put("METHOD", METHOD_NAME);
		/* insert payment values */
		HashMap<String, String> nvp =
				new HashMap<String, String>(payment.getNVPRequest());
        nvpRequest.putAll(nvp);
        nvpRequest.put("TOKEN", token);
        nvpRequest.put("PAYMENTACTION", paymentAction.getValue());
        nvpRequest.put("PAYERID", payerId);
    }
    
    /**
	 * Flag to indicate whether you want the results returned by Fraud 
	 * Management Filters. By default this is false. 
	 * 
	 * @param fmf	true: receive FMF details
	 *				false: do not receive FMF details (default)
	 */
	public void setReturnFMF(boolean fmf) {

		int x = (fmf) ? 1 : 0;
        nvpRequest.put("RETURNFMFDETAILS", Integer.toString(x));
	}

	/**
	 * Sets user selected options
	 * 
	 * @param userOptions
	 */
	/*public void setUserSelectedOptions(UserSelectedOptions userOptions) {

		HashMap<String, String> nvp = 
				new HashMap<String, String>(userOptions.getNVPRequest());
		nvpRequest.putAll(nvp);
	}*/

	/**
	 * Sets address fields
	 *
	 * @param address
	 */
	public void setAddress(Address address) {

		HashMap<String, String> nvp =
				new HashMap<String, String>(address.getNVPRequest());
		nvpRequest.putAll(nvp);
	}

    public Map<String, String> getNVPRequest() {
        return new HashMap<String,String>(nvpRequest);
    }

    public void setNVPResponse(Map<String, String> nvpResponse) {
        this.nvpResponse = new HashMap<String, String>(nvpResponse);
    }

    public Map<String, String> getNVPResponse() {
        return new HashMap<String, String>(nvpResponse);
    }

    @Override
    public String toString() {

        StringBuffer str = new StringBuffer("instance of DoExpressCheckoutPayment ");
        str.append("class with the values: nvpRequest - ");
        str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
    }

}
