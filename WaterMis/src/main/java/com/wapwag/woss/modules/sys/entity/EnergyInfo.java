package com.wapwag.woss.modules.sys.entity;

public class EnergyInfo {

	private String id;
	private String bPumpName;
	private String bEle;
	private String bFlow;

	private String lPumpName;
	private String lEle;
	private String lFlow;

	private String oPumpName;
	private String oEle;
	private String oFlow;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getbPumpName() {
		return bPumpName;
	}

	public void setbPumpName(String bPumpName) {
		this.bPumpName = bPumpName;
	}

	public String getbEle() {
		return bEle;
	}

	public void setbEle(String bEle) {
		this.bEle = bEle;
	}

	public String getbFlow() {
		return bFlow;
	}

	public void setbFlow(String bFlow) {
		this.bFlow = bFlow;
	}

	public String getlPumpName() {
		return lPumpName;
	}

	public void setlPumpName(String lPumpName) {
		this.lPumpName = lPumpName;
	}

	public String getlEle() {
		return lEle;
	}

	public void setlEle(String lEle) {
		this.lEle = lEle;
	}

	public String getlFlow() {
		return lFlow;
	}

	public void setlFlow(String lFlow) {
		this.lFlow = lFlow;
	}

	public String getoPumpName() {
		return oPumpName;
	}

	public void setoPumpName(String oPumpName) {
		this.oPumpName = oPumpName;
	}

	public String getoEle() {
		return oEle;
	}

	public void setoEle(String oEle) {
		this.oEle = oEle;
	}

	public String getoFlow() {
		return oFlow;
	}

	public void setoFlow(String oFlow) {
		this.oFlow = oFlow;
	}
}
