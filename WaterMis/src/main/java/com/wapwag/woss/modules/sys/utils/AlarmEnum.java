package com.wapwag.woss.modules.sys.utils;

public enum AlarmEnum {

    START_UP("1", "启用告警"),
    STOP("0", "禁止告警"),;

    String value;
    String name;

    AlarmEnum(String value, String name) {
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
