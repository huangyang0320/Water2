package com.wapwag.woss.common.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.StaticApplicationContext;

import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.sys.dao.ServiceValuesDao;
import com.wapwag.woss.modules.sys.entity.StatTotalInfo;

public class ServicValuesDayTask {

	private static final long ONE_DAT_TIME = 24 * 60 * 60 * 1000;
	private static final long ONE_HOUR_TIME = 60 * 60 * 1000;
	private static final long ONE_MIU__TIME = 60 * 1000;
	private static final SimpleDateFormat YMDH = new SimpleDateFormat(
			"yyyy-MM-dd HH");
	
	private static final SimpleDateFormat YMD  = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat YM  = new SimpleDateFormat("yyyyMM");
	
	
	private static final String SV = "service_values_hour_";
	private static final String SVD = "service_values_day_";
	
	private static final Long STAT_LAST_TIME = 1000 * 60 * 60L;
	
	private static ServiceValuesDao mapper = SpringContextHolder
			.getBean(ServiceValuesDao.class);
	private static Logger LOG = LoggerFactory
			.getLogger(ServicValuesDayTask.class);
	
	private static Long starTime = 0L; 

	public static void stat() {
		
		if (starTime < 1) {
			starTime = System.currentTimeMillis();
		}else if( System.currentTimeMillis() - starTime < 60000){
			return;
		}
		
		starTime = System.currentTimeMillis();
		 
		Date newDate = new Date(System.currentTimeMillis() - STAT_LAST_TIME);
		String tableDate = YMD.format(newDate);
	
		
		StatTotalInfo info = new StatTotalInfo();
		info.setName("'" +YMDH.format(newDate) + ":00:00" + "'");
		info.setBeginTime(YMDH.format(newDate) + ":00:00");
		info.setEndTime(YMDH.format(newDate) + ":59:59");
		
		info.setTableName(SV + YMD.format(newDate));
		info.setInsetTableName(SVD + YM.format(newDate));
		mapper.statServiceValsHour(info);
	}
}
