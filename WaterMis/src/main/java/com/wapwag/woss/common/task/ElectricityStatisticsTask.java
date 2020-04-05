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
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.modules.sys.dao.CountDao;
import com.wapwag.woss.modules.sys.entity.ValueCount;

public class ElectricityStatisticsTask {

	
	private static CountDao countDao = SpringContextHolder.getBean(CountDao.class);
	private static List<String> times = new ArrayList<String>();
	private static String runTag = "0";
	private static long runTime = 0;
	private static long MAX_TIME = 600000;
	
	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(ElectricityStatisticsTask.class);
	/**
	 * 定时加载统计信息
	 */
	public static void beginTask()
	{
		if (((System.currentTimeMillis() - runTime) < 10000)) {
			return;
		}
		LOG.info("Task ElectricityStatisticsTask begin is running");
		
		if (0 == runTime) {
			runTime = System.currentTimeMillis();
		}
		if ("1".equals(runTag) && ((System.currentTimeMillis() - runTime) < MAX_TIME)) {
			LOG.info("Task ElectricityStatisticsTask begin is stoped,task is running");
			return;
		}
		runTag = "1";
		runTime = System.currentTimeMillis();
		initTimes();
		List<ValueCount> values = getCountValues();
		Map<String, ValueCount> maps = new HashMap<String, ValueCount>();
		for (int i = 0; i < values.size(); i++) {
			maps.put(values.get(i).getDateTime(), values.get(i));
		}
		values = new ArrayList<ValueCount>(0);
		
		long current = -1;
		long last = -1;
		String qryValue;
		QryObject qryInfo = new QryObject();
		ValueCount valueCount = new ValueCount();
		Map<String, String> totalVals = new HashMap<String, String>();
		for (int i = 0; i < times.size()-2; i++) {
			if (null == maps.get(times.get(i))) {
				valueCount.setDateTime(times.get(i));
				qryInfo.setEndTimeNemol(times.get(i) +":00:00");
				qryInfo.setBeginTime(times.get(i+1) +":00:00");
				qryInfo.setTableName(getTabkeName(times.get(i)));
				if (StringUtils.isEmpty(totalVals.get(times.get(i)))) {
					qryValue = statisticElectricity(qryInfo);
				}else {
					qryValue = totalVals.get(times.get(i))  ;
				}
				if (StringUtils.isEmpty(qryValue)) {
					valueCount.setTotal("0");
					valueCount.setPv("0");
					saveCountValue(valueCount);
					continue;
				}else {
					current = Long.parseLong(qryValue);
					qryInfo.setEndTimeNemol(times.get(i+1)+":00:00");
					qryInfo.setBeginTime(times.get(i+2)+":00:00");
					qryInfo.setTableName(getTabkeName(times.get(i+1)));
					qryValue = statisticElectricity(qryInfo);
					if (StringUtils.isEmpty(qryValue)) {
						totalVals.put(times.get(i+1), "0");
						valueCount.setTotal(current+"");
						valueCount.setPv("0");
						saveCountValue(valueCount);
					}else {
						totalVals.put(times.get(i+1), qryValue);
						last = Long.parseLong(qryValue);
						if (last > current) {
							valueCount.setTotal(current+"");
							valueCount.setPv("0");
							saveCountValue(valueCount);
						}else {
							valueCount.setTotal(current+"");
							valueCount.setPv((current - last) + "");
							saveCountValue(valueCount);
						}
					}
				}
			}
		}
		LOG.info("Task ElectricityStatisticsTask begin is stoped");
		runTag = "0";
	}
	
	private static void initTimes(){
		times = new ArrayList<String>();
		Date nowTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		String nowStr = sdf.format(nowTime);
		try {
			//170
			nowTime = sdf.parse(nowStr);
			for (int i = 0; i < 30; i++) {
				times.add(sdf.format(new Date(nowTime.getTime() - i*3600000)));
			}
		} catch (ParseException e) {
			LOG.error(e.toString());
		}
	}
	
	private static String getTabkeName(String timeValue){
		String ooTime = timeValue.split(" ")[1];
		timeValue = timeValue.split(" ")[0];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowStr = sdf.format(new Date());
		if(nowStr.equals(timeValue) && !"00".equals(ooTime)){
			SimpleDateFormat sdfToday = new SimpleDateFormat("yyyyMMdd");
			return "service_values_"+sdfToday.format(new Date());
		}
		if("00".equals(ooTime)){
			try {
				timeValue = sdf.format(new Date((sdf.parse(timeValue).getTime()) - 60*60*1000));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(timeValue);
		}
		timeValue = timeValue.replace("-", "");
		return "service_values_" + timeValue;
	}
	
	private static String statisticElectricity(QryObject info){
		return countDao.statisticElectricityTime(info);
	}
	
	@Transactional(readOnly = false)
	private static void saveCountValue(ValueCount info){
		 countDao.saveCountValue(info);
	}
	
	private static List<ValueCount> getCountValues(){
		return countDao.getCountValues();
	}
	
	public static CountDao getCountConnction(){
		return countDao;
	}
}
