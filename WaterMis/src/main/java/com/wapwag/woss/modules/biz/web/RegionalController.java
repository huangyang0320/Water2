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
import com.wapwag.woss.modules.biz.entity.Regional;
import com.wapwag.woss.modules.biz.service.RegionalService;

/**
 * 地区Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/regional")
public class RegionalController extends BaseController {

	@Autowired
	private RegionalService regionalService;
	
	@ModelAttribute
	public Regional get(@RequestParam(required=false) String id) {
		Regional entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = regionalService.get(id);
		}
		if (entity == null){
			entity = new Regional();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:regional:view")
	@RequestMapping(value = {"list", ""})
	public String list(Regional regional, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Regional> page = regionalService.findPage(new Page<Regional>(request, response), regional); 
		model.addAttribute("page", page);
		return "modules/biz/regionalList";
	}

	@RequiresPermissions("biz:regional:view")
	@RequestMapping(value = "form")
	public String form(Regional regional, Model model) {
		model.addAttribute("regional", regional);
		return "modules/biz/regionalForm";
	}

	@RequiresPermissions("biz:regional:edit")
	@RequestMapping(value = "save")
	public String save(Regional regional, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, regional)){
			return form(regional, model);
		}
		regionalService.save(regional);
		addMessage(redirectAttributes, "保存地区成功");
		return "redirect:"+Global.getAdminPath()+"/biz/regional/?repage";
	}
	
	@RequiresPermissions("biz:regional:edit")
	@RequestMapping(value = "delete")
	public String delete(Regional regional, RedirectAttributes redirectAttributes) {
		regionalService.delete(regional);
		addMessage(redirectAttributes, "删除地区成功");
		return "redirect:"+Global.getAdminPath()+"/biz/regional/?repage";
	}

}