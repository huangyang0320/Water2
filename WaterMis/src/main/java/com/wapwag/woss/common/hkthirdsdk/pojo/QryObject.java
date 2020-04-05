package com.wapwag.woss.common.hkthirdsdk.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QryObject {
	private String beginTime;
	private String endTime;
	private int pageNumber;
	private int pageSize;
	private String deviceId;
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
	private String userDBName;
	private int subIndex;
	
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
	public String getUserDBName() {
		return userDBName;
	}
	public void setUserDBName(String userDBName) {
		this.userDBName = userDBName;
	}
	public int getSubIndex() {
		return subIndex;
	}
	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}
}
