package com.wapwag.woss.common.hkthirdsdk.pojo;

public class DeviceRate {

	private String orgId;
	private String dbCode;
	private String areaId;
	private String dateTime;
	private String deviceNum;
	private String deviceUploadNum;
	private String tableName;
	public String getOrgId() {
		return orgId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDbCode() {
		return dbCode;
	}
	public void setDbCode(String dbCode) {
		this.dbCode = dbCode;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDeviceNum() {
		return deviceNum;
	}
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	public String getDeviceUploadNum() {
		return deviceUploadNum;
	}
	public void setDeviceUploadNum(String deviceUploadNum) {
		this.deviceUploadNum = deviceUploadNum;
	}
}
