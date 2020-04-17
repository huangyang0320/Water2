package com.wapwag.woss.modules.biz.entity;

import com.wapwag.woss.common.persistence.DataEntity;
import com.wapwag.woss.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 通知Entity
 * Created by Fyc on 2020/4/17.
 */
public class NoticeDto extends DataEntity<NoticeDto> {
    private static final long serialVersionUID = 1L;

    private String bizId;  //业务标识

    private String noticeStatus;  //通知状态：01待查看，02已查看，03忽略

    private String noticeTitle;  //通知标题

    private String noticeType;  //通知类型，01告警工单，02巡检工单，03告维保工单

    private String noticeContent;  //通知内容

    private String noticeGroupObj;  //通知分组：01:个人，02：部门：03：所有人

    private String noticeGroupRefId;  //通知分组关系ID，用于存人的id/部门的id/其他

    private User deleteBy;  //删除人

    private Date deleteDate;  //删除时间

    public NoticeDto() {
    }

    public NoticeDto(String id) {
        super(id);
    }

    @Length(min=0, max=50, message="业务标识长度必须介于 0 和 50 之间")
    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    @Length(min=0, max=2, message="通知状态长度必须介于 0 和 2 之间")
    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    @Length(min=0, max=200, message="通知标题长度必须介于 0 和 200 之间")
    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    @Length(min=0, max=50, message="通知类型长度必须介于 0 和 50 之间")
    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    @Length(min=0, max=500, message="通知内容长度必须介于 0 和 500 之间")
    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    @Length(min=0, max=2, message="通知分组长度必须介于 0 和 2 之间")
    public String getNoticeGroupObj() {
        return noticeGroupObj;
    }

    public void setNoticeGroupObj(String noticeGroupObj) {
        this.noticeGroupObj = noticeGroupObj;
    }

    @Length(min=0, max=200, message="通知分组关系长度必须介于 0 和 200 之间")
    public String getNoticeGroupRefId() {
        return noticeGroupRefId;
    }

    public void setNoticeGroupRefId(String noticeGroupRefId) {
        this.noticeGroupRefId = noticeGroupRefId;
    }

    public User getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(User deleteBy) {
        this.deleteBy = deleteBy;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
}
