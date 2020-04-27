package com.wapwag.woss.modules.home.entity;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 告警信息表
 * </p>
 *
 * @author AutoGeneration
 * @since 2017-11-07
 */
public class AlarmStat {

	private String alarmTime;

	private String lati;
	private String longi;

	private String id;
    private String phId;
    private String address;
	private String startDate;
	private String endDate;
	private Date endTime;
	private String alarmInfo;
	private String alarmType;
	private String phName;
	private String deviceId;
	private String deviceName;
	private String alarDescription;
	private String alarmLevel;
	private String alarmReason;
	private String confirmDate;

	//告警类型备注
	private String alarmTypeRemarks;
	
	private String confirmStat;
	private String userName;
	private String confirmReson;
	private String configUrl;
	private String alarmCode;
	private String ticketId;

	private String deptId;

	private String areaCode;
	private String pumpHouseName;
	private String userId;
	private List<String> phIdList;

	private String sortName;
	private String sortOrder;
	private String functionName;
	private String alarmSetValue;


	public String getPumpHouseName() {
		return pumpHouseName;
	}

	public void setPumpHouseName(String pumpHouseName) {
		this.pumpHouseName = pumpHouseName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

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

	public String getLati() {
		return lati;
	}

	public void setLati(String lati) {
		this.lati = lati;
	}

	public String getLongi() {
		return longi;
	}

	public void setLongi(String longi) {
		this.longi = longi;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getConfirmReson() {
		return confirmReson;
	}

	public void setConfirmReson(String confirmReson) {
		this.confirmReson = confirmReson;
	}

	public String getConfigUrl() {
		return configUrl;
	}

	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
	}

	public String getConfirmStat() {
		return confirmStat;
	}

	public void setConfirmStat(String confirmStat) {
		this.confirmStat = confirmStat;
	}

	public String getPhId() {
		return phId;
	}

	public void setPhId(String phId) {
		this.phId = phId;
	}

	public String getAlarmReason() {
		return alarmReason;
	}

	public void setAlarmReason(String alarmReason) {
		this.alarmReason = alarmReason;
	}

	public String getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<String> getPhIdList() {
		return phIdList;
	}

	public void setPhIdList(List<String> phIdList) {
		this.phIdList = phIdList;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getAlarmSetValue() {
		return alarmSetValue;
	}

	public void setAlarmSetValue(String alarmSetValue) {
		this.alarmSetValue = alarmSetValue;
	}

	public String getAlarmTypeRemarks() {
		return alarmTypeRemarks;
	}

	public void setAlarmTypeRemarks(String alarmTypeRemarks) {
		this.alarmTypeRemarks = alarmTypeRemarks;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
