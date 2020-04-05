package com.wapwag.woss.common.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.sys.dao.ServiceValuesDao;
import com.wapwag.woss.modules.sys.entity.StatTotalInfo;

/**
 * 月表统计
 * @author gongll
 *
 */
public class ServicValuesMonthTask {

	private static final long ONE_DAT_TIME = 24 * 60 * 60 * 1000;


	private static final SimpleDateFormat YMD  = new SimpleDateFormat("yyyyMMdd");
	
	private static final String SVD = "service_values_day_";
	private static ServiceValuesDao mapper = SpringContextHolder.getBean(ServiceValuesDao.class);
	private static final SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat YM = new SimpleDateFormat("yyyyMM");
	
	private static final Long STAT_TIME_FLAG = 60000L;
	private static Long runTime = 0L;
	
	private static final String SV = "service_values_hour_";

	public static void stat() {

		Long nowTime = System.currentTimeMillis();
		
		if (nowTime - runTime < STAT_TIME_FLAG) {
			return;
		}
		
		//校验并判断小时、天表是否存在，不存在创建
		checkTable();
				
		String beginTime = sdfYMD.format(new Date(nowTime - ONE_DAT_TIME));
		String endTime = sdfYMD.format(new Date(nowTime));
		String dayTableName = SVD + YM.format(new Date(nowTime - ONE_DAT_TIME));

		StatTotalInfo info = new StatTotalInfo();
		info.setSubIndex("10");
		info.setSubTag(" 00:00:00");

		info.setBeginTime(beginTime);
		info.setEndTime(endTime);

		info.setTableName(dayTableName);
		info.setInsetTableName("service_values_month");

		mapper.statServiceValsDMY(info);

	}
	
	
	/**
	 * 判断表是否存在
	 */
	private static void checkTable(){
		
		//当前嘻信息
	    long checTime = System.currentTimeMillis();
		String hourTable = YMD.format(new Date(checTime));
		String dayTable = YM.format(new Date(checTime));
		
		if (StringUtils.isEmp(mapper.checkTableExit(SV + hourTable))) {
			mapper.createTable(SV + hourTable);
		}
		
		if (StringUtils.isEmp(mapper.checkTableExit(SVD + dayTable))) {
			mapper.createTable(SVD + dayTable);
		}
		
		//明天信息
		checTime = System.currentTimeMillis() + ONE_DAT_TIME;
		hourTable = YMD.format(new Date(checTime));
		dayTable = YM.format(new Date(checTime));
		
		if (StringUtils.isEmp(mapper.checkTableExit(SV + hourTable))) {
			mapper.createTable(SV + hourTable);
		}
		
		if (StringUtils.isEmp(mapper.checkTableExit(SVD + dayTable))) {
			mapper.createTable(SVD + dayTable);
		}
	}

}
