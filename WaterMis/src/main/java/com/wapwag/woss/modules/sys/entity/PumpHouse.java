package com.wapwag.woss.modules.sys.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PumpHouse {
	
	private String id;
	
    private String pumpHouseId;
    
    private String areaId;

    private String projectId;

    private String pumpHouseName;

    private String pumpHouseAddress;

    private String memo;

    private BigDecimal longi;

    private BigDecimal lati;

    private String accessCtrlIpAddr;

    private Float accessCtrlPort;

    private String allPicUrl;
    
    private String deviceManufacturers; // 设备厂家
	private String deviceManufacturersInformation; // 设备厂家联系方式
	private String selfControlManufacturers; // 自控改造厂家
	private String selfControlManufacturersInformation; // 自控改造厂家联系方式
	private String constructionSide; // 建设方
	private String constructionSideInformation; // 建设方联系方式
	private String construction; // 施工方
	private String constructionInformation; // 施工方联系方式
	private String property; // 物业
	private String propertyInformation; // 物业联系方式
	private String cellName; // 小区名称
	private String cellAdress; // 小区地址
	private String handoverTime; // 移交时间。	

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

    public String getPumpHouseName() {
        return pumpHouseName;
    }

    public void setPumpHouseName(String pumpHouseName) {
        this.pumpHouseName = pumpHouseName;
    }

    public String getPumpHouseAddress() {
        return pumpHouseAddress;
    }

    public void setPumpHouseAddress(String pumpHouseAddress) {
        this.pumpHouseAddress = pumpHouseAddress;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getLongi() {
        return longi;
    }

    public void setLongi(BigDecimal longi) {
        this.longi = longi;
    }

    public BigDecimal getLati() {
        return lati;
    }

    public void setLati(BigDecimal lati) {
        this.lati = lati;
    }

    public String getAccessCtrlIpAddr() {
        return accessCtrlIpAddr;
    }

    public void setAccessCtrlIpAddr(String accessCtrlIpAddr) {
        this.accessCtrlIpAddr = accessCtrlIpAddr;
    }

    public Float getAccessCtrlPort() {
        return accessCtrlPort;
    }

    public void setAccessCtrlPort(Float accessCtrlPort) {
        this.accessCtrlPort = accessCtrlPort;
    }

    public String getAllPicUrl() {
        return allPicUrl;
    }

    public void setAllPicUrl(String allPicUrl) {
        this.allPicUrl = allPicUrl;
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

	public void setDeviceManufacturersInformation(
			String deviceManufacturersInformation) {
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

	public void setSelfControlManufacturersInformation(
			String selfControlManufacturersInformation) {
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

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getCellAdress() {
		return cellAdress;
	}

	public void setCellAdress(String cellAdress) {
		this.cellAdress = cellAdress;
	}

	public String getHandoverTime() {
		return handoverTime;
	}

	public void setHandoverTime(String handoverTime) {
		this.handoverTime = handoverTime;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}