package com.wapwag.woss.common.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.modules.biz.entity.Device;
import com.wapwag.woss.modules.sys.dao.CountDao;
import com.wapwag.woss.modules.sys.entity.ValueCount;

/**
 * 测试类
 * @author gongll
 *
 */
public class CountValueUtil {
	
	/** 查询类型 */
	private static String[] qryNames = new String[]{"bhyl","sbpl","ssdl","ssll","ljll","jkyl"};
	private static Map<String, List<ValueCount>> countValues = new HashMap<String, List<ValueCount>>();
	
	/** 临时文件 */
	private static Map<String, List<ValueCount>> tempCount = new HashMap<String, List<ValueCount>>();

	private static CountDao countDao = SpringContextHolder.getBean(CountDao.class);
	
	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(CountValueUtil.class);
	
	private static Map<String, Double> summaryVals = new HashMap<>();
	
	/** 能耗 */
	private static String elementSum;
	private static String flowSum;
	private static String runTag = "0";
	private static long runTime = 0;
	
	private static Map<String, String> pumpRunTime = new HashMap<String,String>();
	private static Map<String, String> statEle = new HashMap<String,String>();
	private static Map<String, String> statFlow = new HashMap<String,String>();
	
	private static List<Device> areaStat = new ArrayList<Device>();
	
	/** 定时任务失败再次执行的时间间隔  */
	private static long MAX_TIME = 600000;
	/**
	 * 定时加载统计信息
	 */
	public static void initValues()
	{
		 LOG.info("Task CountValueUtil begin is running");
		if (0 == runTime) {
			runTime = System.currentTimeMillis();
		}
		if ("1".equals(runTag) && ((System.currentTimeMillis() - runTime) < MAX_TIME)) {
			LOG.info("Task CountValueUtil begin is stoped , task is running");
			return;
		}
		runTag = "1";
		runTime = System.currentTimeMillis();
		//加载定好分析
		initElement();
		
		Map<String, ValueCount> map = qryMinMaxConf();
		List<ValueCount> count= null;
		//加载实时数据统计
		for (int i = 0; i < qryNames.length; i++) {
			count = countDao.countValueByName(new ValueCount(qryNames[i],StringUtils.getTodayTable()));
			for (int j = 0; j < count.size(); j++) {
				if (null != map.get(count.get(j).getServiceId())) {
					count.get(j).setMax(map.get(count.get(j).getServiceId()).getMax());
					count.get(j).setMin(map.get(count.get(j).getServiceId()).getMin());
				}
			}
			tempCount.put(qryNames[i],count );
		}
		LOG.info(tempCount.size()+"");
		if (null != tempCount && tempCount.size() > 0) {
			countValues = tempCount;
			tempCount = new HashMap<String, List<ValueCount>>();
		}
		runTag = "0";
		LOG.info("Task CountValueUtil begin is stoped");
	}
	
	/**
	 * 加载一个配置
	 */
	public static List<ValueCount> initOneVount(String qryName)
	{
		List<ValueCount> countOne = countDao.countValueByName(new ValueCount(qryName,StringUtils.getTodayTable()));
		if (null != countOne && countOne.size() > 0) {
			countValues.put(qryName, countOne);
		}
		return countOne;
	}
	
	/**
	 * 加载能耗统计
	 */
	public static String initElement()
	{
		List<Device> devices = countDao.pumpRunTime(StringUtils.getTodayTable());
		for(Device device:devices){
			pumpRunTime.put(device.getId(), device.getName());
		}
		
		devices = countDao.statEle(StringUtils.getTodayTable());
		for(Device device:devices){
			statEle.put(device.getId(), device.getName());
		}
		
		devices = countDao.statFlow(StringUtils.getTodayTable());
		for(Device device:devices){
			statFlow.put(device.getId(), device.getName());
		}

        List<Map<String, String>> tempVals = countDao.summaryMaxVals();
        Map<String, Double> temp = new HashMap<>();
        for (int i = 0; i < tempVals.size(); i++) {
        	temp.put(tempVals.get(i).get("name"), Double.parseDouble(tempVals.get(i).get("value")));
		}
        
        if (null != temp || temp.size() > 0) {
        	summaryVals.putAll(temp);
		}
		//areaStat = countDao.countDeviceAreaChina();
		return "0";
	}
	
	public static Map<String, Double> getSummary(){
		
		Map<String, Double> temp = new HashMap<>();
		if (summaryVals.size() < 1) {
			List<Map<String, String>> tempVals = countDao.summaryMaxVals();
			for (int i = 0; i < tempVals.size(); i++) {
				if (null == tempVals.get(i).get("value")) {
					continue;
				}
				temp.put(tempVals.get(i).get("name"),Double.parseDouble(tempVals.get(i).get("value")));
			}
	        if (null != temp || temp.size() > 0) {
	        	summaryVals.putAll(temp);
			}
	        tempVals = new ArrayList<>();
		}
		return summaryVals;
	}
	
	/**
	 * 加载能耗统计
	 */
	public static String initFlow()
	{
		String flow = countDao.statisticFlow(StringUtils.getTodayTable());
		if (!StringUtils.isEmpty(flow)) {
			flowSum = flow;
			return flowSum;
		}
		return "0";
	}
	
	/**
	 * 获取统计信息  statisticElectricity
	 * @param tagName
	 * @return
	 */
	public static List<ValueCount> getCountByTag(String tagName){
		LOG.info(countValues.size()+"");
		if (null != countValues.get(tagName)) {
			LOG.info("1");
			return countValues.get(tagName);
		}else {
			LOG.info("1");
			return initOneVount(tagName);
		}
	}
	
	/**
	 * 能耗统计 
	 * @param tagName
	 * @return
	 */
	public static String statisticElectricity(){
		if (!StringUtils.isEmpty(elementSum)) {
			return elementSum;
		}else {
			initElement();
			return elementSum;
		}
	}
	
	public static String runTime(String id){
		if (StringUtils.isEmpty(pumpRunTime.get(id))) {
			return "-";
		}else {
			return pumpRunTime.get(id);
		}
	}
	
	public static String statEle(String id){
		if (StringUtils.isEmpty(statEle.get(id))) {
			return "-";
		}else {
			return statEle.get(id);
		}
	}
	
	public static String statFlow(String id){
		if (StringUtils.isEmpty(statFlow.get(id))) {
			return "-";
		}else {
			return statFlow.get(id);
		}
	}
	/**
	 * 流量 
	 * @param tagName
	 * @return
	 */
	public static String statisticFlow(){
		if (!StringUtils.isEmpty(flowSum)) {
			return flowSum;
		}else {
			return "0";
		}
	}
	
	private static Map<String, ValueCount>  qryMinMaxConf(){
		List<ValueCount> list = countDao.qryMinMaxConf();
		Map<String, ValueCount> map = new HashMap<String, ValueCount>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getServiceId(), list.get(i));
		}
		return map;
	}

	public static List<Device> countDeviceAreaChina() {
		// TODO Auto-generated method stub
		return areaStat;
	}
}
