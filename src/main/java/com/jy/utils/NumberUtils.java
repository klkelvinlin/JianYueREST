package com.jy.utils;

import java.text.DecimalFormat;

public class NumberUtils {

	public static Boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Boolean isLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Integer ceilNumber(Double number) {
		return (int) Math.ceil(number);
	}

	public static Integer ceilNumber(Integer number) {
		return (int) Math.ceil(number);
	}

	public static String doubleDigit(Double number) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		return decimalFormat.format(number).toString();
	}

	public static String doubleDigit(String number) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		return decimalFormat.format(number).toString();
	}

}
