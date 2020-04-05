package com.wapwag.woss.modules.biz.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;

public class AccListLifeDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8400173937477084315L;
	
	private Double[] value;
	private String[] xData;
	private int currentIndex;
	private double minValue;
	
	public AccListLifeDto() {
		super();
	}

	public Double[] getValue() {
		for(int i=0;i<value.length;i++) {
			value[i] = Double.parseDouble(new DecimalFormat("0.0##").format(value[i]));
		}
		return value;
	}

	public void setValue(Double[] value) {
		this.value = value;
	}

	public String[] getxData() {
		return xData;
	}

	public void setxData(String[] xData) {
		this.xData = xData;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public double getMinValue() {
		double min = 1;
		for(double v : value) {
			if(v<min) {
				min=v;
			}
		}
		if(min>=0.99) {
			minValue = 0.9;
		}else if(min>0.65) {
			minValue = 0.5;
		}else {
			minValue = 0;
		}
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

}
