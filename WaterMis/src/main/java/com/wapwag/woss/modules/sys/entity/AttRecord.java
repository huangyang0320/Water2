package com.wapwag.woss.modules.sys.entity;

import com.wapwag.woss.common.persistence.DataEntity;

public class AttRecord  extends DataEntity<AttRecord> {
	private String attRecordId;
	private String personId;
	private String personName;
	private String cardNum;
	
	public String getAttRecordId() {
		return attRecordId;
	}
	public void setAttRecordId(String attRecordId) {
		this.attRecordId = attRecordId;
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
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getAcReaderId() {
		return acReaderId;
	}
	public void setAcReaderId(String acReaderId) {
		this.acReaderId = acReaderId;
	}
	public String getAcReaderName() {
		return acReaderName;
	}
	public void setAcReaderName(String acReaderName) {
		this.acReaderName = acReaderName;
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
	public String getControllerId() {
		return controllerId;
	}
	public void setControllerId(String controllerId) {
		this.controllerId = controllerId;
	}
	public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getAttReaderName() {
		return attReaderName;
	}
	public void setAttReaderName(String attReaderName) {
		this.attReaderName = attReaderName;
	}
	private String departId;
	private String departName;
	private String acReaderId;
	private String acReaderName;
	
	private String doorId;
	private String doorName;
	private String controllerId;
	private String controllerName;
	
	private String eventTime;
	private String eventType;
	private String recordType;
	private String attReaderName;
	private String beginTime;
	private String endTime;
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
