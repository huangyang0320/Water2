package com.wapwag.woss.common.hkthirdsdk.pojo;

public class ValueCount {

	private String deviceId;
	private String pv;
	private String pumpHouseName;
	private String pumpHouseId;
	private String dateTime;
	private String areaId;
	private String total;
	private String serviceId;
	private String min;
	private String max;

	private String userDBName;

	public void setUserDBName(String userDBName) {
		this.userDBName = userDBName;
	}

	public String getUserDBName() {
		return userDBName;
	}

	public ValueCount(String deviceId, String userDBName) {
		this.userDBName = userDBName;
		this.deviceId = deviceId;
	}

	public ValueCount() {

	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public String getPumpHouseName() {
		return pumpHouseName;
	}

	public void setPumpHouseName(String pumpHouseName) {
		this.pumpHouseName = pumpHouseName;
	}

	public String getPumpHouseId() {
		return pumpHouseId;
	}

	public void setPumpHouseId(String pumpHouseId) {
		this.pumpHouseId = pumpHouseId;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}
}
