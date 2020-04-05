package com.wapwag.woss.modules.monitor.pumpNode;

import java.io.Serializable;

public class GwDevice implements Serializable{
	
	private static final long serialVersionUID = -2396510631955962916L;
	
	private String gateWayId;
	private String deviceId;
	
	public String getGateWayId() {
		return gateWayId;
	}
	public void setGateWayId(String gateWayId) {
		this.gateWayId = gateWayId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	

}
