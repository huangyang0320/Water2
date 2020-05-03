package com.wapwag.woss.common.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QryObject {
	private String constructionSide;

	@Override
	public String toString() {
		return "QryObject{" +
				"constructionSide='" + constructionSide + '\'' +
				", beginTime='" + beginTime + '\'' +
				", endTime='" + endTime + '\'' +
				", pageNumber=" + pageNumber +
				", pageSize=" + pageSize +
				", deviceId='" + deviceId + '\'' +
				", deviceName='" + deviceName + '\'' +
				", alarmInfo='" + alarmInfo + '\'' +
				", startAlarmTime='" + startAlarmTime + '\'' +
				", endAlarmTime='" + endAlarmTime + '\'' +
				", projectId='" + projectId + '\'' +
				", projectName='" + projectName + '\'' +
				", userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", pumpHouseId='" + pumpHouseId + '\'' +
				", area='" + area + '\'' +
				", type='" + type + '\'' +
				", exportType='" + exportType + '\'' +
				", exportMustNum='" + exportMustNum + '\'' +
				", tableName='" + tableName + '\'' +
				", name='" + name + '\'' +
				", areaId='" + areaId + '\'' +
				", subIndex=" + subIndex +
				", keyword='" + keyword + '\'' +
				", phId='" + phId + '\'' +
				", pumpHouseName='" + pumpHouseName + '\'' +
				", areaCode='" + areaCode + '\'' +
				", sortName='" + sortName + '\'' +
				", sortOrder='" + sortOrder + '\'' +
				'}';
	}

	public QryObject() {
	}

	public QryObject(String constructionSide, String beginTime, String endTime, int pageNumber, int pageSize, String deviceId, String deviceName, String alarmInfo, String startAlarmTime, String endAlarmTime, String projectId, String projectName, String userId, String userName, String pumpHouseId, String area, String type, String exportType, String exportMustNum, String tableName, String name, String areaId, int subIndex, String keyword, String phId, String pumpHouseName, String areaCode, String sortName, String sortOrder) {
		this.constructionSide = constructionSide;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.alarmInfo = alarmInfo;
		this.startAlarmTime = startAlarmTime;
		this.endAlarmTime = endAlarmTime;
		this.projectId = projectId;
		this.projectName = projectName;
		this.userId = userId;
		this.userName = userName;
		this.pumpHouseId = pumpHouseId;
		this.area = area;
		this.type = type;
		this.exportType = exportType;
		this.exportMustNum = exportMustNum;
		this.tableName = tableName;
		this.name = name;
		this.areaId = areaId;
		this.subIndex = subIndex;
		this.keyword = keyword;
		this.phId = phId;
		this.pumpHouseName = pumpHouseName;
		this.areaCode = areaCode;
		this.sortName = sortName;
		this.sortOrder = sortOrder;
	}

	public String getConstructionSide() {
		return constructionSide;
	}

	public void setConstructionSide(String constructionSide) {
		this.constructionSide = constructionSide;
	}

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
