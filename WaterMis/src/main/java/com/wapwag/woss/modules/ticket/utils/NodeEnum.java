package com.wapwag.woss.modules.ticket.utils;

/**
 * @Classname DeviceStatus
 * @Description TODO
 * @Date 2020/04/13 11:55
 * @Created by 颜小能
 */
public enum NodeEnum {

    DISTRIBUTION("01", "分发"),
    SINGLE_BACK("02", "退单"),
    HANDLE("03", "处理"),
    UN_AGREE("04", "不同意"),
    AGREE("05", "同意");

    String value;
    String name;

    NodeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
