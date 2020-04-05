package com.wapwag.woss.modules.home.web;

import java.util.Date;

public class PumpDevStatus {
    private Integer id;

    private String pumpid;

    private String pumpstatus;

    private String devid;

    private String devstatus;

    private Date createtime;

    private String creator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPumpid() {
        return pumpid;
    }

    public void setPumpid(String pumpid) {
        this.pumpid = pumpid == null ? null : pumpid.trim();
    }

    public String getPumpstatus() {
        return pumpstatus;
    }

    public void setPumpstatus(String pumpstatus) {
        this.pumpstatus = pumpstatus == null ? null : pumpstatus.trim();
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid == null ? null : devid.trim();
    }

    public String getDevstatus() {
        return devstatus;
    }

    public void setDevstatus(String devstatus) {
        this.devstatus = devstatus == null ? null : devstatus.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }
}