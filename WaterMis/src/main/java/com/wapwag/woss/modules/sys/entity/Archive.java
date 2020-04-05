package com.wapwag.woss.modules.sys.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 档案信息
 * 
 * @author gongll
 *
 */
public class Archive {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;
	
	private String type;

	private String pumpHouseId;

	private String projectId;

	private Date dateManufacture;

	private Date datePurchase;

	private BigDecimal purchaseAmount;

	private Date createtime;

	private Date updatetime;

	public String getPumpHouseId() {
		return pumpHouseId;
	}

	public void setPumpHouseId(String pumpHouseId) {
		this.pumpHouseId = pumpHouseId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getDateManufacture() {
		return dateManufacture;
	}

	public void setDateManufacture(Date dateManufacture) {
		this.dateManufacture = dateManufacture;
	}

	public Date getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(Date datePurchase) {
		this.datePurchase = datePurchase;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	// 项目信息
	private List<Project> projectList;
	
	private List<PumpHouse> pumpList;

	// 水泵标牌信息
	private List<DevicePumpLabel> pumpLabels;

	// 成套设备标牌
	private List<DeviceSetLabel> setLabels;

	// 电器设备标牌
	private List<DeviceElectricalLabel> electricalLabels;	

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	public List<DevicePumpLabel> getPumpLabels() {
		return pumpLabels;
	}

	public void setPumpLabels(List<DevicePumpLabel> pumpLabels) {
		this.pumpLabels = pumpLabels;
	}

	public List<DeviceSetLabel> getSetLabels() {
		return setLabels;
	}

	public void setSetLabels(List<DeviceSetLabel> setLabels) {
		this.setLabels = setLabels;
	}

	public List<DeviceElectricalLabel> getElectricalLabels() {
		return electricalLabels;
	}

	public void setElectricalLabels(List<DeviceElectricalLabel> electricalLabels) {
		this.electricalLabels = electricalLabels;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<PumpHouse> getPumpList() {
		return pumpList;
	}

	public void setPumpList(List<PumpHouse> pumpList) {
		this.pumpList = pumpList;
	}

	
}
