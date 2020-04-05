/**
 * 
 */
package com.wapwag.woss.modules.home.web;

import java.util.List;
import java.util.Map;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wapwag.woss.modules.home.entity.EnergyDTO;
import com.wapwag.woss.modules.home.entity.EnergyRank;
import com.wapwag.woss.modules.home.entity.RankData;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.home.service.EnergyService;
import com.wapwag.woss.modules.sys.entity.BootPage;

/*
@author gongll
 * @since 2018-04-16 15:11:07
 */
@RestController
@RequestMapping("${adminPath}/energy")
@SessionAttributes("user")
public class EnergyController {

	@Autowired
	private EnergyService energyService;

	/**
	 * ("根据设备IDs统计设备能耗，m3:流量，kwh:电量，energy:能耗")
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getDevEnergyStat/dev") 
	public Map<String, Map<String, List<Map<String, String>>>> getDevEnergyStatDev(
			@RequestBody EnergyDTO queryDTO) throws Exception {
		return energyService.getDeviceEngrgy(queryDTO, "id_device");
	}

	/**
	 * ("根据设备IDs统计多个设备能耗【日月年】,用于饼状图")
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getDevsEnergyStat/dev") 
	public Map<String, Object> getDevsEnergyStatDev(@RequestBody EnergyDTO queryDTO) throws Exception {
		return energyService.getDevicesEngrgy(queryDTO, "id_device");
	}

	/**
	 * ("根据设备IDs统计泵房能耗，m3:流量，kwh:电量，energy:能耗")
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getDevEnergyStat/pump") 
	public Map<String, Map<String, List<Map<String, String>>>> getDevEnergyStatPump(
			@RequestBody EnergyDTO queryDTO) throws Exception {
		return energyService.getDeviceEngrgy(queryDTO, "id_pump_house");
	}

	/**
	 * ("根据设备IDs统计多个泵房能耗【日月年】,用于饼状图")
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getDevsEnergyStat/pump") 
	public Map<String, Object> getDevsEnergyStatPump(@RequestBody EnergyDTO queryDTO) throws Exception {
		return energyService.getDevicesEngrgy(queryDTO, "id_pump_house");
	}
	
	/**
	 * 能效统计
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/statRank") 
	public 	Map<String, List<RankData>> statRankDev(@RequestBody EnergyDTO queryDTO,User user) {
		queryDTO.setUserId(user.getUserId());
		return energyService.statRank(queryDTO,queryDTO.getQueryType());
	}
}
