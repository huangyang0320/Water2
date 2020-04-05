package com.wapwag.woss.modules.home.entity;

import com.wapwag.woss.common.persistence.DataEntity;

import java.util.List;

/**
 * Pumphouse entity
 */
public class PumpHouseInfo extends DataEntity<PumpHouseInfo> {

    private static final long serialVersionUID = 1L;

    private String pumpHouseId;

    private String projectId;

    private String pumpHouseName;

    private String pumpHouseAddress;

    private String memo;

    private String longi;

    private String lati;

    private String accessCtrlIpAddr;

    private String accessCtrlPort;

    private String allPicUrl;

    private List<DeviceInfo> deviceList;

    public String getPumpHouseId() {
        return pumpHouseId;
    }

    public void setPumpHouseId(String pumpHouseId) {
        this.pumpHouseId = pumpHouseId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPumpHouseName() {
        return pumpHouseName;
    }

    public void setPumpHouseName(String pumpHouseName) {
        this.pumpHouseName = pumpHouseName;
    }

    public String getPumpHouseAddress() {
        return pumpHouseAddress;
    }

    public void setPumpHouseAddress(String pumpHouseAddress) {
        this.pumpHouseAddress = pumpHouseAddress;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getAccessCtrlIpAddr() {
        return accessCtrlIpAddr;
    }

    public void setAccessCtrlIpAddr(String accessCtrlIpAddr) {
        this.accessCtrlIpAddr = accessCtrlIpAddr;
    }

    public String getAccessCtrlPort() {
        return accessCtrlPort;
    }

    public void setAccessCtrlPort(String accessCtrlPort) {
        this.accessCtrlPort = accessCtrlPort;
    }

    public String getAllPicUrl() {
        return allPicUrl;
    }

    public void setAllPicUrl(String allPicUrl) {
        this.allPicUrl = allPicUrl;
    }

    public List<DeviceInfo> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceInfo> deviceList) {
        this.deviceList = deviceList;
    }
}