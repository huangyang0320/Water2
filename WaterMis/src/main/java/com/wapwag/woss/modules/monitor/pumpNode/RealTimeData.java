package com.wapwag.woss.modules.monitor.pumpNode;

import java.util.Map;

/**
 * @author ChangWei Li
 * @version 2018-01-10 11:32
 */
public class RealTimeData {

    private String deviceId;
    private String timestamp;
    private Map<String, Object> values;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
