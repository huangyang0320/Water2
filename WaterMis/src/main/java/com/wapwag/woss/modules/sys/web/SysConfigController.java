package com.wapwag.woss.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wapwag.woss.common.task.AccessRecordTask;

@Controller
@RequestMapping(value = "${adminPath}/sysConfig")
public class SysConfigController {
	
	  
	@ResponseBody
	@RequestMapping(value = "/getSysConfigByKey/{key}")
	public String getSysConfigByKey(@PathVariable String key) {
		return AccessRecordTask.getSysConfigByKey(key);
	}
}
