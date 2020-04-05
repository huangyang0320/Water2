/**
 * 
 */
package com.wapwag.woss.modules.home.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leo
 * @since 2018-01-29 20:17:17
 */
public class DateFormatUtil {
	
	private static final SimpleDateFormat TIME_ALL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat TIME_HOUR = new SimpleDateFormat("yyyyMMddHHmm");  // 小时,获取分钟的统计数据
	private static final SimpleDateFormat TIME_DAY = new SimpleDateFormat("yyyyMMddHH");     // 天,获取小时的统计数据
	private static final SimpleDateFormat TIME_MONTH = new SimpleDateFormat("yyyyMMdd");     // 月,获取天的统计数据
	private static final SimpleDateFormat TIME_YEAR = new SimpleDateFormat("yyyyMM");        // 年,获取月的统计数据
	
	private static final SimpleDateFormat DATE_TYPE_HOUR = new SimpleDateFormat("yyyy-MM-dd HH");
	private static final SimpleDateFormat DATE_TYPE_DAY = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DATE_TYPE_MONTH = new SimpleDateFormat("yyyy-MM");
	
	private static Map<String, SimpleDateFormat> DATE_FORMAT_MAP = new HashMap<>(4);
	
	public static SimpleDateFormat getDataFormat(String dateType) {
		if (DATE_FORMAT_MAP.isEmpty()) {
			DATE_FORMAT_MAP.put("all", TIME_ALL);
			DATE_FORMAT_MAP.put("hour", TIME_HOUR);
			DATE_FORMAT_MAP.put("day", TIME_DAY);
			DATE_FORMAT_MAP.put("month", TIME_MONTH);
			DATE_FORMAT_MAP.put("year", TIME_YEAR);
		}
		return DATE_FORMAT_MAP.get(dateType);
	}
	
	public synchronized static List<String> getDates(String beginTime,String endTime,String type) throws ParseException{
		List<String> times = new ArrayList<String>();
		
		if (!checkTime(beginTime,endTime)) {
			return times;
		}
		
		Date beginDate = TIME_ALL.parse(beginTime);
		Calendar cal = Calendar.getInstance();  
		cal.setTime(beginDate);
		
		int calType;
		if ("day".equals(type)) {
			beginTime = DATE_TYPE_HOUR.format(cal.getTime());
			while (endTime.indexOf(beginTime) < 0) {
				times.add(beginTime);
				cal.add(cal.HOUR_OF_DAY, 1);
				beginTime = DATE_TYPE_HOUR.format(cal.getTime());
			}
			times.add(beginTime);
			
		}else if ("month".equals(type)) {
			beginTime = DATE_TYPE_DAY.format(cal.getTime());
			while (endTime.indexOf(beginTime) < 0) {
				times.add(beginTime);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				beginTime = DATE_TYPE_DAY.format(cal.getTime());
			}
			times.add(beginTime);
		}else if ("year".equals(type)) {
			beginTime = DATE_TYPE_MONTH.format(cal.getTime());
			while (endTime.indexOf(beginTime) < 0) {
				times.add(beginTime);
				cal.add(Calendar.MONTH, 1);
				beginTime = DATE_TYPE_MONTH.format(cal.getTime());
			}
			times.add(beginTime);
		}else {
			return times;
					
		}
		return times;
	}
	
	private static boolean checkTime(String beginTime,String endTime) throws ParseException{
		Date beginDate = TIME_ALL.parse(beginTime);
		Date endDate = TIME_ALL.parse(endTime);
		if (beginDate.getTime() >= endDate.getTime()) {
			return false;
		}
		return true;
	}
}
