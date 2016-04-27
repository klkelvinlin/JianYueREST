package com.jy.paypal.core;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jy.paypal.profile.Profile;
import com.jy.paypal.request.Request;

/**
 * Instance of this class is used for sending requests and returning responses
 * from paypal.
 * 
 * @author wdong
 * 
 */
public class PayPal implements Serializable {

	private static final long serialVersionUID = -7647329815798486694L;

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * indicates if server for use with api signature (or api certificate if
	 * false) should be used
	 */
	private final boolean apiSignature;

	/**
	 * version
	 */
	private static final String VERSION = "61.0";

	/**
	 * sends request and returns response
	 */
	private final Transport transport;

	/**
	 * class holding profile details
	 */
	private final Profile profile;

	/**
	 * environment - sandbox, live etc.
	 */
	private final Environment environment;

	public enum Environment {
		/**
		 * live environment
		 */
		LIVE(""),

		/**
		 * test environment
		 */
		SANDBOX("sandbox."),

		/**
		 * beta test environment
		 */
		BETA_SANDBOX("beta-sandbox.");

		/**
		 * string represnetation of the environment/part of the url
		 */
		private final String environment;

		private Environment(String environment) {
			this.environment = environment;
		}

		/**
		 * Return url where you send request, this changes according to the
		 * environment set.
		 * 
		 * @return - url string where to send request
		 */
		private String getEnvironmentPartUrl() {
			return environment;
		}
	}

	/**
	 * same for all constructors
	 */
	{
		transport = new HttpPost();
	}

	/**
	 * Returns new instance of PayPalService for use with api signatures.
	 * 
	 * @param profile
	 * @param environment
	 */
	public PayPal(Profile profile, Environment environment) {
		this.profile = profile;
		this.environment = environment;
		this.apiSignature = true;
	}

	/**
	 * Returns new instance of PayPalService.
	 * 
	 * @param profile
	 * @param environment
	 * @param apiSignature
	 */
	public PayPal(Profile profile, Environment environment,
			boolean apiSignature) {
		this.profile = profile;
		this.environment = environment;
		this.apiSignature = apiSignature;
	}

	public void setResponse(Request request) {

		StringBuffer nvpString = new StringBuffer();
		/* character encoding for the nvp string */
		String encoding = "UTF-8";

		/* create nvp string */
		try {
			/* profile part */
			for (Map.Entry<String, String> e : profile.getNVPMap().entrySet()) {
				nvpString.append(e.getKey() + "="
						+ URLEncoder.encode(e.getValue(), encoding));
				nvpString.append("&");
			}
			/* request part */
			for (Map.Entry<String, String> e : request.getNVPRequest()
					.entrySet()) {
				nvpString.append(e.getKey() + "="
						+ URLEncoder.encode(e.getValue(), encoding));
				nvpString.append("&");
			}
			/* the rest */
			nvpString.append("VERSION=" + URLEncoder.encode(VERSION, encoding));
		} catch (UnsupportedEncodingException ex) {
			logger.error(ex);
		}

		/* create end point url */
		StringBuffer endpointUrl = new StringBuffer();
		if (apiSignature) {
			endpointUrl.append("https://api-3t.");
		} else {
			endpointUrl.append("https://api.");
		}
		endpointUrl.append(environment.getEnvironmentPartUrl());
		endpointUrl.append("paypal.com/nvp");

		/* send request and save response */
		String response = null;
		try {
			response = transport.getResponse(endpointUrl.toString(),
					nvpString.toString());
		} catch (MalformedURLException ex) {
			logger.error(ex);
		}

		if (response != null) {

			/* map holding response */
			Map<String, String> responseMap = new HashMap<String, String>();

			/* add response to the Map */
			try {
				String[] pairs = response.split("&"); // split nvp
				for (String pair : pairs) {
					String[] nvp = pair.split("="); // split key value
					responseMap
							.put(nvp[0], URLDecoder.decode(nvp[1], encoding));
				}
			} catch (UnsupportedEncodingException ex) {
				logger.error(ex);
			}

			/* set response */
			request.setNVPResponse(responseMap);
		}
	}

	/**
	 * Returns paypal url, where profile should be redirected. If Request has
	 * not been sent, or response has not been successful, null is returned.
	 * @return - url where to redirect profile
	 */
	public String getRedirectUrl(Request request) {

		/* response */
		Map<String, String> response = request.getNVPResponse();

		/* nvpResponse is not set */
		if (response == null) {
			return null;
		}

		String ack = response.get("ACK");
		String token = response.get("TOKEN");

		/* ack is not successfull or token is not set */
		if ((ack == null || !ack.equals("Success"))
				|| (token == null || token.equals(""))) {

			return null;
		}

		/* return redirect url */
		return "https://www." + environment.getEnvironmentPartUrl()
				+ "paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="
				+ token;
	}

	@Override
	public String toString() {

		return "instance of PayPalNVP class with values: VERSION: " + VERSION
				+ ", User profile: " + profile.toString()
				+ ", Transpor transport: " + transport.toString()
				+ ", Environment environment: " + environment.toString();
	}
}
