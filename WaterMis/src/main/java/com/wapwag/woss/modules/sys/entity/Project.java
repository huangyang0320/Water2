package com.wapwag.woss.modules.sys.entity;

import java.util.Date;

public class Project {
    private String projectId;

    private String projectName;

    private String regionId;

    private String projectManager;

    private String projectAddress;

    private String projectMemo;

    private String salesman;

    private String contractType;

    private String buyerName;

    private String buyerContactsName;

    private Date signDate;

    private Date arrivalDate;

    private Date yjtsDate;

    private String htqdDw;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getProjectMemo() {
        return projectMemo;
    }

    public void setProjectMemo(String projectMemo) {
        this.projectMemo = projectMemo;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerContactsName() {
        return buyerContactsName;
    }

    public void setBuyerContactsName(String buyerContactsName) {
        this.buyerContactsName = buyerContactsName;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getYjtsDate() {
        return yjtsDate;
    }

    public void setYjtsDate(Date yjtsDate) {
        this.yjtsDate = yjtsDate;
    }

    public String getHtqdDw() {
        return htqdDw;
    }

    public void setHtqdDw(String htqdDw) {
        this.htqdDw = htqdDw;
    }
}