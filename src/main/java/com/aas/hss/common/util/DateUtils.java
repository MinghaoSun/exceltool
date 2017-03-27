package com.aas.hss.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @ClassName: DateUtils
 * @Description: 封装一些常用操作，获取时间等
 * @author Minghao
 * @date 2017年3月27日16:42:17
 * @version 1.0-SNAPSHOT
 */
public final class DateUtils {

	/**
	 * @Description: 返回日期：yyyyMMddHHmmss格式的字符串
	 * @date 2014-3-6 上午8:24:58
	 * @return String 返回类型
	 */
	public static String getStrOfDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	

	/**
	 * @Description: 获取当前日期时间 返回日期：yyyy-MM-dd HH:mm:ss
	 * @date 2014-3-6 上午8:25:30
	 * @return String 返回类型
	 */
	public static String getStrOfDateTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 字符串转为日期类型，返回yyyy-MM-dd HH:mm:ss格式
	 * @date 2014-3-6 上午8:25:45
	 * @return Date 返回类型
	 */
	public static Date getDateByStr(String str) {
		Date date = new Date();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @Description: 获取当前日期时间 返回日期：yyyy-MM-dd HH:mm
	 * @date 2014-3-6 上午8:26:09
	 * @return String 返回类型
	 */
	public static String getStrOfDateMinute() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 返回日期：yyyyMMddHHmmssSSS格式的字符串
	 * @date 2014-3-6 上午8:26:30
	 * @return String 返回类型
	 */
	public static String getStrOfMs() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 返回日期：yyyyMM格式的字符串
	 * @date 2014-3-6 上午8:26:48
	 * @return String 返回类型
	 */
	public static String getMonthFolder() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 返回日期：yyyyMM格式的字符串
	 * @date 2014-3-6 上午8:27:01
	 * @return String 返回类型
	 */
	public static String getDateFolder() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 获取当前月份
	 * @date 2014-3-6 上午8:27:18
	 * @return String 返回类型
	 */
	public static String getMonth() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 获取当前年份
	 * @date 2014-3-6 下午1:20:47
	 * @return String 返回类型
	 */
	public static String getYear() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 返回日期：yyyyMMddHH格式的字符串s
	 * @date 2014-3-6 下午1:20:58
	 * @return String 返回类型
	 */
	public static String getDataOfHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 返回时间：yyyyMMddHHmm格式
	 * @date 2014-3-6 下午1:21:05
	 * @return String 返回类型
	 */
	public static String getStrOfTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 返回当前时间：yyyy-MM-dd格式
	 * @date 2014-3-6 下午1:21:13
	 * @return String 返回类型
	 */
	public static String getCurrentDay() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Description: 返回当前日期减去day日期
	 * @date 2014-3-6 下午1:23:43
	 * @return String 返回类型
	 */
	public static String getLastDay(int day) {
		java.util.Date yestoday = new java.util.Date(new java.util.Date().getTime() - 1000 * 60 * 60 * 24 * day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(yestoday);
	}

	/**
	 * @Description: 获取昨天、前天的日期
	 * @date 2014-3-6 下午1:24:16
	 * @return String[] 返回类型
	 */
	public static String[] getLastDates(String currentDate) {
		String currYear, currMonth, currDay;
		currYear = currentDate.substring(0, 4);
		currMonth = currentDate.substring(4, 6);
		currDay = currentDate.substring(6);

		// 月份或日期首位是0
		String tempMonth, tempDay;
		if (currMonth.substring(0, 1).equals("0")) {
			tempMonth = "0";
		} else {
			tempMonth = "";
		}
		if (currDay.substring(0, 1).equals("0") || currDay.equals("10")) {
			tempDay = "0";
		} else {
			tempDay = "";
		}

		String returnDays[] = new String[2];

		if (currMonth.equals("01") && currDay.equals("01")) {
			returnDays[0] = (Integer.parseInt(currYear) - 1) + "1231";
			returnDays[1] = (Integer.parseInt(currYear) - 1) + "1230";
		} else if (currMonth.equals("01") && currDay.equals("02")) {
			returnDays[0] = currYear + "0101";
			returnDays[1] = (Integer.parseInt(currYear) - 1) + "1231";
		} else if (Integer.parseInt(currMonth) >= 1 && Integer.parseInt(currDay) > 2) {
			returnDays[0] = currYear + currMonth + tempDay + (Integer.parseInt(currDay) - 1);
			if (currDay.equals("11")) {
				returnDays[1] = currYear + currMonth + "0" + (Integer.parseInt(currDay) - 2);
			} else {
				returnDays[1] = currYear + currMonth + tempDay + (Integer.parseInt(currDay) - 2);
			}
		} else if (Integer.parseInt(currMonth) > 1 && Integer.parseInt(currDay) == 2) {
			returnDays[0] = currYear + currMonth + "01";
			if (currMonth.equals("10")) {
				returnDays[1] = currYear + "0" + (Integer.parseInt(currMonth) - 1) + (getLastDayOfUpMonth(Integer.parseInt(currYear), Integer.parseInt(currMonth), Integer.parseInt(currDay)));
			} else {
				returnDays[1] = currYear + tempMonth + (Integer.parseInt(currMonth) - 1) + (getLastDayOfUpMonth(Integer.parseInt(currYear), Integer.parseInt(currMonth), Integer.parseInt(currDay)));
			}
		} else if (Integer.parseInt(currMonth) > 1 && Integer.parseInt(currDay) == 1) {
			if (currMonth.equals("10")) {
				returnDays[0] = currYear + "0" + (Integer.parseInt(currMonth) - 1) + (getLastDayOfUpMonth(Integer.parseInt(currYear), Integer.parseInt(currMonth), Integer.parseInt(currDay)));
				returnDays[1] = currYear + "0" + (Integer.parseInt(currMonth) - 1) + (getLastDayOfUpMonth(Integer.parseInt(currYear), Integer.parseInt(currMonth), Integer.parseInt(currDay)) - 1);
			} else {
				returnDays[0] = currYear + tempMonth + (Integer.parseInt(currMonth) - 1) + (getLastDayOfUpMonth(Integer.parseInt(currYear), Integer.parseInt(currMonth), Integer.parseInt(currDay)));
				returnDays[1] = currYear + tempMonth + (Integer.parseInt(currMonth) - 1) + (getLastDayOfUpMonth(Integer.parseInt(currYear), Integer.parseInt(currMonth), Integer.parseInt(currDay)) - 1);
			}
		} else {
			returnDays[0] = currYear + currMonth + tempDay + (Integer.parseInt(currDay) - 1);
			returnDays[1] = currYear + currMonth + tempDay + (Integer.parseInt(currDay) - 2);
		}

		return returnDays;
	}

	/**
	 * @Description: 获取上个月的最后一天
	 * @date 2014-3-6 下午1:24:28
	 * @return int 返回类型
	 */
	public static int getLastDayOfUpMonth(int year, int month, int date) {
		Calendar calendar = new GregorianCalendar(year, month, date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		calendar.add(Calendar.MONTH, -1);// 月增减1天
		calendar.add(Calendar.DAY_OF_MONTH, -1);// 日期倒数一日,既得到本月最后一天
		return calendar.get(Calendar.DATE);
	}

	/**
	 * @Description: 获取当月第一天
	 * @date 2014-3-6 下午1:26:37
	 * @return String 返回类型
	 */
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * @Description: 获取当月最后一天
	 * @date 2014-3-6 下午1:26:54
	 * @return String 返回类型
	 */
	public static String getLastDayOfMonteh() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * @Description: 获取去年的年份
	 * @date 2014-3-6 下午1:27:03
	 * @return String 返回类型
	 */
	public static String getLastYear() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(currentTime);
		c.add(Calendar.YEAR, -1);
		String dateString = formatter.format(c.getTime());
		return dateString;
	}

	/**
	 * @Description: 获取前年的年份
	 * @date 2014-3-6 下午1:27:12
	 * @return String 返回类型
	 */
	public static String getBeforeLastYear() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(currentTime);
		c.add(Calendar.YEAR, -2);
		String dateString = formatter.format(c.getTime());
		return dateString;
	}

	/**
	 * @Description: 获取某月最后一天
	 * @date 2014-3-6 下午1:27:21
	 * @return String 返回类型
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * @Description: 获取某月第一天
	 * @date 2014-3-6 下午1:27:45
	 * @return String 返回类型
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * @Description: 判断日期是否为同一天
	 * @date 2014-3-6 下午1:27:58
	 * @return boolean 返回类型
	 */
	public static boolean isSameDay(Date dateA, Date dateB) {
		Calendar calDateA = Calendar.getInstance();
		calDateA.setTime(dateA);

		Calendar calDateB = Calendar.getInstance();
		calDateB.setTime(dateB);

		return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH) && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}
	
	/** 
	* @Description: 日期做加法 
	* @param date 日期
	* @param amount 天数
	* @date 2014-5-19 上午10:32:59
	* @return Date    返回类型  
	*/ 
	public static Date dateAddDay(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, amount);
		return calendar.getTime();
	}
	
	/**
	 * @description 格式化日期類型爲string
	 * @param date 日期類型
	 * @param format 日期格式（yyyy-MM-dd HH:mm:ss）
	 * @date 2015年12月9日14:51:09
	 * @return
	 */
	public static String formatDate(Date date,String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 获取星期
	 * @return String 星期
	 */
	public static String getWeek(){
		Date date=new Date();
	    SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
	    String week=dateFm.format(date);
	    return week;
	}
	
	/**
	 * 时间为上午还是下午
	 * @return String 上午还是下午
	 */
	public static String getAMPM(){
		GregorianCalendar ca = new GregorianCalendar();  
	    int i=ca.get(GregorianCalendar.AM_PM);
	    String str="";
	    if (i==0) {
			str="上午";
		}else {
			str="下午";
		}
	    return str;
	}
}
