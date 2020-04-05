package com.wapwag.woss.modules.ticket.Entity;

import com.wapwag.woss.common.persistence.DataEntity;
import com.wapwag.woss.modules.sys.entity.User;

import java.util.Date;

public class TicketToDoDto  extends DataEntity<TicketToDoDto> {
    private String id;
    private String ticketId;
    private String bizId;
    private String status;
    private String userId;
    private String validFlag;


    public TicketToDoDto(String id, String ticketId, String bizId, String status, String userId, String validFlag, Date createDate, Date updateDate, User updateBy,User createBy) {

        this.id=id;
        this.ticketId = ticketId;
        this.bizId = bizId;
        this.status = status;
        this.userId = userId;
        this.validFlag = validFlag;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.updateBy=updateBy;
        this.createBy= createBy;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }
}
