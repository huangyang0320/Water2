package com.wapwag.woss.common.hkthirdsdk.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")  
@XmlAccessorType(XmlAccessType.FIELD) 
public class DoorRecordRequest {

	private String sessionId;
	private String personIds;
	private String doorIds;
	private String tpyes;
	private String cards;
	
	private String beginTime;
	private String endTime;
	
	private String pageSize;
	private String pageIndex;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getPersonIds() {
		return personIds;
	}
	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}
	public String getDoorIds() {
		return doorIds;
	}
	public void setDoorIds(String doorIds) {
		this.doorIds = doorIds;
	}
	public String getTpyes() {
		return tpyes;
	}
	public void setTpyes(String tpyes) {
		this.tpyes = tpyes;
	}
	public String getCards() {
		return cards;
	}
	public void setCards(String cards) {
		this.cards = cards;
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
		this.endTime = endTime;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
}
