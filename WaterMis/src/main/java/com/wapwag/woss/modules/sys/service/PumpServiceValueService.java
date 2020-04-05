package com.wapwag.woss.modules.sys.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.common.utils.TimeUtils;
import com.wapwag.woss.modules.sys.dao.PumpServiceValueDao;
import com.wapwag.woss.modules.sys.entity.BootPage;
import com.wapwag.woss.modules.sys.entity.EnergyInfo;
import com.wapwag.woss.modules.sys.entity.PumpServiceValue;

/**
 * 设备传感器数据
 */
@Service
@Transactional(readOnly = true)
public class PumpServiceValueService extends CrudService<PumpServiceValueDao, PumpServiceValue> {

	private static final String HOUR_TABLE = "service_values_hour";
	private static final String DAY_TABLE = "service_values_day";
	private static final String PATTERN = "\u6545\u969C|\u62A5\u8B66|\u4F11\u606F|\u8FD0\u884C|\u8FDC\u7A0B\u63A7\u5236\u7535\u52A8\u9600";

	private static final String UA = "Ua";
	private static final String UB = "Ub";
	private static final String UC = "Uc";
	private static final String IA = "Ia";
	private static final String IB = "Ib";
	private static final String IC = "Ic";

	private static final long THREE_HOUR = 1000 * 60 * 60 * 1;
	private static SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat tableFormat = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private PumpServiceValueDao pumpServiceValueDao;

	public Page<PumpServiceValue> findPage(Page<PumpServiceValue> page, PumpServiceValue pumpSensorData) {
		return super.findPage(page, pumpSensorData);
	}

	public String[] findServiceByDeviceID(String deviceId, String tableName) {
		if ("0".equals(pumpServiceValueDao.exitTable(tableName))) {
			return new String[0];
		}

		PumpServiceValue info = new PumpServiceValue();
		info.setDeviceId(deviceId);
		info.setTableName(tableName);
		return pumpServiceValueDao.findServiceByDeviceID(info);
	}

	public List<PumpServiceValue> exportAll(PumpServiceValue pumpServiceValue) {
		return pumpServiceValueDao.exportAll(pumpServiceValue);
	}

	public BootPage findAll(PumpServiceValue pumpServiceValue) {

		List<PumpServiceValue> list = null;
		if ("ho".equals(pumpServiceValue.getOperType())) {
			pumpServiceValue.setTableName(TimeUtils.dateFormart(HOUR_TABLE, pumpServiceValue.getBeginTime()));
		} else if ("da".equals(pumpServiceValue.getOperType())) {
			pumpServiceValue.setTableName(TimeUtils.dateFormart(DAY_TABLE, pumpServiceValue.getBeginTime()));
		} else if ("mo".equals(pumpServiceValue.getOperType())) {
			pumpServiceValue.setTableName("service_values_month");
		} else {
			pumpServiceValue.setTableName("service_values_year");
		}

		if ("ho".equals(pumpServiceValue.getOperType()) || "da".equals(pumpServiceValue.getOperType())) {
			list = pumpServiceValueDao.findOneAll(pumpServiceValue);
		} else {
			list = pumpServiceValueDao.findAll(pumpServiceValue);
		}

		String[] ttest = findServiceByDeviceID(pumpServiceValue.getDeviceId(), pumpServiceValue.getTableName());
		Map<String, String> tagName = new HashMap<String, String>();
		BootPage bootPage = new BootPage();
		List resultList = new ArrayList();
		String createTime = null;
		for (int i = 0; i < list.size(); i++) {
			if (null == createTime) {
				resultList.add(tagName);
				createTime = list.get(i).getBeginTime();
				tagName.put("createDate", createTime);
				tagName.put(list.get(i).getTagName(), countPV(list.get(i)));
			} else if (createTime.equals(list.get(i).getBeginTime())) {
				tagName.put(list.get(i).getTagName(), countPV(list.get(i)));
			} else {
				tagName = new HashMap<String, String>();
				resultList.add(tagName);
				createTime = list.get(i).getBeginTime();
				tagName.put("createDate", createTime);
				tagName.put(list.get(i).getTagName(), countPV(list.get(i)));
			}

		}
		bootPage.setRows(resultList);
		return bootPage;
	}

	private String countPV(PumpServiceValue info) {
		Pattern r = Pattern.compile(PATTERN);
		Matcher m = r.matcher(info.getTagName());
		if (m.find()) {
			return (int) (Double.parseDouble(info.getPv()) * info.getCount()) + "";
		}
		return info.getPv();
	}

	public String[] countSum(PumpServiceValue pumpServiceValue) {

		if ("ho".equals(pumpServiceValue.getOperType()) || "da".equals(pumpServiceValue.getOperType())) {
			if ("ho".equals(pumpServiceValue.getOperType())) {
				pumpServiceValue.setTableName(TimeUtils.dateFormart(HOUR_TABLE, pumpServiceValue.getBeginTime()));
			} else {
				pumpServiceValue.setTableName(TimeUtils.dateFormart(DAY_TABLE, pumpServiceValue.getBeginTime()));
			}
			if ("0".equals(pumpServiceValueDao.exitTable(pumpServiceValue.getTableName()))) {
				return new String[0];
			}
			return pumpServiceValueDao.countOneSum(pumpServiceValue);
		} else {
			return pumpServiceValueDao.countSum(pumpServiceValue);
		}
	}

	public BootPage reverseColumn(Page<PumpServiceValue> pages) {
		List<PumpServiceValue> pumpServiceValues = pages.getList();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

		PumpServiceValue pumpServiceValue;
		Map<String, String> data;
		String rows;
		String[] dataValue;
		for (int i = 0; i < pumpServiceValues.size(); i++) {
			data = new HashMap<String, String>();
			pumpServiceValue = pumpServiceValues.get(i);
			data.put("createDate", pumpServiceValue.getBeginTime());
			rows = pumpServiceValue.getTagName();
			dataValue = rows.split(",");
			for (int j = 0; j < dataValue.length; j++) {
				if (dataValue[j].split("\\|").length < 2) {
					continue;
				}
				data.put(dataValue[j].split("\\|")[0], dataValue[j].split("\\|")[1] + "|"
						+ pumpServiceValue.getOperType().split(",")[j].split("\\|")[1]);
				data.put("count", pumpServiceValue.getOperType().split(",")[j].split("\\|")[1]);
			}
			resultList.add(data);
		}
		BootPage bootPage = new BootPage();
		bootPage.setTotal(pages.getCount());
		bootPage.setRows(resultList);
		return bootPage;
	}

	/**
	 * 设备运行状态统计
	 * 
	 * @param info
	 * @return
	 */
	public List<PumpServiceValue> aa(PumpServiceValue info) {
		return null;
	}

	/**
	 * 频率统计
	 * 
	 * @param info
	 * @return
	 */
	public PumpServiceValue[] powerStatis(PumpServiceValue info) {
		long curTime = System.currentTimeMillis() - THREE_HOUR;

		info.setBeginTime(sdFormat.format(new Date(curTime)));
		info.setTableName("service_values_" + tableFormat.format(new Date()));
		List<PumpServiceValue> stat = pumpServiceValueDao.powerStatis(info);

		Map<String, String> vals = new HashMap<String, String>();
		for (int i = 0; i < stat.size(); i++) {
			vals.put(stat.get(i).getRemarks() + ":00", stat.get(i).getPv());
		}
		return countFre(vals);
	}

	/**
	 * 频率统计
	 * 
	 * @param info
	 * @return
	 */
	public PumpServiceValue[] frequencyStatis(PumpServiceValue info) {
		long curTime = System.currentTimeMillis() - THREE_HOUR;

		info.setBeginTime(sdFormat.format(new Date(curTime)));
		info.setTableName("service_values_" + tableFormat.format(new Date()));
		List<PumpServiceValue> stat = pumpServiceValueDao.frequencyStatis(info);

		Map<String, String> vals = new HashMap<String, String>();
		for (int i = 0; i < stat.size(); i++) {
			vals.put(stat.get(i).getRemarks() + ":00", stat.get(i).getPv());
		}
		return countFre(vals);
	}

	/**
	 * 频率统计
	 * 
	 * @param info
	 * @return
	 */
	public PumpServiceValue powerStatisRel(PumpServiceValue info) {
		long curTime = System.currentTimeMillis() - THREE_HOUR;

		info.setBeginTime(sdFormat.format(new Date(curTime)));
		info.setTableName("service_values_" + tableFormat.format(new Date()));
		info = pumpServiceValueDao.powerStatisRel(info);
		if (null == info) {
			info = new PumpServiceValue();
		}
		return info;
	}

	/**
	 * 频率统计
	 * 
	 * @param info
	 * @return
	 */
	public PumpServiceValue frequencyStatisRel(PumpServiceValue info) {
		long curTime = System.currentTimeMillis() - THREE_HOUR;

		info.setBeginTime(sdFormat.format(new Date(curTime)));
		info.setTableName("service_values_" + tableFormat.format(new Date()));
		info = pumpServiceValueDao.frequencyStatisRel(info);
		if (null == info) {
			info = new PumpServiceValue();
		}
		return info;
	}

	private PumpServiceValue[] countFre(Map<String, String> val) {
		List<String> statVal = detailTime();

		PumpServiceValue[] result = new PumpServiceValue[statVal.size()];
		PumpServiceValue temp;
		Map<String, String> point = new HashMap<String, String>();
		String lastPv = "0";
		for (int i = 0; i < statVal.size(); i++) {
			if (i == 16) {
				System.out.println(i);
			}
			temp = new PumpServiceValue();
			if (StringUtils.isEmpty(val.get(statVal.get(i)))) {
				temp.setRemarks(countTime(statVal.get(i)));
				temp.setPv(lastPv);
			} else {
				temp.setRemarks(countTime(statVal.get(i)));
				temp.setPv(val.get(statVal.get(i)));
				lastPv = temp.getPv();
			}
			result[i] = temp;
		}
		return result;
	}

	private String countTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(time).getTime() + "";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";

	}

	private List<String> detailTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<String> list = new ArrayList<String>();
		long curTime = System.currentTimeMillis() - THREE_HOUR;
		for (int i = 0; i < 60; i++) {
			list.add(sdf.format(new Date(curTime + i * 60000)).substring(0, 16) + ":00");
		}
		return list;

	}

	/**
	 * 查询一小时 频率、功率数据
	 * 
	 * @param info
	 * @return
	 */
	public Map<String, Map<Long, String>> performance(PumpServiceValue info) {
		info.setTableName(com.wapwag.woss.common.utils.StringUtils.getTodayTable());
		List<PumpServiceValue> stat = pumpServiceValueDao.performance(info);
		Map<String, Map<Long, String>> resuMap = new HashMap<String, Map<Long, String>>();
		Map<Long, String> vals;
		String pv = "";
		for (int i = 0; i < stat.size(); i++) {
			if (!StringUtils.isEmpty(stat.get(i).getRatio())) {
				pv = Double.parseDouble(stat.get(i).getPv()) * Double.parseDouble(stat.get(i).getRatio()) + "";
			} else {
				pv = stat.get(i).getPv();
			}
			if (null == resuMap.get(stat.get(i).getTagName())) {
				vals = new HashMap<Long, String>();
				vals.put(stat.get(i).getDateTime().getTime(), pv);
				vals.put(1L, pv);
				resuMap.put(stat.get(i).getTagName(), vals);
			} else {
				resuMap.get(stat.get(i).getTagName()).put(stat.get(i).getDateTime().getTime(), pv);
			}
		}
		return resuMap;
	}

	/**
	 * 实时查询频率、功率数据
	 * 
	 * @param info
	 * @return
	 */
	public Map<String, Map<String, String>> performanceRel(PumpServiceValue info) {
		info.setTableName(com.wapwag.woss.common.utils.StringUtils.getTodayTable());
		List<PumpServiceValue> stat = pumpServiceValueDao.performanceRel(info);
		Map<String, Map<String, String>> resuMap = new HashMap<String, Map<String, String>>();
		Map<String, String> hzs = new HashMap<String, String>();
		Map<String, String> kWs = new HashMap<String, String>();
		String pv;
		for (int i = 0; i < stat.size(); i++) {
			if (!StringUtils.isEmpty(stat.get(i).getRatio())) {
				pv = Double.parseDouble(stat.get(i).getPv()) * Double.parseDouble(stat.get(i).getRatio()) + "";
			} else {
				pv = stat.get(i).getPv();
			}
			if ("Hz".equals(stat.get(i).getRemarks())) {
				hzs.put(stat.get(i).getTagName(), pv);
			}
			if ("kW".equals(stat.get(i).getRemarks())) {
				kWs.put(stat.get(i).getTagName(), pv);
			}
		}
		resuMap.put("Hz", hzs);
		resuMap.put("kW", kWs);
		return resuMap;
	}

	public List<String> pumpNames(String deviceId) {
		return pumpServiceValueDao.pumpNames(deviceId);
	}

	public List<EnergyInfo> energy(QryObject qryObject){
		qryObject.setName("desc");
		List<EnergyInfo> energyMax = pumpServiceValueDao.energyMaxMin(qryObject);
		qryObject.setName("asc");
		List<EnergyInfo> energyMin = pumpServiceValueDao.energyMaxMin(qryObject);
		List<EnergyInfo> energy = pumpServiceValueDao.energy(qryObject);
		List<EnergyInfo> result = new ArrayList<EnergyInfo>();
		Map<String, String> keys = new HashMap<String, String>();
		for (EnergyInfo info : energyMax) {
			keys.put(info.getId(), info.getId());
		}
		for (EnergyInfo info : energyMin) {
			keys.put(info.getId(), info.getId());
		}
		for (int i = energy.size() - 1; i > -1; i--) {
			if (null != keys.get(energy.get(i).getId())) {
				energy.remove(i);
			}
		}

		EnergyInfo info = null;
		for (int i=0;i<energyMax.size();i++) {
			info = energyMax.get(i);
			if (energyMin.size() > i) {
				info.setlEle(energyMin.get(i).getbEle());
				info.setlFlow(energyMin.get(i).getbFlow());
				info.setlPumpName(energyMin.get(i).getbPumpName());
			}
			if (energy.size() > i) {
				info.setoEle(energy.get(i).getbEle());
				info.setoFlow(energy.get(i).getbFlow());
				info.setoPumpName(energy.get(i).getbPumpName());
			}
			result.add(info);
		}

		return result;
	}
	
	public List<EnergyInfo> energyDev(QryObject qryObject){
		qryObject.setName("desc");
		List<EnergyInfo> energyMax = pumpServiceValueDao.energyMaxMinDev(qryObject);
		qryObject.setName("asc");
		List<EnergyInfo> energyMin = pumpServiceValueDao.energyMaxMinDev(qryObject);
		List<EnergyInfo> energy = pumpServiceValueDao.energy(qryObject);
		List<EnergyInfo> result = new ArrayList<EnergyInfo>();
		Map<String, String> keys = new HashMap<String, String>();
		for (EnergyInfo info : energyMax) {
			keys.put(info.getId(), info.getId());
		}
		for (EnergyInfo info : energyMin) {
			keys.put(info.getId(), info.getId());
		}
		for (int i = energy.size() - 1; i > -1; i--) {
			if (null != keys.get(energy.get(i).getId())) {
				energy.remove(i);
			}
		}

		EnergyInfo info = null;
		for (int i=0;i<energyMax.size();i++) {
			info = energyMax.get(i);
			if (energyMin.size() > i) {
				info.setlEle(energyMin.get(i).getbEle());
				info.setlFlow(energyMin.get(i).getbFlow());
				info.setlPumpName(energyMin.get(i).getbPumpName());
			}
			if (energy.size() > i) {
				info.setoEle(energy.get(i).getbEle());
				info.setoFlow(energy.get(i).getbFlow());
				info.setoPumpName(energy.get(i).getbPumpName());
			}
			result.add(info);
		}

		return result;
	}

}
