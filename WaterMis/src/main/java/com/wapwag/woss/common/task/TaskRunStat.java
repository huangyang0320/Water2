package com.wapwag.woss.common.task;

public class TaskRunStat {

	private long runTime; //运行时间
	private String runStat; //运行状态，0：暂停，1：运行中
	public long getRunTime() {
		return runTime;
	}
	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}
	public String getRunStat() {
		return runStat;
	}
	public void setRunStat(String runStat) {
		this.runStat = runStat;
	}
}
