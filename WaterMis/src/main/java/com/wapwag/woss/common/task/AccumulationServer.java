package com.wapwag.woss.common.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wapwag.woss.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.modules.sys.dao.AccumulationMapper;

/**
 * 能耗统计
 * 
 * @author gongll
 * @date 2018年4月10日
 */
public class AccumulationServer {

	private static Logger log = LoggerFactory.getLogger(AccumulationServer.class);

	private static AccumulationMapper mapper = SpringContextHolder.getBean(AccumulationMapper.class);

	private static final SimpleDateFormat YMDH = new SimpleDateFormat("yyyy-MM-dd HH");
	private static final SimpleDateFormat YMD = new SimpleDateFormat("yyyyMMdd");
	private static final long ONE_HOUR = 60 * 60 * 1000;
	private static final String LAST_TIME = ":00:00";

	public static void stat() {

		long nowTime = System.currentTimeMillis();
		try {
			// 查询并封装设备-泵房关系
			List<Map<String, String>> devs = mapper.devPumps();
			Map<String, Object> devPumps = new HashMap<String, Object>();
			for (int i = 0; i < devs.size(); i++) {
				devPumps.put(devs.get(i).get("dev"), devs.get(i).get("pump"));
			}

			List<Map<String, Object>> statVal = mapper.queryStatAccumulation(
					YMDH.format(new Date(nowTime - ONE_HOUR)) + LAST_TIME, YMDH.format(new Date(nowTime)) + LAST_TIME,
					YMD.format(new Date(nowTime - ONE_HOUR)),
					"'" + YMDH.format(new Date(nowTime - ONE_HOUR)) + LAST_TIME + "'");

			// 添加泵房ID
			for (int i = 0; i < statVal.size(); i++) {
				statVal.get(i).put("pumpid", devPumps.get(statVal.get(i).get("deviceid")));
			}

			// 持久化数据
			if (statVal.size() > 0) {
				mapper.batchInsert(statVal, "service_values_summary_history");
			}

			statVal = mapper.queryStatPass(YMDH.format(new Date(nowTime - ONE_HOUR)) + LAST_TIME,
					YMDH.format(new Date(nowTime)) + LAST_TIME, YMD.format(new Date(nowTime - ONE_HOUR)),
					"'" + YMDH.format(new Date(nowTime - ONE_HOUR)) + LAST_TIME + "'");

			// 添加泵房ID
			for (int i = 0; i < statVal.size(); i++) {
				statVal.get(i).put("pumpid", devPumps.get(statVal.get(i).get("deviceid")));
			}

			// 持久化数据
			if (statVal.size() > 0) {
				mapper.batchInsert(statVal, "service_values_summary_history");
			}

			copySummary(nowTime);

		} catch (Exception e) {
			log.error("能耗统计，能耗分析异常", e);
		}
	}

	private static void copySummary(Long nowTime) {

		// 所有设备配置信息
		List<Map<String, String>> devs = mapper.getAllDevConf();
		Map<String, Double> devConf = new HashMap<>();
		for (int i = 0; i < devs.size(); i++) {
			Double flow= StringUtils.isNotBlank(devs.get(i).get("flow"))?Double.parseDouble(devs.get(i).get("flow")):0.0;
			devConf.put(devs.get(i).get("id") + "_flow",flow);
			devConf.put(devs.get(i).get("id") + "_power", flow);
		}
		// 泵房设备数
		devs = mapper.getPumpDevSize();
		Map<String, Integer> pumpDevSize = new HashMap<>();
		for (int i = 0; i < devs.size(); i++) {
			pumpDevSize.put(devs.get(i).get("pumpid") + "", Integer.parseInt(devs.get(i).get("pv")));
		}

		// 历史两小时平均值
		List<Map<String, Object>> historyVal = mapper.getTwoHourAvg(YMDH.format(new Date(nowTime - 4 * ONE_HOUR)),
				YMDH.format(new Date(nowTime - 2 * ONE_HOUR)));
		Map<String, Object> historyTemp = new HashMap<>();
		for (int i = 0; i < historyVal.size(); i++) {
			historyTemp.put(historyVal.get(i).get("dev") + "", historyVal.get(i).get("pv"));
		}

		// 当前
		List<Map<String, Object>> nowVal = mapper.getOneSummary(YMDH.format(new Date(nowTime - ONE_HOUR)));

		List<Map<String, Object>> statVal = new ArrayList<>();

		Iterator<Map<String, Object>> iterator = nowVal.iterator();
		Map<String, Object> temp;
		String dev;
		while (iterator.hasNext()) {
			temp = iterator.next();

			if (null == temp.get("pumpid") || null == pumpDevSize.get(temp.get("pumpid"))) {
				continue;
			}

			dev = temp.get("deviceid") + "";
			if ("kWh".equals(temp.get("unit")) && Double.parseDouble(temp.get("pv") + "") > devConf.get(dev + "_power")
					* pumpDevSize.get(temp.get("pumpid"))) {
				if (null != historyTemp.get(dev + "_kWh")) {
					temp.put("pv", historyTemp.get(dev + "_kWh"));
				} else {
					temp.put("pv", "0");
				}
			}

			if ("m3".equals(temp.get("unit")) && Double.parseDouble(temp.get("pv") + "") > devConf.get(dev + "_flow")
					* pumpDevSize.get(temp.get("pumpid"))) {
				if (null != historyTemp.get(dev + "_m3")) {
					temp.put("pv", historyTemp.get(dev + "_m3"));
				} else {
					temp.put("pv", "0");
				}
			}
			statVal.add(temp);
		}

		// 持久化数据
		if (statVal.size() > 0) {
			mapper.batchInsert(statVal, "service_values_summary");
		}

	}

}
