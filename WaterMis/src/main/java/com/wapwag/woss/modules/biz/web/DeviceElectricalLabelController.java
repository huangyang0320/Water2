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
import com.wapwag.woss.modules.biz.entity.DeviceElectricalLabel;
import com.wapwag.woss.modules.biz.service.DeviceElectricalLabelService;

/**
 * 电气设备标牌Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/deviceElectricalLabel")
public class DeviceElectricalLabelController extends BaseController {

	@Autowired
	private DeviceElectricalLabelService deviceElectricalLabelService;
	
	@ModelAttribute
	public DeviceElectricalLabel get(@RequestParam(required=false) String id) {
		DeviceElectricalLabel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deviceElectricalLabelService.get(id);
		}
		if (entity == null){
			entity = new DeviceElectricalLabel();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:deviceElectricalLabel:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeviceElectricalLabel deviceElectricalLabel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeviceElectricalLabel> page = deviceElectricalLabelService.findPage(new Page<DeviceElectricalLabel>(request, response), deviceElectricalLabel); 
		model.addAttribute("page", page);
		return "modules/biz/deviceElectricalLabelList";
	}

	@RequiresPermissions("biz:deviceElectricalLabel:view")
	@RequestMapping(value = "form")
	public String form(DeviceElectricalLabel deviceElectricalLabel, Model model) {
		model.addAttribute("deviceElectricalLabel", deviceElectricalLabel);
		return "modules/biz/deviceElectricalLabelForm";
	}

	@RequiresPermissions("biz:deviceElectricalLabel:edit")
	@RequestMapping(value = "save")
	public String save(DeviceElectricalLabel deviceElectricalLabel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deviceElectricalLabel)){
			return form(deviceElectricalLabel, model);
		}
		deviceElectricalLabelService.save(deviceElectricalLabel);
		addMessage(redirectAttributes, "保存电气设备标牌成功");
		return "redirect:"+Global.getAdminPath()+"/biz/deviceElectricalLabel/?repage";
	}
	
	@RequiresPermissions("biz:deviceElectricalLabel:edit")
	@RequestMapping(value = "delete")
	public String delete(DeviceElectricalLabel deviceElectricalLabel, RedirectAttributes redirectAttributes) {
		deviceElectricalLabelService.delete(deviceElectricalLabel);
		addMessage(redirectAttributes, "删除电气设备标牌成功");
		return "redirect:"+Global.getAdminPath()+"/biz/deviceElectricalLabel/?repage";
	}

}