package com.wapwag.woss.modules.home.entity;

import com.wapwag.woss.common.persistence.DataEntity;

public class ProjectInfo extends DataEntity<ProjectInfo> {

    private static final long serialVersionUID = 1L;

    private String projectId;

    private String projectName;

    private String projectManager;

    private String projectAddress;

    private String projectMemo;

    private String salesman;

    private String contractType;

    private String buyerName;

    private String buyerContactsName;

    private String signDate;

    private String arrivalDate;

    private String yjtsDate;

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

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getYjtsDate() {
        return yjtsDate;
    }

    public void setYjtsDate(String yjtsDate) {
        this.yjtsDate = yjtsDate;
    }

    public String getHtqdDw() {
        return htqdDw;
    }

    public void setHtqdDw(String htqdDw) {
        this.htqdDw = htqdDw;
    }

}