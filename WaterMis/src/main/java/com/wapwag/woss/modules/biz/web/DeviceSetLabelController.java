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
import com.wapwag.woss.modules.biz.entity.DeviceSetLabel;
import com.wapwag.woss.modules.biz.service.DeviceSetLabelService;

/**
 * 设备配置标牌Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/deviceSetLabel")
public class DeviceSetLabelController extends BaseController {

	@Autowired
	private DeviceSetLabelService deviceSetLabelService;
	
	@ModelAttribute
	public DeviceSetLabel get(@RequestParam(required=false) String id) {
		DeviceSetLabel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deviceSetLabelService.get(id);
		}
		if (entity == null){
			entity = new DeviceSetLabel();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:deviceSetLabel:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeviceSetLabel deviceSetLabel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeviceSetLabel> page = deviceSetLabelService.findPage(new Page<DeviceSetLabel>(request, response), deviceSetLabel); 
		model.addAttribute("page", page);
		return "modules/biz/deviceSetLabelList";
	}

	@RequiresPermissions("biz:deviceSetLabel:view")
	@RequestMapping(value = "form")
	public String form(DeviceSetLabel deviceSetLabel, Model model) {
		model.addAttribute("deviceSetLabel", deviceSetLabel);
		return "modules/biz/deviceSetLabelForm";
	}

	@RequiresPermissions("biz:deviceSetLabel:edit")
	@RequestMapping(value = "save")
	public String save(DeviceSetLabel deviceSetLabel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deviceSetLabel)){
			return form(deviceSetLabel, model);
		}
		deviceSetLabelService.save(deviceSetLabel);
		addMessage(redirectAttributes, "保存设备配置标牌成功");
		return "redirect:"+Global.getAdminPath()+"/biz/deviceSetLabel/?repage";
	}
	
	@RequiresPermissions("biz:deviceSetLabel:edit")
	@RequestMapping(value = "delete")
	public String delete(DeviceSetLabel deviceSetLabel, RedirectAttributes redirectAttributes) {
		deviceSetLabelService.delete(deviceSetLabel);
		addMessage(redirectAttributes, "删除设备配置标牌成功");
		return "redirect:"+Global.getAdminPath()+"/biz/deviceSetLabel/?repage";
	}

}