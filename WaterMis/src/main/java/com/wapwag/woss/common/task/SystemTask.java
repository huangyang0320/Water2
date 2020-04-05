package com.wapwag.woss.common.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.persistence.QryObject;

public class SystemTask {

	private static String runFlag = "0";
	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(SystemTask.class);
	
	/**
	 * 定时加载统计信息
	 */
	public static void sysTask()
	{
		LOG.info("Task SystemTask is running");
		if ("1".equals(runFlag)) {
			return;
		}
		QryObject info = new QryObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		info.setBeginTime(sdf.format(new Date()));
		ElectricityStatisticsTask.getCountConnction().modSystemRunTime(info);
		LOG.info("Task SystemTask is stop");
		runFlag = "1";
	}
}
