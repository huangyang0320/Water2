package com.wapwag.woss.common.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.modules.home.dao.PumpDevStatusDao;
import com.wapwag.woss.modules.home.web.PumpDevStatus;
import com.wapwag.woss.modules.sys.dao.CountDao;

/**
 * 定时统计设备、泵房状态
 * @author gongll
 *
 */
public class PumpDevStatusTask {

//	@Autowired
//	private TreeTypeService treeTypeService;
	
	private static CountDao countDao = SpringContextHolder.getBean(CountDao.class);

	private static PumpDevStatusDao pumpDevStatusDao = SpringContextHolder.getBean(PumpDevStatusDao.class);
	
	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(PumpDevStatusTask.class);

	private static Map<String, String> devStatus = new HashMap<>();

	private static Map<String, String> pumpStatus = new HashMap<>();

	private static final SimpleDateFormat YMD = new SimpleDateFormat("yyyyMMdd");
	
	/** 定时任务失败再次执行的时间间隔 */
	private static long MAX_TIME = 60000;

	private static long runTime = 0L;
	
	private static String initType = "0";

	/**
	 * 定时加载统计信息
	 */
	public static void initValues() {
		LOG.info("Task PumpDevStatusTask begin is running");
		if (((System.currentTimeMillis() - runTime) < MAX_TIME)) {
			LOG.info("Task PumpDevStatusTask begin is stoped , task is running");
			return;
		}
		runTime = System.currentTimeMillis();

		queryData();
		initType = "1";

		LOG.info("Task CountValueUtil begin is stoped");
	}

	/**
	 * 查询设备、泵房在线信息
	 */
	private static void queryData() {
		Date dateLast = new Date();
		//String lastDate = "";//上次时间
		String tableTag = YMD.format(new Date());
		
		List<Map<String, String>> devStat = countDao.deviceStatus(tableTag);

		List<Map<String, String>> devPump = countDao.devPump();

		Map<String, String> teMap = new HashMap<>();
		List<PumpDevStatus> list = new ArrayList<PumpDevStatus>();

		for (int i = 0; i < devStat.size(); i++) {
			teMap.put(devStat.get(i).get("dev"), devStat.get(i).get("status"));
			//保存到数据库
			PumpDevStatus devstat = new PumpDevStatus();
			devstat.setDevid(devStat.get(i).get("dev"));
			devstat.setDevstatus(devStat.get(i).get("status"));
			devstat.setCreatetime(dateLast);
			devstat.setCreator("sys");
			list.add(devstat);
		}
		devStatus.clear();
		devStatus.putAll(teMap);
		//保存到内存  同时也记录到数据库表  如果需要 可以异步保存
//	    PumpDevStatus selectTimeByDevId = pumpDevStatusDao.selectTimeByDevId(list.get(0).getDevid());
//	    if(selectTimeByDevId!=null){
//	     SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		 lastDate = sDateFormat.format(selectTimeByDevId.getCreatetime());
//	    }
		if(list!=null && list.size()>0){
		   pumpDevStatusDao.insertBatch(list);
		}
		
		teMap = new HashMap<>();
		list = new ArrayList<PumpDevStatus>();
		for (int i = 0; i < devPump.size(); i++) {

			if ("1".equals(teMap.get(devPump.get(i).get("pump")))) {
				continue;
			}
			if (null != devStatus.get(devPump.get(i).get("dev"))) {
				teMap.put(devPump.get(i).get("pump"), devStatus.get(devPump.get(i).get("dev")));
				//保存数据库
				PumpDevStatus pumpState = new PumpDevStatus();
				pumpState.setPumpid(devPump.get(i).get("pump"));
				pumpState.setPumpstatus(devStatus.get(devPump.get(i).get("dev")));
				pumpState.setCreatetime(dateLast);
				pumpState.setCreator("sys");
				list.add(pumpState);
			}
		}
		pumpStatus.clear();
		pumpStatus.putAll(teMap);
		if(list!=null && list.size()>0){
		   pumpDevStatusDao.insertBatch(list);
		}
		//把上一次时间的数据 删除
	    SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    List<PumpDevStatus> selectTimeByTime = pumpDevStatusDao.selectTimeByTime(sDateFormat.format(dateLast));
	    if(selectTimeByTime!=null && selectTimeByTime.size()>0){
	    	Date afterDate = new Date(dateLast .getTime() - 600000);//当前时间减去三分钟  多保留一会当前数据。
	    	pumpDevStatusDao.deleteBycreateTime(sDateFormat.format(afterDate));
	    }
		
		teMap = new HashMap<>();

	}

	/**
	 * 所有泵房在线状态[0:在线,1：报警]
	 * @return
	 */
	public static Map<String, String> ALL_PUMP_STATS() {
		if ("0".equals(initType)) {
			queryData();
		}
		Map<String, String> temp = new HashMap<>();
		temp.putAll(pumpStatus);
		return temp;
	}
	
	/**
	 * 单个泵房在线状态[0:在线,1：报警]
	 * @return
	 */
	public static String ONE_PUMP_STATS(String pumpId) {
		if ("0".equals(initType)) {
			queryData();
		}
		if (null == pumpStatus.get(pumpId)) {
			return "";
		}
		return pumpStatus.get(pumpId);
	}
	
	/**
	 * 所有设备在线状态[0:在线,1：报警]
	 * @return
	 */
	public static Map<String, String> ALL_DEV_STATS() {
		if ("0".equals(initType)) {
			queryData();
		}
		Map<String, String> temp = new HashMap<>();
		temp.putAll(devStatus);
		return temp;
	}
	
	/**
	 * 单个设备在线状态[0:在线,1：报警]
	 * @return
	 */
	public static String ONE_DEV_STATS(String devId) {
		if ("0".equals(initType)) {
			queryData();
		}
		if (null == devStatus.get(devId)) {
			return "";
		}
		return devStatus.get(devId);
	}

}
