package com.wapwag.woss.modules.biz.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wapwag.woss.common.config.Global;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.web.BaseController;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.biz.entity.Function;
import com.wapwag.woss.modules.biz.service.FunctionService;

/**
 * 功能Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/function")
public class FunctionController extends BaseController {

	@Autowired
	private FunctionService functionService;
	
	@ModelAttribute
	public Function get(@RequestParam(required=false) String id) {
		Function entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = functionService.get(id);
		}
		if (entity == null){
			entity = new Function();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:function:view")
	@RequestMapping(value = {"list", ""})
	public String list(Function function, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Function> page = functionService.findPage(new Page<Function>(request, response), function); 
		model.addAttribute("page", page);
		return "modules/biz/functionList";
	}

	@RequiresPermissions("biz:function:view")
	@RequestMapping(value = "form")
	public String form(Function function, Model model) {
		model.addAttribute("function", function);
		return "modules/biz/functionForm";
	}

	@RequiresPermissions("biz:function:edit")
	@RequestMapping(value = "save")
	public String save(Function function, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, function)){
			return form(function, model);
		}
		functionService.save(function);
		addMessage(redirectAttributes, "保存功能成功");
		return "redirect:"+Global.getAdminPath()+"/biz/function/?repage";
	}
	
	@RequiresPermissions("biz:function:edit")
	@RequestMapping(value = "delete")
	public String delete(Function function, RedirectAttributes redirectAttributes) {
		functionService.delete(function);
		addMessage(redirectAttributes, "删除功能成功");
		return "redirect:"+Global.getAdminPath()+"/biz/function/?repage";
	}
	
	@RequestMapping(value = "getByDeviceIds")
	@ResponseBody
	public List<Function> getByDeviceIds(@RequestParam("deviceIds")String deviceIds){
		return functionService.getByDeviceIds(deviceIds);
	}
}