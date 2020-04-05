package com.wapwag.woss.modules.home.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author gongll
 * @version 2017-10-27 14:22
 */
@ApiModel
public class AlarmDTO {

	@ApiModelProperty(notes = "(\"开始时间:时间格式yyyy-MM-dd HH:mm:ss\")")
	private String startDate;
	
	//("结束时间:时间格式yyyy-MM-dd HH:mm:ss")
	private String endDate;
	
	@ApiModelProperty(notes = "(\"告警信息\")")
    private String alarmInfo;
	
	//("告警类型")
    private String alarmType;
	
	//("泵房名称")
	@ApiModelProperty(notes = "(\"泵房名称\")")
    private String phName;
	
	//("设备")
    private String deviceId;
	
	//("设备名称")
    private String deviceName;
	
	//("任务描述")
    private String alarDescription;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getPhName() {
		return phName;
	}

	public void setPhName(String phName) {
		this.phName = phName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getAlarDescription() {
		return alarDescription;
	}

	public void setAlarDescription(String alarDescription) {
		this.alarDescription = alarDescription;
	}
}
