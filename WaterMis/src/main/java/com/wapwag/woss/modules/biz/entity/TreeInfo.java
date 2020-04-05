package com.wapwag.woss.modules.biz.entity;

import java.io.Serializable;

public class TreeInfo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;		
	private String pId;		
	private String pIds;	
	private String name;
	private String operType;
	
	//增加泵房的属性
	private String pumpHouseId;
	private String projectId;
	private String projectName;
	private String longi;
	private String lati;
	private String allPicUrl;
	private String address;

	private String deviceManufacturers;//设备厂家
	private String deviceManufacturersInformation;//设备厂家联系方式
	private String selfControlManufacturers;//自控改造厂家
	private String selfControlManufacturersInformation;//自控改造厂家联系方式
	private String constructionSide;//建设方
	private String constructionSideInformation;//建设方联系方式
	private String construction;//施工方
	private String constructionInformation;//施工方联系方式
	private String property;//物业方
	private String propertyInformation;//物业方联系方式
	private String handoverTime;//移交时间



	
	//水司的ID
	private String realId;
	private String type;
	
	//树节点是否显示
	private Boolean isHidden;
	
	//视频属性
	private String monitorIp;
	private String monitorPort;
	private String channelNo;
	
	//设备ID 兼容老的页面
	private String deviceId;
	private String deviceName;
	
	private String pumpStatus;//[0:在线,1：报警 2:离线] 
	private	String devStatus;//[0:在线,1：报警 2:离线]
	
	private String pinyin;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getpIds() {
		return pIds;
	}
	public void setpIds(String pIds) {
		this.pIds = pIds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getPumpHouseId() {
		return pumpHouseId;
	}
	public void setPumpHouseId(String pumpHouseId) {
		this.pumpHouseId = pumpHouseId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getLongi() {
		return longi;
	}
	public void setLongi(String longi) {
		this.longi = longi;
	}
	public String getLati() {
		return lati;
	}
	public void setLati(String lati) {
		this.lati = lati;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getRealId() {
		return realId;
	}
	public void setRealId(String realId) {
		this.realId = realId;
	}
	public Boolean getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMonitorIp() {
		return monitorIp;
	}
	public void setMonitorIp(String monitorIp) {
		this.monitorIp = monitorIp;
	}
	public String getMonitorPort() {
		return monitorPort;
	}
	public void setMonitorPort(String monitorPort) {
		this.monitorPort = monitorPort;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getAllPicUrl() {
		return allPicUrl;
	}
	public void setAllPicUrl(String allPicUrl) {
		this.allPicUrl = allPicUrl;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getPumpStatus() {
		return pumpStatus;
	}
	public void setPumpStatus(String pumpStatus) {
		this.pumpStatus = pumpStatus;
	}
	public String getDevStatus() {
		return devStatus;
	}
	public void setDevStatus(String devStatus) {
		this.devStatus = devStatus;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getDeviceManufacturers() {
		return deviceManufacturers;
	}

	public void setDeviceManufacturers(String deviceManufacturers) {
		this.deviceManufacturers = deviceManufacturers;
	}

	public String getDeviceManufacturersInformation() {
		return deviceManufacturersInformation;
	}

	public void setDeviceManufacturersInformation(String deviceManufacturersInformation) {
		this.deviceManufacturersInformation = deviceManufacturersInformation;
	}

	public String getSelfControlManufacturers() {
		return selfControlManufacturers;
	}

	public void setSelfControlManufacturers(String selfControlManufacturers) {
		this.selfControlManufacturers = selfControlManufacturers;
	}

	public String getSelfControlManufacturersInformation() {
		return selfControlManufacturersInformation;
	}

	public void setSelfControlManufacturersInformation(String selfControlManufacturersInformation) {
		this.selfControlManufacturersInformation = selfControlManufacturersInformation;
	}

	public String getConstructionSide() {
		return constructionSide;
	}

	public void setConstructionSide(String constructionSide) {
		this.constructionSide = constructionSide;
	}

	public String getConstructionSideInformation() {
		return constructionSideInformation;
	}

	public void setConstructionSideInformation(String constructionSideInformation) {
		this.constructionSideInformation = constructionSideInformation;
	}

	public String getConstruction() {
		return construction;
	}

	public void setConstruction(String construction) {
		this.construction = construction;
	}

	public String getConstructionInformation() {
		return constructionInformation;
	}

	public void setConstructionInformation(String constructionInformation) {
		this.constructionInformation = constructionInformation;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getPropertyInformation() {
		return propertyInformation;
	}

	public void setPropertyInformation(String propertyInformation) {
		this.propertyInformation = propertyInformation;
	}

	public String getHandoverTime() {
		return handoverTime;
	}

	public void setHandoverTime(String handoverTime) {
		this.handoverTime = handoverTime;
	}

	public Boolean getHidden() {
		return isHidden;
	}

	public void setHidden(Boolean hidden) {
		isHidden = hidden;
	}
}
