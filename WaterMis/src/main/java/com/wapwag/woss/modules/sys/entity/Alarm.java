package com.wapwag.woss.modules.sys.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 告警信息
 * @author gongll
 *
 */
public class Alarm extends DataEntity<Alarm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String alarmId;
	private String projectId;
	private String projectName;
	private String deviceId;
	private String deviceName;
	private String pumpHouseId;
	private String pumpHouseName;
	private String alarmContent;
	private String alarmReason;
	private String alarmType;
	private Date alarmTime;
	private String alarmTimeStr;
	private String beginTime;
	private String endTime;
	private String area;
	private int minMun;
	private int maxNum;

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPumpHouseId() {
		return pumpHouseId;
	}

	public void setPumpHouseId(String pumpHouseId) {
		this.pumpHouseId = pumpHouseId;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getAlarmReason() {
		return alarmReason;
	}

	public void setAlarmReason(String alarmReason) {
		this.alarmReason = alarmReason;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getPumpHouseName() {
		return pumpHouseName;
	}

	public void setPumpHouseName(String pumpHouseName) {
		this.pumpHouseName = pumpHouseName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAlarmTimeStr() {
		return alarmTimeStr;
	}

	public void setAlarmTimeStr(String alarmTimeStr) {
		this.alarmTimeStr = alarmTimeStr;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getMinMun() {
		return minMun;
	}

	public void setMinMun(int minMun) {
		this.minMun = minMun;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
}