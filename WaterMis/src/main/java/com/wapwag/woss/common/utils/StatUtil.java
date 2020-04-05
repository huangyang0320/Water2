package com.wapwag.woss.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统计公用类
 * 
 * @author gongll
 *
 */
public class StatUtil {

	private static final long TWO_DAY = 48 * 60 * 60 * 1000;
	public static final long ONE_HOUR = 60 * 60 * 1000;
	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH");
	/** 日志 */
	private static final Logger LOG = LoggerFactory.getLogger(StatUtil.class);

	/**
	 * 获取查询的表明
	 * 
	 * @param timeValue
	 *            格式: yyyy-MM-dd ...
	 * @return
	 */
	public static String getTabkeName(String timeValue) {
		timeValue = timeValue.split(" ")[0];
		timeValue = timeValue.replace("-", "");
		return "service_values_" + timeValue;
	}

	/**
	 * 获取记录时间
	 * 
	 * @return
	 */
	public synchronized static List<String> getStatTimes(String lastTime) {
		long twoDay = System.currentTimeMillis() - TWO_DAY;
		List<String> timeValue = new ArrayList<String>();
		while (!sdf.format(new Date()).equals(sdf.format(new Date(twoDay)))) {
			timeValue.add(sdf.format(new Date(twoDay)));
			twoDay += ONE_HOUR;

		}
		timeValue.add(sdf.format(new Date(twoDay)));
		if (StringUtils.isEmpty(lastTime)) {
			return timeValue;
		}
		long dataTime = 0;
		try {
			dataTime = sdf.parse(lastTime).getTime();
		} catch (ParseException e) {
			LOG.error(e.toString() + " " + lastTime);
		}
		if (dataTime < (System.currentTimeMillis() - TWO_DAY)
				&& !lastTime.equals(sdf.format(System.currentTimeMillis()
						- TWO_DAY))) {
			return timeValue;
		}

		for (int i = 0; i < timeValue.size(); i++) {
			if (lastTime.equals(timeValue.get(i))) {
				return timeValue.subList(i + 1, timeValue.size());
			}
		}
		return timeValue;
	}
}
