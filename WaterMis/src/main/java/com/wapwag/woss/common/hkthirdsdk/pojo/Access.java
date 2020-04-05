package com.wapwag.woss.common.hkthirdsdk.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")  
@XmlAccessorType(XmlAccessType.FIELD)  
public class Access {

	private String deviceBasicId;
	private String dagId;
	private String sysCode;
	private String deviceType;
	private String deviceBasicName;
	private String controlUnitId;
	private String userName;
	private String userPwd;
	private String deployId;
	private String deviceBasicParentId;
	private String deviceLevel;
	private String ip;
	private String port;
	private String baudRate;
	private String connectMethod;
	private String mainType;
	public String getDeviceBasicId() {
		return deviceBasicId;
	}
	public void setDeviceBasicId(String deviceBasicId) {
		this.deviceBasicId = deviceBasicId;
	}
	public String getDagId() {
		return dagId;
	}
	public void setDagId(String dagId) {
		this.dagId = dagId;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceBasicName() {
		return deviceBasicName;
	}
	public void setDeviceBasicName(String deviceBasicName) {
		this.deviceBasicName = deviceBasicName;
	}
	public String getControlUnitId() {
		return controlUnitId;
	}
	public void setControlUnitId(String controlUnitId) {
		this.controlUnitId = controlUnitId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getDeployId() {
		return deployId;
	}
	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}
	public String getDeviceBasicParentId() {
		return deviceBasicParentId;
	}
	public void setDeviceBasicParentId(String deviceBasicParentId) {
		this.deviceBasicParentId = deviceBasicParentId;
	}
	public String getDeviceLevel() {
		return deviceLevel;
	}
	public void setDeviceLevel(String deviceLevel) {
		this.deviceLevel = deviceLevel;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}
	public String getConnectMethod() {
		return connectMethod;
	}
	public void setConnectMethod(String connectMethod) {
		this.connectMethod = connectMethod;
	}
	public String getMainType() {
		return mainType;
	}
	public void setMainType(String mainType) {
		this.mainType = mainType;
	}
}
