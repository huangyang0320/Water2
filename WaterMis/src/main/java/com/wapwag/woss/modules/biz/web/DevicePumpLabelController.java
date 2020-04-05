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
import com.wapwag.woss.modules.biz.entity.DevicePumpLabel;
import com.wapwag.woss.modules.biz.service.DevicePumpLabelService;

/**
 * 水泵设备标牌Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/devicePumpLabel")
public class DevicePumpLabelController extends BaseController {

	@Autowired
	private DevicePumpLabelService devicePumpLabelService;
	
	@ModelAttribute
	public DevicePumpLabel get(@RequestParam(required=false) String id) {
		DevicePumpLabel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = devicePumpLabelService.get(id);
		}
		if (entity == null){
			entity = new DevicePumpLabel();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:devicePumpLabel:view")
	@RequestMapping(value = {"list", ""})
	public String list(DevicePumpLabel devicePumpLabel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DevicePumpLabel> page = devicePumpLabelService.findPage(new Page<DevicePumpLabel>(request, response), devicePumpLabel); 
		model.addAttribute("page", page);
		return "modules/biz/devicePumpLabelList";
	}

	@RequiresPermissions("biz:devicePumpLabel:view")
	@RequestMapping(value = "form")
	public String form(DevicePumpLabel devicePumpLabel, Model model) {
		model.addAttribute("devicePumpLabel", devicePumpLabel);
		return "modules/biz/devicePumpLabelForm";
	}

	@RequiresPermissions("biz:devicePumpLabel:edit")
	@RequestMapping(value = "save")
	public String save(DevicePumpLabel devicePumpLabel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, devicePumpLabel)){
			return form(devicePumpLabel, model);
		}
		devicePumpLabelService.save(devicePumpLabel);
		addMessage(redirectAttributes, "保存水泵设备标牌成功");
		return "redirect:"+Global.getAdminPath()+"/biz/devicePumpLabel/?repage";
	}
	
	@RequiresPermissions("biz:devicePumpLabel:edit")
	@RequestMapping(value = "delete")
	public String delete(DevicePumpLabel devicePumpLabel, RedirectAttributes redirectAttributes) {
		devicePumpLabelService.delete(devicePumpLabel);
		addMessage(redirectAttributes, "删除水泵设备标牌成功");
		return "redirect:"+Global.getAdminPath()+"/biz/devicePumpLabel/?repage";
	}

}