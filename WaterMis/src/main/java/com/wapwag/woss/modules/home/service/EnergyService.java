/**
 * 
 */
package com.wapwag.woss.modules.home.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wapwag.woss.modules.home.dao.EnergyMapper;
import com.wapwag.woss.modules.home.entity.EnergyDTO;
import com.wapwag.woss.modules.home.entity.EnergyRank;
import com.wapwag.woss.modules.home.entity.HistoryData;
import com.wapwag.woss.modules.home.entity.HundredVO;
import com.wapwag.woss.modules.home.entity.RankData;
import com.wapwag.woss.modules.home.entity.StatVO;
import com.wapwag.woss.modules.home.util.DateFormatUtil;
import com.wapwag.woss.modules.home.util.MapSortUtil;

/**
 * 能耗统计
 * 
 * @author gongll
 * @since 2018-04-16 15:10:34
 */
@Service
public class EnergyService {

	private static Logger log = LoggerFactory.getLogger(EnergyService.class);

	private static final String DATE_TYPE_HOUR = "'%Y-%m-%d %H'";
	private static final String DATE_TYPE_DAY = "'%Y-%m-%d'";
	private static final String DATE_TYPE_MONTH = "'%Y-%m'";
	DecimalFormat FNUM_4 = new DecimalFormat("##0.0000");
	DecimalFormat FNUM_3 = new DecimalFormat("##0.000");
	DecimalFormat FNUM_2 = new DecimalFormat("##0.00");

	private EnergyMapper energyMapper;

	@Autowired
	public EnergyService(EnergyMapper energyMapper) {
		this.energyMapper = energyMapper;
	}

	public Map<String, Object> getDevicesEngrgy(EnergyDTO queryDTO, String type) throws Exception {
		SimpleDateFormat dateTypeFormat = DateFormatUtil.getDataFormat(queryDTO.getQueryType());
		SimpleDateFormat timeAll = DateFormatUtil.getDataFormat("all");

		String startTime = queryDTO.getStartDate();
		String endTime = queryDTO.getEndDate();

		try {
			// startTime =
			// timeAll.format(dateTypeFormat.parse(queryDTO.getStartDate()));
			// endTime =
			// timeAll.format(dateTypeFormat.parse(queryDTO.getEndDate()));

		} catch (Exception e) {
			log.error("能耗查询，时间格式错误:", e);
			return new HashMap<>();
		}

		List<String> deviceIds = queryDTO.getDeviceIds();
		if (queryDTO.getDeviceIds().size() <= 0) {
			return new HashMap<>();
		}

		List<HistoryData> result = energyMapper.getDevicesEngrgy(deviceIds, startTime, endTime, type);
		Map<String, Float> temps = new HashMap<String, Float>();
		for (HistoryData info : result) {
			temps.put(info.getIdDevice(), info.getAvgData());
		}

		// 查询设备|泵房名称
		Map<String, String> names = new HashMap<String, String>();
		List<Map<String, String>> listNames = energyMapper.getNames(deviceIds, type);
		for (int i = 0; i < listNames.size(); i++) {
			/*names.put(listNames.get(i).get("devid"), listNames.get(i).get("pumpname") + "_"
					+ listNames.get(i).get("devname") + "(" + listNames.get(i).get("devid") + ")");*/
			//yxn 20200418(显示的名称 去掉设备的英文名)
			names.put(listNames.get(i).get("devid"), listNames.get(i).get("pumpname") + "_"
					+ listNames.get(i).get("devname"));
			names.put(listNames.get(i).get("pumpid"), listNames.get(i).get("pumpname"));
		}

		// 流量最大最小值
		StatVO m3 = null;
		// 电量最大最小值
		StatVO kwh = null;
		// 能耗最大最小值
		StatVO kwh_m3 = null;

		List<Map<String, String>> vals = new ArrayList<Map<String, String>>();
		Map<String, String> val;
		double flow = 0D;
		double ele = 0D;
		for (int i = 0; i < deviceIds.size(); i++) {
			flow = 0;
			ele = 0;
			val = new HashMap<String, String>();
			val.put("name", names.get(deviceIds.get(i)));

			if (null != temps.get(deviceIds.get(i) + "_m3")) {
				flow = temps.get(deviceIds.get(i) + "_m3");
				m3 = comparData(m3, names.get(deviceIds.get(i)), flow);
			} else {
				val.put("value", "-");
				vals.add(val);
			}
			if (null != temps.get(deviceIds.get(i) + "_kWh")) {
				ele = temps.get(deviceIds.get(i) + "_kWh");
				kwh = comparData(kwh, names.get(deviceIds.get(i)), ele);
			} else {
				val.put("value", "-");
				vals.add(val);
				continue;
			}
			if (flow > 0 && ele > 0) {
				val.put("value", FNUM_4.format(ele / flow));
				kwh_m3 = comparData(kwh_m3, names.get(deviceIds.get(i)), ele / flow);
			} else {
				val.put("value", "-");
			}
			vals.add(val);
		}

		Map<String, StatVO> minMax = new HashMap<String, StatVO>();
		if (null != m3) {
			minMax.put("m3", m3);
		}

		if (null != kwh) {
			minMax.put("kwh", kwh);
		}

		if (null != kwh_m3) {
			minMax.put("kwh_m3", kwh_m3);
		}

		Map<String, Object> resVal = new HashMap<String, Object>();
		resVal.put("minmax", minMax);
		resVal.put("count", vals);
		return resVal;
	}

	/**
	 * 获取最大最小值
	 * 
	 * @param vo
	 * @param name
	 * @param pv
	 * @return
	 */
	private StatVO comparData(StatVO vo, String name, double pv) {
		pv = Double.parseDouble(FNUM_4.format(pv));
		if (null == vo) {
			return new StatVO(name, pv, name, pv);
		}
		if (pv < vo.getMinVal()) {
			vo.setMinName(name);
			vo.setMinVal(pv);
		}
		if (pv > vo.getMaxVal()) {
			vo.setMaxName(name);
			vo.setMaxVal(pv);
		}
		return vo;
	}

	/**
	 * 用于统计多个设备|泵房电量、流量、能耗曲线【设备+泵房信息】
	 * 
	 * @param queryDTO
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Map<String, Map<String, List<Map<String, String>>>> getDeviceEngrgy(EnergyDTO queryDTO, String type)
			throws Exception {
		SimpleDateFormat dateTypeFormat = DateFormatUtil.getDataFormat(queryDTO.getQueryType());
		SimpleDateFormat timeAll = DateFormatUtil.getDataFormat("all");

		String startTime = queryDTO.getStartDate();
		String endTime = queryDTO.getEndDate();
		try {
			// startTime =
			// timeAll.format(dateTypeFormat.parse(queryDTO.getStartDate()));
			// endTime =
			// timeAll.format(dateTypeFormat.parse(queryDTO.getEndDate()));

		} catch (Exception e) {
			log.error("能耗查询，时间格式错误:", e);
			return new HashMap<String, Map<String, List<Map<String, String>>>>();
		}

		List<String> deviceIds = queryDTO.getDeviceIds();
		if (queryDTO.getDeviceIds().size() <= 0) {
			return new HashMap<String, Map<String, List<Map<String, String>>>>();
		}

		String dateTime = DATE_TYPE_HOUR;
		if ("day".equals(queryDTO.getQueryType())) {
			dateTime = DATE_TYPE_HOUR;
		} else if ("month".equals(queryDTO.getQueryType())) {
			dateTime = DATE_TYPE_DAY;
		} else if ("year".equals(queryDTO.getQueryType())) {
			dateTime = DATE_TYPE_MONTH;
		}
		queryDTO.setStartDate(startTime);
		queryDTO.setEndDate(endTime);
		List<HistoryData> result = energyMapper.getDeviceEngrgy(dateTime, startTime, endTime, deviceIds, type);

		return statDevsEnergy(result, queryDTO, type);
	}

	/**
	 * 计算多个设备|泵房电量、流量、能耗曲线【设备+泵房信息】
	 * 
	 * @param result
	 * @param queryDTO
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	private Map<String, Map<String, List<Map<String, String>>>> statDevsEnergy(List<HistoryData> result,
			EnergyDTO queryDTO, String type) throws ParseException {
		Map<String, Map<String, List<Map<String, String>>>> vals = new HashMap<String, Map<String, List<Map<String, String>>>>();
		vals.put("m3", new HashMap<String, List<Map<String, String>>>());
		vals.put("kWh", new HashMap<String, List<Map<String, String>>>());
		vals.put("energy", new HashMap<String, List<Map<String, String>>>());

		Map<String, Float> temps = new HashMap<String, Float>();
		for (HistoryData info : result) {
			temps.put(info.getIdDevice(), info.getAvgData());
		}
		List<String> times = DateFormatUtil.getDates(queryDTO.getStartDate(), queryDTO.getEndDate(),
				queryDTO.getQueryType());
		List<String> devIds = queryDTO.getDeviceIds();

		float flow = 0;
		float ele = 0;
		Map<String, String> val;
		for (int i = 0; i < devIds.size(); i++) {
			for (int j = 0; j < times.size(); j++) {
				// 流量
				vals.get("m3").put(devIds.get(i), new ArrayList<Map<String, String>>());

				// 电量
				vals.get("kWh").put(devIds.get(i), new ArrayList<Map<String, String>>());

				// 能耗
				vals.get("energy").put(devIds.get(i), new ArrayList<Map<String, String>>());
			}
		}

		for (int i = 0; i < devIds.size(); i++) {
			for (int j = 0; j < times.size(); j++) {
				flow = 0;
				ele = 0;

				// 流量
				val = new HashMap<String, String>();
				val.put("dateTime", times.get(j));
				if (null != temps.get(devIds.get(i) + "_" + times.get(j) + "_m3")) {
					flow = temps.get(devIds.get(i) + "_" + times.get(j) + "_m3");
					val.put("avgData", FNUM_3.format(flow));
				} else {
					val.put("avgData", "-");
				}
				vals.get("m3").get(devIds.get(i)).add(val);

				// 电量
				val = new HashMap<String, String>();
				val.put("dateTime", times.get(j));
				if (null != temps.get(devIds.get(i) + "_" + times.get(j) + "_" + "kWh")) {
					ele = temps.get(devIds.get(i) + "_" + times.get(j) + "_" + "kWh");
					val.put("avgData", FNUM_3.format(ele));
				} else {
					val.put("avgData", "-");
				}

				vals.get("kWh").get(devIds.get(i)).add(val);

				// 能耗
				val = new HashMap<String, String>();
				val.put("dateTime", times.get(j));
				if (flow > 0 && ele > 0) {
					val.put("avgData", FNUM_4.format(ele / flow));
				} else {
					val.put("avgData", "-");
				}

				vals.get("energy").get(devIds.get(i)).add(val);
			}
		}
		return vals;
	}

	/**
	 * 能效统计和分析
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	public Map<String, List<RankData>> statRank(EnergyDTO dto, String type) {

		Map<String, List<RankData>> resultData = new HashMap<String, List<RankData>>();

		// 统计分析所有设备数据
		Map<String, HundredVO> vals = qryAllDeicePv(dto);
		
		if (null == vals || vals.size() < 1) {
			return resultData;
		}

		Map<String, HundredVO> pump = new HashMap<String, HundredVO>();
		Map<HundredVO, Double> statVal = new HashMap<HundredVO, Double>();
		HundredVO vo;
		if ("pump".equals(type)) {
			for (Map.Entry<String, HundredVO> entry : vals.entrySet()) {

				if (null != pump.get(entry.getValue().getPumpId())) {
					pump.get(entry.getValue().getPumpId()).addPv(entry.getValue().getPv(), entry.getValue().getEnergy(),
							entry.getValue().getFlow());
				} else {
					pump.put(entry.getValue().getPumpId(), entry.getValue());
					pump.get(entry.getValue().getPumpId()).setValSize(1);
				}
			}

			for (Map.Entry<String, HundredVO> entry : pump.entrySet()) {
				vo = entry.getValue();
				vo.statVal();
				statVal.put(vo, vo.getPv());
			}

		} else {
			for (Map.Entry<String, HundredVO> entry : vals.entrySet()) {
				vo = entry.getValue();
				statVal.put(vo, vo.getPv());
			}
		}
		
		if (null == statVal || statVal.size() < 1) {
			return resultData;
		}

		Map<HundredVO, Double> sortVal = MapSortUtil.sortMapByValue(statVal);
		List<HundredVO> temp = new ArrayList<HundredVO>();

		double avgStat = 0;
		for (Map.Entry<HundredVO, Double> entry : sortVal.entrySet()) {
			avgStat += entry.getKey().getPv();
			temp.add(entry.getKey());
		}
		avgStat = avgStat / temp.size();

		List<RankData> valData = new ArrayList<RankData>();
		List<EnergyRank> result = new ArrayList<>();
		EnergyRank ranl;
		// 添加前十名
		for (int i = 0; i < temp.size(); i++) {
			if (i > 9) {
				break;
			}
			valData.add(new RankData(temp.get(i).getPumpName(), FNUM_2.format(temp.get(i).getPv())));
			ranl = new EnergyRank();
			ranl.addTop(temp.get(i).getPumpName(), FNUM_2.format(temp.get(i).getPv()),
					FNUM_2.format(temp.get(i).getFlow()));
			result.add(ranl);

		}

		resultData.put("top", valData);
		valData = new ArrayList<RankData>();
		// 添加差额数据
		if (temp.size() < 11) {
			return resultData;
		}

		// 添加或十名
		for (int i = temp.size() - 1; i > 9; i--) {
			if (temp.size() - i < 11) {
				valData.add(new RankData(temp.get(i).getPumpName(), FNUM_2.format(temp.get(i).getPv())));
				result.get(temp.size() - i - 1).addLast(temp.get(i).getPumpName(), FNUM_2.format(temp.get(i).getPv()),
						FNUM_2.format(temp.get(i).getFlow()));
			}
		}

		resultData.put("last", valData);
		valData = new ArrayList<RankData>();

		// 添加差额数据
		if (temp.size() < 21) {
			return resultData;
		}
		temp = temp.subList(10, 20);
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i).getPv() > avgStat * 1.5) {
				valData.add(new RankData(temp.get(i).getPumpName(), FNUM_2.format(temp.get(i).getPv())));
				result.get(i).addHigh(temp.get(i).getPumpName(), FNUM_2.format(temp.get(i).getPv()),
						FNUM_2.format(temp.get(i).getFlow()));
			}
		}
		resultData.put("high", valData);
		return resultData;

	}

	/**
	 * 查询出所有设备电量、流量数据
	 */
	private Map<String, HundredVO> qryAllDeicePv(EnergyDTO dto) {
		
		// 所有设备
		List<HundredVO> devs = energyMapper.getAllDevs(dto);

		Map<String, HundredVO> result = new HashMap<String, HundredVO>();

		// 查询所有数据
		List<HundredVO> pvs = energyMapper.getAllStatValsAvg(dto);

		Map<String, Double> temps = new HashMap<String, Double>();
		for (int i = 0; i < pvs.size(); i++) {
			temps.put(pvs.get(i).getDevID(), pvs.get(i).getPv());
		}

		pvs = energyMapper.getAllStatValsSum(dto);
		for (int i = 0; i < pvs.size(); i++) {
			temps.put(pvs.get(i).getDevID(), pvs.get(i).getPv());
		}

		//需要根据id排序
		devs.sort((a, b) -> a.getDevID().compareTo(b.getDevID()));

		double m3 = 0;
		double kwh = 0;

		double input = 0;
		double out = 0;

		HundredVO vo;
		for (int i = 0; i < devs.size(); i++) {
			
			vo = new HundredVO();
			m3 = 0;
			kwh = 0;
			input = 0;
			out = 0;

			if (null != temps.get(devs.get(i).getDevID() + "_m3")) {
				m3 = temps.get(devs.get(i).getDevID() + "_m3");
			} else {
				//continue;
			}

			if (null != temps.get(devs.get(i).getDevID() + "_kWh")) {
				kwh = temps.get(devs.get(i).getDevID() + "_kWh");
			} else {
				//continue;
			}
			if (null != temps.get(devs.get(i).getDevID() + "_out")) {
				out = temps.get(devs.get(i).getDevID() + "_out");
			} else {
				//continue;
			}

			if (null != temps.get(devs.get(i).getDevID() + "_in")) {
				input = temps.get(devs.get(i).getDevID() + "_in");
			}


			if (m3 > 0 && kwh > 0 && out > 0) {
				vo = devs.get(i);
				vo.setFlow(m3);
				vo.setEnergy(kwh);
				if ("1".equals(devs.get(i).getUnit()) && out - input > 0) {
					vo.setPv((kwh * 1000) / (m3 * (out - input)));
					result.put(devs.get(i).getDevID(), vo);
				}

				if ("2".equals(devs.get(i).getUnit())) {
					vo.setPv((kwh * 1000) / (m3 * out));
					result.put(devs.get(i).getDevID(), vo);
				}
			}
		}

		return result;
	}

}