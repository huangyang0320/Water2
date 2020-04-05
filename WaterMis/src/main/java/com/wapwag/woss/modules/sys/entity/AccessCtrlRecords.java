package com.wapwag.woss.modules.sys.entity;

import java.util.Date;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 门禁信息
 * 
 * @author gongll
 *
 */
public class AccessCtrlRecords extends DataEntity<AccessCtrlRecords> {

	private String acEventId;
	private String eventType;
	private String eventTime;
	private String eventName;
	private String eventCode;

	private String eventCard;
	private String personId;
	private String personName;
	private String deptId;
	private String deptName;

	private String deptCode;
	private String deviceId;
	private String deviceName;
	private String deviceType;
	private String doorId;

	private String doorName;
	private String devicel1Id;
	private String devicel1Name;
	private String devicel1Type;
	private String devicel2Id;

	private String devicel2Name;
	private String devicel2Type;
	private String devicel3Id;
	private String devicel3Name;
	private String devicel3Type;

	private String regionId;
	private String regionName;
	private String controlUnitId;
	private String controlUnitName;
	private String tag;

	private String systemType;
	private String systemName;
	private String state;
	private String triggerRecord;
	private String remark;
	private int pageNumber;
	private int pageSize;
	private String beginTime;
	private String endTime;

	public String getAcEventId() {
		return acEventId;
	}

	public void setAcEventId(String acEventId) {
		this.acEventId = acEventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventCard() {
		return eventCard;
	}

	public void setEventCard(String eventCard) {
		this.eventCard = eventCard;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
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

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}

	public String getDoorName() {
		return doorName;
	}

	public void setDoorName(String doorName) {
		this.doorName = doorName;
	}

	public String getDevicel1Id() {
		return devicel1Id;
	}

	public void setDevicel1Id(String devicel1Id) {
		this.devicel1Id = devicel1Id;
	}

	public String getDevicel1Name() {
		return devicel1Name;
	}

	public void setDevicel1Name(String devicel1Name) {
		this.devicel1Name = devicel1Name;
	}

	public String getDevicel1Type() {
		return devicel1Type;
	}

	public void setDevicel1Type(String devicel1Type) {
		this.devicel1Type = devicel1Type;
	}

	public String getDevicel2Id() {
		return devicel2Id;
	}

	public void setDevicel2Id(String devicel2Id) {
		this.devicel2Id = devicel2Id;
	}

	public String getDevicel2Name() {
		return devicel2Name;
	}

	public void setDevicel2Name(String devicel2Name) {
		this.devicel2Name = devicel2Name;
	}

	public String getDevicel2Type() {
		return devicel2Type;
	}

	public void setDevicel2Type(String devicel2Type) {
		this.devicel2Type = devicel2Type;
	}

	public String getDevicel3Id() {
		return devicel3Id;
	}

	public void setDevicel3Id(String devicel3Id) {
		this.devicel3Id = devicel3Id;
	}

	public String getDevicel3Name() {
		return devicel3Name;
	}

	public void setDevicel3Name(String devicel3Name) {
		this.devicel3Name = devicel3Name;
	}

	public String getDevicel3Type() {
		return devicel3Type;
	}

	public void setDevicel3Type(String devicel3Type) {
		this.devicel3Type = devicel3Type;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getControlUnitId() {
		return controlUnitId;
	}

	public void setControlUnitId(String controlUnitId) {
		this.controlUnitId = controlUnitId;
	}

	public String getControlUnitName() {
		return controlUnitName;
	}

	public void setControlUnitName(String controlUnitName) {
		this.controlUnitName = controlUnitName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTriggerRecord() {
		return triggerRecord;
	}

	public void setTriggerRecord(String triggerRecord) {
		this.triggerRecord = triggerRecord;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime + " 23:59:59";
	}

}