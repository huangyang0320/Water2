package com.wapwag.woss.modules.sys.entity;

import com.wapwag.woss.common.persistence.DataEntity;

public class PumpFault extends DataEntity<PumpFault> {
    private String faultId;

    private String alarmId;

    private String deviceId;

    private Integer faultLevel;

    private String faultMessage;

    private String faultState;

    public String getFaultId() {
        return faultId;
    }

    public void setFaultId(String faultId) {
        this.faultId = faultId;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(Integer faultLevel) {
        this.faultLevel = faultLevel;
    }

    public String getFaultMessage() {
        return faultMessage;
    }

    public void setFaultMessage(String faultMessage) {
        this.faultMessage = faultMessage;
    }

    public String getFaultState() {
        return faultState;
    }

    public void setFaultState(String faultState) {
        this.faultState = faultState;
    }
}