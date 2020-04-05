package com.wapwag.woss.common.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时任控制管理入口
 * @author gongll
 *
 */
public class TaskControl implements Runnable {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(TaskControl.class);

	//web。xml使用实例
	public static void main(String[] args) {
		TaskControl.initTask();
	}

	/**
	 * run方法
	 */
	public void run() {
		LOG.info("Task is init");
		TaskControl taskControl = new TaskControl();
		taskControl.init();
	}

	private static List<Timer> timerList = new ArrayList<>();

	// provide timer close hook when application redeployed
	public static void stopTask() {
		if (!timerList.isEmpty()) {
			for (Timer timer : timerList) {
				timer.cancel();
			}
		}
	}

	/**
	 * 初始化定时任务
	 */
	public static void initTask()
	{
		Thread taskThread = new Thread(new TaskControl());
		taskThread.start();
	}

	/**
	 * 定时任务具体实现方法
	 */
	private void init() {

		//加载配置文件
		List<TaskInfo> tasks = TaskConfigInit.getTasksConfig();

		if (null == tasks || 0 == tasks.size()) {
			System.out.println("Init TASK ,getTasksConfig the value is null");
			return;
		}

		//开始时间
		Date firstTime = null;
		//延迟执行时间
		long delay = 0;
		//重复执行时间间隔
		long period = 0;

		for (int i = 0; i < tasks.size(); i++) {
			TaskInfo taskInfo = tasks.get(i);
			if (!"0".equals(taskInfo.getIsNormal())) {
				continue;
			}
			TaskTimer taskTimer = new TaskTimer();
			TimerTask task = taskTimer.createTimer(taskInfo);

            Timer timer = new Timer();
            //save timer for stop use
			timerList.add(timer);

            //0:指定的时间开始，按照指定的时间重复执行.
            if ("0".equals(taskInfo.getMode()))
            {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            	firstTime = createFirstTime(sdf.format(new Date()) +" "+ taskInfo.getFirstTime());
            	period = createLong(taskInfo.getPeriod());
            	timer.scheduleAtFixedRate(task, firstTime,period);

			}
            //1:系统启动延迟多长时间后，按照指定的时间重复执行.
            else if ("1".equals(taskInfo.getMode()))
			{
            	delay = createLong(taskInfo.getDelay());
				period = createLong(taskInfo.getPeriod());
            	timer.schedule(task, delay,period);

			}
            //2:系统启动延迟多长时间后执行，仅执行一次
            else if ("2".equals(taskInfo.getMode()))
			{
            	delay = createLong(taskInfo.getDelay());
            	timer.schedule(task, delay);

			}
            //3:按照指定的时间执行，仅执行一次
            else if ("3".equals(taskInfo.getMode())) {
            	firstTime = createFirstTime(taskInfo.getFirstTime());
            	timer.schedule(task, firstTime);
			}

		}
    }

	private Date createFirstTime(String configFirstTime)
	{
		Date firstTime = null ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			firstTime = sdf.parse(configFirstTime);
		} catch (ParseException e) {
			LOG.error(e.toString());
		}
		return firstTime;
	}

	private long createLong(String configValue)
	{
		/*if(StringUtils.isBlank(configValue)){
			configValue="0";
		}*/
		long sysValue = Long.parseLong(configValue);
		return sysValue == 0 ? 2000:sysValue;
	}
}
