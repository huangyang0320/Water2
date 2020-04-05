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

import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.sys.dao.ServiceValuesDao;
import com.wapwag.woss.modules.sys.entity.StatTotalInfo;

public class ServicValuesHourTask {

	private static final long ONE_DAT_TIME = 24 * 60 * 60 * 1000;
	private static final long ONE_HOUR_TIME = 60 * 60 * 1000;
	private static final long ONE_MIU__TIME = 60 * 1000;
	private static final SimpleDateFormat YMDHM = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	
	private static final SimpleDateFormat YMD  = new SimpleDateFormat("yyyyMMdd");
	
	
	private static final String SV = "service_values_";
	private static final String SVH = "service_values_hour_";
	
	private static final Long STAT_LAST_TIME = 1000 * 60 * 5L;
	
	private static ServiceValuesDao mapper = SpringContextHolder
			.getBean(ServiceValuesDao.class);
	private static Logger LOG = LoggerFactory
			.getLogger(ServicValuesHourTask.class);

	public static void stat() {
		 
		Date newDate = new Date(System.currentTimeMillis() - STAT_LAST_TIME);
		String tableDate = YMD.format(newDate);
		String startDate = YMDHM.format(newDate) + ":00";
		String endDate = YMDHM.format(newDate) + ":59";
		
		StatTotalInfo info = new StatTotalInfo();
		info.setName("'" +YMDHM.format(newDate) + ":00" + "'");
		info.setBeginTime(YMDHM.format(newDate) + ":00");
		info.setEndTime(YMDHM.format(newDate) + ":59");
		info.setTableName(SV + tableDate);
		info.setInsetTableName(SVH + tableDate);
		mapper.statServiceValsHour(info);
	}
}
