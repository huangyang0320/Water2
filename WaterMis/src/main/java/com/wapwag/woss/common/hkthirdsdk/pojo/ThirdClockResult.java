package com.wapwag.woss.common.hkthirdsdk.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "body")
public class ThirdClockResult {

	@XmlElement
	private String total;
	
	@XmlElement
	private String totalPage;
	
	@XmlElement  
	private String pageIndex;
	
	@XmlElement  
	private String pageSize;
	
	@XmlElement(name = "item") 
	private List<ClockRecordResult> result;

	@XmlTransient
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@XmlTransient
	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	@XmlTransient
	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	@XmlTransient
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	@XmlTransient
	public List<ClockRecordResult> getResult() {
		return result;
	}

	public void setResult(List<ClockRecordResult> result) {
		this.result = result;
	}

	
	
}
