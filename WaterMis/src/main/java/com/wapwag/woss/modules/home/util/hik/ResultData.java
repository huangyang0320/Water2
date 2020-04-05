package com.wapwag.woss.modules.home.util.hik;

import java.util.List;
import java.util.Map;

/**
 * 海康接口返回对象
 * 
 * @author gongll
 *
 */
public class ResultData {

	private int total;
	private int pageNo;
	private int pageSize;
	private List<Map<String, Object>> list;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

}
