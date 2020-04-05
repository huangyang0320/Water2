package com.wapwag.woss.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间计算工具类
 */
public class TimeUtils {

	private static long ONE_DAY = 24*60*60*1000;
	
	public static String toTimeString(long time) {
		TimeUtils t = new TimeUtils(time);
		int day = t.get(TimeUtils.DAY);
		int hour = t.get(TimeUtils.HOUR);
		int minute = t.get(TimeUtils.MINUTE);
		int second = t.get(TimeUtils.SECOND);
		StringBuilder sb = new StringBuilder();
		if (day > 0) {
			sb.append(day).append("天");
		}
		if (hour > 0) {
			sb.append(hour).append("时");
		}
		if (minute > 0) {
			sb.append(minute).append("分");
		}
		if (second > 0) {
			sb.append(second).append("秒");
		}
		return sb.toString();
	}

	/**
	 * 时间字段常量，表示“秒”
	 */
	public final static int SECOND = 0;

	/**
	 * 时间字段常量，表示“分”
	 */
	public final static int MINUTE = 1;

	/**
	 * 时间字段常量，表示“时”
	 */
	public final static int HOUR = 2;

	/**
	 * 时间字段常量，表示“天”
	 */
	public final static int DAY = 3;

	/**
	 * 各常量允许的最大值
	 */
	private final int[] maxFields = { 59, 59, 23, Integer.MAX_VALUE - 1 };

	/**
	 * 各常量允许的最小值
	 */
	private final int[] minFields = { 0, 0, 0, Integer.MIN_VALUE };

	/**
	 * 默认的字符串格式时间分隔符
	 */
	private String timeSeparator = ":";

	/**
	 * 时间数据容器
	 */
	private int[] fields = new int[4];

	/**
	 * 无参构造，将各字段置为 0
	 */
	public TimeUtils() {
		this(0, 0, 0, 0);
	}

	/**
	 * 使用时、分构造一个时间
	 * 
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 */
	public TimeUtils(int hour, int minute) {
		this(0, hour, minute, 0);
	}

	/**
	 * 使用时、分、秒构造一个时间
	 * 
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @param second
	 *            秒
	 */
	public TimeUtils(int hour, int minute, int second) {
		this(0, hour, minute, second);
	}

	/**
	 * 使用一个字符串构造时间<br/>
	 * Time time = new Time("14:22:23");
	 * 
	 * @param time
	 *            字符串格式的时间，默认采用“:”作为分隔符
	 */
	public TimeUtils(String time) {
		this(time, null);
		// System.out.println(time);
	}

	/**
	 * 使用时间毫秒构建时间
	 * 
	 * @param time
	 */
	public TimeUtils(long time) {
		this(new Date(time));
	}

	/**
	 * 使用日期对象构造时间
	 * 
	 * @param date
	 */
	public TimeUtils(Date date) {
		this(DateFormatUtils.formatUTC(date, "HH:mm:ss"));
	}

	/**
	 * 使用天、时、分、秒构造时间，进行全字符的构造
	 * 
	 * @param day
	 *            天
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 */
	public TimeUtils(int day, int hour, int minute, int second) {
		initialize(day, hour, minute, second);
	}

	/**
	 * 使用一个字符串构造时间，指定分隔符<br/>
	 * Time time = new Time("14-22-23", "-");
	 * 
	 * @param time
	 *            字符串格式的时间
	 */
	public TimeUtils(String time, String timeSeparator) {
		if (timeSeparator != null) {
			setTimeSeparator(timeSeparator);
		}
		parseTime(time);
	}

	/**
	 * 设置时间字段的值
	 * 
	 * @param field
	 *            时间字段常量
	 * @param value
	 *            时间字段的值
	 */
	public void set(int field, int value) {
		if (value < minFields[field]) {
			throw new IllegalArgumentException(value
					+ ", time value must be positive.");
		}
		fields[field] = value % (maxFields[field] + 1);
		// 进行进位计算
		int carry = value / (maxFields[field] + 1);
		if (carry > 0) {
			int upFieldValue = get(field + 1);
			set(field + 1, upFieldValue + carry);
		}
	}

	/**
	 * 获得时间字段的值
	 * 
	 * @param field
	 *            时间字段常量
	 * @return 该时间字段的值
	 */
	public int get(int field) {
		if (field < 0 || field > fields.length - 1) {
			throw new IllegalArgumentException(field
					+ ", field value is error.");
		}
		return fields[field];
	}

	/**
	 * 将时间进行“加”运算，即加上一个时间
	 * 
	 * @param time
	 *            需要加的时间
	 * @return 运算后的时间
	 */
	public TimeUtils addTime(TimeUtils time) {
		TimeUtils result = new TimeUtils();
		int up = 0; // 进位标志
		for (int i = 0; i < fields.length; i++) {
			int sum = fields[i] + time.fields[i] + up;
			up = sum / (maxFields[i] + 1);
			result.fields[i] = sum % (maxFields[i] + 1);
		}
		return result;
	}

	/**
	 * 将时间进行“减”运算，即减去一个时间
	 * 
	 * @param time
	 *            需要减的时间
	 * @return 运算后的时间
	 */
	public TimeUtils subtractTime(TimeUtils time) {
		TimeUtils result = new TimeUtils();
		int down = 0; // 退位标志
		for (int i = 0, k = fields.length - 1; i < k; i++) {
			int difference = fields[i] + down;
			if (difference >= time.fields[i]) {
				difference -= time.fields[i];
				down = 0;
			} else {
				difference += maxFields[i] + 1 - time.fields[i];
				down = -1;
			}
			result.fields[i] = difference;
		}
		result.fields[DAY] = fields[DAY] - time.fields[DAY] + down;
		return result;
	}

	/**
	 * 获得时间字段的分隔符
	 * 
	 * @return
	 */
	public String getTimeSeparator() {
		return timeSeparator;
	}

	/**
	 * 设置时间字段的分隔符（用于字符串格式的时间）
	 * 
	 * @param timeSeparator
	 *            分隔符字符串
	 */
	public void setTimeSeparator(String timeSeparator) {
		this.timeSeparator = timeSeparator;
	}

	private void initialize(int day, int hour, int minute, int second) {
		set(DAY, day);
		set(HOUR, hour);
		set(MINUTE, minute);
		set(SECOND, second);
	}

	private void parseTime(String time) {
		if (time == null) {
			initialize(0, 0, 0, 0);
			return;
		}
		String t = time;
		int field = DAY;
		set(field--, 0);
		int p = -1;
		while ((p = t.indexOf(timeSeparator)) > -1) {
			parseTimeField(time, t.substring(0, p), field--);
			t = t.substring(p + timeSeparator.length());
		}
		parseTimeField(time, t, field--);
	}

	private void parseTimeField(String time, String t, int field) {
		if (field < SECOND || t.length() < 1) {
			parseTimeException(time);
		}
		char[] chs = t.toCharArray();
		int n = 0;
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] <= ' ') {
				continue;
			}
			if (chs[i] >= '0' && chs[i] <= '9') {
				n = n * 10 + chs[i] - '0';
				continue;
			}
			parseTimeException(time);
		}
		set(field, n);
	}

	private void parseTimeException(String time) {
		throw new IllegalArgumentException(time + ", time format error, HH"
				+ this.timeSeparator + "mm" + this.timeSeparator + "ss");
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(16);
		sb.append(fields[DAY]).append(',').append(' ');
		buildString(sb, HOUR).append(timeSeparator);
		buildString(sb, MINUTE).append(timeSeparator);
		buildString(sb, SECOND);
		return sb.toString();
	}

	private StringBuilder buildString(StringBuilder sb, int field) {
		if (fields[field] < 10) {
			sb.append('0');
		}
		return sb.append(fields[field]);
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + Arrays.hashCode(fields);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TimeUtils other = (TimeUtils) obj;
		if (!Arrays.equals(fields, other.fields)) {
			return false;
		}
		return true;
	}

	public static String addOneDay(String endTime) {
		long oneDay = 86400000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date endDate = sdf.parse(endTime);
			oneDay = oneDay + endDate.getTime();
			endTime = sdf.format(oneDay);
			return endTime;
		} catch (ParseException e) {
			return endTime;
		}
	}

	public static String dateFormart(String tableName, String beginTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date endDate = sdf.parse(beginTime);
			beginTime = sdf.format(endDate);
		} catch (ParseException e) {
		}
		String[] timeSplit = beginTime.split("-");
		StringBuffer formartStr = new StringBuffer(tableName);
		formartStr.append("_");
		int sum = 3;
		if ("service_values_day".equals(tableName)) {
			sum = 2;
		}
		for (int i = 0; i < sum; i++) {
			if (timeSplit[i].length() < 2) {
				formartStr.append("0").append(timeSplit[i]);
			} else {
				formartStr.append(timeSplit[i]);
			}
		}
		return formartStr.toString();
	}

	public static String countDats(String beginTime) {
		String formatStr = "yyyy-MM-dd HH:mm:ss";
		if (StringUtils.isEmp(beginTime)) {
			return "-";
		}
		if (beginTime.split(" ").length < 2) {
			formatStr = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Date endDate;
		StringBuffer days = new StringBuffer();
		try {
			endDate = sdf.parse(beginTime);
			long sumTime = System.currentTimeMillis() - endDate.getTime();
			if (sumTime < 0) {
				return "-";
			}
			sumTime = sumTime / 3600000;
			
			return numberWithDelimiter(sumTime + 1);

			/**
			if (!(sumTime < 365 * 24)) {
				days.append(sumTime / (365 * 24)).append("\u5E74");
				sumTime = sumTime % (365 * 24);
			}

			if (!(sumTime < 30 * 24)) {
				days.append(sumTime / (30 * 24)).append("\u6708");
				sumTime = sumTime % (30 * 24);
			}
			if (!(sumTime < 24)) {
				days.append(sumTime / 24).append("\u5929");
				sumTime = sumTime % 24;
			}
			if (sumTime > 0) {
				days.append(sumTime).append("\u5C0F\u65F6");
			}
			**/
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return days.toString();
	}

	public static String numberWithDelimiter(long num) {
        StringBuilder accum = new StringBuilder();
        int len = accum.append(num).length();
        if (len <= 3) return accum.toString();   //如果长度小于等于3不做处理
        while ((len -= 3) > 0) { //从个位开始倒序插入
            accum.insert(len, ",");
        }
        return   accum.toString();
	}
	  public static String getCurrerDay(int dateTime){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String now = sdf.format(new Date());
			try {
				Date nowDate = sdf.parse(now);
				long nowL = nowDate.getTime() - dateTime*ONE_DAY; 
				now = sdf.format(new Date(nowL));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return now;
	    }
	    public static void main(String[] args) {
			System.out.println(getCurrerMouth(4));
		}
	    
	    public static String getCurrerMouth(int dateTime){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    	String now = sdf.format(new Date());
	    	if (0 == dateTime) {
				return now;
			}
	    	int year = Integer.parseInt(now.split("-")[0]);
			int mouth = Integer.parseInt(now.split("-")[1]);
			if (mouth > dateTime) {
				mouth = mouth - dateTime;
			}else{
				year = year -1;
				mouth = 12+mouth-dateTime;
			}
			if (mouth < 10) {
				return year + "-0" + mouth;
			}
			return year + "-" + mouth;
	    }
	    
	    public static String getCurrerYear(int dateTime){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	    	String now = sdf.format(new Date());
	    	int year = Integer.parseInt(now);
			year = year - dateTime;
			return year + "";
		}
	    
	    public static String[] trafficTime(int dateTime,String type){
	    	dateTime++;
	    	String[] times = new String[4];
	    	SimpleDateFormat sdf = null;
	    	Date now = null;
	    	Calendar calendar = new GregorianCalendar();
	    	calendar.setTime(new Date());
	    	if ("2".equals(type)) {
	    		sdf = new SimpleDateFormat("yyyy-MM");
	    		
	    		calendar.add(calendar.MONTH,(1 - dateTime));
	    		times[0] = sdf.format(calendar.getTime());
	    		
	    		calendar.add(calendar.MONTH,1);
	    		times[1] = sdf.format(calendar.getTime());
	    		times[2] = "10";
	    		times[3] = "9";
			}else if ("3".equals(type)) {
				sdf = new SimpleDateFormat("yyyy");
				
				calendar.add(calendar.YEAR,(1 - dateTime));
	    		times[0] = sdf.format(calendar.getTime());
	    		
	    		calendar.add(calendar.YEAR,1);
	    		times[1] = sdf.format(calendar.getTime());
	    		times[2] = "7";
	    		times[3] = "6";
			}else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				
	    		calendar.add(calendar.DATE,(1 - dateTime));
	    		times[0] = sdf.format(calendar.getTime());
	    		
	    		calendar.add(calendar.DATE,1);
	    		times[1] = sdf.format(calendar.getTime());
	    		times[2] = "13";
	    		times[3] = "12";
			}
	    	return times;
	    }
}