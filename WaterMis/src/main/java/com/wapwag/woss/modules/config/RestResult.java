package com.wapwag.woss.modules.config;


/**
 * @author ChangWei Li
 * @version 2017-10-25 11:44
 */
public final class RestResult<T> {

    /**
     * 操作成功状态
     */
    public static final String OPT_SUCCESS_STATUS = "complete";

    /**
     * 操作失败状态
     */
    public static final String OPT_ERROR_STATUS = "error";

    /**
     * api执行结果状态：成功:complete 失败: error
     */
    private String status;//api执行结果状态：成功:complete 失败: error

    /**
     * 错误代码
     */
    private String errorCode;//错误代码

    /**
     * 错误信息
     */
    private String errorMessage;//错误信息

    /**
     * 返回结果数据
     */
    private T resultData;//返回结果数据

    private String doorSource;//门禁来源0无门禁1海康门禁0节点门禁

    public RestResult(String status, String errorCode, String errorMessage, T resultData) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.resultData = resultData;
    }
    public RestResult(String status, String errorCode, String errorMessage, T resultData,String doorSource) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.resultData = resultData;
        this.doorSource = doorSource;
    }

    public static <T> RestResult<T> success(T t) {
        return new RestResult<>("complete", null, null, t);
    }
    public static <T> RestResult<T> successDor(T t,String doorSource) {
        return new RestResult<>("complete", null, null, t,doorSource);
    }
    public static <T> RestResult<T> fail(String errorCode, String errorMessage) {
        return new RestResult<>("error", errorCode, errorMessage, null);
    }
    public static <T> RestResult<T> failDor(String errorCode, String errorMessage,String doorSource) {
        return new RestResult<>("error", errorCode, errorMessage, null,doorSource);
    }

    public static <T> RestResult<T> fail(ErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMessage());
    }
    public static <T> RestResult<T> failDor(ErrorCode errorCode,String doorSource) {
        return failDor(errorCode.getCode(), errorCode.getMessage(),doorSource);
    }

    public static <T> RestResult<T> failList(ErrorCode errorCode, T t) {
        return new RestResult<>(errorCode.getCode(), errorCode.getMessage(), null, t);
    }

    public RestResult() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
