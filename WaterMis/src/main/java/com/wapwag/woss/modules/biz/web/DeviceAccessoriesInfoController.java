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
import com.wapwag.woss.modules.biz.entity.DeviceAccessoriesInfo;
import com.wapwag.woss.modules.biz.service.DeviceAccessoriesInfoService;

/**
 * 设备配件信息Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/deviceAccessoriesInfo")
public class DeviceAccessoriesInfoController extends BaseController {

	@Autowired
	private DeviceAccessoriesInfoService deviceAccessoriesInfoService;
	
	@ModelAttribute
	public DeviceAccessoriesInfo get(@RequestParam(required=false) String id) {
		DeviceAccessoriesInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deviceAccessoriesInfoService.get(id);
		}
		if (entity == null){
			entity = new DeviceAccessoriesInfo();
		}
		return entity;
	}

	@RequiresPermissions("biz:deviceAccessoriesInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeviceAccessoriesInfo deviceAccessoriesInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeviceAccessoriesInfo> page = deviceAccessoriesInfoService.findPage(new Page<DeviceAccessoriesInfo>(request, response), deviceAccessoriesInfo); 
		model.addAttribute("page", page);
		return "modules/biz/deviceAccessoriesInfoList";
	}

	@RequiresPermissions("biz:deviceAccessoriesInfo:view")
	@RequestMapping(value = "form")
	public String form(DeviceAccessoriesInfo deviceAccessoriesInfo, Model model) {
		model.addAttribute("deviceAccessoriesInfo", deviceAccessoriesInfo);
		return "modules/biz/deviceAccessoriesInfoForm";
	}

	@RequiresPermissions("biz:deviceAccessoriesInfo:edit")
	@RequestMapping(value = "save")
	public String save(DeviceAccessoriesInfo deviceAccessoriesInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deviceAccessoriesInfo)){
			return form(deviceAccessoriesInfo, model);
		}
		deviceAccessoriesInfoService.save(deviceAccessoriesInfo);
		addMessage(redirectAttributes, "保存设备配件信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/deviceAccessoriesInfo/?repage";
	}
	
	@RequiresPermissions("biz:deviceAccessoriesInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(DeviceAccessoriesInfo deviceAccessoriesInfo, RedirectAttributes redirectAttributes) {
		deviceAccessoriesInfoService.delete(deviceAccessoriesInfo);
		addMessage(redirectAttributes, "删除设备配件信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/deviceAccessoriesInfo/?repage";
	}

}