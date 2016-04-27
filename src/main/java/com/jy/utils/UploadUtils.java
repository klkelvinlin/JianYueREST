package com.jy.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public enum UploadUtils {

	INSTANCE;
	
	private Properties properties;
	
	private UploadUtils() {
	}
	
	public  String getAppleP12Path(){
		return getProperties().getProperty("p12.file.path");
	}
	
	public  String getAppleP12Password(){
		return getProperties().getProperty("p12.pwd");
	}
	
	public  String getFacebookPublicUrlPrefix(){
		return getProperties().getProperty("facebook.public.url");
	}
	
	public String getUploadFileName(String id,String originalName){
		try {
			return new SimpleDateFormat("yyyyMMddHHmmssms").format(DateUtils.timeConverter())+"-"+id+"-"+originalName;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public  String getUploadProfileFilePath(){
		return getProperties().getProperty("profile.file.path");
	}
	
	public  String getUploadprofileHttpPath(){
		return getProperties().getProperty("profile.http.path");
	}
	
	public  String getUploadMessageFilePath(){
		return getProperties().getProperty("message.file.path");
	}
	
	public  String getUploadMessageHttpPath(){
		return getProperties().getProperty("message.http.path");
	}
	
	public  String getUploadPostFilePath(){
		return getProperties().getProperty("post.file.path");
	}
	
	public  String getUploadPostHttpPath(){
		return getProperties().getProperty("post.http.path");
	}
	
	public  String getUploadRestaurantFilePath(){
		return getProperties().getProperty("restaurant.file.path");
	}
	
	public  String getUploadRestaurantHttpPath(){
		return getProperties().getProperty("restaurant.http.path");
	}
	
	public  String getUploadCategoryFilePath(){
		return getProperties().getProperty("category.file.path");
	}
	
	public  String getUploadCategoryHttpPath(){
		return getProperties().getProperty("category.http.path");
	}
	
	public  String getUploadEntreeFilePath(){
		return getProperties().getProperty("entree.file.path");
	}
	
	public  String getUploadEntreeHttpPath(){
		return getProperties().getProperty("entree.http.path");
	}
	
	public  String getMailImagesHttpPath(){
		return getProperties().getProperty("mail.http.path");
	}
	
	public  String getHomepageHttpPath(){
		return getProperties().getProperty("homepage.http.path");
	}
	
	private  Properties getProperties(){
		if(properties==null){
			try {
				properties = new Properties();
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("Config.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	public  String getLocalHostIpAddress() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return addr.getHostAddress();
	}

}
