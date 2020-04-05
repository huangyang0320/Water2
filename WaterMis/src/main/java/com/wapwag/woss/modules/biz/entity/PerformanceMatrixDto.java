package com.wapwag.woss.modules.biz.entity;

import java.util.List;
import java.util.Map;

/**  
    * @ClassName: PerformanceMatrixDto
    * @Description: TODO(图表返回类)  
    * @author zx
    * @date 2018年10月10日
    *    
    */  
public class PerformanceMatrixDto {
	  
	    /**
	    * @Fields field:field:{todo}(值，有值存为dubbo类型，没有值存为'-')  
	    */  
	List<Double> xData;//x轴坐标值

	List<SeriesDto> yData;//y轴坐标值

	String unit;

    public List<Double> getxData() {
        return xData;
    }

    public void setxData(List<Double> xData) {
        this.xData = xData;
    }

    public List<SeriesDto> getyData() {
		return yData;
	}

	public void setyData(List<SeriesDto> yData) {
		this.yData = yData;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
