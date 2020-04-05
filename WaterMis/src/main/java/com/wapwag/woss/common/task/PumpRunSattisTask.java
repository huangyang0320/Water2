package com.wapwag.woss.common.task;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.common.utils.StatUtil;
import com.wapwag.woss.modules.sys.dao.PumpRunStatDao;

public class PumpRunSattisTask {

	private static PumpRunStatDao countDao = SpringContextHolder.getBean(PumpRunStatDao.class);
	private static Logger LOG = LoggerFactory.getLogger(PumpRunSattisTask.class);

	/**
	 * 定时加载统计信息
	 * 
	 * @Override
	 */
	public static void initRunStatis() {
		
	}

	private static String modBeinTime(List<String> times) {
		if (times.size() > 0) {
			try {
				long lastTime = StatUtil.sdf.parse(times.get(0)).getTime();
				lastTime = lastTime - StatUtil.ONE_HOUR;
				return StatUtil.sdf.format(new Date(lastTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

}