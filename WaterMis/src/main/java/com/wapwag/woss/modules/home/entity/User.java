package com.wapwag.woss.modules.home.entity;

import com.wapwag.woss.common.persistence.DataEntity;

import org.apache.ibatis.type.Alias;

/**
 * User entity
 * Created by Administrator on 2016/9/20 0020.
 */
@Alias("com.wapwag.woss.modules.home.entity.User")
public class User extends DataEntity<User> {
    private String userId;

    private String officeId;

    private String loginName;

    private String password;

    private String name;

    private String loginFlag;

    private String alarmRate;

    private String updateTime;
    
    private String newPassword;

    private String email;

    private String mobile;

    private String deviceId;

    private String alarmReceptionLevel;//告警接收级别
  /**
     * 一定记得加密
     */
    private String encryPassword;

    public String getUserId() {
        return userId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public String getAlarmRate() {
        return alarmRate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setAlarmRate(String alarmRate) {
		this.alarmRate = alarmRate;
		
	}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEncryPassword() {
        return encryPassword;
    }

    public void setEncryPassword(String encryPassword) {
        this.encryPassword = encryPassword;
    }

    public String getAlarmReceptionLevel() {
        return alarmReceptionLevel;
    }

    public void setAlarmReceptionLevel(String alarmReceptionLevel) {
        this.alarmReceptionLevel = alarmReceptionLevel;
    }
}
