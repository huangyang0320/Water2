package com.wapwag.woss.modules.home.entity;

public class HundredVO {
	
	
	public void addPv(double pv,double energy,double flow){
		this.energy += energy;
		this.pv += pv;
		this.flow += flow;
		this.valSize ++;
	}
	
	public void statVal(){
		this.pv = this.pv/valSize;
	}
	
	private String devID;
	private String devName;
	private String pumpId;
	private String pumpName;
	
	private String unit;
	
	private double pv;
	
	private double flow;
	
	private double energy;
	
	private double inter;
	
	private double out;
	
	private int valSize;

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getInter() {
		return inter;
	}

	public void setInter(double inter) {
		this.inter = inter;
	}

	public double getOut() {
		return out;
	}

	public void setOut(double out) {
		this.out = out;
	}

	public String getDevID() {
		return devID;
	}

	public void setDevID(String devID) {
		this.devID = devID;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getPumpId() {
		return pumpId;
	}

	public void setPumpId(String pumpId) {
		this.pumpId = pumpId;
	}

	public String getPumpName() {
		return pumpName;
	}

	public void setPumpName(String pumpName) {
		this.pumpName = pumpName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPv() {
		return pv;
	}

	public void setPv(double pv) {
		this.pv = pv;
	}

	public int getValSize() {
		return valSize;
	}

	public void setValSize(int valSize) {
		this.valSize = valSize;
	}
	
}
