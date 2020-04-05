package com.wapwag.woss.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.modules.biz.service.ServiceValuesCurrentService;

public class CurrentValuesTask {
	private static ServiceValuesCurrentService currentService = SpringContextHolder.getBean(ServiceValuesCurrentService.class);
	private static Logger LOG = LoggerFactory.getLogger(ServicValuesHourTask.class);
	private static boolean flag = true;

	public static void stat() {
		if(flag) {
			try {
				flag = false;
				LOG.info("CurrentValuesTask stat....");
				currentService.saveCurrentValues();
				LOG.info("CurrentValuesTask end....");
			} finally {
				flag = true;
			}
		}

	}
}
