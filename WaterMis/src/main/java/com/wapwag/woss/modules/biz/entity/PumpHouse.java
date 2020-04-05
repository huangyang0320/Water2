package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 泵房Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class PumpHouse extends DataEntity<PumpHouse> {
	
	private static final long serialVersionUID = 1L;
	private Project project;		// 项目编号
	private String name;		// 泵房名称
	private String address;		// 泵房地址
	private String memo;		// 备注
	private Double longi;		// 经度
	private Double lati;		// 纬度
	private String accessCtrlIpAddr;		// 门禁IP地址
	private Double accessCtrlPort;		// 门禁端口号
	private String allPicUrl;		// 全景图片地址
	private Regional region;		// 地区id
	private String pinyin;

	private String deviceManufacturers;//设备厂家
	private String deviceManufacturersInformation;//设备厂家联系方式pumpHouseName
	private String selfControlManufacturers;//自控改造厂家
	private String selfControlManufacturersInformation;//自控改造厂家联系方式
	private String constructionSide;//建设方
	private String constructionSideInformation;//建设方联系方式
	private String construction;//施工方
	private String constructionInformation;//施工方联系方式
	private String property;//物业方
	private String propertyInformation;//物业方联系方式
	private String handoverTime;//移交时间


	public PumpHouse() {
		super();
	}

	public PumpHouse(String id){
		super(id);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	@Length(min=0, max=50, message="泵房名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=500, message="泵房地址长度必须介于 0 和 500 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
	
	@Length(min=0, max=20, message="门禁IP地址长度必须介于 0 和 20 之间")
	public String getAccessCtrlIpAddr() {
		return accessCtrlIpAddr;
	}

	public void setAccessCtrlIpAddr(String accessCtrlIpAddr) {
		this.accessCtrlIpAddr = accessCtrlIpAddr;
	}
	
	public Double getAccessCtrlPort() {
		return accessCtrlPort;
	}

	public void setAccessCtrlPort(Double accessCtrlPort) {
		this.accessCtrlPort = accessCtrlPort;
	}
	
	@Length(min=0, max=100, message="全景图片地址长度必须介于 0 和 100 之间")
	public String getAllPicUrl() {
		return allPicUrl;
	}

	public void setAllPicUrl(String allPicUrl) {
		this.allPicUrl = allPicUrl;
	}
	
	public Regional getRegion() {
		return region;
	}

	public void setRegion(Regional region) {
		this.region = region;
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
}