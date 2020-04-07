package com.wapwag.woss.common.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QryObject {
	private String beginTime;
	private String endTime;
	private int pageNumber;
	private int pageSize;
	private String deviceId;
	private String deviceName;
	private String alarmInfo;
	private String startAlarmTime;
	private String endAlarmTime;
	private String projectId;
	private String projectName;
	private String userId;
	private String userName;
	private String pumpHouseId;
	private String area;
	private String type;
	private String exportType;
	private String exportMustNum;
	private String tableName;
	private String name;
	private String areaId;
	private int subIndex;
	private String keyword;

	private String phId;
	private String pumpHouseName;
	private String areaCode;

	private String sortName;
	private String sortOrder;

	public String getPumpHouseName() {
		return pumpHouseName;
	}

	public void setPumpHouseName(String pumpHouseName) {
		this.pumpHouseName = pumpHouseName;
	}

	public String getPhId() {
		return phId;
	}

	public void setPhId(String phId) {
		this.phId = phId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int getSubIndex() {
		return subIndex;
	}
	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}
	
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		if (beginTime.length() < 11) {
			beginTime = beginTime + " 00:00:00";
		}
		this.beginTime = beginTime;
	}
	public void setBeginTimeNemol(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
	    this.endTime = endTime + " 23:59:59";
	}
	public void setEndTimeNemol(String endTime) {
	    this.endTime = endTime;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPumpHouseId() {
		return pumpHouseId;
	}
	public void setPumpHouseId(String pumpHouseId) {
		this.pumpHouseId = pumpHouseId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	public String getExportMustNum() {
		return exportMustNum;
	}
	public void setExportMustNum(String exportMustNum) {
		this.exportMustNum = exportMustNum;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public String getStartAlarmTime() {
		return startAlarmTime;
	}

	public void setStartAlarmTime(String startAlarmTime) {
		this.startAlarmTime = startAlarmTime;
	}

	public String getEndAlarmTime() {
		return endAlarmTime;
	}

	public void setEndAlarmTime(String endAlarmTime) {
		this.endAlarmTime = endAlarmTime;
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
}
