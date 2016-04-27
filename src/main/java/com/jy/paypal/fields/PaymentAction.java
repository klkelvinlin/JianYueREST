package com.jy.paypal.fields;

import java.io.Serializable;

/**
 * 
 * How you want to obtain payment
 *
 * You cannot set this value to Sale on SetExpressCheckout request and then
 * change this value to Authorization on the final PayPal Express Checkout API
 * DoExpressCheckoutPayment request.
 * @author wdong
 *
 */
public enum PaymentAction implements Serializable {
	/**
     * Indicates that this payment is a basic authorization subject to
     * settlement with PayPal Authorization & Capture.
     */
    AUTHORIZATION("Authorization"),

    /**
     * Indicates that this payment is is an order authorization subject to
     * settlement with PayPal Authorization & Capture.
     */
    ORDER("Order"),

    /**
     * Indicates that this is a final sale for which you are requesting payment.
     */
    SALE("Sale");

    private String value;

    private PaymentAction(String value) {
        this.value = value;
    }

    /**
     *
     * @return  string value for nvp request
     */
    public String getValue() {
        return value;
    }
}
