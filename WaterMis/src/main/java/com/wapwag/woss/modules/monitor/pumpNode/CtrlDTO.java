package com.wapwag.woss.modules.monitor.pumpNode;

/**
 * @author leo
 * @since 2018/4/19 10:30
 */
public class CtrlDTO {

    private String deviceId;

    private String pointCode;

    private String pointValue;

    private String doorSource;//门禁来源0无门禁1海康门禁2节点门禁

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getPointValue() {
        return pointValue;
    }

    public void setPointValue(String pointValue) {
        this.pointValue = pointValue;
    }

    public String getDoorSource() {
        return doorSource;
    }

    public void setDoorSource(String doorSource) {
        this.doorSource = doorSource;
    }
}
