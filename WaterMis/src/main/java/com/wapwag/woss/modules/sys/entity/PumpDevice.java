package com.wapwag.woss.modules.sys.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.wapwag.woss.common.persistence.DataEntity;

public class PumpDevice extends DataEntity<PumpDevice> {
    private String deviceId;

    private String deviceName;

    private String pumpHouseId;

    private String projectId;

    private Date dateManufacture;

    private Date datePurchase;

    private BigDecimal purchaseAmount;

    private Date createtime;

    private Date updatetime;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

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

    public Date getDateManufacture() {
        return dateManufacture;
    }

    public void setDateManufacture(Date dateManufacture) {
        this.dateManufacture = dateManufacture;
    }

    public Date getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(Date datePurchase) {
        this.datePurchase = datePurchase;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}