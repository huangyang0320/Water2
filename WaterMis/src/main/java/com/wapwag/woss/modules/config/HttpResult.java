package com.wapwag.woss.modules.config;

/**
 * @author leo
 * @since 2018/4/19 13:40
 */
public class HttpResult {

    private int code;
    private String body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HttpResult() {

    }

    public HttpResult(int code, String body) {
        super();
        this.code = code;
        this.body = body;
    }

}
