/**
 * 
 */
package com.wapwag.woss.modules.home.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.home.entity.AlarmStat;
import com.wapwag.woss.modules.home.entity.RunStatSTO;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.home.service.StatRunService;

/*
 * 功耗统计
@author gongll
 * @since 2018-04-16 15:11:07
 */
@RestController
@RequestMapping("${adminPath}/statRunController")
@SessionAttributes("user")
public class StatRunController {

	@Autowired
	private StatRunService service;

	/**
	 * 水泵运行时间统计
	 * @param queryDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/qryRunStat")
	public Map<String, List<Map<String, Object>>> getPumpRunTime(RunStatSTO queryDTO, User user) {
		return service.getPumpRunTime(queryDTO);
	}

	/**
	 * 水泵频率运行时间统计
	 * @param queryDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/qryFrequencyStat")
	public List<Map<String, Object>> getPumpFrequency(RunStatSTO queryDTO, User user) {
		return service.getPumpFrequency(queryDTO);
	}

}
