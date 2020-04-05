package com.wapwag.woss.common.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.hkthirdsdk.ThirdUtil;
import com.wapwag.woss.common.hkthirdsdk.pojo.ClockRecord;
import com.wapwag.woss.common.hkthirdsdk.pojo.ClockRecordResult;
import com.wapwag.woss.common.hkthirdsdk.pojo.ConfigInfo;
import com.wapwag.woss.common.hkthirdsdk.pojo.ThirdClockResult;
import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.sys.dao.AttendanceDao;

public class AttendanceTask {
	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(AttendanceTask.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static final String PAGE_SIZE = "100";

	private static long MAX_TIME = 60000;

	private static long runTime = 0;

	private static AttendanceDao accessMapper = SpringContextHolder
			.getBean(AttendanceDao.class);

	/**
	 * 定时加载统计信息
	 */
	public static void initAttRecorf() {
		LOG.debug("Enter AttendanceTask");
		if (runTime != 0 && System.currentTimeMillis() - runTime < MAX_TIME) {
			return;
		}

		runTime = System.currentTimeMillis();
		try {
			initSysConfig();
			LOG.debug("sessionID " + ThirdUtil.qrySessionId());
			List<String> departmentIds = ThirdUtil.getDepartments();
			ClockRecord requert = new ClockRecord();
			requert.setBeginTime(getBeginTime()); // mod
			requert.setEndTime(getEndTime()); // mod
			for (int j = 0; j < departmentIds.size(); j++) {
				requert.setPageIndex("1");
				requert.setPageSize(PAGE_SIZE);
				requert.setDepartmentId(departmentIds.get(j));
				ThirdClockResult result = getAccessRecord(requert);
				if (null != result && null != result.getResult()
						&& result.getResult().size() > 0) {
					saveAccessRecord(result.getResult());
					for (int i = 2; i <= Integer
							.parseInt(result.getTotalPage()); i++) {
						requert.setPageIndex(i + "");
						result = getAccessRecord(requert);
						if (null != result && null != result.getResult()
								&& result.getResult().size() > 0) {
							saveAccessRecord(result.getResult());
						}
					}
				}
			}

		} catch (Exception e) {
			LOG.error(e.toString());
		}
		LOG.debug("Exit AttendanceTask");

	}

	/**
	 * 获取门禁记录最新的时间
	 * 
	 * @return
	 */
	private static String getBeginTime() {
		String begomTime = accessMapper.getBeginTime();
		if (!StringUtils.isEmp(begomTime)) {
			try {
				Date date = sdf.parse(begomTime);
				date = new Date(date.getTime() + 1000);
				return sdf.format(date);

			} catch (Exception e) {
				LOG.error(e.toString());
			}
			return begomTime;
		}
		return "2016-12-12 00:00:00";
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	private static String getEndTime() {
		return sdf.format(new Date());
	}

	private static ThirdClockResult getAccessRecord(ClockRecord request) {
		ThirdClockResult result = ThirdUtil.getClockRecordPage(request);
		return result;
	}

	private static void saveAccessRecord(List<ClockRecordResult> doorRecoads) {
		try {
			accessMapper.saveAccessRecord(doorRecoads);

		} catch (Exception e) {
			LOG.error(e.toString());
		}

	}

	private static void initSysConfig() {
		List<ConfigInfo> configs = accessMapper.configInfos();
		for (int i = 0; i < configs.size(); i++) {
			AccessRecordTask.sysConfig.put(configs.get(i).getParamName(),
					configs.get(i).getParamValue());
		}
	}

}
