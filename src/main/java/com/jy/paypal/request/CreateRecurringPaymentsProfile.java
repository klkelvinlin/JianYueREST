package com.jy.paypal.request;

import java.util.HashMap;
import java.util.Map;

import com.jy.paypal.fields.ActivationDetails;
import com.jy.paypal.fields.Address;
import com.jy.paypal.fields.BillingPeriodDetails;
import com.jy.paypal.fields.CreditCard;
import com.jy.paypal.fields.PayerInformation;
import com.jy.paypal.fields.PayerName;
import com.jy.paypal.fields.RecurringPaymentsProfileDetails;
import com.jy.paypal.fields.ScheduleDetails;

/**
 * Create recurring payment profile object.
 * 
 * @author wdong
 * 
 */
public class CreateRecurringPaymentsProfile implements Request {

	private static final long serialVersionUID = -2673454766898600957L;

	private static final String METHOD_NAME = "CreateRecurringPaymentsProfile";

	private final Map<String, String> nvpRequest;

	private Map<String, String> nvpResponse;

	public CreateRecurringPaymentsProfile() {
		nvpRequest = new HashMap<String, String>();
		nvpResponse = new HashMap<String, String>();
		nvpRequest.put("METHOD", METHOD_NAME);
	}

	public CreateRecurringPaymentsProfile(String token, ScheduleDetails details)
			throws IllegalArgumentException {
		this();

		if (token.length() != 20) {
			throw new IllegalArgumentException("Invalid token argument");
		}

		nvpRequest.put("TOKEN", token);
		nvpRequest.putAll(new HashMap<String, String>(details.getNVPRequest()));
	}

	public CreateRecurringPaymentsProfile(CreditCard card) {
		this();
		nvpRequest.putAll(new HashMap<String, String>(card.getNVPRequest()));
	}

	public void setRecurringPaymentsProfileDetails(
			RecurringPaymentsProfileDetails details) {
		nvpRequest.putAll(new HashMap<String, String>(details.getNVPRequest()));
	}

	public void setBillingPeriodDetails(BillingPeriodDetails details) {
		nvpRequest.putAll(new HashMap<String, String>(details.getNVPRequest()));
	}

	public void setActivationDetails(ActivationDetails details) {
		nvpRequest.putAll(new HashMap<String, String>(details.getNVPRequest()));
	}

	public void setPayerInformation(PayerInformation payer) {
		nvpRequest.putAll(new HashMap<String, String>(payer.getNVPRequest()));
	}

	public void setPayerName(PayerName name) {
		nvpRequest.putAll(new HashMap<String, String>(name.getNVPRequest()));
	}

	public void setAddress(Address address) {
		nvpRequest.putAll(new HashMap<String, String>(address.getNVPRequest()));
	}

	public Map<String, String> getNVPRequest() {
		return new HashMap<String, String>(nvpRequest);
	}

	public void setNVPResponse(Map<String, String> nvpResponse) {
		this.nvpResponse = new HashMap<String, String>(nvpResponse);
	}

	public Map<String, String> getNVPResponse() {
		return new HashMap<String, String>(nvpResponse);
	}

	@Override
	public String toString() {

		StringBuffer str = new StringBuffer(
				"instance of CreateRecurringPaymentsProfile");
		str.append("class with the vlues: nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}

}
