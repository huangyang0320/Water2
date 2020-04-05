package com.wapwag.woss.common.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件解析类
 * 
 * @author gongll
 *
 */
public class TaskConfigInit {

	/** 定时任务配置信息 */
	private static List<TaskInfo> tasks = new ArrayList<TaskInfo>();

	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(TaskConfigInit.class);

	/**
	 * 解析定时任务配置文件
	 * 
	 * @return
	 */
	private static void initTaskConfig() {

		LOG.info("Begin init TaskConfigInit,initTaskConfig()");
		try {
			SAXBuilder sx = new SAXBuilder();
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("taskconfig.xml");
			Document doc = sx.build(is);
			Element rootelement = doc.getRootElement();

			List<Element> childs = rootelement.getChildren();
			for (int i = 0; i < childs.size(); i++) {
				TaskInfo taskInfo = new TaskInfo();
				taskInfo.setClassName(childs.get(i).getChildText("class"));
				taskInfo.setMethodName(childs.get(i).getChildText("method"));
				taskInfo.setIsNormal(childs.get(i).getChildText("isNormal"));
				taskInfo.setMode(childs.get(i).getChildText("mode"));
				taskInfo.setFirstTime(childs.get(i).getChildText("firstTime"));
				taskInfo.setDelay(childs.get(i).getChildText("delay"));
				taskInfo.setPeriod(childs.get(i).getChildText("period"));
				tasks.add(taskInfo);

			}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
		} catch (JDOMException e) {
			LOG.error(e.toString());
		} catch (IOException e) {
			LOG.error(e.toString());
		}

		LOG.info("End init TaskConfigInit,initTaskConfig()");
	}

	public static List<TaskInfo> getTasksConfig() {
		if (null == tasks || 0 == tasks.size()) {
			initTaskConfig();
		}
		return tasks;
	}

}
