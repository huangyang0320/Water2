package com.wapwag.woss.modules.home.dao;/**
											* @author leo
											* @data 2018/3/15
											*/

import java.util.List;

import com.wapwag.woss.modules.home.entity.*;

import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author leo
 * @since 2018/3/15 15:33
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.AlarmStatMapper")
public interface AlarmStatMapper {

	/**
	 * 批量插入告警下发状态数据
	 * @param list
	 * @return
	 */
	int insertAlarmSendStatus(@Param(value = "list") List<AlarmSendStatus> list);

	/**
	 * 修改发送失败次数
	 * @param list
	 * @return
	 */
	int updateAlarmSendStatusByAlarmStatisticsId(@Param(value = "list") List<AlarmSendStatus> list);

	/**
	 * 取对应的告警数据（对应的测点名）
	 * @return
	 */
	List<AlarmStat>  getAlarmDetailBySendStatus();


	List<AlarmStat> selectListPage(QryObject paraMap);

	int countAlarm(QryObject paraMap);

	List<AlarmStat> expSelectListPage(QryObject paraMap);

	List<AlarmStat> statType(QryObject paraMap);

	List<AlarmStat> statLevel(QryObject paraMap);

	List<AlarmStat> statTime(QryObject paraMap);

	List<AlarmStat> getAlarmDetail(String userId);
	
	String countAlarms(String userId);

	int indexConfig(AlarmStat info);

	AlarmStat getAlarmById(AlarmStat info);

	int updateAlarmStatisticsTicketId(AlarmStatistics alarmStatistics);

	List<SysDict> queryAlarmWorkTemplate();

	List<User> queryMaintenanceWorkerUser();
}
