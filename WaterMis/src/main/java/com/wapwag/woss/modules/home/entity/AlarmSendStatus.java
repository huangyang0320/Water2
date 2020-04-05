package com.wapwag.woss.modules.home.entity;

import java.util.Date;

public class AlarmSendStatus {
    private String alarmSendId;
    private String alarmStatisticsId;
    private String alarmSendType;
    private Integer sendFailCount;
    private String sendAddr;
    private String isSuccess;
    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private Date delDate;
    private String delBy;
    private String validFlag;

    public AlarmSendStatus(String alarmSendId, String alarmStatisticsId, String alarmSendType, String sendAddr, String isSuccess, Date createDate, String createBy, String validFlag) {
        this.alarmSendId = alarmSendId;
        this.alarmStatisticsId = alarmStatisticsId;
        this.alarmSendType = alarmSendType;
        this.sendAddr = sendAddr;
        this.isSuccess = isSuccess;
        this.createDate = createDate;
        this.createBy = createBy;
        this.validFlag = validFlag;
    }

    public String getAlarmSendId() {
        return alarmSendId;
    }

    public void setAlarmSendId(String alarmSendId) {
        this.alarmSendId = alarmSendId;
    }

    public String getAlarmStatisticsId() {
        return alarmStatisticsId;
    }

    public void setAlarmStatisticsId(String alarmStatisticsId) {
        this.alarmStatisticsId = alarmStatisticsId;
    }

    public String getAlarmSendType() {
        return alarmSendType;
    }

    public void setAlarmSendType(String alarmSendType) {
        this.alarmSendType = alarmSendType;
    }

    public Integer getSendFailCount() {
        return sendFailCount;
    }

    public void setSendFailCount(Integer sendFailCount) {
        this.sendFailCount = sendFailCount;
    }

    public String getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(String sendAddr) {
        this.sendAddr = sendAddr;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }

    public String getDelBy() {
        return delBy;
    }

    public void setDelBy(String delBy) {
        this.delBy = delBy;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }
}
