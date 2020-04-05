package com.wapwag.woss.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.modules.sys.entity.Alarm;
import com.wapwag.woss.modules.sys.service.AlarmService;

/**
 * 告警日志Controller
 */
@Controller
@RequestMapping(value = "/log")
public class AlarmController {

	@Autowired
	private AlarmService alarmService;
	@RequestMapping(value = "alarm")
	public String list(Alarm alarmInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Alarm> page = alarmService.findPage(new Page<Alarm>(request, response), alarmInfo);

		model.addAttribute("page", page);
		return "modules/sys/logList";
	}


}
