package com.wapwag.woss.modules.home.entity;

public class RankData {
	
	public RankData(){
		
	}

	public RankData(String name,String y){
		this.name = name;
		this.y = y;
	}
	private String name;
	private String y;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
}
