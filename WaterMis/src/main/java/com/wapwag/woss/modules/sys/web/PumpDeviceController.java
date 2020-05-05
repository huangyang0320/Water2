package com.wapwag.woss.modules.sys.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.utils.ExcelUtil;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.common.utils.TimeUtils;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.sys.entity.AccessCtrlRecords;
import com.wapwag.woss.modules.sys.entity.Alarm;
import com.wapwag.woss.modules.sys.entity.Archive;
import com.wapwag.woss.modules.sys.entity.AttRecord;
import com.wapwag.woss.modules.sys.entity.BootPage;
import com.wapwag.woss.modules.sys.entity.DeviceAccessories;
import com.wapwag.woss.modules.sys.entity.EnergyInfo;
import com.wapwag.woss.modules.sys.entity.Project;
import com.wapwag.woss.modules.sys.entity.PumpDevice;
import com.wapwag.woss.modules.sys.entity.PumpDeviceRepair;
import com.wapwag.woss.modules.sys.entity.PumpHouse;
import com.wapwag.woss.modules.sys.entity.PumpServiceValue;
import com.wapwag.woss.modules.sys.service.AccessCtrlRecordsService;
import com.wapwag.woss.modules.sys.service.AlarmService;
import com.wapwag.woss.modules.sys.service.AttendanceService;
import com.wapwag.woss.modules.sys.service.PumpDeviceRepairService;
import com.wapwag.woss.modules.sys.service.PumpDeviceService;
import com.wapwag.woss.modules.sys.service.PumpServiceValueService;

/**
 * 设备管理
 * @author gonglld
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/device")
@SessionAttributes("user")
public class PumpDeviceController {
	
	@Autowired
	private PumpDeviceRepairService pumpDeviceRepairService;
	
	@Autowired
	private AccessCtrlRecordsService accessCtrlRecordsService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private PumpServiceValueService pumpSensorDataService;
	
	@Autowired
	private PumpDeviceService deviceService;
	
	@Autowired
	private AlarmService alarmService;

	/**
	 * 查询维保信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deviceRepairList")
	public BootPage getRepairList(QryObject qryObject,User user){
		PumpDeviceRepair pumpDeviceRepair = new PumpDeviceRepair();
		pumpDeviceRepair.setUserId(StringUtils.getQryMsg(qryObject.getUserName()));
		pumpDeviceRepair.setProjectId(qryObject.getProjectId());
		pumpDeviceRepair.setPhId(qryObject.getPhId());
		pumpDeviceRepair.setUserName(user.getUserId());

		BootPage bootPage = new BootPage();
		if("1".equals(qryObject.getExportType())){
			pumpDeviceRepair.setExportMustNum(Integer.parseInt(qryObject.getExportMustNum()));
			List<PumpDeviceRepair> pumpDeviceRepairss = pumpDeviceRepairService.exportAll(pumpDeviceRepair);
			bootPage.setTotal(pumpDeviceRepairss.size());
	        bootPage.setRows(pumpDeviceRepairss);

		}else{
			Page page = new Page();
			page.setPageNo(qryObject.getPageNumber());
			page.setPageSize(qryObject.getPageSize());
	        Page<PumpDeviceRepair> pages = pumpDeviceRepairService.findPage(page,pumpDeviceRepair);
	        bootPage.setTotal(pages.getCount());
	        bootPage.setRows(pages.getList());
		}
		return bootPage;
	}
	
	/**
	 * 查询维保信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRepairListDeatil/{projectId}/{userName}")
	public List<PumpDeviceRepair> getRepairListDeatil(@PathVariable String projectId,@PathVariable String userName,User user){
		PumpDeviceRepair pumpDeviceRepair = new PumpDeviceRepair();
		pumpDeviceRepair.setProjectId(projectId);
		if (!"-1".equals(userName)) {
			pumpDeviceRepair.setUserId(StringUtils.getQryMsg(userName));
		}
		pumpDeviceRepair.setUserName(user.getUserId());
		
		Page page = new Page();
		page.setPageNo(1);
		page.setPageSize(1000);
        Page<PumpDeviceRepair> pages = pumpDeviceRepairService.findPage(page,pumpDeviceRepair);
        return pages.getList();
	}
	
	/**
	 * 门禁日志人员统计
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "accessCtrl/countUser")
	public List<AccessCtrlRecords> getAccessCtrl(QryObject qryObject){
		AccessCtrlRecords info = new AccessCtrlRecords();
		info.setBeginTime(qryObject.getBeginTime());
		info.setEndTime(qryObject.getEndTime());
		info.setPersonName(StringUtils.getQryMsg(qryObject.getUserId()));
		return accessCtrlRecordsService.countLoginUser(info);
		
	}
	
	/**
	 * 考勤统计
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "arrRecord/countUser")
	public List<AttRecord> arrRecord(QryObject qryObject){
		AttRecord info = new AttRecord();
		info.setBeginTime(qryObject.getBeginTime());
		info.setEndTime(qryObject.getEndTime());
		info.setPersonName(StringUtils.getQryMsg(qryObject.getUserId()));
		return attendanceService.countLoginUser(info);
		
	}
	
	
	/**
	 * 门禁日志
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "accessCtrl")
	public BootPage getAccessCtrlById(QryObject qryObject){
		AccessCtrlRecords info = new AccessCtrlRecords();
		BootPage bootPage = new BootPage();
		info.setDoorName(StringUtils.getQryMsg(qryObject.getKeyword()));
		info.setBeginTime(qryObject.getBeginTime());
		info.setEndTime(qryObject.getEndTime());
		info.setPersonName(StringUtils.getQryMsg(qryObject.getUserId()));
		if("1".equals(qryObject.getExportType())){
			info.setExportMustNum(Integer.parseInt(qryObject.getExportMustNum()));
			List<AccessCtrlRecords> list = accessCtrlRecordsService.exportList(info);
			bootPage.setRows(list);
			bootPage.setTotal(list.size());
		}else {
			Page page = new Page();
			page.setPageNo(qryObject.getPageNumber());
			page.setPageSize(qryObject.getPageSize());
			Page<AccessCtrlRecords> pages = accessCtrlRecordsService.findPage(page, info);
	        bootPage.setTotal(pages.getCount());
	        bootPage.setRows(pages.getList());	
		}
		return bootPage;
	}
	
	/**
	 * 门禁日志
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "arrRecord")
	public BootPage arrRecordList(QryObject qryObject){
		AttRecord info = new AttRecord();
		BootPage bootPage = new BootPage();
		info.setBeginTime(qryObject.getBeginTime());
		info.setEndTime(qryObject.getEndTime());
		info.setPersonName(StringUtils.getQryMsg(qryObject.getUserId()));
		if("1".equals(qryObject.getExportType())){
			info.setExportMustNum(Integer.parseInt(qryObject.getExportMustNum()));
			List<AttRecord> list = attendanceService.exportList(info);
			bootPage.setRows(list);
			bootPage.setTotal(list.size());
		}else {
			Page page = new Page();
			page.setPageNo(qryObject.getPageNumber());
			page.setPageSize(qryObject.getPageSize());
			Page<AttRecord> pages = attendanceService.findPage(page, info);
	        bootPage.setTotal(pages.getCount());
	        bootPage.setRows(pages.getList());	
		}
		return bootPage;
	}
	/**
	 * 获取设备历史数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sensorHistory")
	public BootPage gePumpSensorHistory(QryObject qryObject){
		PumpServiceValue info = new PumpServiceValue();
		if (null == qryObject.getDeviceId()) {
			return null;
		}
		Page page = new Page();
		info.setBeginTime(qryObject.getBeginTime());
		info.setEndTime(qryObject.getEndTime());
		info.setDeviceId(qryObject.getDeviceId());
		info.setOperType(qryObject.getType());
		
		//pumpSensorDataService.pumpRunStatis(info);
		if("1".equals(qryObject.getExportType())){
			String[] countSum = pumpSensorDataService.countSum(info);
			page.setCount(countSum.length);
			if(page.getCount() == 0){
				return null;
			}
			BootPage bootPage= pumpSensorDataService.findAll(info);
			bootPage.setTotal(page.getCount());
			return bootPage;
		}else{
			String[] countSum = pumpSensorDataService.countSum(info);
			page.setCount(countSum.length);
			if(page.getCount() == 0){
				return null;
			}
			if ((qryObject.getPageNumber() -1 )*qryObject.getPageSize() >= countSum.length) {
				return null;
			}
			info.setBeginTime(countSum[(qryObject.getPageNumber() -1 )*qryObject.getPageSize()]);
			
			if((qryObject.getPageNumber() -1 )*qryObject.getPageSize() + qryObject.getPageSize() -1 > countSum.length){
				info.setEndTime(countSum[countSum.length - 1]);
			}else {
				info.setEndTime(countSum[(qryObject.getPageNumber() -1 )*qryObject.getPageSize() + qryObject.getPageSize()-1]);
			}
			BootPage bootPage= pumpSensorDataService.findAll(info);
			bootPage.setTotal(page.getCount());
			return bootPage;
		}
	}
	
	/**
	 * 根据设备ID查询历史数据显示的内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDeviceCodeById/{deviceID}/{tableName}")
	public String[] getDeviceCodeById(@PathVariable String deviceID,@PathVariable String tableName){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (tableName.indexOf("today") > -1) {
			tableName = "service_values_"+sdf.format(new Date());
		}
		return pumpSensorDataService.findServiceByDeviceID(deviceID,tableName);
	}
	
	/**
	 * 设备档案信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deviceArchive/{deviceId:.+}")
	public Archive getArchiveByDevId(@PathVariable String deviceId){
		Archive archive;
		if (null != deviceId){
			archive = deviceService.getArchiveByDevId(deviceId);
		}else {
			archive = new Archive();
		}
		return archive;
	}
	
	/**
	 * 设备配件信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "accessories/{deviceId:.+}")
	public List<DeviceAccessories> getAccessoriesByDevId(@PathVariable String deviceId){
		List<DeviceAccessories> accessories;
		if (null != deviceId){
			accessories = deviceService.getAccessoriesByDevId(deviceId);
		}else {
			accessories = new ArrayList<DeviceAccessories>();
		}
		return accessories;
	}
	
	/**
	 * 告警日志
	 * @param alarmInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "alarm")
	public BootPage list(QryObject qryObject,HttpSession session) {
		long begin = System.currentTimeMillis();
		long aa = System.currentTimeMillis();
	    Alarm info = new Alarm();
		info.setDeviceId(StringUtils.getQryMsg(qryObject.getDeviceId()));
		info.setId(qryUserId(session));
		info.setBeginTime(qryObject.getBeginTime());
		info.setEndTime(qryObject.getEndTime());
		info.setProjectId(qryObject.getArea());
		info.setPumpHouseId(qryObject.getPumpHouseId());
		info.setAlarmType(StringUtils.getQryMsg(qryObject.getType()));
		info.setRemarks(qryObject.getExportMustNum());
		BootPage bootPage = new BootPage();
		if ("1".equals(qryObject.getExportType())) {
			info.setExportMustNum(Integer.parseInt(qryObject.getExportMustNum()));
			List<Alarm> alarmList = alarmService.exportList(info);
			bootPage.setRows(alarmList);
			bootPage.setTotal(alarmList.size());
		}else{
			String countNum = alarmService.findListCount(info);
			if ("0".equals(countNum)) {
				bootPage.setTotal(0);
				return bootPage;
			}
			
			info.setMinMun((qryObject.getPageNumber() -1)*qryObject.getPageSize());
			info.setMaxNum(qryObject.getPageSize());
			List<Alarm> list = alarmService.findList(info);
	        bootPage.setTotal(Long.parseLong(countNum));
	        bootPage.setRows(list);
		}
		
		System.out.println(System.currentTimeMillis() - begin);
       return bootPage;
	}
	
	/**
	 * 告警日志
	 * @param alarmInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "alarmExport/{type}")
	public void alarmExport(@PathVariable String type,Alarm info,HttpServletResponse response,HttpSession session) {
		info.setRemarks(type);
		info.setEndTime(TimeUtils.addOneDay(info.getEndTime()));
		info.setId(qryUserId(session));
		List<Alarm> alarmList = alarmService.exportList(info);
        String fileName="alarm";
        String columnNames[]={"项目名称","所属区域","报警类型","报警时间","设备名称","设备编号","泵房名称","报警内容","报警原因"};
        String keys[] = {"projectName","area","alarmType","alarmTimeStr","deviceName","deviceId","pumpHouseId","alarmContent","alarmReason"};
        
		ExcelUtil.replayExpRequest( response, fileName, columnNames, keys, alarmList );
        
	}
	
	/**
	 * 告警统计查询
	 * @param type area：按照地区查询，type：按照类型查询
	 * @param info 查询参数
	 * @return 查询结果
	 */
	@ResponseBody
	@RequestMapping(value = "alarmCount/{type}")
	public List<Alarm> areaAlarmCount(@PathVariable String type,Alarm info,HttpSession session) {
		info.setRemarks(type);
		info.setEndTime(TimeUtils.addOneDay(info.getEndTime()));
		info.setId(qryUserId(session));
		List<Alarm> list =  alarmService.areaAlarmCount(info);
		if (null != list && list.size() > 0) {
	
			for (int i = list.size() -1 ; i >= 0; i--) {
				if (null == list.get(i) || StringUtils.isEmp(list.get(i).getAlarmId())) {
					list.remove(i);
				}
			}
		}
		return list;
	}
	
	/**
	 * 告警统计查询
	 * @param type area：按照地区查询，type：按照类型查询
	 * @param info 查询参数
	 * @return 查询结果
	 */
	@ResponseBody
	@RequestMapping(value = "countAlarmTimes")
	public List<Alarm> countAlarmTimes(Alarm info,HttpSession session) {
		info.setEndTime(TimeUtils.addOneDay(info.getEndTime()));
		info.setId(qryUserId(session));
		List<Alarm> list =  alarmService.countAlarmTimes(info);
		if (null != list && list.size() > 0) {
	
			for (int i = list.size() -1 ; i >= 0; i--) {
				if (null == list.get(i) || StringUtils.isEmp(list.get(i).getAlarmId())) {
					list.remove(i);
				}
			}
		}
		return list;
	}
	/**
	 * 查询项目列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllProject")
	public List<Project> getAllProject(){
		return deviceService.getProject();
	}
	
	@ResponseBody
	@RequestMapping(value = "getProjectByRole")
	public List<Project> getProjectByRole(User user){
		return deviceService.getProjectByRole(user.getUserId());
	}
	
	/**
	 * 查询区域列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllAreas")
	public List<PumpDevice> getAllAreas(User user){
		return deviceService.getAreaList(user.getUserId());
	}
	
	/**
	 * 根据区域ID查询泵房ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getpumpHousesByAreaId/{areaId}")
	public List<PumpDevice> getpumpHousesByAreaId(@PathVariable String areaId){
		return deviceService.getPumpHouseByAreaId(areaId);
	}
	
	/**
	 * 根据区域ID查询泵房ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDeviceIdByPumpId/{pumpId}")
	public List<PumpDevice> getDeviceIdByPumpId(@PathVariable String pumpId){
		return deviceService.getDeviceIdByPumpId(pumpId);
	}
	
	
	/**
	 * 根据项目ID查询设备信 息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDevicesByProjectId/{projectId}")
	public List<PumpDevice> getDevicesByProjectId(@PathVariable String projectId,HttpSession session) {
		PumpDevice info = new PumpDevice();
		info.setId(qryUserId(session));
		info.setProjectId(projectId);
		return deviceService.getDeviceByProjectId(info);
	}
	
	
	
	/**
	 * 查询区域列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAlarmType/{type}")
	public List<Alarm> getAlarmType(@PathVariable String type){
		return alarmService.getAlarmType(type);
	}
	
	private String qryUserId(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null == user || null == user.getUserId()) {
			user = new User();
			return "";
		}
		return user.getUserId();
	}
	
	/**
	 * 查询区域列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pumpHouse")
	public BootPage pumpHouses(QryObject info, User user){
		Alarm alarmInfo = new Alarm();
		alarmInfo.setArea(info.getAreaId());
		alarmInfo.setProjectId(info.getProjectId());
		alarmInfo.setPumpHouseName(StringUtils.getQryMsg(info.getName()));
		alarmInfo.setId(user.getUserId());
		alarmInfo.setConstructionSide(info.getConstructionSide());
		BootPage bootPage = new BootPage();
		String countNum = deviceService.countPumpHouse(alarmInfo);
		if ("0".equals(countNum)) {
			bootPage.setTotal(0);
			return bootPage;
		}
		alarmInfo.setMinMun((info.getPageNumber() -1)*info.getPageSize());
		alarmInfo.setMaxNum(info.getPageSize());
		List<PumpHouse> list = deviceService.pumpHouses(alarmInfo);
        bootPage.setTotal(Long.parseLong(countNum));
        bootPage.setRows(list);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		return bootPage;
	}
	
	/**
	 * 根据区域ID查询项目ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getProjectByAreaId/{areaId}")
	public List<PumpDevice> getProjectByAreaId(@PathVariable String areaId,User user){
		return deviceService.getProjectByAreaId(areaId,user.getUserId());
	}
	/**
	 * 告警统计查询
	 * @param type area：按照地区查询，type：按照类型查询
	 * @param info 查询参数
	 * @return 查询结果
	 */
	@ResponseBody
	@RequestMapping(value = "pumpCount/{type}")
	public List<Alarm> pumpCount(@PathVariable String type,PumpHouse info,User user) {
		info.setId(user.getUserId());
		info.setMemo(type);
		List<Alarm> list =  deviceService.pumpCount(info);
		if (null != list && list.size() > 0) {
	
			for (int i = list.size() -1 ; i >= 0; i--) {
				if (null == list.get(i) || StringUtils.isEmp(list.get(i).getAlarmId())) {
					list.remove(i);
				}
			}
		}
		return list;
	}
	
	/**
	 * 功率实时数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "powerStatisRel/{deviceId}")
	public PumpServiceValue powerStatisRel(@PathVariable String deviceId){
		PumpServiceValue info = new PumpServiceValue();
		info.setDeviceId(deviceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		info.setBeginTime(sdf.format(new Date()));
		info.setRemarks(System.currentTimeMillis()+"");
		if (StringUtils.isEmp(info.getPv())) {
			info.setPv("0");
		}
		info =  pumpSensorDataService.powerStatisRel(info);
		return info;
				
	}
	
	/**
	 * 平率实时曲线  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "frequencyStatisRel/{deviceId}")
	public PumpServiceValue frequencyStatisRel(@PathVariable String deviceId){
		PumpServiceValue info = new PumpServiceValue();
		info.setDeviceId(deviceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		info.setBeginTime(sdf.format(new Date()));
		info =  pumpSensorDataService.frequencyStatisRel(info);
		if (StringUtils.isEmp(info.getPv())) {
			info.setPv("0");
		}
		info.setRemarks(System.currentTimeMillis()+"");
		return info;
				
	}
	
	/**
	 * 功率统计  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "powerStatis/{deviceId}")
	public PumpServiceValue[] powerStatis(@PathVariable String deviceId){
		PumpServiceValue info = new PumpServiceValue();
		info.setDeviceId(deviceId);
		return pumpSensorDataService.powerStatis(info);
	}
	
	/**
	 * 频率统计  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "frequencyStatis/{deviceId}")
	public PumpServiceValue[] frequencyStatis(@PathVariable String deviceId){
		PumpServiceValue info = new PumpServiceValue();
		info.setDeviceId(deviceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		info.setBeginTime(sdf.format(new Date()));
		return pumpSensorDataService.frequencyStatis(info);
				
	}
	
	/*************************性能分析 begin**************************/
	
	/**
	 * 性能分析频率一小时内所有数据  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "performanceHz/{deviceId}")
	public Map<String, Map<Long, String>> performanceHz(@PathVariable String deviceId){
		PumpServiceValue info = new PumpServiceValue();
		
		info.setDeviceId(deviceId);
		info.setRemarks("01");
		return pumpSensorDataService.performance(info);
	}
	
	/**
	 * 性能分析功率一小时内所有数据  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "performancePow/{deviceId}")
	public Map<String, Map<Long, String>> performancePow(@PathVariable String deviceId){
		PumpServiceValue info = new PumpServiceValue();
		
		info.setDeviceId(deviceId);
		info.setRemarks("02");
		return pumpSensorDataService.performance(info);
	}
	
	/**
	 * 实时查询频率、功率数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "performanceRel/{deviceId}")
	public Map<String, Map<String, String>> performanceRel(@PathVariable String deviceId){
		PumpServiceValue info = new PumpServiceValue();
		
		info.setDeviceId(deviceId);
		info.setRemarks("01");
		return pumpSensorDataService.performanceRel(info);
	}
	
	/**
	 * 实时查询频率、功率数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pumpNames/{deviceId}")
	public List<String> pumpNames(@PathVariable String deviceId){
		return pumpSensorDataService.pumpNames(deviceId);
	}
	
	
	/*************************性能分析 end****************************/
	
	/**
	 * 实时查询频率、功率数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "energy")
	public List<EnergyInfo> energy(QryObject qryObject){
		if (qryObject.getType().equals("2")) {
			return pumpSensorDataService.energyDev(qryObject);
		}else {
			return pumpSensorDataService.energy(qryObject);
		}
	}
}
