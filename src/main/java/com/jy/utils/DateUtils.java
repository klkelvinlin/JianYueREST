package com.jy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

public class DateUtils {

	public static boolean isSameDay(Date d1, Date d2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(d1);
		cal2.setTime(d2);

		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2
						.get(Calendar.DAY_OF_YEAR);
		return sameDay;
	}

	public static String getDate(Date d) {
		try {
			if (d == null) {
				return "";
			}

			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			return format.format(d);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getTime(Date d) {
		try {
			if (d == null) {
				return "";
			}

			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			return format.format(d);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	/**
	 * add hours to specifiedDay,if hours is a positive number, that means hours
	 * after specifiedDay, negative number otherwise.
	 * 
	 * @param specifiedDay
	 * @param hours
	 *            +1(positive number) or -1(negative number)
	 * @return
	 */
	public static Date getSpecifyDate(Date specifiedDay, int hours) {
		// System.out.println(specifiedDay);
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		// System.out.println((Date)c.getTime());
		int hour = c.get(Calendar.HOUR);
		hour = hour + hours;
		c.set(Calendar.HOUR, hour);
		// System.out.println((Date)c.getTime());
		return c.getTime();
	}

	public static Date timeConverter(String LocalTime, String LocalTimeZone,
			String partten) throws ParseException {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(new SimpleDateFormat(partten).parse(LocalTime));
		cal.setTimeZone(TimeZone.getTimeZone(LocalTimeZone));
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return new Date(cal.getTimeInMillis());
	}

	public static Date timeConverter(String LocalTime, String LocalTimeZone)
			throws ParseException {
		return timeConverter(LocalTime, LocalTimeZone, "MM/dd/yyyy HH:mm:ss");
	}

	public static Date timeConverter(String LocalTime,
			HttpServletRequest request) throws ParseException {
		return timeConverter(LocalTime,
				request.getParameter("tz") == null ? Calendar.getInstance()
						.getTimeZone().getID() : request.getParameter("tz"),
				"MM/dd/yyyy HH:mm:ss");
	}

	public static Date timeConverter(HttpServletRequest request)
			throws ParseException {
		return timeConverter(
				new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()),
				request);
	}

	public static Date timeConverter(String LocalTimeZone)
			throws ParseException {
		return timeConverter(
				new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()),
				LocalTimeZone);
	}

	public static String getTimeConverter(String LocalTimeZone)
			throws ParseException {
		return new SimpleDateFormat("yyyyMMddHHmmssms")
				.format(timeConverter(LocalTimeZone));
	}
	
	public static Date timeConverter()
			throws ParseException {
		return timeConverter(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
				TimeZone.getDefault().getID(),"yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date timeConverter(Date date)
			throws ParseException {
		return timeConverter(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date),
				TimeZone.getDefault().getID(),"yyyy-MM-dd HH:mm:ss");
	}

	public static String getTimeConverter(HttpServletRequest request)
			throws ParseException {
		return new SimpleDateFormat("yyyyMMddHHmmssms")
				.format(timeConverter(request));
	}

	public static int getIntervalDays(Date from, Date to) {
		long between_days = 0;
		Calendar cal_from = Calendar.getInstance();
		cal_from.setTime(from);
		Calendar cal_to = Calendar.getInstance();
		cal_to.setTime(to);
		Calendar temp = cal_from;
		while (cal_from.getTimeInMillis() < cal_to.getTimeInMillis()) {
			temp.add(Calendar.MONTH, 1);
			if (temp.getTimeInMillis() > cal_to.getTimeInMillis()) {
				cal_from.add(Calendar.MONTH, -1);
				break;
			} else {
				cal_from = temp;
			}
		}
		//
		Double days=(double) Math.ceil((double)(cal_to.getTimeInMillis() - cal_from.getTimeInMillis()) / (1000 * 3600 * 24));
		between_days = days.longValue();
		between_days = (between_days ==0?1:between_days);
		return (int)between_days;
	}
	
	public static Date getDateWithinAMonth(Date from, Date to){
		Calendar cal_from = Calendar.getInstance();
		cal_from.setTime(from);
		Calendar cal_to = Calendar.getInstance();
		cal_to.setTime(to);
		Calendar temp = cal_from;
		while (cal_from.getTimeInMillis() < cal_to.getTimeInMillis()) {
			temp.add(Calendar.MONTH, 1);
			if (temp.getTimeInMillis() > cal_to.getTimeInMillis()) {
				cal_from.add(Calendar.MONTH, -1);
				break;
			} else {
				cal_from = temp;
			}
		}
		return cal_from.getTime();
	}
	
	public static int getDaysInAMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		long between_days = 0;
		try {
			cal.setTime(sdf.parse(sdf.format(date)));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(sdf.format(getDateAfterAMonth(date))));
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int)between_days;
	}
	//this method is for membership changing
	//get a effect time from next month
	public static Date getDateAfterAMonth(Date date){
		Calendar from = Calendar.getInstance();
		from.setTime(date);
		from.add(Calendar.MONTH, 1);
		Date to=from.getTime();
		return to;
	}
	
	//get a specific date, one day before same day next month.
	//autojob should check one day after this day to meet paypal's buffer
	public static Date getDateAfterAMonthMinusADay(Date date){
		Calendar from = Calendar.getInstance();
		from.setTime(date);
		from.add(Calendar.MONTH, 1);
		from.add(Calendar.DAY_OF_MONTH, -1);
		Date to=from.getTime();
		return to;
	}
	
	//to see if a date is in one-month period
	public static Date checkDateWithinThisMonth(Date date){
		try {
			if(timeConverter().getTime()>date.getTime()){
				return getDateAfterAMonthMinusADay(date);
			}else{
				return date;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	public static Date setStartTimeForAdate(Date date){
		String pattern_from="MM/dd/yyyy";
		String pattern_to="MM/dd/yyyy HH:mm:ss";
		Date rlt = null;
		SimpleDateFormat sdf=new SimpleDateFormat(pattern_from);
		SimpleDateFormat sdf_=new SimpleDateFormat(pattern_to);
		try {
			rlt=sdf_.parse(sdf.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlt;
	}
	
	//to MM/dd/yyyy 
	public static Date formatDate(Date src){
		Date date=new Date();
		date.setTime(src.getTime());
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		try {
			return sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String switchToUsTime(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm MM/dd/yyyy");
		return sdf.format(date);
	}
	
	public static String switchToUsTimeWithoutTime(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(date);
	}
	
	public static String switchToUsTimeWithoutDate(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}
	
	public static Date getNext8thOfTheMonth(){
		Calendar c =Calendar.getInstance();
		int currentMonth=c.get(Calendar.DAY_OF_MONTH);
		if (currentMonth>8) {
			c.set(Calendar.MONTH, currentMonth+1);
		}
		c.set(Calendar.DAY_OF_MONTH, 8);
		c.set(Calendar.HOUR, 3);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	public static String switchNowToString(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(formatDate(new Date()));
	}
	
}
