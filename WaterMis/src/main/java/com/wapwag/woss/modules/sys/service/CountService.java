package com.wapwag.woss.modules.sys.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.common.task.CountValueUtil;
import com.wapwag.woss.common.utils.PropUtils;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.common.utils.TimeUtils;
import com.wapwag.woss.modules.biz.entity.Device;
import com.wapwag.woss.modules.sys.dao.AlarmDao;
import com.wapwag.woss.modules.sys.dao.CountDao;
import com.wapwag.woss.modules.sys.dao.PumpServiceValueDao;
import com.wapwag.woss.modules.sys.entity.Alarm;
import com.wapwag.woss.modules.sys.entity.PumpDeviceRepair;
import com.wapwag.woss.modules.sys.entity.PumpServiceValue;
import com.wapwag.woss.modules.sys.entity.PumpServiceValueNew;
import com.wapwag.woss.modules.sys.entity.ValueCount;

/**
 * 告警日志Service
 */
@Service
@Transactional(readOnly = false)
public class CountService extends CrudService<AlarmDao, Alarm> {

	@Autowired
	private CountDao countDao;
	public static final String PATH = PropUtils.getPropertiesString("application.properties", "configuration");
	@Autowired
	private PumpServiceValueDao pumpServiceValueDao;

	/** 休息 */
	private static final String XX = "\u4F11\u606F";

	/** 故障 */
	private static final String GZ = "\u6545\u969C";

	/** 变频运行 */
	private static final String BBPL = "\u53D8\u9891\u8FD0\u884C";

	/** 工频运行 */
	private static final String GPPL = "\u5DE5\u9891\u8FD0\u884C";
	/** 运行 */
	private static final String YX = "\u8FD0\u884C";
	private static final String YXSJ = "\u8FD0\u884C\u65F6\u95F4";

	private static final String KX = "\u7A7A\u95F2\u4E2D";
	private static final SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
	private static final String STR_0 = "0";
	
	DecimalFormat FNUM_2 = new DecimalFormat("##0.00");
	
	DecimalFormat FNUM_0 = new DecimalFormat("##0");

	public List<ValueCount> countValueByName(String qryName, String areaId,String userId) {
		List<ValueCount> list = new ArrayList<ValueCount>();
		List<ValueCount> sysList = CountValueUtil.getCountByTag(qryName);

		List<String> devIds = countDao.getDevIdsByArea(areaId,userId);
		Map<String, String> devMap = new HashMap<>();
		for (int i = 0; i < devIds.size(); i++) {
			devMap.put(devIds.get(i), devIds.get(i));
		}

		for (int i = 0; i < sysList.size(); i++) {
			if (null != devMap.get(sysList.get(i).getDeviceId())) {
				list.add(sysList.get(i));
			}
		}
		return list;
	}

	/**
	 * 统计设备数据
	 * 
	 * @return
	 */
	public Map<String, String> statistcDevice(String userId) {
		Map<String, String> values = new HashMap<String, String>();
		values.put("deviceSum", TimeUtils.numberWithDelimiter(Long.parseLong(countDao.statisticDevivceSum(userId))));
		values.put("pumpSum", TimeUtils.numberWithDelimiter(Long.parseLong(countDao.statisticPumpSum(userId))));
		values.put("deviceRunTime", TimeUtils.countDats(countDao.statisticDevivceRunTime(userId)));

		// 统计耗电量
		values.put("deviceElement",countDao.getAllSummaryByRole("kWh",userId));

		values.put("deviceFlow",countDao.getAllSummaryByRole("m3",userId));
		
		values.put("systemRunTime", countDao.statisticAlaemDevices(userId));
		return values;
	}

	/**
	 * 统计区域下面设备的个数
	 * 
	 * @return
	 */
	public List<Device> statisticDevivceArea(String userId) {
		return countDao.statisticDevivceArea(userId);
	}

	/**
	 * 统计区域下面设备的个数
	 * 
	 * @return
	 */
	public List<Device> countDeviceAreaChina(String userId) {
		return countDao.countDeviceAreaChinaById(userId);
	}

	/**
	 * 统计不同设备类型下的设备数
	 * 
	 * @return
	 */
	public List<Device> statisticDevivceType(String userId) {
		String nullType = countDao.statisticDevivceTypeNull(userId);
		List<Device> list = countDao.statisticDevivceType(userId);
		if (!"0".equals(nullType)) {
			Device device = new Device();
			device.setMemo(nullType);
			device.setName("\u65E0\u8BBE\u5907\u7C7B\u578B");
			list.add(device);
		}
		return list;
	}

	/**
	 * 维保
	 * 
	 * @return
	 */
	public List<Device> statisticRepaicType() {
		return countDao.statisticRepaicType();
	}

	/**
	 * 告警类型
	 * 
	 * @return
	 */
	public List<Device> statisticAlarmType(String userId) {
		return countDao.statisticAlarmType(userId);
	}
	
	/**
	 * 告警类型
	 * 
	 * @return
	 */
	public List<Device> statRealAlarm(String deviceId) {
		return countDao.statRealAlarm(deviceId);
	}
	
	public List<Device> pumpRatedPower(String deviceId) {
		return countDao.pumpRatedPower(deviceId);
	}
	
	/**
	 * 告警类型
	 * 
	 * @return
	 */
	public List<Device> statRealAlarmType(String deviceId) {
		return countDao.statRealAlarmType(deviceId);
	}

	/**
	 * 每一小时能耗
	 * 
	 * @return
	 */
	public List<Device> statisticHourEle(String dateTime) {
		QryObject info = new QryObject();
		if (StringUtils.isEmpty(dateTime)) {
			String endTime = getEndTime();
			info.setEndTimeNemol(endTime);
			info.setBeginTime(endTime.split(" ")[0] + " 00");
		} else {
			info.setEndTimeNemol(addOneDay(dateTime) + " 00");
			info.setBeginTime(dateTime + " 00:00:01");
		}
		return countDao.statisticHourEle(info);
	}

	/**
	 * 每天小时能耗
	 * 
	 * @return
	 */
	public List<Device> statisticDayEle(String dateTime) {
		QryObject info = new QryObject();
		info.setEndTimeNemol(addOnemouth(dateTime) + "-01 00");
		info.setBeginTime(dateTime + "-01 00");
		List<Device> list = countDao.statisticDayEle(info);
		String lastMouthPVS = countDao.lastMouthPV(info);
		int lastMouthPV = 0;
		if (!StringUtils.isEmp(lastMouthPVS)) {
			lastMouthPV = Integer.parseInt(lastMouthPVS);
		}
		for (int i = 0; i < list.size(); i++) {
			if (0 != i) {
				if (list.get(i).getPv() > list.get(i - 1).getPv()) {
					list.get(i).setMemo((list.get(i).getPv() - list.get(i - 1).getPv()) + "");
				} else {
					list.get(i).setMemo("");
				}
			} else {
				if (lastMouthPV < list.get(i).getPv()) {
					list.get(i).setMemo((list.get(i).getPv() - lastMouthPV) + "");
				} else {
					list.get(i).setMemo(list.get(i).getPv() + "");
				}
			}
		}
		return list;
	}

	/**
	 * 每月小时能耗
	 * 
	 * @return
	 */
	public List<Device> statisticMouthEle(String dateTime) {
		QryObject info = new QryObject();
		info.setEndTimeNemol(addOneYear(dateTime) + "-01-01 00");
		info.setBeginTime(dateTime + "-01-01 00");
		List<Device> list = countDao.statisticMouthEle(info);
		String lastMouthPVS = countDao.lastMouthPV(info);
		int lastMouthPV = 0;
		if (!StringUtils.isEmp(lastMouthPVS)) {
			lastMouthPV = Integer.parseInt(lastMouthPVS);
		}
		for (int i = 0; i < list.size(); i++) {
			if (0 != i) {
				if (list.get(i).getPv() > list.get(i - 1).getPv()) {
					list.get(i).setMemo((list.get(i).getPv() - list.get(i - 1).getPv()) + "");
				} else {
					list.get(i).setMemo("");
				}
			} else {
				if (lastMouthPV < list.get(i).getPv()) {
					list.get(i).setMemo((list.get(i).getPv() - lastMouthPV) + "");
				} else {
					list.get(i).setMemo(list.get(i).getPv() + "");
				}
			}
		}
		return list;
	}

	/**
	 * 每一小时能耗
	 * 
	 * @return
	 */
	public List<Device> statisticHourAla(String dateTime,String userId) {
		QryObject info = new QryObject();
		if (StringUtils.isEmpty(dateTime)) {
			String endTime = getEndTime();
			info.setEndTimeNemol(endTime);
			info.setBeginTime(endTime.split(" ")[0] + " 00");
		} else {
			info.setEndTimeNemol(dateTime + " 23:59:59");
			info.setBeginTime(dateTime + " 00:00:01");
		}
		info.setUserId(userId);
		return countDao.statisticHourAla(info);
	}

	/**
	 * public void gongll(){ QryObject info = new QryObject();
	 * info.setBeginTime("2016-10-22 10"); info.setEndTime("2016-10-22 11");
	 * info.setTableName("service_values_20161022");
	 * countDao.statisticElectricityTime(info); countDao.getCountValues();
	 * countDao.statisticAlarmType(); countDao.statisticRepaicType();
	 * savealue(); }
	 * 
	 * @Transactional(readOnly = false) public void savealue() { ValueCount
	 *                         value = new ValueCount();
	 *                         value.setDateTime("123123"); value.setPv("12");
	 *                         value.setTotal("1111");
	 *                         countDao.saveCountValue(value); }
	 */
	private String getEndTime() {
		Date nowTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		String nowStr = sdf.format(nowTime);
		return nowStr;
	}

	public String statisticElectricity() {
		QryObject info = new QryObject();
		info.setBeginTime("2016-11-23 13:00:00");
		info.setEndTimeNemol("2016-11-23 14:00:00");
		info.setTableName("service_values_20161123");
		return countDao.statisticElectricityTime(info);
	}

	public List<Alarm> oneAlarmRecords(QryObject info) {
		return countDao.oneAlarmRecords(info);
	}

	public List<PumpDeviceRepair> oneRepairRecords(QryObject info) {
		return countDao.oneRepairRecords(info);
	}

	public List<String> pumpNames(PumpServiceValue info) {
		List<String> names = countDao.pumpNames(info);
		for (int i = 0; i < names.size(); i++) {

		}
		return names;
	}

	public List<String[][]> pumpRunStatis(PumpServiceValue value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		value.setBeginTime(sdf.format(new Date()) + "-01 00:00:00");
		// 获取和组装泵名称
		List<String> names = countDao.pumpNames(value);
		List<String[][]> result = new ArrayList<String[][]>();
		List<String> viewNames = new ArrayList<String>();
		Map<String, String[]> pumpNames = new HashMap<String, String[]>();
		for (int i = 0; i < names.size(); i++) {
			pumpNames.put(names.get(i), new String[] { names.get(i), "0" });
			viewNames.add(names.get(i));
		}
		// 获取泵房的运行状态
		String[][] runStatis = new String[pumpNames.size()][2];
		value.setTagName("service_values_" + sdfNow.format(new Date()));
		List<PumpServiceValueNew> pumpRunStatis = pumpServiceValueDao.pumpRunStatis(value);
		String tempTag = "0";
		for (int j = 0; j < viewNames.size(); j++) {
			tempTag = "0";
			for (int i = 0; i < pumpRunStatis.size(); i++) {
				if (!StringUtils.isEmp(pumpRunStatis.get(i).getTagName())) {
					if (pumpRunStatis.get(i).getTagName().indexOf(viewNames.get(j)) > -1) {
						if (pumpRunStatis.get(i).getTagName().indexOf(BBPL) > -1) {
							tempTag = "2";
							runStatis[j][0] = viewNames.get(j);
							if (pumpRunStatis.get(i).getMin() > 0) {
								runStatis[j][1] = "3";
								break;
							}
							continue;
						}
						if (pumpRunStatis.get(i).getTagName().indexOf(GPPL) > -1) {
							tempTag = "2";
							runStatis[j][0] = viewNames.get(j);
							if (pumpRunStatis.get(i).getMin() > 0) {
								runStatis[j][1] = "5";
								break;
							}
							continue;
						}
						if (pumpRunStatis.get(i).getTagName().indexOf(YXSJ) > -1) {
							continue;
						}
						if (pumpRunStatis.get(i).getTagName().indexOf(YX) > -1) {
							runStatis[j][0] = viewNames.get(j);
							if (pumpRunStatis.get(i).getMin() > 0) {
								runStatis[j][1] = "1";
							} else {
								runStatis[j][1] = "2";
								break;
							}
						}
						if (pumpRunStatis.get(i).getTagName().indexOf(XX) > -1) {
							runStatis[j][0] = viewNames.get(j);
							if (pumpRunStatis.get(i).getMin() > 0) {
								runStatis[j][1] = "2";
								break;
							} else {
								runStatis[j][1] = "1";
							}
						}
						if (pumpRunStatis.get(i).getTagName().indexOf(GZ) > -1) {
							tempTag = "2";
							runStatis[j][0] = viewNames.get(j);
							if (pumpRunStatis.get(i).getMin() > 0) {
								runStatis[j][1] = "4";
							}
						}
					}
				}
			}
			if (StringUtils.isEmp(runStatis[j][1])) {
				runStatis[j][0] = viewNames.get(j);
				runStatis[j][1] = tempTag;
			}
		}
		result.add(runStatis);
		// 运行时长对比
		String[][] runTime = new String[pumpNames.size()][2];

		// 运行次数对比
		String[][] runTimes = new String[pumpNames.size()][2];

		List<Device> timeList = countDao.pumpRunTimeStatis(value);
		for (int i = 0; i < viewNames.size(); i++) {
			for (int j = 0; j < timeList.size(); j++) {
				if (null != timeList.get(j) && !StringUtils.isEmp(timeList.get(j).getName())) {
					if (timeList.get(j).getName().indexOf(viewNames.get(i)) > -1) {
						runTime[i][0] = viewNames.get(i);
						runTime[i][1] = timeList.get(j).getMemo();

						runTimes[i][0] = viewNames.get(i);
						runTimes[i][1] = timeList.get(j).getRemarks();

						break;
					}
				}
			}
		}
		result.add(runTime);
		result.add(runTimes);

		// 额定功率对比
		String[][] rated = new String[pumpNames.size()][2];
		timeList = countDao.pumpRatedStatis(value);
		for (int i = 0; i < viewNames.size(); i++) {
			for (int j = 0; j < timeList.size(); j++) {
				if (null != timeList.get(j) && !StringUtils.isEmp(timeList.get(j).getName())) {
					if (viewNames.get(i).indexOf(timeList.get(j).getName()) > -1) {
						rated[i][0] = viewNames.get(i);
						rated[i][1] = StringUtils.getNum(timeList.get(j).getMemo());
						break;
					}
				}
			}
		}
		result.add(rated);
		return result;
	}

	private String createRate(String memo) {
		StringBuffer sb = new StringBuffer("0");
		char[] memos = memo.toCharArray();

		for (int i = 0; i < memos.length; i++) {
			if (47 < memos[i] && memos[i] < 58) {
				sb.append(memos[i]);
			}
		}
		return sb.toString();
	}

	private String addOneDay(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date time = sdf.parse(dateTime);
			time = new Date(time.getTime() + 24 * 60 * 60 * 1000);
			return sdf.format(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateTime;
	}

	private String addOnemouth(String dateTime) {
		int year = Integer.parseInt(dateTime.split("-")[0]);
		int mouth = Integer.parseInt(dateTime.split("-")[1]);
		if (mouth == 12) {
			year = year + 1;
			return year + "-" + "01";
		}
		mouth = mouth + 1;
		if (mouth < 10) {
			return year + "-0" + mouth;
		}
		return year + "-" + mouth;
	}

	private String addOneYear(String dateTime) {
		int year = Integer.parseInt(dateTime);
		year = year + 1;
		return year + "";
	}

	public List<Device> countLocation() {
		return countDao.countLocation();
	}

	public List<Device> staticEle(QryObject info) {
		return countDao.staticEle(info);
	}

	/**
	 * 首页汇总信息
	 * 
	 * @return
	 */
	public Map<String, String> summaryStatIndex(String userId) {

		Map<String, String> values = new HashMap<String, String>();
		// 统计设备数量
		values.put("deviceSum", TimeUtils.numberWithDelimiter(Long.parseLong(countDao.statisticDevivceSum(userId))));

		// 统计泵房数量
		values.put("pumpSum", TimeUtils.numberWithDelimiter(Long.parseLong(countDao.statisticPumpSum(userId))));

		// 统计设备最大运行时间
		values.put("deviceRunTime", TimeUtils.countDats(countDao.statisticDevivceRunTime(userId)));

		// 系统运行时间
		values.put("systemRunTime", TimeUtils.countDats(countDao.statisticSysTime()));
		
		
		// 统计耗电量
		values.put("deviceElement",countDao.getAllSummaryByRole("kWh",userId));

		values.put("flowNum",countDao.getAllSummaryByRole("m3",userId));

		// 设备告警数量
		//values.put("deviceAlarmNum", countDao.statisticAlaemDevices(userId));

		// 总装机功率统计
		values.put("statPower", countDao.statPower(userId));

		return values;
	}

	/**
	 * 首页汇总信息
	 * 
	 * @return
	 */
	public Map<String, String> summaryStat(String device) {

		Map<String, String> values = new HashMap<String, String>();
		// 图片地址
		values.put("devicePicUrl", (countDao.devicePicUrl(device)==null)?null:PATH+countDao.devicePicUrl(device));
		
		values.put("v3", countDao.getDevSummaryByDev("kWh",device));
		values.put("v4", countDao.getDevSummaryByDev("m3",device));
		
		values.put("v1", countDao.getConFPower(device));
		values.put("v2", countDao.getSBEDGSYC(device));
		
		values.put("devRunTime", TimeUtils.countDats(countDao.devRunTime(device)));
		
		String videoCode = countDao.videoCode(device);
		if (StringUtils.isEmp(videoCode)) {
			videoCode = "";
		}
		values.put("videoCode", videoCode);
		return values;
	}

	/*
	 * 区域维保项目对比
	 * 
	 * @param user
	 * 
	 * @return
	 */
	public List<Device> areaProjectRepairStat(String userId) {
		return countDao.areaProjectRepairStat(userId);
	}
	
	public List<Device> devEleCom(String devId) {
		return countDao.devEleCom(devId);
	}
	
	public List<Device> traSuppPumpStat(String userId) {
		return countDao.traSuppPumpStat(userId);
	}

	public List<Device> countAlarmTimesStat(String userId,String type) {
		return countDao.countAlarmTimesStat(userId,type);
	}

	public List<Device> countMaintenanceTrend(String userId) {
		return countDao.countMaintenanceTrend(userId);
	}
	
	public List<Device> countMaintenanceTrendDev(String devId) {
		return countDao.countMaintenanceTrendDev(devId);
	}

	public Map<String, String> statisticEleByLocation(String userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(new Date());
		QryObject info = new QryObject();
        info.setUserId(userId);
		info.setBeginTime(nowTime.substring(0, 7) + "-01 00");
		List<Device> values = countDao.statisticEleByLocation(info);
		Map<String, Double> maps = new HashMap<String, Double>();
		for (int i = 0; i < values.size(); i++) {
			maps.put(values.get(i).getName(), Double.parseDouble(values.get(i).getMemo()));
		}
		List<PumpServiceValue> sharing = null;
		if (null == sharing || sharing.size() == 0) {
			sharing = new ArrayList<PumpServiceValue>();
			PumpServiceValue p1 = new PumpServiceValue();
			p1.setMin(0);
			p1.setMax(5);
			p1.setTagName("低峰");
			PumpServiceValue p2 = new PumpServiceValue();
			p2.setMin(20);
			p2.setMax(23);
			p2.setTagName("低峰");
			PumpServiceValue p3 = new PumpServiceValue();
			p3.setMin(6);
			p3.setMax(19);
			p3.setTagName("高峰");
			sharing.add(p1);
			sharing.add(p2);
			sharing.add(p3);
		}

		Map<String, Double> result = new HashMap<String, Double>();

		double countVal = 0;
		for (int i = 0; i < sharing.size(); i++) {
			countVal = 0;
			for (int j = sharing.get(i).getMin(); j <= sharing.get(i).getMax(); j++) {
				if (null == result.get(sharing.get(i).getTagName())) {
					result.put(sharing.get(i).getTagName(), 0D);
				}
				if (null != maps.get(j < 10 ? ("0" + j) : (j + ""))) {
					countVal = result.get(sharing.get(i).getTagName()) + maps.get(j < 10 ? ("0" + j) : (j + ""));
					result.put(sharing.get(i).getTagName(), countVal);
				}
			}
		}

		Iterator<String> iterator = result.keySet().iterator();
		Map<String, String> resVal = new HashMap<String, String>();
		String key;
		while (iterator.hasNext()) {
			key = iterator.next();
			resVal.put(key, FNUM_2.format(result.get(key)));
			
		}
		return resVal;
	}

	public Map<String, String> passStat(String deviceId) {
		List<Device> devices = countDao.passStat(deviceId, StringUtils.getTodayTable());
		Map<String, String> result = new HashMap<String, String>();
		result.put("in", "0");
		result.put("out", "0");
		result.put("git", "0");

		for (Device device : devices) {
			if (device.getName().indexOf("OutletPressure") > -1
					&& device.getName().indexOf("BoosterPumpOutletPressure") == -1) {
				result.put("out", device.getMemo());
			} else if (device.getName().indexOf("InletPressure") > -1) {
				result.put("in", device.getMemo());
			} else if (device.getName().indexOf("GivenPressure") > -1) {
				result.put("git", device.getMemo());
			}
		}

		return result;
	}

	public Map<String, String[]> staticPressMin(String deviceId) {

		List<String> outPre = countDao.outPre(deviceId, StringUtils.getTodayTable());
		List<String> gitPre = countDao.gitPre(deviceId, StringUtils.getTodayTable());
		List<String> inPre = countDao.inPre(deviceId, StringUtils.getTodayTable());

		if (null == inPre || inPre.size() == 0) {
			inPre = new ArrayList<String>();
			inPre.add("0");
		}
		if (null == outPre || outPre.size() == 0) {
			outPre = new ArrayList<String>();
			outPre.add("0");
		}
		if (null == gitPre || gitPre.size() == 0) {
			gitPre = new ArrayList<String>();
			gitPre.add("0");
		}

		String[] inArr = new String[20];
		String[] outArr = new String[20];
		String[] gitArr = new String[20];

		for (int i = inArr.length - 1; i >= 0; i--) {
			if (inPre.size() > i) {
				inArr[i] = inPre.get(i);
			} else {
				inArr[i] = inPre.get(inPre.size() - 1);
			}
		}
		for (int i = outArr.length - 1; i >= 0; i--) {
			if (outPre.size() > i) {
				outArr[i] = outPre.get(i);
			} else {
				outArr[i] = outPre.get(outPre.size() - 1);
			}
		}
		for (int i = gitArr.length - 1; i >= 0; i--) {
			if (gitPre.size() > i) {
				gitArr[i] = gitPre.get(i);
			} else {
				gitArr[i] = gitPre.get(gitPre.size() - 1);
			}
		}

		Map<String, String[]> results = new HashMap<String, String[]>();
		results.put("in", inArr);
		results.put("out", outArr);
		results.put("git", gitArr);
		return results;
	}

	public String getDoorUUId(String deviceId) {
		return countDao.getDoorUUId(deviceId);
	}

	public String getDoorControlUrl() {
		return countDao.getDoorControlUrl();
	}

	public Map<String, Double> statisticEleByDevice(String deviceId) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(new Date());
		QryObject info = new QryObject();

		info.setBeginTime(nowTime.substring(0, 7) + "-01 00");
		info.setDeviceId(deviceId);
		List<Device> values = countDao.statisticEleByDeviceId(info);
		Map<String, Double> maps = new HashMap<String, Double>();
		for (int i = 0; i < values.size(); i++) {
			maps.put(values.get(i).getName(), Double.parseDouble(values.get(i).getMemo()));
		}
		List<PumpServiceValue> sharing = null;
		if (null == sharing || sharing.size() == 0) {
			sharing = new ArrayList<PumpServiceValue>();
			PumpServiceValue p1 = new PumpServiceValue();
			p1.setMin(0);
			p1.setMax(5);
			p1.setTagName("低峰");
			PumpServiceValue p2 = new PumpServiceValue();
			p2.setMin(20);
			p2.setMax(23);
			p2.setTagName("低峰");
			PumpServiceValue p3 = new PumpServiceValue();
			p3.setMin(6);
			p3.setMax(19);
			p3.setTagName("高峰");
			sharing.add(p1);
			sharing.add(p2);
			sharing.add(p3);
		}

		Map<String, Double> result = new HashMap<String, Double>();

		double countVal = 0;
		for (int i = 0; i < sharing.size(); i++) {
			countVal = 0;
			for (int j = sharing.get(i).getMin(); j <= sharing.get(i).getMax(); j++) {
				if (null == result.get(sharing.get(i).getTagName())) {
					result.put(sharing.get(i).getTagName(), 0D);
				}
				if (null != maps.get(j < 10 ? ("0" + j) : (j + ""))) {
					countVal = result.get(sharing.get(i).getTagName()) + maps.get(j < 10 ? ("0" + j) : (j + ""));
					result.put(sharing.get(i).getTagName(), countVal);
				}
			}
		}
		if (null != result.get("高峰")) {
			BigDecimal bg = new BigDecimal(result.get("高峰"));
			result.put("高峰", bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		if (null != result.get("低峰")) {
			BigDecimal bg = new BigDecimal(result.get("低峰"));
			result.put("低峰", bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		return result;
	}

	public List<String[]> deviceStatTheme(PumpServiceValue value) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		value.setBeginTime(sdf.format(new Date()) + "-01 00:00:00");
		// 获取和组装泵名称
		List<String> names = countDao.pumpNames(value);
		value.setTagName("service_values_" + sdfNow.format(new Date()));

		List<PumpServiceValueNew> pumpRunStatis = pumpServiceValueDao.pumpRunStatis(value);
		List<String[]> result = new ArrayList<String[]>();

		for (String name : names) {
			String[] val = new String[3];
			val[0] = name;
			val[1] = "0";
			val[2] = "-";
			for (PumpServiceValueNew info : pumpRunStatis) {
				if (info.getTagName().indexOf(name) > -1) {
					if (info.getTagName().indexOf("变频运行") > -1 && info.getMin() > 0) {
						val[1] = "1";
					}
					if (info.getTagName().indexOf("工频运行") > -1 && info.getMin() > 0) {
						val[1] = "1";
					}
					if (info.getTagName().indexOf("变频频率") > -1 && info.getMin() > 0) {
						val[1] = "1";
						val[2] = info.getMin() + "(Hz)";
					}
					if (info.getTagName().indexOf("工频频率") > -1 && info.getMin() > 0) {
						val[1] = "1";
						val[2] = info.getMin() + "(Hz)";
					}
					if (info.getTagName().indexOf("故障") > -1 && info.getMin() > 0) {
						val[1] = "2";
					}
					if (info.getTagName().indexOf("报警") > -1 && info.getMin() > 0) {
						val[1] = "2";
					}
				}
			}
			result.add(val);
		}

		for (PumpServiceValueNew info : pumpRunStatis) {
			if (info.getTagName().indexOf("浊度") > -1) {
				String[] val = new String[3];
				val[0] = "浊度";
				val[1] = "-";
				val[2] = info.getMin() + "(NTU)";
				result.add(val);
			}
			if (info.getTagName().indexOf("余氯") > -1) {
				String[] val = new String[3];
				val[0] = "余氯";
				val[1] = "-";
				val[2] = info.getMin() + "(mg/L)";
				result.add(val);
			}
			if (info.getTagName().indexOf("酸碱度") > -1) {
				String[] val = new String[3];
				val[0] = "酸碱度";
				val[1] = "-";
				val[2] = info.getMin() + "(pH)";
				result.add(val);
			}
			if (info.getTagName().indexOf("环境温度") > -1) {
				String[] val = new String[3];
				val[0] = "环境温度";
				val[1] = "-";
				val[2] = info.getMin() + "(℃)";
				result.add(val);
			}
			if (info.getTagName().indexOf("湿度") > -1) {
				String[] val = new String[3];
				val[0] = "湿度";
				val[1] = "-";
				val[2] = info.getMin() + "(RH%)";
				result.add(val);
			}
		}
		return result;
	}

	public String[][] runStatDevice(PumpServiceValue value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		value.setBeginTime(sdf.format(new Date()) + "-01 00:00:00");
		// 获取和组装泵名称
		List<String> names = countDao.pumpNames(value);
		value.setTagName("service_values_" + sdfNow.format(new Date()));
		
		// 运行时长对比
		String[][] runTime = new String[names.size()][2];

		List<Device> timeList = countDao.pumpRunTimeStatis(value);
		for (int i = 0; i < names.size(); i++) {
			for (int j = 0; j < timeList.size(); j++) {
				if (null != timeList.get(j) && !StringUtils.isEmp(timeList.get(j).getName())) {
					if (timeList.get(j).getName().indexOf(names.get(i)) > -1) {
						runTime[i][0] = names.get(i);
						runTime[i][1] = timeList.get(j).getMemo();
						break;
					}
				}
			}
		}
		return runTime;
	}
	public String  controalDoor(String deviceId,String operType){
		String doorUUid = getDoorUUId(deviceId);
		if (StringUtils.isEmp(doorUUid)) {
			return "1";
		}
		String downUrl="";
		//http://36.41.173.156:8087/control/doorOper/
		//downUrl="http://106.14.184.87/control/doorOper/";
		if (StringUtils.isEmp(downUrl)) {
			downUrl = getDoorControlUrl();
		}
		HttpClient httpClient = new DefaultHttpClient();
		String url = downUrl + doorUUid + "/"+operType;
		// get method
		HttpGet httpGet = new HttpGet(url);

		//response
		HttpResponse response = null;
		String msg = "操作失败，请稍后再试！";
		try{
			response = httpClient.execute(httpGet);
			if ("0".equals(EntityUtils.toString(response.getEntity()))) {
				return "0";
			}
			msg = EntityUtils.toString(response.getEntity());
		}catch (Exception e) {}

		return msg;
	}
}
