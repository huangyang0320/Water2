package com.wapwag.woss.modules.sys.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.utils.TimeUtils;
import com.wapwag.woss.modules.biz.entity.Device;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.sys.entity.PumpServiceValue;
import com.wapwag.woss.modules.sys.entity.ValueCount;
import com.wapwag.woss.modules.sys.service.CountService;

/**
 * 系统管理
 * 
 * @author gongll
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/count")
@SessionAttributes("user")
public class CountController {
	
	@Autowired
	private CountService countService;
	
	/**
	 * 实时设备参数统计
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countVListalues/{qryType}/{areaId}")
	public List<ValueCount> countVListalues(@PathVariable String qryType , @PathVariable String areaId,User user){
		return countService.countValueByName(qryType,areaId,user.getUserId());
	}
	
	/**
	 * 统计设备信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countDevice")
	public Map<String, String> countDevice(User user){
		return countService.statistcDevice(user.getUserId());
	}
	
	/**
	 * 统计地区下面的设备数量
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countDeviceArea")
	public List<Device> countDeviceArea(User user){
		List<Device> list=countService.statisticDevivceArea(user.getUserId());
		return list;
	}
	
	/**
	 * 统计地区下面的设备数量
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countDeviceAreaChina")
	public List<Device> countDeviceAreaChina(User user){
		return countService.countDeviceAreaChina(user.getUserId());
	}
	
	/**
	 * 统计不同类型的累呗数量
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countDeviceType")
	public List<Device> countDeviceType(User user){
		return countService.statisticDevivceType(user.getUserId());
	}
	
	/**
	 * 区域维保项目对比
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "areaProjectRepairStat")
	public List<Device> areaProjectRepairStat(User user) {
		List<Device> result =  countService.areaProjectRepairStat(user.getUserId());
		return result;
	}
	
	/**
	 * 维保
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "statisticRepaicType")
	public List<Device> statisticRepaicType(User user) {
		List<Device> result =  countService.areaProjectRepairStat(user.getUserId());
		return result;
	}
	
	/**
	 * 能耗对比【设备】
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "devEleCom/{devId}")
	public List<Device> devEleCom(@PathVariable String devId){
		List<Device> result =  countService.devEleCom(devId);
		return result;
	}
	/**
	 * 告警类型
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "statisticAlarmType")
	public List<Device> statisticAlarmType(User user){
		return countService.statisticAlarmType(user.getUserId());
	}
	
	/**
	 * 告警类型
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "statisticAlarmLevel/{deviceId}")
	public List<Device> statisticAlarmLevel(@PathVariable String deviceId){
		return countService.statRealAlarm(deviceId);
	}
	
	/**
	 * 泵房区域额定功率对比
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pumpRatedPower/{deviceId}")
	public List<Device> pumpRatedPower(@PathVariable String deviceId){
		return countService.pumpRatedPower(deviceId);
	}
	
	/**
	 * 告警类型
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "statisticAlarmType/{deviceId}")
	public List<Device> statRealAlarm(@PathVariable String deviceId){
		return countService.statRealAlarmType(deviceId);
	}
	/**
	 * 每一小时能耗
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "statisticHourEle/{dateTime}/{type}")
	public List<Device> statisticHourEle(@PathVariable int dateTime,@PathVariable String type,User user){
		String nowTime ="";
		if ("2".equals(type)) {
			nowTime = TimeUtils.getCurrerMouth(dateTime);
		}else if ("3".equals(type)) {
			nowTime = TimeUtils.getCurrerYear(dateTime);
		}else {
			nowTime = TimeUtils.getCurrerDay(dateTime);
		}

		String[] qryVal = TimeUtils.trafficTime(dateTime, type);
		QryObject info = new QryObject();
		info.setBeginTimeNemol(qryVal[0]);
		info.setEndTimeNemol(qryVal[1]);
		info.setSubIndex(Integer.parseInt(qryVal[2]));
		info.setType(qryVal[3]);
		info.setUserId(user.getUserId());
		
		List<Device> values = countService.staticEle(info);
		if (null == values) {
			values = new ArrayList<Device>();
		}
		for (int i = 0; i < values.size(); i++) {
			values.get(i).setName(info.getBeginTime() + "-" + values.get(i).getName());
		}
		
		Device device = new Device();
		device.setName("date_time");
		device.setMemo(nowTime);
		if (null == values) {
			values = new ArrayList<Device>();
		}
		values.add(device);
	
		return values;
	}
	/**
	 *  每一小时告警
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "statisticHourAla/{dateTime}")
	public List<Device> statisticHourAla(@PathVariable int dateTime,User user){
		String nowTime = TimeUtils.getCurrerDay(dateTime);
		List<Device> list =  countService.statisticHourAla(nowTime,user.getUserId());
		Device device = new Device();
		device.setName("date_time");
		device.setMemo(nowTime);
		if (null == list) {
			list = new ArrayList<Device>();
		}
		list.add(device);
		return list;
	}
	
	/**
	 *  泵运行统计
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pumpRunStatis/{deviceId}")
	public void pumpRunStatis(@PathVariable String deviceId){
		PumpServiceValue value = new PumpServiceValue();
		value.setDeviceId(deviceId);
		countService.pumpRunStatis(value);
	}
	
	/**
	 * 设备区位统计
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countLocation")
	public List<Device> countLocation(){
		return countService.countLocation();
	}
	
	/**
	 * 功耗统计（运行次数、启停、运行状态、额定功率）
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countPowerRun/{deviceId}")
	public List<String[][]> countPowerRun(@PathVariable String deviceId) {
		PumpServiceValue info = new PumpServiceValue();
		info.setDeviceId(deviceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		info.setBeginTime(sdf.format(new Date()) + " 00:00:00");
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		info.setEndTime(sdf.format(new Date()));
		return countService.pumpRunStatis(info);
	}
	
	/**
	 * 每一小时能耗
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countEleStat")
	public List<Device> countEleStat(User user){

		String[] qryVal = TimeUtils.trafficTime(0, "2");
		QryObject info = new QryObject();
		info.setBeginTimeNemol(qryVal[0]);
		info.setEndTimeNemol(qryVal[1]);
		info.setSubIndex(Integer.parseInt(qryVal[2]));
		info.setType(qryVal[3]);
		info.setUserId(user.getUserId());
		
		List<Device> values = countService.staticEle(info);
		if (null == values) {
			values = new ArrayList<Device>();
		}
		for (int i = 0; i < values.size(); i++) {
			values.get(i).setName(info.getBeginTime() + "-" + values.get(i).getName());
		}
		return values;
	}
	
	/**
	 * 每一小时能耗
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countEleStatByDevice/{deviceId}")
	public List<Device> countEleStatByDevice(@PathVariable String deviceId){

		String[] qryVal = TimeUtils.trafficTime(0, "2");
		QryObject info = new QryObject();
		info.setBeginTimeNemol(qryVal[0]);
		info.setEndTimeNemol(qryVal[1]);
		info.setSubIndex(Integer.parseInt(qryVal[2]));
		info.setType(qryVal[3]);
		info.setDeviceId(deviceId);
		
		List<Device> values = countService.staticEle(info);
		if (null == values) {
			values = new ArrayList<Device>();
		}
		for (int i = 0; i < values.size(); i++) {
			values.get(i).setName(info.getBeginTime() + "-" + values.get(i).getName());
		}
		return values;
	}
	
	/**
	 * 首页汇总信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "summaryStat")
	public Map<String, String> summaryStat(User user) {
		return countService.summaryStatIndex(user.getUserId());
	}
	
	/**
	 * 首页汇总信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "summaryStat/{device}")
	public Map<String, String> summaryDeviceStat(@PathVariable String device) {
		return countService.summaryStat(device);
	}
	
	@ResponseBody
	@RequestMapping(value = "traSuppPumpStat")
	public List<Device> traSuppPumpStat(User user) {
		List<Device> result =  countService.traSuppPumpStat(user.getUserId());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "countAlarmTimesStat")
	public List<Device> countAlarmTimesStat(User user){
		List<Device> result = countService.countAlarmTimesStat(user.getUserId(),"user");
		if (null == result) {
			result = new ArrayList<Device>();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "countAlarmTimesStatByDevId/{deviceId}")
	public List<Device> countAlarmTimesStatByDevId(@PathVariable String deviceId) {
		List<Device> result = countService.countAlarmTimesStat(deviceId,"dev");
		if (null == result) {
			result = new ArrayList<Device>();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "countMaintenanceTrend")
	public List<Device> countMaintenanceTrend(User user){
		List<Device> result = countService.countMaintenanceTrend(user.getUserId());
		if (null == result) {
			result = new ArrayList<Device>();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "countMaintenanceTrendDev/{device}")
	public List<Device> countMaintenanceTrendDev(@PathVariable String device) {
		List<Device> result = countService.countMaintenanceTrendDev(device);
		if (null == result) {
			result = new ArrayList<Device>();
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "orderTypeStat")
	public List<Device> orderTypeStat() {
		
		return new ArrayList<Device>();
	}
	
	/**
	 * 能耗峰值统计
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "statisticEleByLocation/{type}")
	public List statisticEleByLocation(User user) {
		Map<String, String> result = countService.statisticEleByLocation(user.getUserId());
		List list = new ArrayList();
		if (null != result) {
			list.add(result);
		}
		return list;
	}
	
	/**
	 * 能耗峰值统计
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "statisticEleByDevice/{deviceId}")
	public List statisticEleByDevice(@PathVariable String deviceId) {
		Map<String, Double> result = countService.statisticEleByDevice(deviceId);
		List list = new ArrayList();
		if (null != result) {
			list.add(result);
		}
		return list;
	}
	
	/**
	 * 首页（用户状态）
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "staticUserWork")
	public List<Device> staticUserWork() {
		return new ArrayList<Device>();
	}
	
	/**
	 *  压力统计
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "passStat/{deviceId}")
	public Map<String, String> passStat(@PathVariable String deviceId){
		return countService.passStat(deviceId);
	}
	
	/**
	 *  压力统计
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "staticPressMin/{deviceId}")
	public Map<String,String[]> staticPressMin(@PathVariable String deviceId){
		return countService.staticPressMin(deviceId);
	}
	
	@ResponseBody
	@RequestMapping(value = "deviceStatTheme/{deviceId}")
	public List<String[]> deviceStatTheme(@PathVariable String deviceId){
		PumpServiceValue info = new PumpServiceValue();
		info.setDeviceId(deviceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		info.setBeginTime(sdf.format(new Date()) + " 00:00:00");
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		info.setEndTime(sdf.format(new Date()));
		return countService.deviceStatTheme(info);
	}
	
	@ResponseBody
	@RequestMapping(value = "runStatDevice/{deviceId}")
	public String[][] runStatDevice(@PathVariable String deviceId){
		PumpServiceValue info = new PumpServiceValue();
		info.setDeviceId(deviceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		info.setBeginTime(sdf.format(new Date()) + " 00:00:00");
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		info.setEndTime(sdf.format(new Date()));
		return countService.runStatDevice(info);
	}
}
