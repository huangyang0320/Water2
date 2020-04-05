package com.wapwag.woss.modules.sys.entity;

/**
 * 统计对象
 * 
 * @author gongll
 *
 */
public class StatTotalInfo {

	private String id;
	private String name;
	private String type;
	private String pv;
	private String memo;
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	private String beginTime;
	private String endTime;
	private String userDBName;
	private String tableName;
	private String insetTableName;
	private String areaId;
	public String getUx1() {
		return ux1;
	}

	public void setUx1(String ux1) {
		this.ux1 = ux1;
	}

	public String getUx2() {
		return ux2;
	}

	public void setUx2(String ux2) {
		this.ux2 = ux2;
	}

	public String getUx3() {
		return ux3;
	}

	public void setUx3(String ux3) {
		this.ux3 = ux3;
	}

	private String subIndex;
	private String ux1;
	private String ux2;
	private String ux3;
	

	public String getSubTag() {
		return subTag;
	}

	public void setSubTag(String subTag) {
		this.subTag = subTag;
	}

	private String subTag;

	public String getSubIndex() {
		return subIndex;
	}

	public void setSubIndex(String subIndex) {
		this.subIndex = subIndex;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUserDBName() {
		return userDBName;
	}

	public void setUserDBName(String userDBName) {
		this.userDBName = userDBName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getInsetTableName() {
		return insetTableName;
	}

	public void setInsetTableName(String insetTableName) {
		this.insetTableName = insetTableName;
	}
}
