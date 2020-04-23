package com.wapwag.woss.modules.biz.entity;

import java.util.List;
import java.util.Map;

/**
    * @ClassName: ChartSeriesDto
    * @Description: TODO(图表返回类)
    * @author jiaxm
    * @date 2018年6月8日
    *
    */
public class ChartSeriesDto {

	    /**
	    * @Fields field:field:{todo}(名称)
	    */
	String name;

	List<String> xData;

	    /**
	    * @Fields field:field:{todo}(值，有值存为dubbo类型，没有值存为'-')
	    */
	List<Double> data;

	String unit;

	List<Map<String, Object>> serviceData;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<Map<String, Object>> getServiceData() {
		return serviceData;
	}

	public void setServiceData(List<Map<String, Object>> serviceData) {
		this.serviceData = serviceData;
	}

	public List<String> getxData() {
		return xData;
	}

	public void setxData(List<String> xData) {
		this.xData = xData;
	}
}
