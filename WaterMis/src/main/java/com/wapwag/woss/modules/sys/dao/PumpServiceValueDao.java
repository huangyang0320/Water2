package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.EnergyInfo;
import com.wapwag.woss.modules.sys.entity.PumpServiceValue;
import com.wapwag.woss.modules.sys.entity.PumpServiceValueNew;

/**
 * Pump Sensor Data
 * @author gongll
 *
 */
@MyBatisDao
public interface PumpServiceValueDao extends CrudDao<PumpServiceValue> {
	
	String[] findServiceByDeviceID(PumpServiceValue info);
	List<PumpServiceValue> exportAll(PumpServiceValue pumpServiceValue);
	List<PumpServiceValue> findAll(PumpServiceValue pumpServiceValue);
	List<PumpServiceValue> findOneAll(PumpServiceValue pumpServiceValue);
	String[] countSum(PumpServiceValue pumpServiceValue);
	String[] countOneSum(PumpServiceValue pumpServiceValue);
	String exitTable(String tableName);
	
	List<PumpServiceValueNew> pumpRunStatis(PumpServiceValue pumpServiceValue);
	
	List<PumpServiceValue> powerStatis(PumpServiceValue pumpServiceValue);
	PumpServiceValue powerStatisRel(PumpServiceValue pumpServiceValue);
	
	PumpServiceValue frequencyStatisRel(PumpServiceValue info);
	List<PumpServiceValue> frequencyStatis(PumpServiceValue info);
	
	List<PumpServiceValue> performance(PumpServiceValue info);
	List<PumpServiceValue> performanceRel(PumpServiceValue info);
	List<String> pumpNames(@Param("deviceId") String deviceId);
	
	List<EnergyInfo> energy(QryObject qryObject);
	List<EnergyInfo>  energyMaxMin(QryObject qryObject);
	
	List<EnergyInfo> energyDev(QryObject qryObject);
	List<EnergyInfo>  energyMaxMinDev(QryObject qryObject);
	
}