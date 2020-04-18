/**
 * 
 */
package com.wapwag.woss.modules.home.web;

import java.util.ArrayList;
import java.util.List;


import com.wapwag.woss.modules.biz.entity.WorkOrder;
import com.wapwag.woss.modules.home.entity.SysDict;
import com.wapwag.woss.modules.sys.service.SortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.home.entity.AlarmStat;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.home.service.AlarmStatService;
import com.wapwag.woss.modules.sys.entity.BootPage;
import sun.rmi.runtime.NewThreadAction;

import javax.servlet.http.HttpServletRequest;

/*
 * 告警统计
@author gongll
 * @since 2018-04-16 15:11:07
 */
@RestController
@RequestMapping("${adminPath}/alarmStatController")
@SessionAttributes("user")
@Api(description = "告警相关API")
public class AlarmStatController {

	@Autowired
	private AlarmStatService alarmService;

	@Autowired
	private SortService sortService;

	/**
	 * 分页、导出统计
	 * @param qryObject
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/alarmList")
	@ApiOperation(value = "告警列表数据查询", httpMethod = "POST",response = BootPage.class ,notes = "")
	public BootPage list( QryObject qryObject, User user) {

		qryObject.setUserId(user.getUserId());
		qryObject.setKeyword((StringUtils.getQryMsg(qryObject.getKeyword())));
		
		if ("1".equals(qryObject.getExportType())) {
			return alarmService.expList(qryObject);
		}
		return alarmService.list(qryObject);

	}

	/**
	 * 告警级别统计
	 * @param qryObject
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/statLevel")
	public List<AlarmStat> statLevel(QryObject qryObject, User user) {
		qryObject.setUserId(user.getUserId());
		//qryObject.setKeyword((StringUtils.getQryMsg(qryObject.getKeyword())));

		List<AlarmStat> list = alarmService.statLevel(qryObject);
		if (null != list && list.size() > 0) {

			for (int i = list.size() - 1; i >= 0; i--) {
				if (null == list.get(i) || StringUtils.isEmp(list.get(i).getDeviceName())) {
					list.remove(i);
				}
			}
		}
		return list;
	}

	/**
	 * 告警类型统计
	 * @param qryObject
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/statType")
	public List<AlarmStat> statType(QryObject qryObject, User user) {
		qryObject.setUserId(user.getUserId());
		//qryObject.setKeyword((StringUtils.getQryMsg(qryObject.getKeyword())));

		List<AlarmStat> list = alarmService.statType(qryObject);
		if (null != list && list.size() > 0) {

			for (int i = list.size() - 1; i >= 0; i--) {
				if (null == list.get(i) || StringUtils.isEmp(list.get(i).getDeviceName())) {
					list.remove(i);
				}
			}
		}
		return list;
	}
	
	/**
	 * 告警时间统计
	 * @param qryObject
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/statTime")
	public List<AlarmStat> statTime(QryObject qryObject, User user) {
		qryObject.setUserId(user.getUserId());
		//qryObject.setKeyword((StringUtils.getQryMsg(qryObject.getKeyword())));

		List<AlarmStat> list = alarmService.statTime(qryObject);
		if (null != list && list.size() > 0) {

			for (int i = list.size() - 1; i >= 0; i--) {
				if (null == list.get(i) || StringUtils.isEmp(list.get(i).getDeviceName())) {
					list.remove(i);
				}
			}
		}
		return list;
	}

	/**
	 * 告警当天统计
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAlarmByPumpIds")
	@ApiOperation(value = "根据泵房ids告警列表信息", httpMethod = "POST",response = AlarmStat.class ,notes = "")
	public List<AlarmStat> getAlarmByPumpIds(@RequestBody AlarmStat alarmObj, User user) {
		alarmObj.setUserId(user.getUserId());
		// false 屏蔽告警信息
		boolean isAlarm = sortService.getSortValueByCode("ALARM_CONTROL");
		List<AlarmStat> list=list= new ArrayList<>();
		if(isAlarm){
			list = alarmService.getAlarmDetail(alarmObj);
		}
		return list;
	}
	/**
	 * 告警时间统计
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexAlarm")
	@ApiOperation(value = "首页屏蔽告警提示/列表信息", httpMethod = "POST",response = AlarmStat.class ,notes = "")
	public List<AlarmStat> getAlarmDetail(AlarmStat alarmStat, User user, HttpServletRequest request) {
		alarmStat.setUserId(user.getUserId());
		// false 屏蔽告警信息
		boolean isAlarm = sortService.getSortValueByCode("ALARM_CONTROL");
		List<AlarmStat> list= new ArrayList<>();
		if(isAlarm){
			list = alarmService.getAlarmDetail(alarmStat);
		}
		return list;
	}
	
	/**
	 * 告警时间统计
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexAlarmSize")
	@ApiOperation(value = "告警量统计", httpMethod = "POST" ,notes = "")
	public String countAlarms(User user) {
		// false 屏蔽告警信息
		boolean isAlarm = sortService.getSortValueByCode("ALARM_CONTROL");
		if(isAlarm){
			return alarmService.countAlarms(user.getUserId());
		}
		return null;
	}
	
	/**
	 * 告警时间统计
	 * @param info
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexConfig")
	public String indexConfig(AlarmStat info,User user) {
		info.setConfirmReson((StringUtils.getQryMsg(info.getConfirmReson())));
		if (null != user.getName()) {
			info.setUserName(user.getName());
		}else {
			info.setUserName(user.getLoginName());
		}
		return alarmService.indexConfig(info);
	}
	
	/**
	 * 查看一个告警根据
	 * @param info
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAlarmById")
	public AlarmStat getAlarmById(AlarmStat info){
		return alarmService.getAlarmById(info);
	}

	/**
	 * 创建告警工单
	 * @param workOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitWorkOrder")
	public String submitWorkOrder(WorkOrder workOrder,User user){
		return alarmService.submitWorkOrder(workOrder,user);
	}

	/**
	 * 查询告警工单模版
	 * @return List<SysDict>
	 */
	@ResponseBody
	@RequestMapping("/queryAlarmWorkTemplate")
	public List<SysDict> queryAlarmWorkTemplate(){
		return alarmService.queryAlarmWorkTemplate();
	}

	/**
	 * 查询维修班组
	 * @return List<SysDict>
	 */
	@ResponseBody
	@RequestMapping("/queryMaintenanceWorkerUser")
	public List<User> queryMaintenanceWorkerUser(){
		return alarmService.queryMaintenanceWorkerUser();
	}

    /**
     * 查询泵房是否有工单
     * @return List<SysDict>
     */
    @ResponseBody
    @RequestMapping("/queryWhetherWorker")
    public String queryWhetherWorker(@RequestBody String deviceId){
        return alarmService.queryWhetherWorker(deviceId);
    }

}
