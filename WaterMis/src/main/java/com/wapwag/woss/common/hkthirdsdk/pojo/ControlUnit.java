package com.wapwag.woss.common.hkthirdsdk.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")  
@XmlAccessorType(XmlAccessType.FIELD)  
public class ControlUnit {

	private String controlUnitId;
	private String parentId;
	private String name;
	private String sysCode;
	private String description;
	
	public String getControlUnitId() {
		return controlUnitId;
	}
	public void setControlUnitId(String controlUnitId) {
		this.controlUnitId = controlUnitId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
