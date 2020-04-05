package com.wapwag.woss.common.task;

import java.lang.reflect.Method;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成TimerTask实例
 * @author gongll
 *
 */
public class TaskTimer {
	private TaskInfo taskInfo;
	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(TaskTimer.class);
	public TimerTask createTimer(TaskInfo info)
	{
		this.taskInfo = info;
		TimerTask task = new TimerTask() {
            public void run() {
            	try {
            		Class classType = Class.forName(taskInfo.getClassName());
            		Method getMethod = classType.getMethod(taskInfo.getMethodName());
            		getMethod.invoke(classType);
				} catch (Exception e) {
					LOG.error(e.toString() + " " + taskInfo.getClassName());
				}
            }
        };
        return task;
	}
}
