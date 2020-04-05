package com.wapwag.woss.common.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.modules.sys.dao.ServiceValuesDao;
import com.wapwag.woss.modules.sys.entity.StatTotalInfo;

public class ServiceValuesYearTask {

	private static ServiceValuesDao mapper = SpringContextHolder.getBean(ServiceValuesDao.class);
	private static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyy-MM");

	private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");

	private static Logger LOG = LoggerFactory.getLogger(ServiceValuesYearTask.class);
	
	private static final long ONE_DAY = 24 * 60 * 60 * 1000;

	public static void stat() {

		StatTotalInfo info = new StatTotalInfo();
		info.setSubIndex("7");
		info.setSubTag("-01 00:00:00");
		info.setBeginTime(YMDHMS.format(new Date()) + "-01 00:00:00");
		info.setEndTime("2099-12-01 00:00:00");
		info.setInsetTableName("service_values_year");
		info.setTableName("service_values_month");
		mapper.deleteOldVal(info);
		mapper.statServiceValsDMY(info);
		
		//电量、流量、压力统计
		statEnergyToDay();
	}

	private static void statEnergyToDay() {

		try {
			String nowDate = YYYYMMDD.format(new Date());
			Long nowTime = YYYYMMDD.parse(nowDate).getTime();
			String dateTime = YYYYMMDD.format(nowTime - ONE_DAY);

			mapper.statSumToDay(YYYYMMDD.format(new Date(nowTime - ONE_DAY)), YYYYMMDD.format(new Date(nowTime)));
			
			mapper.statAvgToDay(YYYYMMDD.format(new Date(nowTime - ONE_DAY)), YYYYMMDD.format(new Date(nowTime)));

			
		} catch (Exception e) {
			LOG.error("统计一天电量、流量、压力异常", e);
		}

	}
}