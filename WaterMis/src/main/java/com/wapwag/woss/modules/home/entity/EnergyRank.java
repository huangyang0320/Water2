package com.wapwag.woss.modules.home.entity;

public class EnergyRank {

	private String topPumpName;
	private String topEnergy;
	private String topFlow;
	
	public void addTop(String pumpName,String energy,String flow){
		this.topPumpName = pumpName;
		this.topEnergy = energy;
		this.topFlow = flow;
	}
	
	private String highPumpName;
	private String highEnergy;
	private String HighFlow;
	
	public void addHigh(String pumpName,String energy,String flow){
		this.highPumpName = pumpName;
		this.highEnergy = energy;
		this.HighFlow = flow;
	}
	
	private String lastPumpName;
	private String lastEnergy;
	private String lastFlow;
	
	public void addLast(String pumpName,String energy,String flow){
		this.lastPumpName = pumpName;
		this.lastEnergy = energy;
		this.lastFlow = flow;
	}
	
	public String getTopPumpName() {
		return topPumpName;
	}
	public void setTopPumpName(String topPumpName) {
		this.topPumpName = topPumpName;
	}
	public String getTopEnergy() {
		return topEnergy;
	}
	public void setTopEnergy(String topEnergy) {
		this.topEnergy = topEnergy;
	}
	public String getTopFlow() {
		return topFlow;
	}
	public void setTopFlow(String topFlow) {
		this.topFlow = topFlow;
	}
	public String getHighPumpName() {
		return highPumpName;
	}
	public void setHighPumpName(String highPumpName) {
		this.highPumpName = highPumpName;
	}
	public String getHighEnergy() {
		return highEnergy;
	}
	public void setHighEnergy(String highEnergy) {
		this.highEnergy = highEnergy;
	}
	public String getHighFlow() {
		return HighFlow;
	}
	public void setHighFlow(String highFlow) {
		HighFlow = highFlow;
	}
	public String getLastPumpName() {
		return lastPumpName;
	}
	public void setLastPumpName(String lastPumpName) {
		this.lastPumpName = lastPumpName;
	}
	public String getLastEnergy() {
		return lastEnergy;
	}
	public void setLastEnergy(String lastEnergy) {
		this.lastEnergy = lastEnergy;
	}
	public String getLastFlow() {
		return lastFlow;
	}
	public void setLastFlow(String lastFlow) {
		this.lastFlow = lastFlow;
	}
}
