package com.wapwag.woss.modules.biz.entity;

import java.io.Serializable;
import java.util.List;

//设备信息
public class DeviceInfo implements Serializable {
	
	private static final long serialVersionUID = 4217826551133270945L;
	private String deviceId;
	private String deviceName;
	private String deviceStatus;
	private String type;
	private List<PointInfo> pointList;
	private String location;
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
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public List<PointInfo> getPointList() {
		return pointList;
	}
	public void setPointList(List<PointInfo> pointList) {
		this.pointList = pointList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	

}
