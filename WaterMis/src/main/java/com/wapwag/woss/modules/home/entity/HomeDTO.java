package com.wapwag.woss.modules.home.entity;

public class HomeDTO {
    private String id;
    private String dateTime;
    private String startTime;
    private String endTime;
    private String unit;
    private String pv;

    private String areaName;
    private String areaId;
    private String pumpHouseId;

    private String pumpName;
    private String defaultVal;
    private String sxGg;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getPumpHouseId() {
        return pumpHouseId;
    }

    public void setPumpHouseId(String pumpHouseId) {
        this.pumpHouseId = pumpHouseId;
    }

    public String getPumpName() {
        return pumpName;
    }

    public void setPumpName(String pumpName) {
        this.pumpName = pumpName;
    }

    public String getDefaultVal() {
        return "0";
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getSxGg() {
        return sxGg;
    }

    public void setSxGg(String sxGg) {
        this.sxGg = sxGg;
    }
}
