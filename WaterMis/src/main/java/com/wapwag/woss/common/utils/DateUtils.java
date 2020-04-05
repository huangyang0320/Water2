package com.wapwag.woss.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	public static final String YMD_FM = "yyyyMMdd";
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
    
    public static String formatDateTimeByFormat(Date date,String format){
    	SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(date);
    }
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 获取两个日期之间的天数
	 * ps: 如果是2019-02-01 到2019-02-28 是28天 但是计算的结果是27，所以需要加1
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	//设置转换的日期格式
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static double getDistanceOfTwoDate(String before, String after) {
		try {
			long beforeTime = sdf.parse(before).getTime();
			long afterTime = sdf.parse(after).getTime();
			return (afterTime - beforeTime) / (1000 * 60 * 60 * 24)+1d;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0d;

	}

	/**
	 * 获取当月的第一天
	 * @return
	 */
	public static String  getCurrentMonthFirstDay(){

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(calendar.getTime());

	}

	/**
	 * 获取输入月的最后一天
	 * @return
	 */
	public static Date  getCurrentMonthLastDay(Date date){

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		ParsePosition pos = new ParsePosition(8);
		Date monthLastDay = null;
		try {
			monthLastDay = format.parse(format.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return monthLastDay;
	}

	/**
	 * 获取当月最后一天
	 * @return
	 */
	public static String  getCurrentMonthLastDay(){

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());
	}

	/**
	 * 获取上月的第一天
	 * @return
	 */
	public static String  getLastMonthFirstDay(){

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar=Calendar.getInstance();

		calendar.add(Calendar.MONTH, -1);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(calendar.getTime());

	}

	/**
	 * 上月最后一天
	 * @return
	 */
	public static String  getLastMonthLastDay(){

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());

	}

	/**
	 * 去年当月第一天
	 * @return
	 */
	public static String  getLastYearFirstDay(){

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(calendar.getTime());

	}

	/**
	 * 去年当月 最后一天
	 * @return
	 */
	public static String  getLastYearLastDay(){

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());

	}

	/**
	 * 去年
	 * @return
	 */
	public static String  getLastYear(){

		SimpleDateFormat format=new SimpleDateFormat("yyyy");
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		return format.format(calendar.getTime());

	}

	/**
	 * 今年
	 * @return
	 */
	public static String  getCurrentYear(){

		SimpleDateFormat format=new SimpleDateFormat("yyyy");
		Calendar calendar=Calendar.getInstance();
		return format.format(calendar.getTime());

	}


	/**
	 * 昨天
	 * @return
	 */
	public static String  getLastDay(String type,int day){

		SimpleDateFormat format=new SimpleDateFormat(type);
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return format.format(calendar.getTime());

	}

	/**
	 * 获取某月的 天数
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(String date) {
		try {
			SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendr = Calendar.getInstance();
			calendr.setTime(sd2.parse(date));
			return calendr.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));

		//getLastMonthFirstDay();
		//System.out.println(getLastDay());

		System.out.println(getCurrentYear());
	}
}
