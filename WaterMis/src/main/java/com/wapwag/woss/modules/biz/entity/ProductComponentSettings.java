package com.wapwag.woss.modules.biz.entity;

import com.wapwag.woss.common.persistence.DataEntity;

public class ProductComponentSettings extends DataEntity<ProductComponentSettings> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long productId;

    private Long componentId;

    private Integer serialNum;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }
}