package com.wapwag.woss.modules.sys.entity;

import com.wapwag.woss.common.persistence.DataEntity;

public class SysParameter extends DataEntity<SysParameter> {

	private String paramName;
	private String paramType;
	private String paramValue;
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
