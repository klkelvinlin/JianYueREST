package com.jy.web.utils;

import java.util.regex.Pattern;

public class CommonUtils {
	
	/**
	 * If a string is null or not
	 * @param src
	 * @return
	 */
	public static boolean isEmptyString(String src){
		return null==src||src.length()<=0;
	}
	
	/**
	 * If a string is a number or not
	 * @param src
	 * @return
	 */
	public static boolean isNumberString(String src){
		isEmptyString(src);
		String pattern = "[0-9]+(.[0-9]+)?";  
        Pattern p = Pattern.compile(pattern); 
        return p.matcher(src).matches();
	}
	
	/**
	 * If a string is a integer
	 * @param src
	 * @return
	 */
	public static boolean isIntegerString(String src){
		isEmptyString(src);
		String pattern = "[0-9]?";  
        Pattern p = Pattern.compile(pattern); 
        return p.matcher(src).matches();
	}
	
	/**
	 * If a string is a email address
	 * @param src
	 * @return
	 */
	public static boolean isEmailString(String src){
		isEmptyString(src);
		String pattern = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";  
        Pattern p = Pattern.compile(pattern); 
        return p.matcher(src).matches();
	}
	
	/**
	 * If a string is between the minimun and the maximun
	 * @param src
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isWithinLimit(String src,Double min, Double max){
		isEmptyString(src);
		double srcNum = 0;
		try {
			srcNum = Double.parseDouble(src);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return srcNum>=min && srcNum<=max;
	}
	
	
}
