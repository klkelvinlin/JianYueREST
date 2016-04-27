package com.jy.utils;

import java.io.IOException;
import java.util.Properties;

public enum SocialUtils {

	INSTANCE;
	
	private Properties properties;
	
	private SocialUtils() {
	}
	
	public  String getFacebookClientId(){
		return getProperties().getProperty("facebook.clientId");
	}
	
	public  String getFacebookClientSecret(){
		return getProperties().getProperty("facebook.clientSecret");
	}
	
	public  String getLinkedInApiKey(){
		return getProperties().getProperty("linkedIn.apiKey");
	}
	
	public  String getLinkedInSecretKey(){
		return getProperties().getProperty("linkedIn.secretKey");
	}
	
	public  String getLinkedInOauthUserToken(){
		return getProperties().getProperty("linkedIn.oauthUserToken");
	}
	
	public  String getLinkedInOauthUserSecret(){
		return getProperties().getProperty("linkedIn.oauthUserSecret");
	}
	
	private  Properties getProperties(){
		if(properties==null){
			try {
				properties = new Properties();
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("Social.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
	

}
