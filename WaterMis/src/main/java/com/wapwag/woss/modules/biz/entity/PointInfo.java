package com.wapwag.woss.modules.biz.entity;

import java.io.Serializable;

public class PointInfo implements Serializable {
	
	private static final long serialVersionUID = -6290397603610293905L;
	
	private String pointCode;
	private String pointName;
	private String pointValue;
	private String unit;
	public String getPointCode() {
		return pointCode;
	}
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getPointValue() {
		return pointValue;
	}
	public void setPointValue(String pointValue) {
		this.pointValue = pointValue;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	

}
