package com.wapwag.woss.modules.monitor.pumpNode;

/**
 * @author zx
 * @since 2018/5/22
 */
public class DevicePoint {

    private Long id;
    private String deviceId;
    private String deviceLocation;
    private String pointName;
    private String pointMemo;
    private String pointUnit;

    public DevicePoint(String deviceId, String pointName) {
        this.deviceId = deviceId;
        this.pointName = pointName;
    }
	public DevicePoint() {
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceLocation() {
		return deviceLocation;
	}

	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getPointMemo() {
		return pointMemo;
	}

	public void setPointMemo(String pointMemo) {
		this.pointMemo = pointMemo;
	}

	public String getPointUnit() {
		return pointUnit;
	}

	public void setPointUnit(String pointUnit) {
		this.pointUnit = pointUnit;
	}
}
