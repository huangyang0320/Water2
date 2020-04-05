package com.wapwag.woss.modules.home.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wapwag.woss.modules.home.dao.StatRunMapper;
import com.wapwag.woss.modules.home.entity.RunStatSTO;
import com.wapwag.woss.modules.home.util.DateFormatUtil;
import com.wapwag.woss.modules.home.util.MapSort;

@Service
public class StatRunService {
	
	StatRunMapper statRunMapper;
	
	@Autowired
	public StatRunService(StatRunMapper statRunMapper){
		this.statRunMapper = statRunMapper;
	}

	/**
	 * 查询水泵运行时间
	 * @param queryDTO
	 * @param type
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> getPumpRunTime(RunStatSTO queryDTO) {
		SimpleDateFormat dateTypeFormat = null;
		if ("day".equals(queryDTO.getQueryType())) {
			dateTypeFormat = new SimpleDateFormat("yyyyMMddHH");
		}else if ("month".equals(queryDTO.getQueryType())) {
			dateTypeFormat = new SimpleDateFormat("yyyyMMdd");
		}else if ("year".equals(queryDTO.getQueryType())) {
			dateTypeFormat = new SimpleDateFormat("yyyyMM");
		}
		
		List<String> times;
		try {
			times = DateFormatUtil.getDates(queryDTO.getStartDate(), queryDTO.getEndDate(), queryDTO.getQueryType());
		} catch (ParseException e) {
			return new HashMap<>();
		}
		
		//统计查询结果
		Map<String, String> statVal = queryPumpRunStat(queryDTO);
		
		List<String> pumps = statRunMapper.getPumpByDevCode(queryDTO.getDeviceId());
		
		
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String, Object> data;
		List<Double> vals;
		String val;
		for (int i = 0; i < pumps.size(); i++) {
			if (null == pumps.get(i)) {
				continue;
			}
			data = new HashMap<String, Object>();
			vals = new ArrayList<>();
			
			for (int j = 0; j < times.size(); j++) {
				val = statVal.get(pumps.get(i) + "_" + times.get(j));
				if (null == val || "-".equals(val)) {
					vals.add(0D);
				}else {
					vals.add(Double.parseDouble(val));
				}
				
			}
			
			data.put("name", pumps.get(i));
			data.put("data", vals);
			datas.add(data);
		}
		
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String,Object>>>();
		
		List<Map<String, Object>> title = new ArrayList<Map<String,Object>>();
		data = new HashMap<String, Object>();
		data.put("data", times);
		title.add(data);
		
		result.put("series", datas);
		result.put("xAxis", title);
		return result;
	}
	
	private Map<String, String> queryPumpRunStat(RunStatSTO info){
		Map<String, String> statVal = new HashMap<String, String>();
		Map<String, Object> qryObject = new HashMap<String, Object>();
		qryObject.put("devCode", info.getDeviceId());
		qryObject.put("start", info.getStartDate());
		//qryObject.put("end", info.getEndDate());


		if ("day".equals(info.getQueryType())) {
			qryObject.put("end", info.getEndDate().replace(":00:00", ":59:59"));
			qryObject.put("maxVal", 60);
			qryObject.put("subIndex", 13);
			qryObject.put("except", 60);
			
		}else if ("month".equals(info.getQueryType())) {
			qryObject.put("end", info.getEndDate().replace("00:00:00", "23:59:59"));
			qryObject.put("maxVal", 24);
			qryObject.put("subIndex", 10);
			qryObject.put("except", 3600);
			
		}else if ("year".equals(info.getQueryType())) {
			qryObject.put("end", info.getEndDate().replace("01 00:00:00", "31 23:59:59"));
			qryObject.put("maxVal", 31);
			qryObject.put("subIndex", 7);
			qryObject.put("except", 24*3600);
			
		}else {
			return statVal;
		}
		List<Map<String, String>> vals = statRunMapper.qryRunStat(qryObject);
		for (int i = 0; i < vals.size(); i++) {
			statVal.put(vals.get(i).get("name"), vals.get(i).get("value"));
		}
		return statVal;
	}
	
	/**
	 * 查询水泵低频、高频运行时间
	 * @param queryDTO
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> getPumpFrequency(RunStatSTO queryDTO) {
		SimpleDateFormat dateTypeFormat = null;
		if ("day".equals(queryDTO.getQueryType())) {
			dateTypeFormat = new SimpleDateFormat("yyyyMMddHH");
		}else if ("month".equals(queryDTO.getQueryType())) {
			dateTypeFormat = new SimpleDateFormat("yyyyMMdd");
		}else if ("year".equals(queryDTO.getQueryType())) {
			dateTypeFormat = new SimpleDateFormat("yyyyMM");
		}
		
		
		//水泵信息
		List<String> pumps = statRunMapper.getPumpByDevCode(queryDTO.getDeviceId());
		
		//统计查询结果
		Map<String, Object> statVal = qryFreStat(queryDTO);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < pumps.size(); i++) {
			Map<String, Object> tmpMap= new HashMap<String, Object>();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			if (null == pumps.get(i)) {
				continue;
			}
			Map<String, Object> dMap= new HashMap<String, Object>();
			dMap.put("y", statVal.get(pumps.get(i) + "_低频")==null?0:statVal.get(pumps.get(i) + "_低频"));
			dMap.put("name", "低频");
			Map<String, Object> zMap= new HashMap<String, Object>();
			zMap.put("y", statVal.get(pumps.get(i) + "_中频")==null?0:statVal.get(pumps.get(i) + "_中频"));
			zMap.put("name", "中频");
			Map<String, Object> gMap= new HashMap<String, Object>();
			gMap.put("y", statVal.get(pumps.get(i) + "_高频")==null?0:statVal.get(pumps.get(i) + "_高频"));
			gMap.put("name", "高频");
			data.add(dMap);
			data.add(zMap);
			data.add(gMap);
			tmpMap.put("title", pumps.get(i)+"电机运行频率统计");
			tmpMap.put("data", data);
			result.add(tmpMap);
		}
		return result;
	}
	
	private Map<String, Object> qryFreStat(RunStatSTO info){
		Map<String, Object> statVal = new HashMap<String, Object>();
		Map<String, Object> qryObject = new HashMap<String, Object>();
		qryObject.put("devCode", info.getDeviceId());
		qryObject.put("start", info.getStartDate());

		if ("day".equals(info.getQueryType())) {
			qryObject.put("end", info.getEndDate().replace(":00:00", ":59:59"));
			qryObject.put("maxVal", 60);
			qryObject.put("except", 60);
			
		}else if ("month".equals(info.getQueryType())) {
			qryObject.put("end", info.getEndDate().replace("00:00:00", "23:59:59"));
			qryObject.put("maxVal", 24);
			qryObject.put("except", 3600);
			
		}else if ("year".equals(info.getQueryType())) {
			qryObject.put("end", info.getEndDate().replace("01 00:00:00", "31 23:59:59"));
			qryObject.put("maxVal", 31);
			qryObject.put("except", 24*3600);
			
		}else {
			return statVal;
		}
		List<Map<String, Object>> vals = statRunMapper.qryFreStat(qryObject);
		for (int i = 0; i < vals.size(); i++) {
			statVal.put(vals.get(i).get("name").toString(), vals.get(i).get("value"));
		}
		return statVal;
	}
}
