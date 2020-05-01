package com.wapwag.woss.modules.ticket.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wapwag.woss.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

public class TicketDto extends DataEntity<TicketDto> {
    private String sortName;
    private String sortOrder;

    private String ticketId;
    private String ids;
    private List<String> idsList;
    private String ticketType;
    private String ticketTypeName;
    private String title;
    private String comments;
    private String ticketLevel;
    private String address;
    private Date eventTime;//告警生成时间
    private String deviceCode;
    private String deviceName;
    private String deviceName1;
    private String pumpId;
    private String pumpName;
    private String areaId;
    private String channel;
    private String channelName;
    private String reportName;
    private String reportPhone;
    private Date  startTime;//计划开始
    private String startBeginTime;//执行时间开始
    private String startEndTime;//执行时间结束
    private Date endTime;//计划结束
    private String createBeginTime;//新建开始 用于条件
    private String createEndTime;//新建截止  用于条件
    private String createTime;//等于当前日期的记录
    private String validFlag;
    private String createName;//创建人

    private String ticketReason;
    private String ticketDescription;
    private String bizId;
    private String status;
    private String currentStatusName;
    private String deptId;
    private String deptName;

    private String createByStr;
    private String updateBySre;

    private String saveOrCreateFlag;//保存还是创建

    private String isMyHandle;//待处理标志

    private String alarmStatus;

    private Date alarmTime;

    private String alarmLevel;

    private String confirmStatus;
    /**
     * 01：分发业务到人修改为处理状态3、
     * 02回退业务分子修改为待分发状态2、
     * 03处理业务修改为待待审核状态4
     * 04审核不同意业务修改为处理状态3
     * 05审核同意业务修改为完成状态5
     */
    private String handleStatus;//待处状态（有页面传入，走不同的业务流程分支 01：分发业务到人修改为处理状态3、02回退业务分子修改为待分发状态2、03处理业务修改为待待审核状态4）
    private String handleUserId;//待处理人;
    private String allHandleUser;//所有处理人

    private String mgName;//部门负责人姓名


    private String approveOpinion;//处理结果及其意见
    private String approveOperation;//处理类型 同意不同意

    private List<String> PumpHouseIds;

    private String clearTicketId;//删除告警工单时，需要清空告警表中 关联的工单ID


    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTicketLevel() {
        return ticketLevel;
    }

    public void setTicketLevel(String ticketLevel) {
        this.ticketLevel = ticketLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getPumpId() {
        return pumpId;
    }

    public void setPumpId(String pumpId) {
        this.pumpId = pumpId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportPhone() {
        return reportPhone;
    }

    public void setReportPhone(String reportPhone) {
        this.reportPhone = reportPhone;
    }


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }






    public String getTicketReason() {
        return ticketReason;
    }

    public void setTicketReason(String ticketReason) {
        this.ticketReason = ticketReason;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public String getCreateByStr() {
        return createByStr;
    }

    public void setCreateByStr(String createByStr) {
        this.createByStr = createByStr;
    }

    public String getUpdateBySre() {
        return updateBySre;
    }

    public void setUpdateBySre(String updateBySre) {
        this.updateBySre = updateBySre;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPumpName() {
        return pumpName;
    }

    public void setPumpName(String pumpName) {
        this.pumpName = pumpName;
    }

    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

    public String getIsMyHandle() {
        return isMyHandle;
    }

    public void setIsMyHandle(String isMyHandle) {
        this.isMyHandle = isMyHandle;
    }

    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public String getCreateBeginTime() {
        return createBeginTime;
    }

    public void setCreateBeginTime(String createBeginTime) {
        this.createBeginTime = createBeginTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
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

    public String getAllHandleUser() {
        return allHandleUser;
    }

    public void setAllHandleUser(String allHandleUser) {
        this.allHandleUser = allHandleUser;
    }

    public String getStartBeginTime() {
        return startBeginTime;
    }

    public void setStartBeginTime(String startBeginTime) {
        this.startBeginTime = startBeginTime;
    }

    public String getStartEndTime() {
        return startEndTime;
    }

    public void setStartEndTime(String startEndTime) {
        this.startEndTime = startEndTime;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getMgName() {
        return mgName;
    }

    public void setMgName(String mgName) {
        this.mgName = mgName;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getDeviceName1() {
        return deviceName1;
    }

    public void setDeviceName1(String deviceName1) {
        this.deviceName1 = deviceName1;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<String> getIdsList() {
        return idsList;
    }

    public void setIdsList(List<String> idsList) {
        this.idsList = idsList;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public List<String> getPumpHouseIds() {
        return PumpHouseIds;
    }

    public void setPumpHouseIds(List<String> pumpHouseIds) {
        PumpHouseIds = pumpHouseIds;
    }

    public String getSaveOrCreateFlag() {
        return saveOrCreateFlag;
    }

    public void setSaveOrCreateFlag(String saveOrCreateFlag) {
        this.saveOrCreateFlag = saveOrCreateFlag;
    }

    public String getClearTicketId() {
        return clearTicketId;
    }

    public void setClearTicketId(String clearTicketId) {
        this.clearTicketId = clearTicketId;
    }
}
