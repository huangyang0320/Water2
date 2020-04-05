package com.wapwag.woss.modules.home.entity;

/**
 * 视频查询对象
 * @author gongll
 *
 */
public class HikQryInfo {

	private String videoNo;
	private String videoIp;
	private String token;
	public String getPumpHouseId() {
		return pumpHouseId;
	}
	public void setPumpHouseId(String pumpHouseId) {
		this.pumpHouseId = pumpHouseId;
	}
	private String beginTime;
	private String endTime;
	private String deviceType;
	private String appKey;
	private String  pumpHouseId;
	private String  resouseXml;
	private String userName;
	public String getVideoNo() {
		return videoNo;
	}
	public void setVideoNo(String videoNo) {
		this.videoNo = videoNo;
	}
	public String getVideoIp() {
		return videoIp;
	}
	public void setVideoIp(String videoIp) {
		this.videoIp = videoIp;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getResouseXml() {
		return resouseXml;
	}
	public void setResouseXml(String resouseXml) {
		this.resouseXml = resouseXml;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
