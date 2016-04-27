package com.jy.paypal.request;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jy.paypal.fields.Address;
import com.jy.paypal.fields.BillingAgreement;
import com.jy.paypal.fields.Payment;
import com.jy.paypal.fields.PaymentAction;
import com.jy.paypal.fields.ShipToAddress;
import com.jy.paypal.fields.ShippingOptions;
import com.jy.paypal.utils.Validator;

public class SetExpressCheckout implements Request {

	private static final long serialVersionUID = -3931493992688026609L;

	/**
	 * Method value of this request
	 */
	private static final String METHOD_NAME = "SetExpressCheckout";

	public enum LocalCode {
		/**
		 * United States
		 */
		US;
	}

	/**
	 * Type of checkout flow
	 */
	public enum SolutionType {
		/** 
		 * Express Checkout for auctions
		 * 
		SOLE,

		/** 
		 * Normal Express Checkout
		 */
		MARK
	}
	
	/**
	 * Type of PayPal page to display
	 */
	public enum LandingPage {
		/** 
		 * non-PayPal account
		 * 
        BILLING,

        /** 
         * PayPal account login
         */
        LOGIN
	}
	
	public enum ChannelType {
		/**
		 * non-auction seller
		 */
		MERCHANT
	}
	
	/**
	 * name value pair request
	 */
	private final Map<String, String> nvpRequest;
	
	/**
	 * name value pair response
	 */
	private Map<String, String> nvpResponse;
	
	/**
	 * shipping options, empty if no options set
	 */
	private List<Map<String, String>> shippingOptions;
	
	/**
	 * billing agreement (recurring payment etc.), empty if no agreemnt set
	 */
	private List<Map<String, String>> billingAgreement;
	
	/**
     * PayPal recommends that the returnUrl be the final review page on which
     * the customer confirms the order and payment or billing agreement.
     *
     * PayPal recommends that the cancelUrl be the original page on which the
     * customer chose to pay with PayPal or establish a billing agreement.
     * 
     * @param payment
     * @param returnUrl URL to which the customer��s browser is returned after
     *                  choosing to pay with PayPal. Maximum 2048 characters.
     * @param cancelUrl URL to which the customer is returned if he does not
     *                  approve the use of PayPal to pay you. Maximum 2048
     *                  characters.
     * @throws IllegalArgumentException
     */
    public SetExpressCheckout(Payment payment, String returnUrl,
            String cancelUrl) throws IllegalArgumentException {

        /* cancel url and return url has to be less or equal to 2048 chars */
        if (returnUrl.length() >= 2048) {
            throw new IllegalArgumentException("returnUrl cannot be longer "
                    + "than 2048 characters.");
        }
        if (cancelUrl.length() >= 2048) {
            throw new IllegalArgumentException("cancelUrl cannot be longer "
                    + "than 2048 characters.");
        }
        if (payment == null || returnUrl == null || cancelUrl == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        nvpResponse = new HashMap<String, String>();
        nvpRequest  = new HashMap<String, String>();
        shippingOptions = new LinkedList<Map<String, String>>();
        billingAgreement = new LinkedList<Map<String, String>>();

        nvpRequest.put("METHOD", METHOD_NAME);

        /* copy nvp from payment */
		HashMap<String, String> nvp =
				new HashMap<String, String>(payment.getNVPRequest());
        nvpRequest.putAll(nvp);

        nvpRequest.put("RETURNURL", returnUrl);
        nvpRequest.put("CANCELURL", cancelUrl);
    }

    /**
     *
     * @param token A timestamp token
     * @throws IllegalArgumentException
     */
    public void setToken(String token) throws IllegalArgumentException {

        if (token.length() != 20) {
            throw new IllegalArgumentException("Invalid token argument");
        }
        nvpRequest.put("TOKEN", token);
    }

    /**
     * The expected maximum total amount of the complete order, including
     * shipping cost and tax charges.
     * If the transaction does not include a one-time purchase, this field is
     * ignored.
     * Limitations: Must not exceed $10,000 USD in any currency. No currency
     * symbol. Must have two decimal places, decimal separator must be a
     * period (.), and no thousands separator.
     *
     * @param maxAmount number with exactly two decimal places
     * @throws IllegalArgumentException
     */
    public void setMaxAmount(String maxAmount) throws IllegalArgumentException {

        /* amount is number with exactly two decimal places */
        if (!Validator.isValidAmount(maxAmount)) {
            throw new IllegalArgumentException("Amount " + maxAmount +
                    " is not valid. Amount has to have exactly two decimal "
                    + "places seaprated by \".\" - example: \"50.00\"");
        }

        /* values for this request */
        nvpRequest.put("MAXAMT", maxAmount);
    }

    /**
     * URL to which the callback request from PayPal is sent. It must start
     * with HTTPS for production integration. It can start with HTTPS or HTTP
     * for sandbox testing.
     *
     * @param callback max 1024 characters
     */
    public void setCallback(String callback) throws IllegalArgumentException {

        if (callback.length() > 1024) {
            throw new IllegalArgumentException("Callback can be maximum 1024 "
                    + "in length");
        }
        nvpRequest.put("CALLBACK", callback);
    }

    /**
     * An override for you to request more or less time to be able to process
     * the callback request and respond. The acceptable range for the override
     * is 1 to 6 seconds.
     *
     * @param timeout integer has to be between 1 - 6
     */
    public void setCallbackTimeout(int timeout) {

        if (timeout < 1 || timeout > 6) {
            throw new IllegalArgumentException("Timeout has to be between 1 - 6");
        }

        nvpRequest.put("CALLBACKTIMEOUT", Integer.toString(timeout));
    }

    /**
     * Indicates that you require that the customer��s shipping address on file
     * with PayPal be a confirmed address.
     * Setting this field overrides the setting you have specified in your
     * Merchant Account Profile.
     *
     * @param required if true than confirmed address is required
     */
    public void setRequireConfirmedShipping(boolean required) {

        String req = (required) ? "1" : "0";
        nvpRequest.put("REQCONFIRMSHIPPING", req);
    }

    /**
     * Indicates that on the PayPal pages, no shipping address fields should be 
     * displayed whatsoever.
     * 
     * @param noShipping if true, no address fields will be displayed
     */
    public void setNoShipping(boolean noShipping) {

        String no = (noShipping) ? "1" : "0";
        nvpRequest.put("NOSHIPPING", no);
    }

    /**
     * Indicates that the customer may enter a note to the merchant on the 
     * PayPal page during checkout. The note is returned in the 
     * GetExpressCheckoutDetails response and the DoExpressCheckoutPayment 
     * response.
     * 
     * @param allowNote if true, note can be entered by customer
     */
    public void setAllowNote(boolean allowNote) {
        
        String note = (allowNote) ? "1" : "0";
        nvpRequest.put("ALLOWNOTE", note);
    }

    /**
     * Indicates that the PayPal pages should display the shipping address set 
     * by you in this SetExpressCheckout request, not the shipping address on 
     * file with PayPal for this customer.
     * Displaying the PayPal street address on file does not allow the customer 
     * to edit that address.
     * Set address using setAddress(ShipToAddress address) method
     * 
     * @param rOverride if true set address will be used
     */
    public void setAddressOverride(boolean rOverride) {

        String over = (rOverride) ? "1" : "0";
        nvpRequest.put("ADDROVERRIDE", over);
    }

    /**
     * Locale of pages displayed by PayPal during Express Checkout.
     * 
     * @param localCode
     */
    public void setLocalCode(LocalCode localCode) {
        nvpRequest.put("LOCALECODE", localCode.toString());
    }

    /**
     * Sets the Custom Payment Page Style for payment pages associated with 
     * this button/link. This value corresponds to the HTML variable page_style 
     * for customizing payment pages. The value is the same as the Page Style 
     * Name you chose when adding or editing the page style from the Profile 
     * subtab of the My Account tab of your PayPal account.
     * 
     * Character length and limitations: 30 single-byte alphabetic characters
     * 
     * @param pageStyle 
     * @throws IllegalArgumentException
     */
    public void setPageStyle(String pageStyle) throws IllegalArgumentException {

        if (pageStyle.length() > 30) {
            throw new IllegalArgumentException("Character length exceeded 30 " 
                    + "characters");
        }
        nvpRequest.put("PAGESTYLE", pageStyle);
    }
    
    /**
     * URL for the image you want to appear at the top left of the payment 
     * page. The image has a maximum size of 750 pixels wide by 90 pixels high. 
     * PayPal recommends that you provide an image that is stored on a secure 
     * (https) server. If you do not specify an image, the business name is 
     * displayed.
     * Character length and limit: 127 single-byte alphanumeric characters
     * 
     * @param imgUrl
     * @throws IllegalArgumentException
     */
    public void setImage(String imgUrl) throws IllegalArgumentException {

        if (imgUrl.length() > 127) {
            throw new IllegalArgumentException("Character length exceeded 30 " 
                    + "characters");
        }
        nvpRequest.put("HDRIMG", imgUrl);
    }

    /**
     * Sets the border color around the header of the payment page. The border 
     * is a 2-pixel perimeter around the header space, which is 750 pixels wide 
     * by 90 pixels high. By default, the color is black.
     * Character length and limitation: Six character HTML hexadecimal color 
     * code in ASCII
     * 
     * @param hexColor
     * @throws IllegalArgumentException
     */
    public void setBorderColor(String hexColor) 
            throws IllegalArgumentException {

        /* allowed characters 0-9 and a-f. Exactly 6 characters */
        Pattern pattern = Pattern.compile("^[0-9,a-f,A-F]{6}$");
        Matcher matcher = pattern.matcher(hexColor);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Hex color" + hexColor + 
                    " is not valid.");
        }
        nvpRequest.put("HDRBORDERCOLOR", hexColor);
    }
	
    /**
     * Sets the background color for the header of the payment page. By 
     * default, the color is white.
     * Character length and limitation: Six character HTML hexadecimal color 
     * code in ASCII
     * 
     * @param hexColor
     * @throws IllegalArgumentException
     */
    public void setBackgroundColor(String hexColor) 
            throws IllegalArgumentException {

        /* allowed characters 0-9 and a-f. Exactly 6 characters */
        Pattern pattern = Pattern.compile("^[0-9,a-f,A-F]{6}$");
        Matcher matcher = pattern.matcher(hexColor);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Hex color" + hexColor + 
                    " is not valid.");
        }
        nvpRequest.put("HDRBACKCOLOR", hexColor);
    }

    /**
     * Sets the background color for the payment page. By default, the color is
     * white.
     * Character length and limitation: Six character HTML hexadecimal color
     * code in ASCII
     *
     * @param hexColor
     * @throws IllegalArgumentException
     */
    public void setPayFlowColor(String hexColor)
            throws IllegalArgumentException {

        /* allowed characters 0-9 and a-f. Exactly 6 characters */
        Pattern pattern = Pattern.compile("^[0-9,a-f,A-F]{6}$");
        Matcher matcher = pattern.matcher(hexColor);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Hex color" + hexColor +
                    " is not valid.");
        }
        nvpRequest.put("PAYFLOWCOLOR", hexColor);
    }
	
    /**
     * How you want to obtain payment:
     * <ul>
     *  <li>
     *      Sale indicates that this is a final sale for which you are 
     *      requesting payment. (Default)
     *  </li>
     *  <li>
     *      Authorization indicates that this payment is a basic authorization 
     *      subject to settlement with PayPal Authorization & Capture.
     *  </li>
     *  <li>
     *      Order indicates that this payment is an order authorization subject 
     *      to settlement with PayPal Authorization & Capture.
     *  </li>
     * </ul>
     * If the transaction does not include a one-time purchase, this field is 
     * ignored.
     * Note:
     * You cannot set this value to Sale in SetExpressCheckout request and then 
     * change this value to Authorization or Order on the final API 
     * DoExpressCheckoutPayment request. If the value is set to Authorization 
     * or Order in SetExpressCheckout, the value may be set to Sale or the same 
     * value (either Authorization or Order) in DoExpressCheckoutPayment.
     * 
     * @param paymentAction
     */
    public void setPaymentAction(PaymentAction paymentAction) {
        nvpRequest.put("PAYMENTACTION", paymentAction.getValue());
    }

    /**
     * Email address of the buyer as entered during checkout. PayPal uses this 
     * value to pre-fill the PayPal membership sign-up portion of the PayPal 
     * login page.
     * Character length and limit: 127 single-byte alphanumeric characters
     * 
     * @param email
     * @throws IllegalArgumentException
     */
    public void setEmail(String email) throws IllegalArgumentException {

        if (!Validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email is not valid");
        }

        if (email.length() > 127) {
            throw new IllegalArgumentException("Email can be maximum 127 " 
                    + "characters long.");
        }
        nvpRequest.put("EMAIL", email);
    }

    /**
     * Type of checkout flow:
     * <ul>
     *  <li><b>Sole:</b> Express Checkout for auctions</li>
     *  <li><b>Mark:</b> Normal Express Checkout</li>
     * </ul>
     *
     * @param solutionType
     */
    public void setSolutionType(SolutionType solutionType) {
        nvpRequest.put("SOLUTIONTYPE", solutionType.toString());
    }

    /**
     * Type of PayPal page to display:
     * <ul>
     *  <li><b>Billing:</b> non-PayPal account</li>
     *  <li><b>Login:</b> PayPal account login</li>
     * </ul>
     * 
     * @param landingPage
     */
    public void setLandingPage(LandingPage landingPage) {
        nvpRequest.put("LANDINGPAGE", landingPage.toString());
    }

    /**
     * Type of channel:
     * <ul>
     *  <li><b>Merchant:</b> non-auction seller</li>
     *  <li><b>eBayItem:</b> eBay auction</li>
     * </ul>
     * 
     * @param channelType
     */
    public void setChannelType(ChannelType channelType) {
        nvpRequest.put("CHANNELTYPE", channelType.toString());
    }
	
    /**
     * The URL on the merchant site to redirect to after a successful giropay 
     * payment.
     * Use this field only if you are using giropay or bank transfer payment 
     * methods in Germany.
     * 
     * @param url
     */
    public void setGiroPaySuccessUrl(String url) {
        nvpRequest.put("GIROPAYSUCCESSURL", url);
    }

    /**
     * The URL on the merchant site to redirect to after a unsuccessful giropay 
     * payment.
     * Use this field only if you are using giropay or bank transfer payment 
     * methods in Germany.
     * 
     * @param url
     */
    public void setGiroCancelUrl(String url) {
        nvpRequest.put("GIROPAYCANCELURL", url);
    }
	
    /**
     * The URL on the merchant site to transfer to after a bank transfer 
     * payment.
     * Use this field only if you are using giropay or bank transfer payment 
     * methods in Germany.
     * 
     * @param url
     */
    public void setBankTxPendingUrl(String url) {
        nvpRequest.put("BANKTXNPENDINGURL", url);
    }

	/**
	 * Sets address fields
	 * 
	 * @param address
	 */
	public void setAddress(Address address) {
		nvpRequest.putAll(address.getNVPRequest());
	}

	/**
	 * Sets shipping options
	 * 
     * @param options
	 */
	public void setShippingOptions(ShippingOptions[] options) {

        /* check items */
        if (options == null || options.length == 0) {
            throw new IllegalArgumentException("You did not supply options.");
        }

        /* iterate supplied array */
        int x = 0;  // this is only for exception message
        for (ShippingOptions option : options) {
            /* item cannot be null */
            if (option == null) {
                throw new IllegalArgumentException("Option at index " + x
                        + " is not set.");
            }
            this.shippingOptions.add(new HashMap<String, String>(option.getNVPRequest()));
            x++;
        }
	}

	/**
	 * Sets billing agreement (recurring payments etc.). Maximum allowed
     * billing agreements is 10
	 * 
	 * @param agreements
	 */
	public void setBillingAgreement(BillingAgreement[] agreements) {

        /* check items */
        if (agreements == null || agreements.length == 0) {
            throw new IllegalArgumentException("You did not supply any agreement.");
        }
        if (agreements.length > 10) {
            throw new IllegalArgumentException("Maximum allowed agreements is 10");
        }

        /* iterate supplied array */
        int x = 0;  // this is only for exception message
        for (BillingAgreement agreement : agreements) {
            /* item cannot be null */
            if (agreement == null) {
                throw new IllegalArgumentException("Agreement at index " + x
                        + " is not set.");
            }
            this.billingAgreement.add(new HashMap<String, String>(agreement.getNVPRequest()));
            x++;
        }
	}

	/**
	 * Sets buyer details
	 * 
	 * @param buyer
	 */
	//public void setBuyerDetails(BuyerDetails buyer) {
	//	nvpRequest.putAll(new HashMap<String, String>(buyer.getNVPRequest()));
	//}

    /**
     *
     * @param address shipping address
     */
    public void setShippingAddress(ShipToAddress address) {
        nvpRequest.putAll(new HashMap<String, String>(address.getNVPRequest()));
    }

    public Map<String, String> getNVPRequest() {

		/* hash map holding response */
		HashMap<String, String> nvp = new HashMap<String, String>(nvpRequest);

        /* shipping options */
		for (int i = 0; i < shippingOptions.size(); i++) {
			for (Map.Entry<String, String> entry
					: shippingOptions.get(i).entrySet()) {

				/* KEYn VALUE */
				nvp.put(entry.getKey() + i, entry.getValue());
			}
		}

        /* billing agreement */
		for (int i = 0; i < billingAgreement.size(); i++) {
			for (Map.Entry<String, String> entry
					: billingAgreement.get(i).entrySet()) {

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

        StringBuffer str = new StringBuffer("instance of SetExpressCheckout ");
        str.append("class with the vlues: nvpRequest - ");
        str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
    }

}
