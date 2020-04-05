package com.wapwag.woss.modules.sys.entity;

import java.util.Date;

import com.wapwag.woss.common.persistence.DataEntity;

public class DeviceAccessories extends DataEntity<DeviceAccessories> {
    private String accessoriesId;

    private String accessoriesName;

    private String deviceId;

    private String accessoriesNo;

    private String accessoriesUnits;

    private String accessoriesItemid;
    private String accessoriesType;

    private Date insertTime;

    private Date lastUpdateTime;
    
    private int countNum;

    public String getAccessoriesId() {
        return accessoriesId;
    }

    public void setAccessoriesId(String accessoriesId) {
        this.accessoriesId = accessoriesId;
    }

    public String getAccessoriesName() {
        return accessoriesName;
    }

    public void setAccessoriesName(String accessoriesName) {
        this.accessoriesName = accessoriesName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAccessoriesNo() {
        return accessoriesNo;
    }

    public void setAccessoriesNo(String accessoriesNo) {
        this.accessoriesNo = accessoriesNo;
    }

    public String getAccessoriesUnits() {
        return accessoriesUnits;
    }

    public void setAccessoriesUnits(String accessoriesUnits) {
        this.accessoriesUnits = accessoriesUnits;
    }

    public String getAccessoriesItemid() {
        return accessoriesItemid;
    }

    public void setAccessoriesItemid(String accessoriesItemid) {
        this.accessoriesItemid = accessoriesItemid;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

	public int getCountNum() {
		return countNum;
	}

	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}

	public String getAccessoriesType() {
		return accessoriesType;
	}

	public void setAccessoriesType(String accessoriesType) {
		this.accessoriesType = accessoriesType;
	}
}