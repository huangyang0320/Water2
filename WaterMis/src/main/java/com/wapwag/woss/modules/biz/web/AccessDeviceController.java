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
import com.wapwag.woss.modules.biz.entity.AccessDevice;
import com.wapwag.woss.modules.biz.service.AccessDeviceService;

/**
 * 门禁Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/accessDevice")
public class AccessDeviceController extends BaseController {

	@Autowired
	private AccessDeviceService accessDeviceService;
	
	@ModelAttribute
	public AccessDevice get(@RequestParam(required=false) String id) {
		AccessDevice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accessDeviceService.get(id);
		}
		if (entity == null){
			entity = new AccessDevice();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:accessDevice:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccessDevice accessDevice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccessDevice> page = accessDeviceService.findPage(new Page<AccessDevice>(request, response), accessDevice); 
		model.addAttribute("page", page);
		return "modules/biz/accessDeviceList";
	}

	@RequiresPermissions("biz:accessDevice:view")
	@RequestMapping(value = "form")
	public String form(AccessDevice accessDevice, Model model) {
		model.addAttribute("accessDevice", accessDevice);
		return "modules/biz/accessDeviceForm";
	}

	@RequiresPermissions("biz:accessDevice:edit")
	@RequestMapping(value = "save")
	public String save(AccessDevice accessDevice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accessDevice)){
			return form(accessDevice, model);
		}
		accessDeviceService.save(accessDevice);
		addMessage(redirectAttributes, "保存门禁成功");
		return "redirect:"+Global.getAdminPath()+"/biz/accessDevice/?repage";
	}
	
	@RequiresPermissions("biz:accessDevice:edit")
	@RequestMapping(value = "delete")
	public String delete(AccessDevice accessDevice, RedirectAttributes redirectAttributes) {
		accessDeviceService.delete(accessDevice);
		addMessage(redirectAttributes, "删除门禁成功");
		return "redirect:"+Global.getAdminPath()+"/biz/accessDevice/?repage";
	}

}