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
import com.wapwag.woss.modules.biz.entity.PumpFaultInfo;
import com.wapwag.woss.modules.biz.service.PumpFaultInfoService;

/**
 * 水泵故障信息Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/pumpFaultInfo")
public class PumpFaultInfoController extends BaseController {

	@Autowired
	private PumpFaultInfoService pumpFaultInfoService;
	
	@ModelAttribute
	public PumpFaultInfo get(@RequestParam(required=false) String id) {
		PumpFaultInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pumpFaultInfoService.get(id);
		}
		if (entity == null){
			entity = new PumpFaultInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:pumpFaultInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(PumpFaultInfo pumpFaultInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PumpFaultInfo> page = pumpFaultInfoService.findPage(new Page<PumpFaultInfo>(request, response), pumpFaultInfo); 
		model.addAttribute("page", page);
		return "modules/biz/pumpFaultInfoList";
	}

	@RequiresPermissions("biz:pumpFaultInfo:view")
	@RequestMapping(value = "form")
	public String form(PumpFaultInfo pumpFaultInfo, Model model) {
		model.addAttribute("pumpFaultInfo", pumpFaultInfo);
		return "modules/biz/pumpFaultInfoForm";
	}

	@RequiresPermissions("biz:pumpFaultInfo:edit")
	@RequestMapping(value = "save")
	public String save(PumpFaultInfo pumpFaultInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, pumpFaultInfo)){
			return form(pumpFaultInfo, model);
		}
		pumpFaultInfoService.save(pumpFaultInfo);
		addMessage(redirectAttributes, "保存水泵故障信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/pumpFaultInfo/?repage";
	}
	
	@RequiresPermissions("biz:pumpFaultInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(PumpFaultInfo pumpFaultInfo, RedirectAttributes redirectAttributes) {
		pumpFaultInfoService.delete(pumpFaultInfo);
		addMessage(redirectAttributes, "删除水泵故障信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/pumpFaultInfo/?repage";
	}

}