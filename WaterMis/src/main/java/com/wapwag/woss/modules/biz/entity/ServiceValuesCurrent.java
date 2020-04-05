package com.wapwag.woss.modules.biz.entity;

import java.io.Serializable;
import java.util.Date;

import com.wapwag.woss.common.persistence.DataEntity;

public class ServiceValuesCurrent extends DataEntity<ServiceValuesCurrent> implements Serializable {
	private static final long serialVersionUID = -1798766983244523151L;
	private String idDevice;

	private String idService;

	private String functionName;

	private String functionMemo;

	private String functionType;
	
	private Date dateTime;

	private Float pv;

	private Float min;

	private Float max;

	private String unit;

	public String getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(String idDevice) {
		this.idDevice = idDevice == null ? null : idDevice.trim();
	}

	public String getIdService() {
		return idService;
	}

	public void setIdService(String idService) {
		this.idService = idService == null ? null : idService.trim();
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName == null ? null : functionName.trim();
	}

	public String getFunctionMemo() {
		return functionMemo;
	}

	public void setFunctionMemo(String functionMemo) {
		this.functionMemo = functionMemo == null ? null : functionMemo.trim();
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Float getPv() {
		return pv;
	}

	public void setPv(Float pv) {
		this.pv = pv;
	}

	public Float getMin() {
		return min;
	}

	public void setMin(Float min) {
		this.min = min;
	}

	public Float getMax() {
		return max;
	}

	public void setMax(Float max) {
		this.max = max;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	
}