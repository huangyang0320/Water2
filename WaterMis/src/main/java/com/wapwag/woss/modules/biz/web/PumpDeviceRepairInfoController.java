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
import com.wapwag.woss.modules.biz.entity.PumpDeviceRepairInfo;
import com.wapwag.woss.modules.biz.service.PumpDeviceRepairInfoService;

/**
 * 水泵故障维护信息Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/pumpDeviceRepairInfo")
public class PumpDeviceRepairInfoController extends BaseController {

	@Autowired
	private PumpDeviceRepairInfoService pumpDeviceRepairInfoService;
	
	@ModelAttribute
	public PumpDeviceRepairInfo get(@RequestParam(required=false) String id) {
		PumpDeviceRepairInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pumpDeviceRepairInfoService.get(id);
		}
		if (entity == null){
			entity = new PumpDeviceRepairInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:pumpDeviceRepairInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(PumpDeviceRepairInfo pumpDeviceRepairInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PumpDeviceRepairInfo> page = pumpDeviceRepairInfoService.findPage(new Page<PumpDeviceRepairInfo>(request, response), pumpDeviceRepairInfo); 
		model.addAttribute("page", page);
		return "modules/biz/pumpDeviceRepairInfoList";
	}

	@RequiresPermissions("biz:pumpDeviceRepairInfo:view")
	@RequestMapping(value = "form")
	public String form(PumpDeviceRepairInfo pumpDeviceRepairInfo, Model model) {
		model.addAttribute("pumpDeviceRepairInfo", pumpDeviceRepairInfo);
		return "modules/biz/pumpDeviceRepairInfoForm";
	}

	@RequiresPermissions("biz:pumpDeviceRepairInfo:edit")
	@RequestMapping(value = "save")
	public String save(PumpDeviceRepairInfo pumpDeviceRepairInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, pumpDeviceRepairInfo)){
			return form(pumpDeviceRepairInfo, model);
		}
		pumpDeviceRepairInfoService.save(pumpDeviceRepairInfo);
		addMessage(redirectAttributes, "保存水泵故障维护信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/pumpDeviceRepairInfo/?repage";
	}
	
	@RequiresPermissions("biz:pumpDeviceRepairInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(PumpDeviceRepairInfo pumpDeviceRepairInfo, RedirectAttributes redirectAttributes) {
		pumpDeviceRepairInfoService.delete(pumpDeviceRepairInfo);
		addMessage(redirectAttributes, "删除水泵故障维护信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/pumpDeviceRepairInfo/?repage";
	}

}