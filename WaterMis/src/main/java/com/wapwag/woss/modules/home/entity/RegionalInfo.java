package com.wapwag.woss.modules.home.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class RegionalInfo implements Serializable {

	private static final long serialVersionUID = -6017224050403310154L;

	private String id;

    private String parentId;

    private String parentIds;

    private String name;

    private BigDecimal longi;

    private BigDecimal lati;

    private String type;

    private String remarks;

    private String delFlag;

    private List<PumpHouseInfo> pumpHouseList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLongi() {
        return longi;
    }

    public void setLongi(BigDecimal longi) {
        this.longi = longi;
    }

    public BigDecimal getLati() {
        return lati;
    }

    public void setLati(BigDecimal lati) {
        this.lati = lati;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public List<PumpHouseInfo> getPumpHouseList() {
        return pumpHouseList;
    }

    public void setPumpHouseList(List<PumpHouseInfo> pumpHouseList) {
        this.pumpHouseList = pumpHouseList;
    }
}