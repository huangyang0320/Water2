package com.wapwag.woss.modules.biz.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wapwag.woss.common.config.Global;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.web.BaseController;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.biz.entity.AlarmInfo;
import com.wapwag.woss.modules.biz.service.AlarmInfoService;

/**
 * 报警信息Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/alarmInfo")
public class AlarmInfoController extends BaseController {

	@Autowired
	private AlarmInfoService alarmInfoService;
	
	@ModelAttribute
	public AlarmInfo get(@RequestParam(required=false) String id) {
		AlarmInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = alarmInfoService.get(id);
		}
		if (entity == null){
			entity = new AlarmInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:alarmInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AlarmInfo alarmInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AlarmInfo> page = alarmInfoService.findPage(new Page<AlarmInfo>(request, response), alarmInfo); 
		model.addAttribute("page", page);
		return "modules/biz/alarmInfoList";
	}

	@RequiresPermissions("biz:alarmInfo:view")
	@RequestMapping(value = "form")
	public String form(AlarmInfo alarmInfo, Model model) {
		model.addAttribute("alarmInfo", alarmInfo);
		return "modules/biz/alarmInfoForm";
	}

	@RequiresPermissions("biz:alarmInfo:edit")
	@RequestMapping(value = "save")
	public String save(AlarmInfo alarmInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, alarmInfo)){
			return form(alarmInfo, model);
		}
		alarmInfoService.save(alarmInfo);
		addMessage(redirectAttributes, "保存报警信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/alarmInfo/?repage";
	}
	
	@RequiresPermissions("biz:alarmInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AlarmInfo alarmInfo, RedirectAttributes redirectAttributes) {
		alarmInfoService.delete(alarmInfo);
		addMessage(redirectAttributes, "删除报警信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/alarmInfo/?repage";
	}

}