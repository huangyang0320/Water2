package com.wapwag.woss.modules.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
@ApiModel(value = "分页对象",description = "分页对象")
public class BootPage {
	@ApiModelProperty(name = "total",value = "返回的分页总记录数")
	private long total;
	@ApiModelProperty(name = "rows",value = "返回的分页集合数据")
	private List rows;
	
	public void setTotal(long total){
		this.total = total;
	}
	
	public long getTotal(){
		return total;
	}
	
	public void setRows(List rows){
		this.rows = rows;
	}
	
	public List getRows(){
		return rows;
	}

	public BootPage() {
	}

	public BootPage(long total, List rows) {
		this.total = total;
		this.rows = rows;
	}
}
