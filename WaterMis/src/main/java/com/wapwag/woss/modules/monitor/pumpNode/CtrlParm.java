package com.wapwag.woss.modules.monitor.pumpNode;

/**
 * @author leo
 * @since 2018/4/19 13:51
 */
public class CtrlParm {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CtrlParm() {
    }

    public CtrlParm(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
