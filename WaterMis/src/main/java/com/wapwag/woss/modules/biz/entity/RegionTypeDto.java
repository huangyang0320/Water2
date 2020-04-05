package com.wapwag.woss.modules.biz.entity;

public class RegionTypeDto {
	private String regionId;
	
	private String regionType;
	
	private String pumpHouseId;
	
	private String companyId;//水司ID

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getPumpHouseId() {
		return pumpHouseId;
	}

	public void setPumpHouseId(String pumpHouseId) {
		this.pumpHouseId = pumpHouseId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	

}
