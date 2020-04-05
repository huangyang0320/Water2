package com.wapwag.woss.modules.home.entity;

import java.util.Date;

public class AlarmStatistics {
    private String deviceCode;

    private String functionName;

    private Date startDate;

    private Date endDate;

    private Boolean alarmStatus;

    private Boolean confirmStatus;

    private String orgUserName;

    private Date confirmDate;

    private String confirmReason;

    private String confirmUrl;

    private String ticketId;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode == null ? null : deviceCode.trim();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(Boolean alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public Boolean getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Boolean confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getOrgUserName() {
        return orgUserName;
    }

    public void setOrgUserName(String orgUserName) {
        this.orgUserName = orgUserName == null ? null : orgUserName.trim();
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmReason() {
        return confirmReason;
    }

    public void setConfirmReason(String confirmReason) {
        this.confirmReason = confirmReason == null ? null : confirmReason.trim();
    }

    public String getConfirmUrl() {
        return confirmUrl;
    }

    public void setConfirmUrl(String confirmUrl) {
        this.confirmUrl = confirmUrl == null ? null : confirmUrl.trim();
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketid(String ticketId) {
        this.ticketId = ticketId == null ? null : ticketId.trim();
    }
}