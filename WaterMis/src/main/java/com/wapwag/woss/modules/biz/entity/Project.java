package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 项目Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class Project extends DataEntity<Project> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 项目名称
	private Regional region;		// 地区id
	private String projectManager;		// 项目经理
	private String address;		// 项目地址
	private String memo;		// 备忘录
	private String salesman;		// 业务员
	private String contractType;		// 合同类型
	private String buyerName;		// 购买方名称
	private String buyerContactsName;		// 购买方联系人
	private Date signDate;		// 签订日期
	private Date arrivalDate;		// 到货日期
	private Date yjtsDate;		// 预计调试日期
	private String htqdDw;		// 合同签订单位
	
	private String regionType;//区域类型 1 城区  2农村  3 山区
	private String companyNode;//水司子节点的ID
	private String pinyin;//拼音简写
	private String companyName;//水司名称
	
	private String waterDepartOrgnode;//组织机构水司节点
    private String waterHouseOrgnode;//组织机构水务所节点
    private String businessPlaceOrgnode;//组织机构营业所节点
	
	public Project() {
		super();
	}

	public Project(String id){
		super(id);
	}

	@Length(min=0, max=50, message="项目名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Regional getRegion() {
		return region;
	}

	public void setRegion(Regional region) {
		this.region = region;
	}
	
	@Length(min=0, max=50, message="项目经理长度必须介于 0 和 50 之间")
	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	
	@Length(min=0, max=500, message="项目地址长度必须介于 0 和 500 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=2000, message="备忘录长度必须介于 0 和 2000 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Length(min=0, max=20, message="业务员长度必须介于 0 和 20 之间")
	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	
	@Length(min=0, max=50, message="合同类型长度必须介于 0 和 50 之间")
	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	@Length(min=0, max=200, message="购买方名称长度必须介于 0 和 200 之间")
	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	
	@Length(min=0, max=20, message="购买方联系人长度必须介于 0 和 20 之间")
	public String getBuyerContactsName() {
		return buyerContactsName;
	}

	public void setBuyerContactsName(String buyerContactsName) {
		this.buyerContactsName = buyerContactsName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="签订日期不能为空")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="到货日期不能为空")
	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="预计调试日期不能为空")
	public Date getYjtsDate() {
		return yjtsDate;
	}

	public void setYjtsDate(Date yjtsDate) {
		this.yjtsDate = yjtsDate;
	}
	
	@Length(min=0, max=200, message="合同签订单位长度必须介于 0 和 200 之间")
	public String getHtqdDw() {
		return htqdDw;
	}

	public void setHtqdDw(String htqdDw) {
		this.htqdDw = htqdDw;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public String getCompanyNode() {
		return companyNode;
	}

	public void setCompanyNode(String companyNode) {
		this.companyNode = companyNode;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWaterDepartOrgnode() {
		return waterDepartOrgnode;
	}

	public void setWaterDepartOrgnode(String waterDepartOrgnode) {
		this.waterDepartOrgnode = waterDepartOrgnode;
	}

	public String getWaterHouseOrgnode() {
		return waterHouseOrgnode;
	}

	public void setWaterHouseOrgnode(String waterHouseOrgnode) {
		this.waterHouseOrgnode = waterHouseOrgnode;
	}

	public String getBusinessPlaceOrgnode() {
		return businessPlaceOrgnode;
	}

	public void setBusinessPlaceOrgnode(String businessPlaceOrgnode) {
		this.businessPlaceOrgnode = businessPlaceOrgnode;
	}
	
	
	
}