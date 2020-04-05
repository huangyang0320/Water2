package com.wapwag.woss.modules.sys.entity;

public class DeviceElectricalLabel {
    private String id;

    private String deviceId;

    private String modelNo;

    private String ratedVoltage;

    private String ratedPower;

    private String standards;

    private String controlQuantity;

    private String ratedCurrent;

    private String ratedFrequency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getRatedVoltage() {
        return ratedVoltage;
    }

    public void setRatedVoltage(String ratedVoltage) {
        this.ratedVoltage = ratedVoltage;
    }

    public String getRatedPower() {
        return ratedPower;
    }

    public void setRatedPower(String ratedPower) {
        this.ratedPower = ratedPower;
    }

    public String getStandards() {
        return standards;
    }

    public void setStandards(String standards) {
        this.standards = standards;
    }

    public String getControlQuantity() {
        return controlQuantity;
    }

    public void setControlQuantity(String controlQuantity) {
        this.controlQuantity = controlQuantity;
    }

    public String getRatedCurrent() {
        return ratedCurrent;
    }

    public void setRatedCurrent(String ratedCurrent) {
        this.ratedCurrent = ratedCurrent;
    }

    public String getRatedFrequency() {
        return ratedFrequency;
    }

    public void setRatedFrequency(String ratedFrequency) {
        this.ratedFrequency = ratedFrequency;
    }
}