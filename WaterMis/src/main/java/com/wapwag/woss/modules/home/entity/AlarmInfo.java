package com.wapwag.woss.modules.home.entity;

import com.wapwag.woss.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;

@Alias("com.wapwag.woss.modules.home.entity.AlarmInfo")
public class AlarmInfo extends DataEntity<AlarmInfo> {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String alarmId;

    private String alarmType;

    private String projectName;

    private String deviceId;

    private String deviceName;

    private String alarmContent;

    private String alarmReason;

    private String alarmTime;

    private String taskDescription;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String getAlarmReason() {
        return alarmReason;
    }

    public void setAlarmReason(String alarmReason) {
        this.alarmReason = alarmReason;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}