package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 地区Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class Regional extends DataEntity<Regional> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private Regional parent;		// parent_id
	private String parentIds;		// parent_ids
	private String type;		// type
	private String memo;		// 备注
	private String longi;		// longi
	private String lati;		// lati
	
	public Regional() {
		super();
	}

	public Regional(String id){
		super(id);
	}

	@Length(min=0, max=50, message="名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonBackReference
	public Regional getParent() {
		return parent;
	}

	public void setParent(Regional parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="parent_ids长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=1, message="type长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getLongi() {
		return longi;
	}

	public void setLongi(String longi) {
		this.longi = longi;
	}
	
	public String getLati() {
		return lati;
	}

	public void setLati(String lati) {
		this.lati = lati;
	}
	
}