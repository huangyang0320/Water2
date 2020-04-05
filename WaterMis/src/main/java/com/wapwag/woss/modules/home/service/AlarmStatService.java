package com.wapwag.woss.modules.home.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wapwag.woss.modules.biz.entity.WorkOrder;
import com.wapwag.woss.modules.config.HttpResult;
import com.wapwag.woss.modules.config.RestResult;
import com.wapwag.woss.modules.home.entity.*;
import com.wapwag.woss.modules.monitor.service.HttpAPIService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.modules.home.dao.AlarmStatMapper;
import com.wapwag.woss.modules.sys.entity.BootPage;

/**
 * 告警统计
 * @author leo
 * @since 2018/3/23 16:16
 */
@Service
public class AlarmStatService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String CREATEWORKE = "/WaterMisTicket/createTicket";

    private static final String CONDITION = "/WaterMisTicket/getCondition";

    @Autowired
	private AlarmStatMapper alarmStatMapper;

	@Autowired
	public AlarmStatService(AlarmStatMapper alarmStatMapper) {
		this.alarmStatMapper = alarmStatMapper;
	}

	@Value(value = "${workOrderUrl}")
	private String workOrderUrl;

	public boolean insertAlarmSendStatus(List<AlarmSendStatus> list){
		return alarmStatMapper.insertAlarmSendStatus(list)>0;
	}

	public boolean updateAlarmSendStatusByAlarmStatisticsId(List<AlarmSendStatus> list){
		return alarmStatMapper.updateAlarmSendStatusByAlarmStatisticsId(list)>0;
	}

	public List<AlarmStat>  getAlarmDetailBySendStatus(){
		return alarmStatMapper.getAlarmDetailBySendStatus();
	}

	/**
	 * 分页查询
	 * @param entityPage
	 * @return
	 */
	public BootPage list(QryObject entityPage) {

		BootPage page = new BootPage();

		int total = alarmStatMapper.countAlarm(entityPage);
		page.setTotal(total);
		if (total < 1) {
			page.setRows(new ArrayList<AlarmDTO>());
			return page;
		}

		entityPage.setPageNumber((entityPage.getPageNumber() - 1) * entityPage.getPageSize());

		page.setRows(alarmStatMapper.selectListPage(entityPage));

		return page;
	}

	/**
	 * 导出数据
	 * @param entityPage
	 * @return
	 */
	public BootPage expList(QryObject entityPage) {

		BootPage page = new BootPage();

		page.setRows(alarmStatMapper.expSelectListPage(entityPage));
		page.setTotal(page.getRows().size());

		return page;
	}
	
	/**
	 * 统计告警级别
	 * @param entityPage
	 * @return
	 */
	public List<AlarmStat>  statLevel(QryObject entityPage) {

		return alarmStatMapper.statLevel(entityPage);
	}
	
	/**
	 * 统计告警类型
	 * @param entityPage
	 * @return
	 */
	public List<AlarmStat>  statType(QryObject entityPage) {

		return alarmStatMapper.statType(entityPage);
	}

	
	/**
	 * 统计告警时间
	 * @param qryObject
	 * @return
	 */
	public List<AlarmStat> statTime(QryObject qryObject) {
		return alarmStatMapper.statTime(qryObject);
	}
	
	/**
	 * 首页告警列表
	 * @param userId
	 * @return
	 */
	public List<AlarmStat> getAlarmDetail(String userId) {
		return alarmStatMapper.getAlarmDetail(userId);
	}
	
	/**
	 * 告警个数
	 * @param userId
	 * @return
	 */
	public String countAlarms(String userId) {
		return alarmStatMapper.countAlarms(userId);
	}

	public String indexConfig(AlarmStat info) {
		String id = info.getId();
		info.setDeviceId(id.split("_")[0]);
		info.setStartDate(id.split("_")[2]);
		info.setDeviceName(id.split("_")[1]);
		int result = alarmStatMapper.indexConfig(info); 
		return "Success";
	}

	public AlarmStat getAlarmById(AlarmStat info) {
		String id = info.getId();
		info.setDeviceId(id.split("_")[0]);
		info.setStartDate(id.split("_")[2]);
		info.setDeviceName(id.split("_")[1]);
		return alarmStatMapper.getAlarmById(info); 
	}

	public String submitWorkOrder(WorkOrder workOrder,User user) {
        workOrder.setCreate(user.getLoginName());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String s = gson.toJson(workOrder);
        logger.info("submitWorkOrder创建工单开始"+new Date());
        logger.info("创建工单参数==="+s);
        AlarmStatistics alarmStatistics =new AlarmStatistics();
		Map<String,String> map = new HashMap<>();
		map.put("status","fail");
		String rest = null;
		String url = workOrderUrl+CREATEWORKE;
        try {
			HttpResult result = HttpAPIService.httpPost(url, s);
			logger.info("创建工单返回"+gson.toJson(result));
            if(result.getCode()!=200){
				map.put("message","工单创建网络连接异常");
				rest = gson.toJson(map);
                logger.error(rest);
				return rest;
            }
            if(StringUtils.isBlank(result.getBody())){
				map.put("message","工单创建接口返回码为空");
				rest = gson.toJson(map);
				logger.error(rest);
				return rest;
            }
            logger.info(result.getBody());
            RestResult restResult = gson.fromJson(result.getBody(), RestResult.class);
            if(restResult.getStatus() == null || !restResult.getStatus().equals("complete")
					|| restResult.getResultData() == null || restResult.getResultData().toString().equals("")){
				map.put("message","创建工单失败");
				rest = gson.toJson(map);
				logger.error(rest);
				return rest;
            }

            alarmStatistics.setDeviceCode(workOrder.getProcessObject());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            alarmStatistics.setStartDate(sdf.parse(workOrder.getAlarmTime()));
            alarmStatistics.setTicketid(restResult.getResultData().toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message","创建工单出现异常");
			rest = gson.toJson(map);
			logger.error(rest);
			return rest;
		}
        alarmStatMapper.updateAlarmStatisticsTicketId(alarmStatistics);
		map.put("status","success");
		map.put("message","创建成功工单号为"+alarmStatistics.getTicketId());
		rest = gson.toJson(map);
        return rest;
    }

    public List<SysDict> queryAlarmWorkTemplate() {
       return alarmStatMapper.queryAlarmWorkTemplate();
    }

    public List<User> queryMaintenanceWorkerUser(){
	    return alarmStatMapper.queryMaintenanceWorkerUser();
    }

    public String queryWhetherWorker(String deviceId) {
        Gson gson = new Gson();
        Map<String,String> device = new HashMap<>();
        device.put("deviceId",deviceId);
        String s = gson.toJson(device);
        logger.info("queryWhetherWorker新建工单前验证"+new Date());
        logger.info("验证工单参数==="+s);
        Map<String,String> map = new HashMap<>();

        map.put("status","fail");
        String rest = null;
        String url = workOrderUrl+CONDITION;
        try {
            HttpResult result = HttpAPIService.httpPost(url, s);
            logger.info("新建工单前验证返回"+gson.toJson(result));
            if(result.getCode()!=200){
                map.put("message","创建工单验证失败");
                rest = gson.toJson(map);
                logger.error(rest);
                return rest;
            }
            if(StringUtils.isBlank(result.getBody())){
                map.put("message","创建工单验证返回码为空");
                rest = gson.toJson(map);
                logger.error(rest);
                return rest;
            }
            logger.info(result.getBody());
            RestResult restResult = gson.fromJson(result.getBody(), RestResult.class);
            if(restResult.getStatus() == null || !restResult.getStatus().equals("complete")){
                map.put("message","创建工单验证失败");
                rest = gson.toJson(map);
                logger.error(rest);
                return rest;
            }
            String workNum = restResult.getResultData().toString();
            if(Integer.parseInt(workNum) == 0){
                map.put("status","success");
            }else {
                map.put("workNum",workNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message","创建工单验证出现异常");
            rest = gson.toJson(map);
            logger.error(rest);
            return rest;
        }
        rest = gson.toJson(map);
        return rest;
    }
}
