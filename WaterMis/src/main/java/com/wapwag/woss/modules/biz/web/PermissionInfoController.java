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
import com.wapwag.woss.modules.biz.entity.PermissionInfo;
import com.wapwag.woss.modules.biz.service.PermissionInfoService;

/**
 * 授权信息Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/permissionInfo")
public class PermissionInfoController extends BaseController {

	@Autowired
	private PermissionInfoService permissionInfoService;
	
	@ModelAttribute
	public PermissionInfo get(@RequestParam(required=false) String id) {
		PermissionInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = permissionInfoService.get(id);
		}
		if (entity == null){
			entity = new PermissionInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:permissionInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(PermissionInfo permissionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PermissionInfo> page = permissionInfoService.findPage(new Page<PermissionInfo>(request, response), permissionInfo); 
		model.addAttribute("page", page);
		return "modules/biz/permissionInfoList";
	}

	@RequiresPermissions("biz:permissionInfo:view")
	@RequestMapping(value = "form")
	public String form(PermissionInfo permissionInfo, Model model) {
		model.addAttribute("permissionInfo", permissionInfo);
		return "modules/biz/permissionInfoForm";
	}

	@RequiresPermissions("biz:permissionInfo:edit")
	@RequestMapping(value = "save")
	public String save(PermissionInfo permissionInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, permissionInfo)){
			return form(permissionInfo, model);
		}
		permissionInfoService.save(permissionInfo);
		addMessage(redirectAttributes, "保存授权信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/permissionInfo/?repage";
	}
	
	@RequiresPermissions("biz:permissionInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(PermissionInfo permissionInfo, RedirectAttributes redirectAttributes) {
		permissionInfoService.delete(permissionInfo);
		addMessage(redirectAttributes, "删除授权信息成功");
		return "redirect:"+Global.getAdminPath()+"/biz/permissionInfo/?repage";
	}

}