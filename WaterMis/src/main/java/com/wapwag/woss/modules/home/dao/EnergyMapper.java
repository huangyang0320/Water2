/**
 * 
 */
package com.wapwag.woss.modules.home.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.home.entity.EnergyDTO;
import com.wapwag.woss.modules.home.entity.HistoryData;
import com.wapwag.woss.modules.home.entity.HundredVO;

/**
 * @author gongll
 * @since 2018-04-16 15:10:34
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.interface")
public interface EnergyMapper {

	List<HistoryData> getDevicesEngrgy(@Param("devices") List<String> devices,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime,
			@Param("type") String type);

	List<HistoryData> getDeviceEngrgy(@Param("dateTime") String dateTime,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime,
			@Param("devices") List<String> devices,
			@Param("type") String type);
	
	List<Map<String, String>> getNames(@Param("devices") List<String> devices,@Param("type") String type);
	List<Map<String, String>> getDevIdsbyPumpIds(@Param("devices") List<String> devices);
	
	List<HundredVO> getAllDevs(EnergyDTO dto);
	List<HundredVO> getAllStatValsAvg(EnergyDTO dto);
	List<HundredVO> getAllStatValsSum(EnergyDTO dto);
}
