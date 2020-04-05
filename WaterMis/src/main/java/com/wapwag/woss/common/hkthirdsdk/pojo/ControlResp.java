package com.wapwag.woss.common.hkthirdsdk.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "body")
public class ControlResp {

	@XmlElement
	private String itemNum;
	
	@XmlElement(name = "item") 
	private List<ControlUnit> controlUnits;

	@XmlTransient
	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	@XmlTransient
	public List<ControlUnit> getControlUnits() {
		return controlUnits;
	}

	public void setControlUnits(List<ControlUnit> controlUnits) {
		this.controlUnits = controlUnits;
	}
}
