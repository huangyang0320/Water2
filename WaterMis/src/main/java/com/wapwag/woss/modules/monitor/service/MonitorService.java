package com.wapwag.woss.modules.monitor.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.wapwag.woss.common.task.PumpDevStatusTask;
import com.wapwag.woss.common.utils.DateUtils;
import com.wapwag.woss.common.utils.PropUtils;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.biz.dao.PumpHouseDao;
import com.wapwag.woss.modules.biz.dao.ServicesDao;
import com.wapwag.woss.modules.biz.entity.*;
import com.wapwag.woss.modules.home.dao.DeviceDao;
import com.wapwag.woss.modules.home.dao.VideoDao;
import com.wapwag.woss.modules.home.entity.RunTimeDTO;
import com.wapwag.woss.modules.home.entity.RunTimeVO;
import com.wapwag.woss.modules.home.entity.VideoInfo;
import com.wapwag.woss.modules.home.service.AlarmStatService;
import com.wapwag.woss.modules.home.service.PumpRunTimeService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.beans.BeanUtils.copyProperties;
//import com.wapwag.woss.modules.home.dao.PumpHouseDao;

/**
 * MonitorService
 * Created by Administrator on 2016/8/2.
 */
@Service
@Transactional
public class MonitorService {

    private final DeviceDao deviceDao;
    private final ServicesDao servicesDao;

	@Autowired
	private AlarmStatService alarmService;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String PATH = PropUtils.getPropertiesString("application.properties", "configuration");
    @Autowired
    public MonitorService(DeviceDao deviceDao,ServicesDao servicesDao) {
        this.deviceDao = deviceDao;
        this.servicesDao = servicesDao;
    }
    
    @Autowired
    private PumpHouseDao pumpHouseDao;
    
    @Autowired
    private VideoDao videoDao;

    @Autowired
	PumpRunTimeService pumpRunTimeService;

    public Map<String, Object> queryDeviceStatistic(String deviceId, String date, String dimen) {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("realTimeData", deviceDao.findDeviceRealTimeData(deviceId, dimen,StringUtils.getTodayTable()));
        return resultMap;
    }
    
    public List<Device> mapRealDetail(String pumpHouseId){
    	return deviceDao.mapRealDetail(pumpHouseId,StringUtils.getTodayTable());
    }

//    @Cacheable(value = "deviceDataCache")
    public List<Map<String, String>> queryDeviceHistory(String deviceId, String index, String dimen,
                                                        String startDate, String endDate) {
        return deviceDao.findDeviceHistoryData(deviceId, index, dimen, startDate, endDate);
    }

//    @Cacheable(value = "deviceDataCache")
    public List<Map<String, String>> queryDeviceConsume(String deviceIndexs, String dimen,
                                                  String startDate, String endDate) {
        return deviceDao.findDeviceConsumeSpecial(deviceIndexs, dimen, startDate, endDate);
    }

//    @Cacheable(value = "deviceDataCache")
    public List<Map<String, String>> queryDiffDeviceConsume(String deviceIndexs, String dimen, String startDate,
                                                            String endDate) {
        return deviceDao.findDiffDeviceConsumeSpecial(deviceIndexs, dimen, startDate, endDate);
    }
    
    public List<Map<String, Object>> getLatestDeviceData(String deviceId, String serviceId) {
        DateTime now = DateTime.now();
        String startDate = now.plusHours(-1).toString(DATE_FORMAT);
        String endDate = now.toString(DATE_FORMAT);
        return deviceDao.getLatestDeviceData(startDate, endDate, deviceId, serviceId,StringUtils.getTodayTable());
    }

    public List<Map<String, Object>> getServiceData(String deviceId, String serviceIds) {
        DateTime now = DateTime.now();
        String startDate = now.plusHours(-1).toString(DATE_FORMAT);
        String endDate = now.toString(DATE_FORMAT);
        return deviceDao.getServiceData(startDate, endDate, deviceId, serviceIds,StringUtils.getTodayTable());
    }

    public List<Map<String, Object>> getServiceInfo(@PathVariable String deviceId) {
        return deviceDao.getServiceInfo(deviceId);
    }

	public List<Device> mapOnlineStat(String deviceId) {
		return deviceDao.mapOnlineStat(deviceId,StringUtils.getTodayTable());
	}
	
	/*public String pumpOnlineStat(List<String> pumpId){
		List<DevicePumpHoseDto> deviceByPumpHouse = deviceDao.findDeviceByPumpHoseId(pumpId);
		List<String> deviceId =  deviceDao.pumpOnlineStat(deviceByPumpHouse,StringUtils.getTodayTable());
		Map<String,String> devicePumpHouse=deviceByPumpHouse.stream().collect(
				Collectors.toMap(DevicePumpHoseDto::getDeviceId,DevicePumpHoseDto::getPumpHoseId));
		Set set = new HashSet();
		for (String device :deviceId) {
			set.add(devicePumpHouse.get(device));
		}
		String pumpHoiseList =JSON.toJSONString(set);
		return pumpHoiseList;
	}*/

	public String pumpOnlineStat(List<String> pumpId){
		/*
		List<String> deviceId =  deviceDao.findDevice(StringUtils.getTodayTable());
		String pumpHouseList=null;
		if(deviceId!=null && deviceId.size()>0){
			List<String> deviceByPumpHouse = deviceDao.findPump(deviceId);
			pumpId.retainAll(deviceByPumpHouse);
			pumpHouseList=JSON.toJSONString(pumpId);
		}
		return pumpHouseList;
		*/
		
		Map<String, String> pumpStatus = PumpDevStatusTask.ALL_PUMP_STATS();
		List<String> result = new ArrayList<>();
		for(String pump : pumpId){
			if (null != pumpStatus.get(pump)) {
				result.add(pump);
			}
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 根据泵房ID返回相关信息
	 * @return
	 */
    public PumpHouseInfo findPumpHouseInfoById(String pumpHouseId) {
     	PumpHouseInfo pumpHouseInfo = new PumpHouseInfo();
    	//泵房信息
    	PumpHouse pumpHouse = pumpHouseDao.get(pumpHouseId);
    	if(pumpHouse==null){
    		pumpHouse = new PumpHouse();
    	}
    	pumpHouseInfo.setAddress(pumpHouse.getAddress());
    	if(StringUtils.isEmp(pumpHouse.getAddress())){
        	pumpHouseInfo.setAddress("");
    	}
    	pumpHouseInfo.setAllPicUrl(pumpHouse.getAllPicUrl());
    	if(StringUtils.isEmp(pumpHouse.getAllPicUrl())){
        	pumpHouseInfo.setAllPicUrl("");
    	}else {
			pumpHouseInfo.setAllPicUrl(PATH+pumpHouse.getAllPicUrl());
		}
    	pumpHouseInfo.setLati(pumpHouse.getLati());
    	pumpHouseInfo.setLongi(pumpHouse.getLongi());
    	pumpHouseInfo.setPumpHouseId(pumpHouse.getId());
    	pumpHouseInfo.setPumpHouseName(pumpHouse.getName());
    	//yxn
		pumpHouseInfo.setDeviceManufacturers(pumpHouse.getDeviceManufacturers());
		pumpHouseInfo.setDeviceManufacturersInformation(pumpHouse.getDeviceManufacturersInformation());
		pumpHouseInfo.setSelfControlManufacturers(pumpHouse.getSelfControlManufacturers());
		pumpHouseInfo.setSelfControlManufacturersInformation(pumpHouse.getSelfControlManufacturersInformation());
		pumpHouseInfo.setConstructionSide(pumpHouse.getConstructionSide());
		pumpHouseInfo.setConstructionSideInformation(pumpHouse.getConstructionSideInformation());
		pumpHouseInfo.setConstruction(pumpHouse.getConstruction());
		pumpHouseInfo.setConstructionInformation(pumpHouse.getConstructionInformation());
		pumpHouseInfo.setProperty(pumpHouse.getProperty());
		pumpHouseInfo.setPropertyInformation(pumpHouse.getPropertyInformation());
		pumpHouseInfo.setHandoverTime(pumpHouse.getHandoverTime());


    	//视频
//    	List<String> monitorIpList = new ArrayList<String>();
    	List<VideoInfo> videoInfoList = videoDao.findVideoListByPumpId(pumpHouseId);
//    	System.out.println("videoInfoList:"+videoInfoList);
//    	for (VideoInfo videoInfo : videoInfoList) {
//    		monitorIpList.add(videoInfo.getMonitorIp());
//		}
    	pumpHouseInfo.setVideoInfoList(videoInfoList);
//    	if(monitorIpList.size()==0){
//    		pumpHouseInfo.setMonitorIpList(new ArrayList<String>());
//    	}
    	//设备
		List<DeviceInfo> deviceInfoList = deviceDao.findDeviceListByPumpHouse(pumpHouseId);
		pumpHouseInfo.setDeviceInfolist(deviceInfoList);
		pumpHouseInfo.setPumpHouseStatus("0");
		
		/**
		if(!deviceInfoList.isEmpty()){
			for (DeviceInfo deviceInfo: deviceInfoList) {
				List<Device> mapOnlineStat = deviceDao.mapOnlineStat(deviceInfo.getDeviceId(), StringUtils.getTodayTable());
				if(mapOnlineStat.size()>0){
					//只要一台设备有数据即为整个泵房在线
					pumpHouseInfo.setPumpHouseStatus("1");
					break;
				}
			}
		}
		*/
		
		if(!"".equals( PumpDevStatusTask.ONE_PUMP_STATS(pumpHouseId))){
			pumpHouseInfo.setPumpHouseStatus("1");
		}
        return pumpHouseInfo;
    }
    
    /**
     * 请求设备的实时测点数据  过慢 拆分接口
     * @param pumpHouseId
     * @return
     */
    public List<DeviceInfo> getPointInfoListByDeviceIds(String pumpHouseId){
		List<DeviceInfo> deviceInfoList = deviceDao.findDeviceListByPumpHouse(pumpHouseId);
    	for (DeviceInfo deviceInfo : deviceInfoList) {
			//List<Device> mapOnlineStat = deviceDao.mapOnlineStat(deviceInfo.getDeviceId(), StringUtils.getTodayTable());
    		String one_DEV_STATS = PumpDevStatusTask.ONE_DEV_STATS(deviceInfo.getDeviceId());
			if(one_DEV_STATS.equals("")){
				deviceInfo.setDeviceStatus("0");
			}else{
				deviceInfo.setDeviceStatus("1");//在线
			}
    		//测点 实时数据
    		String dimen = null;
    		List<PointInfo> pointInfoList = deviceDao.findDeviceRealTimeDataByPoint(deviceInfo.getDeviceId(), dimen,StringUtils.getTodayTable());
    		deviceInfo.setPointList(pointInfoList);
		}
		return deviceInfoList;
    }

	/**
	 * 请求设备当天的泵机运行时长
	 * @param pumpHouseId
	 * @return
	 */
	public List<RunTimeVO> getPumpRunTimeByPHId(String pumpHouseId){
		List<DeviceInfo> deviceInfoList = deviceDao.findDeviceListByPumpHouse(pumpHouseId);
		List<RunTimeVO> runTimeVOList = new ArrayList<RunTimeVO>();
		for (DeviceInfo deviceInfo : deviceInfoList) {
			RunTimeVO runTimeVO = new RunTimeVO();
			copyProperties(deviceInfo, runTimeVO);

			String one_DEV_STATS = PumpDevStatusTask.ONE_DEV_STATS(runTimeVO.getDeviceId());
			if(one_DEV_STATS.equals("")){
				runTimeVO.setDeviceStatus("0");
			}else{
				runTimeVO.setDeviceStatus("1");//在线
			}
			DateTime dateTime = new DateTime();
			String format = "yyyy-MM-dd HH:mm:ss";
			String start = dateTime.dayOfMonth().roundFloorCopy().toString(format);
			String end = dateTime.toString(format);

			RunTimeDTO runTimeDTO = new RunTimeDTO();
			List<String> deviceId = new ArrayList<String>();
			deviceId.add(runTimeVO.getDeviceId());
			runTimeDTO.setDeviceIds(deviceId);
			runTimeDTO.setStartDate(start);
			runTimeDTO.setEndDate(end);
			//查询一个设备的泵机运行时间
			Map<String, List<Map<String, Object>>> pumpRunTime = pumpRunTimeService.selectPumpRunTime(runTimeDTO);
			runTimeVO.setDatas(pumpRunTime.get("series"));
			runTimeVOList.add(runTimeVO);
		}
		return runTimeVOList;
	}

    public List<DeviceInfo> getDeviceStatus(String pumpHouseId){
 		List<DeviceInfo> deviceInfoList = deviceDao.findDeviceListByPumpHouse(pumpHouseId);
     	for (DeviceInfo deviceInfo : deviceInfoList) {
 			List<Device> mapOnlineStat = deviceDao.mapOnlineStat(deviceInfo.getDeviceId(), StringUtils.getTodayTable());
 			if(mapOnlineStat.size()>0){
 				deviceInfo.setDeviceStatus("1");
 			}else{
 				deviceInfo.setDeviceStatus("0");//离线
 			}
 		}
 		return deviceInfoList;
     }
    
	public List<ChartSeriesDto> queryDeviceHistory(String deviceIds, String functionCodes, String dimen, String startDate,
			String endDate, String addOnDate) {
		List<ChartSeriesDto> returnObj = new ArrayList<ChartSeriesDto>();
		//叠加时长处理
		int addTime = Integer.parseInt(addOnDate);
		String[] deviceIdArr = deviceIds.split(",");
		String[] funcitonCodeArr = functionCodes.split(",");
		String idCode = "";
		//设备
		for(String deviceId:deviceIdArr) {
			com.wapwag.woss.modules.home.entity.DeviceInfo device = deviceDao.get(deviceId);
			for(String functionCode:funcitonCodeArr) {
				//获取点表别名
				List<Services> servicesList = servicesDao.getByDeviceAndFunName(deviceId, functionCode);
				if(servicesList.size()>0) {
					idCode = deviceId + "\\" +servicesList.get(0).getCode();
					for(int i=0;i<=addTime;i++) {
						//叠加
						MonitorHistoryDto hisDto = new MonitorHistoryDto();
						hisDto.setIdAndFunCode(idCode);
						hisDto.setBeginDate(DateUtils.parseDate(startDate));
						hisDto.setEndDate(DateUtils.parseDate(endDate));

						hisDto.setDimen(dimen);
						if("hour".equals(hisDto.getDimen())) {
							hisDto.setTableName("service_values_hour_"+DateUtils.formatDateTimeByFormat(hisDto.getBeginDate(), DateUtils.YMD_FM));
						}
						dateTime(dimen,hisDto,i);
						//有时间叠加情况（叠加后的月最后一天日期是变动的（需要计算））
						if("month".equals(hisDto.getDimen())) {
							hisDto.setEndDate(DateUtils.getCurrentMonthLastDay(hisDto.getEndDate()));
						}

						List<ServiceValueDto> list = deviceDao.findHistoryDataByDto(hisDto);
						List<Double> values = disposeValue(list, hisDto);
						ChartSeriesDto charDto = new ChartSeriesDto();
						String dateTab = addTime>0?getTimeLab(hisDto)+"-":"";
						String nuit = StringUtils.isEmpty(servicesList.get(0).getUnit())?"":"("+servicesList.get(0).getUnit()+")";
						charDto.setName(device.getProjectInfo().getProjectName()+"-"+device.getDeviceName()+"-"+dateTab+servicesList.get(0).getName()+nuit);
						charDto.setData(values);
						returnObj.add(charDto);
					}
				}
			}
		}
		return returnObj;
	}
	private List<Double> disposeValue(List<ServiceValueDto> list,MonitorHistoryDto hisDto){
		List<Double> valueList = new ArrayList<Double>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hisDto.getBeginDate());
		int dimenFlag = 0;
		if("year".equals(hisDto.getDimen())) {
			dimenFlag = Calendar.MONTH;
		}else if("month".equals(hisDto.getDimen())){
			dimenFlag = Calendar.DATE;
		}else if("day".equals(hisDto.getDimen())){
			dimenFlag = Calendar.HOUR;
		}else if("hour".equals(hisDto.getDimen())){
			dimenFlag = Calendar.MINUTE;
		}
		for(ServiceValueDto dto:list) {
			while(calendar.getTime().before(dto.getQueryDate())) {
				valueList.add(0D);
				calendar.add(dimenFlag, 1);
			}
			if(calendar.getTime().equals(dto.getQueryDate())) {
				calendar.add(dimenFlag, 1);
				valueList.add(dto.getQueryValue());
			}
		}
		return valueList;
	}
	private void dateTime(String dimen, MonitorHistoryDto hisDto,int i) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(hisDto.getBeginDate());
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(hisDto.getEndDate());
		int calendarFlag = 0;
		if("year".equals(dimen)) {
			calendarFlag = Calendar.YEAR;
		}else if("month".equals(dimen)){
			calendarFlag = Calendar.MONTH;
		}else if("day".equals(dimen)){
			calendarFlag = Calendar.DATE;
		}else if("hour".equals(dimen)){
			calendarFlag = Calendar.HOUR;
		}
		startCalendar.add(calendarFlag, -i);
		endCalendar.add(calendarFlag, -i);
		hisDto.setBeginDate(startCalendar.getTime());
		hisDto.setEndDate(endCalendar.getTime());
	}
	public String getTimeLab(MonitorHistoryDto hisDto) {
		Date date = hisDto.getEndDate();
		String f = "yyyy-MM-dd";
		if("year".equals(hisDto.getDimen())) {
			f = "yyy";
		}else if("month".equals(hisDto.getDimen())){
			f="yyyy/MM";
		}else if("day".equals(hisDto.getDimen())){
			f="yyyy/MM/dd";
		}else if("hour".equals(hisDto.getDimen())){
			f="yyyy/MM/dd HH";
		}
		SimpleDateFormat format = new SimpleDateFormat(f);
		return format.format(date);
	}
}
