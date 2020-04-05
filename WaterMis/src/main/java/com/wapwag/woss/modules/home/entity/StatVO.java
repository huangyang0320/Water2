package com.wapwag.woss.modules.home.entity;

public class StatVO {
	
	public StatVO(){
		
	}

	public StatVO(String maxName,double maxVal,String minName,double minVal){
		this.maxName = maxName;
		this.maxVal = maxVal;
		
		this.minName = minName;
		this.minVal = minVal;
	}
	private double maxVal;
	private String maxName;
	
	private double minVal;
	private String minName;
	public double getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(double maxVal) {
		this.maxVal = maxVal;
	}

	public String getMaxName() {
		return maxName;
	}

	public void setMaxName(String maxName) {
		this.maxName = maxName;
	}

	public double getMinVal() {
		return minVal;
	}

	public void setMinVal(double minVal) {
		this.minVal = minVal;
	}

	public String getMinName() {
		return minName;
	}

	public void setMinName(String minName) {
		this.minName = minName;
	}
}
