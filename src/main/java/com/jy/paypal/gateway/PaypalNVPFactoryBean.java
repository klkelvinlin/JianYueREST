package com.jy.paypal.gateway;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.jy.paypal.core.PayPal;
import com.jy.paypal.core.PayPal.Environment;
import com.jy.paypal.profile.BaseProfile;
import com.jy.paypal.profile.Profile;

/**
 * Paypal NVP Spring factory bean.
 * 
 * @author wdong
 * 
 * @param <T>
 */
public class PaypalNVPFactoryBean implements FactoryBean<PayPal>,
		InitializingBean {

	private String username;
	private String password;
	private String signature;
	private String environment;

	private Profile profile;
	private PayPal paypal;

	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword(){
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String getEnvironment() {
		return this.environment;
	}
	
	public void setEnvironment(String env) {
		this.environment = env;
	}

	public void afterPropertiesSet() throws Exception {
		profile = new BaseProfile.Builder(this.username, this.password)
				.signature(this.signature).build();
		paypal = new PayPal(profile, this.environment.equalsIgnoreCase("sandbox")?Environment.SANDBOX:Environment.LIVE);
	}

	public PayPal getObject() throws Exception {
		return this.paypal;
	}

	public Class<? extends PayPal> getObjectType() {
		return (this.paypal != null) ? this.paypal.getClass() : PayPal.class;
	}

	public boolean isSingleton() {
		return false;
	}

}
