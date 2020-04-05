package com.wapwag.woss.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.Device;
import com.wapwag.woss.modules.sys.entity.Alarm;
import com.wapwag.woss.modules.sys.entity.PumpDeviceRepair;
import com.wapwag.woss.modules.sys.entity.PumpServiceValue;
import com.wapwag.woss.modules.sys.entity.ValueCount;
/**  
 * 告警日志DAO接口
 */
@MyBatisDao("com.wapwag.woss.modules.sys.dao.CountDao")
public interface CountDao extends CrudDao<ValueCount> {
	List<ValueCount> countValueByName(ValueCount info);
	String statisticDevivceSum(String userId);
	String statisticElectricity(@Param("tableName") String tableName);
	String statisticFlow(@Param("tableName") String tableName);
	String statisticDevivceRunTime(String userId);
	String statisticSysTime();
	List<Device> statisticDevivceArea(String userId);
	List<Device> countDeviceAreaChina();
	List<Device> statisticDevivceType(String userId);
	String statisticDevivceTypeNull(String userId);
	String statisticElectricityTime(QryObject info);
	List<ValueCount> getCountValues();
	int saveCountValue(ValueCount info);
	List<Device> statisticAlarmType(String userId);
	List<Device> statRealAlarm(@Param("deviceId") String deviceId);
	List<Device> pumpRatedPower(@Param("deviceId") String deviceId);
	List<Device> statRealAlarmType(@Param("deviceId") String deviceId);
	List<Device> statisticRepaicType();
	List<Device> statisticHourEle(QryObject info);
	List<Device> statisticHourAla(QryObject info);
	List<Device> statisticDayEle(QryObject info);
	List<Device> statisticMouthEle(QryObject info);
	int modSystemRunTime(QryObject info);
	List<Alarm> oneAlarmRecords(QryObject info);
	List<PumpDeviceRepair> oneRepairRecords(QryObject info);
	List<PumpServiceValue> pumpHistoryStatis();
	List<PumpServiceValue> pumpRunStatis(PumpServiceValue info);
	int savePumpRunStatis(List<PumpServiceValue> list);
	String pumpHistoryMaxTime();
	
	List<String> pumpNames(PumpServiceValue info);
	List<Device> pumpRunTimeStatis(PumpServiceValue info);
	List<Device> pumpRunTimesStatis(PumpServiceValue info);
	List<Device> pumpRatedStatis(PumpServiceValue info);
	String lastMouthPV(QryObject info);
	List<ValueCount> qryMinMaxConf();
	List<Device> countLocation();
	String statisticAlaemDevices(String userId);
	List<Device> staticEle(QryObject info);
	
	String statisticPumpSum(String userId);
	String statPower(String userId);
	List<Device> areaProjectRepairStat(String userId);
	List<Device> devEleCom(String devId);
	List<Device> traSuppPumpStat(String userId);
	List<Device> countAlarmTimesStat(@Param("userId") String userId,@Param("type") String type);
	List<Device> countMaintenanceTrend(@Param("userId") String userId);
	List<Device> countMaintenanceTrendDev(@Param("devId") String devId);
	List<Device> statisticEleByLocation(QryObject info);
	List<Device> statisticEleByDeviceId(QryObject info);
	
	List<Device> pumpRunTime(@Param("tableName") String tableName);
	String devicePicUrl(String deviceId);
	
	Device statRat(String deviceId);
	Device pumpPow(String deviceId);
	
	List<Device> statEle(@Param("tableName") String tableName);
	
	String videoCode(String deviceId);
	List<Device> passStat(@Param("deviceId") String deviceId,@Param("tableName") String tableName);
	String getDoorUUId(String deviceId);
	String getDoorControlUrl();
	
	List<String> inPre(@Param("deviceId") String deviceId,@Param("tableName") String tableName);
	
	List<String> outPre(@Param("deviceId") String deviceId,@Param("tableName") String tableName);
	
	List<String> gitPre(@Param("deviceId") String deviceId,@Param("tableName") String tableName);
	List<Device> statFlow(@Param("tableName") String tableName);
	String statSummary(@Param("userId") String userId,@Param("operType") String operType);
	List<Map<String, String>> summaryMaxVals();
	List<String> getDevIdsByUser(String userId);
	List<String> getDevIdsByArea(@Param("areaId") String areaId, @Param("userId") String userId);
	
	List<Map<String, String>> deviceStatus(@Param("tableTag") String tableTag);
	
	List<Map<String, String>> devPump();
	List<Device> countDeviceAreaChinaById(String userId);
	
	String getAllSummaryByRole(@Param("type") String type,@Param("userId") String userId);
	String getDevSummaryByDev(@Param("type") String type,@Param("devId") String devId);
	
	
	String getConFPower(String devId);
	String getSBEDGSYC(String devId);
	String devRunTime(String devId);
}
