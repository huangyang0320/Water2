package com.wapwag.woss.modules.sys.entity;

import java.util.Date;

public class UserDto {

    private String id;
    private String loginName;
    private String officeId;
    private String password;
    private String phone;
    private String photo;
    private String userType;
    private String loginIp;
    private Date loginDate;
    private Date updateDate;
    private String remarks;
    private String loginFlag;
    private String delFlag;
    private String alarmRate;
    private String treeTypes;
    private String isControl;
    private String alarmReceptionLevel;
    private String crolType;
    private String themeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getAlarmRate() {
        return alarmRate;
    }

    public void setAlarmRate(String alarmRate) {
        this.alarmRate = alarmRate;
    }

    public String getTreeTypes() {
        return treeTypes;
    }

    public void setTreeTypes(String treeTypes) {
        this.treeTypes = treeTypes;
    }

    public String getIsControl() {
        return isControl;
    }

    public void setIsControl(String isControl) {
        this.isControl = isControl;
    }

    public String getAlarmReceptionLevel() {
        return alarmReceptionLevel;
    }

    public void setAlarmReceptionLevel(String alarmReceptionLevel) {
        this.alarmReceptionLevel = alarmReceptionLevel;
    }

    public String getCrolType() {
        return crolType;
    }

    public void setCrolType(String crolType) {
        this.crolType = crolType;
    }

    public String getThemeType() {
        return themeType;
    }

    public void setThemeType(String themeType) {
        this.themeType = themeType;
    }
}
