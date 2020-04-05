package com.wapwag.woss.modules.biz.entity;

import java.io.Serializable;
import java.util.List;

import com.wapwag.woss.modules.home.entity.VideoInfo;

/**
 * 泵房信息
 * 设备
 * 视频
 * @author zhaom
 *
 */
public class PumpHouseInfo implements Serializable{
	
	private static final long serialVersionUID = -2131330210045742214L;
	private String PumpHouseName;
	private String PumpHouseId;
	private String address;
	private Double longi;// 经度
	private Double lati;
	private String allPicUrl;
	private List<VideoInfo> videoInfoList;//视频多个 
	private List<DeviceInfo> deviceInfolist;
	private String pumpHouseStatus;//泵房在线状态0离线1在线


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


	public String getPumpHouseName() {
		return PumpHouseName;
	}
	public void setPumpHouseName(String pumpHouseName) {
		PumpHouseName = pumpHouseName;
	}
	public String getPumpHouseId() {
		return PumpHouseId;
	}
	public void setPumpHouseId(String pumpHouseId) {
		PumpHouseId = pumpHouseId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLongi() {
		return longi;
	}
	public void setLongi(Double longi) {
		this.longi = longi;
	}
	public Double getLati() {
		return lati;
	}
	public void setLati(Double lati) {
		this.lati = lati;
	}
	public String getAllPicUrl() {
		return allPicUrl;
	}
	public void setAllPicUrl(String allPicUrl) {
		this.allPicUrl = allPicUrl;
	}
	public List<DeviceInfo> getDeviceInfolist() {
		return deviceInfolist;
	}
	public void setDeviceInfolist(List<DeviceInfo> deviceInfolist) {
		this.deviceInfolist = deviceInfolist;
	}
	public List<VideoInfo> getVideoInfoList() {
		return videoInfoList;
	}
	public void setVideoInfoList(List<VideoInfo> videoInfoList) {
		this.videoInfoList = videoInfoList;
	}

	public String getPumpHouseStatus() {
		return pumpHouseStatus;
	}

	public void setPumpHouseStatus(String pumpHouseStatus) {
		this.pumpHouseStatus = pumpHouseStatus;
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
}
