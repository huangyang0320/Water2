package com.wapwag.woss.modules.monitor.pumpNode;

/**
 * @author zx
 * @since 2018/5/22
 */
public class DeviceInfo {
    private String id;
    private String devicePosition;
    private String type;
    private String isWaterTank="0";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDevicePosition() {
		return devicePosition;
	}
	public void setDevicePosition(String devicePosition) {
		this.devicePosition = devicePosition;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsWaterTank() {
		return isWaterTank;
	}
	public void setIsWaterTank(String isWaterTank) {
		this.isWaterTank = isWaterTank;
	}
	
    
}
