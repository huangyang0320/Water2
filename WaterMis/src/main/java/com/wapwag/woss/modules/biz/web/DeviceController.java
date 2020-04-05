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
import com.wapwag.woss.modules.biz.entity.Device;
import com.wapwag.woss.modules.biz.service.DeviceService;

/**
 * 设备Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/device")
public class DeviceController extends BaseController {

	@Autowired
	private DeviceService deviceService;
	
	@ModelAttribute
	public Device get(@RequestParam(required=false) String id) {
		Device entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deviceService.get(id);
		}
		if (entity == null){
			entity = new Device();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:device:view")
	@RequestMapping(value = {"list", ""})
	public String list(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Device> page = deviceService.findPage(new Page<Device>(request, response), device); 
		model.addAttribute("page", page);
		return "modules/biz/deviceList";
	}

	@RequiresPermissions("biz:device:view")
	@RequestMapping(value = "form")
	public String form(Device device, Model model) {
		model.addAttribute("device", device);
		return "modules/biz/deviceForm";
	}

	@RequiresPermissions("biz:device:edit")
	@RequestMapping(value = "save")
	public String save(Device device, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, device)){
			return form(device, model);
		}
		deviceService.save(device);
		addMessage(redirectAttributes, "保存设备成功");
		return "redirect:"+Global.getAdminPath()+"/biz/device/?repage";
	}
	
	@RequiresPermissions("biz:device:edit")
	@RequestMapping(value = "delete")
	public String delete(Device device, RedirectAttributes redirectAttributes) {
		deviceService.delete(device);
		addMessage(redirectAttributes, "删除设备成功");
		return "redirect:"+Global.getAdminPath()+"/biz/device/?repage";
	}

}