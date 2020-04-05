package com.wapwag.woss.modules.ticket.Entity;

import com.wapwag.woss.common.persistence.DataEntity;


public class TicketLogDto  extends DataEntity<TicketLogDto> {

    private String id;
    private String  ticketId;
    private String  status;
    private String approveOpinion;
    private String approveOperation;
    private String currentStatusName;
    private String ticketLogName;
    private String validFlag;
    private String nodeName;
    private String nodeId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproveOpinion() {
        return approveOpinion;
    }

    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion;
    }

    public String getApproveOperation() {
        return approveOperation;
    }

    public void setApproveOperation(String approveOperation) {
        this.approveOperation = approveOperation;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

    public String getTicketLogName() {
        return ticketLogName;
    }

    public void setTicketLogName(String ticketLogName) {
        this.ticketLogName = ticketLogName;
    }
}
