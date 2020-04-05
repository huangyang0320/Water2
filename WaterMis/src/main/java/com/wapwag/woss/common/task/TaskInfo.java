package com.wapwag.woss.common.task;

/**
 * 定时任务对象
 * @author gongll
 *
 */
public class TaskInfo {
	
	/** 定时任务类 */
	private String className;
	
	/** 执行方法 */
	private String methodName;
	
	/** 执行方法 */
	private String isNormal;
	
	/** 执行方式 */
	private String mode;
	
	/** 开始执行时间 */
	private String firstTime;
	
	/** 延迟执行时间 */
	private String delay;
	
	/** 重复执行时间间隔 */
	private String period;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getIsNormal() {
		return isNormal;
	}
	public void setIsNormal(String isNormal) {
		this.isNormal = isNormal;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}
	public String getDelay() {
		return delay;
	}
	public void setDelay(String delay) {
		this.delay = delay;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
}
