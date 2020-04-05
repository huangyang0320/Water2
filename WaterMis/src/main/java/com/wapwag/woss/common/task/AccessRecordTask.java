package com.wapwag.woss.common.task;

import java.awt.Robot;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.hkthirdsdk.ThirdUtil;
import com.wapwag.woss.common.hkthirdsdk.pojo.ConfigInfo;
import com.wapwag.woss.common.hkthirdsdk.pojo.DoorRecoad;
import com.wapwag.woss.common.hkthirdsdk.pojo.DoorRecordRequest;
import com.wapwag.woss.common.hkthirdsdk.pojo.ThirdDoorResult;
import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.sys.dao.AccessCtrlRecordsDao;

/**
 * 同步海康门径记录
 * 
 * @author gongll
 *
 */
public class AccessRecordTask {
	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(AccessRecordTask.class);
	
	public static Map<String, String> sysConfig = new HashMap<String, String>();

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static final String PAGE_SIZE = "100";
	
	private static long MAX_TIME = 60000;
	
	private static long runTime = 0;

	private static AccessCtrlRecordsDao accessMapper = SpringContextHolder.getBean(AccessCtrlRecordsDao.class);;

	/**
	 * 定时加载统计信息
	 */
	public static void initAccessRecorf() {
		LOG.debug("Enter AccessRecordTask");
		if (runTime != 0 && System.currentTimeMillis() - runTime < MAX_TIME) {
			return;
		}
		runTime = System.currentTimeMillis();
		try {
			initSysConfig();
			DoorRecordRequest requert = new DoorRecordRequest();
			requert.setBeginTime(getBeginTime());
			requert.setEndTime(getEndTime());
			requert.setPageIndex("1");
			requert.setPageSize(PAGE_SIZE);

			ThirdDoorResult result = getAccessRecord(requert);
			if (null != result && null != result.getDoorRecoads()
					&& result.getDoorRecoads().size() > 0) {
				saveAccessRecord(result.getDoorRecoads());
				for (int i = 2; i <= Integer.parseInt(result.getTotalPage()); i++) {
					requert.setPageIndex(i + "");
					result = getAccessRecord(requert);
					if (null != result && null != result.getDoorRecoads()
							&& result.getDoorRecoads().size() > 0) {
						saveAccessRecord(result.getDoorRecoads());
					}
				}
			}
		} catch (Exception e) {
		LOG.error(e.toString());
		}
		LOG.debug("Exit AccessRecordTask");
		
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

	private static ThirdDoorResult getAccessRecord(DoorRecordRequest request) {
		ThirdDoorResult result = ThirdUtil.accessRecord(request);
		return result;
	}

	private static void saveAccessRecord(List<DoorRecoad> doorRecoads) {
		try {
			accessMapper.saveAccessRecord(doorRecoads);

		} catch (Exception e) {
			LOG.error(e.toString());
		}

	}
	
	private static void initSysConfig(){
		List<ConfigInfo> configs = accessMapper.configInfos();
		for (int i = 0; i < configs.size(); i++) {
			sysConfig.put(configs.get(i).getParamName(), configs.get(i).getParamValue());
		}
	}
	
	public static String getSysConfigByKey(String key) {
		String value = sysConfig.get(key);
		if(value == null) {
			initSysConfig();
			value = sysConfig.get(key);
		}
		return value;
	}

}
