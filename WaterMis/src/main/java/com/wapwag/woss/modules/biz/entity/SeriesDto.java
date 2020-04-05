package com.wapwag.woss.modules.biz.entity;

import java.util.List;
import java.util.Map;

/**  
    * @ClassName: PerformanceMatrixDto
    * @Description: TODO(图表返回yDate类)
    * @author zx
    * @date 2018年10月10日
    *    
    */  
public class SeriesDto {

	private String name;
	private List<Double> data;

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
}
