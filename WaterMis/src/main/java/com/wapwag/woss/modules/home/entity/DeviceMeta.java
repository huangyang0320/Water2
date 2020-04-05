package com.wapwag.woss.modules.home.entity;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * Device metadata entity
 * Created by Administrator on 2016/8/23.
 */
public class DeviceMeta extends DataEntity<DeviceMeta> {

    private String name;

    private String code;

    private String memo;

    private String type;

    private String unit;

    private String max;

    private String min;

    private String display;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
