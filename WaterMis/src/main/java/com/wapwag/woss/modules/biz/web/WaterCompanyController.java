package com.wapwag.woss.modules.biz.web;

import java.util.Date;
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
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.common.web.BaseController;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.biz.entity.WaterCompany;
import com.wapwag.woss.modules.biz.service.WaterCompanyService;
import com.wapwag.woss.modules.sys.entity.User;
import com.wapwag.woss.modules.sys.utils.UserUtils;

/**
 * 水司Controller
 * @author zhaoming
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/waterCompany")
public class WaterCompanyController extends BaseController {

	@Autowired
	private WaterCompanyService waterCompanyService;
	
	@ModelAttribute
	public WaterCompany get(@RequestParam(required=false) String id) {
		WaterCompany entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = waterCompanyService.get(id);
		}
		if (entity == null){
			entity = new WaterCompany();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:waterCompany:view")
    @RequestMapping(value = {"index"})
    public String index(WaterCompany waterCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
          return "modules/biz/waterCompanyIndex";
    }
	
	@RequiresPermissions("biz:waterCompany:view")
	@RequestMapping(value = {"list", ""})
	public String list(WaterCompany waterCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WaterCompany> page = new Page<WaterCompany>(request, response);
		page = waterCompanyService.findPage(page , waterCompany);
		model.addAttribute("page", page);
		return "modules/biz/waterCompanyList";
	}

	@RequiresPermissions("biz:waterCompany:view")
	@RequestMapping(value = "form")
	public String form(WaterCompany waterCompany, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("waterCompany", waterCompany);
		return "modules/biz/waterCompanyForm";
	}

	@RequiresPermissions("biz:waterCompany:edit")
	@RequestMapping(value = "save")
	public String save(WaterCompany waterCompany, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, waterCompany)){
			return form(waterCompany, model,redirectAttributes);
		}
		waterCompany.setUpdateDate(new Date());
		User user = UserUtils.getUser();
		waterCompany.setCreator(user.getName());
		waterCompany.setUpdator(user.getName());
		waterCompanyService.save(waterCompany);
		addMessage(redirectAttributes, "保存水司成功");
		return "redirect:"+Global.getAdminPath()+"/biz/waterCompany/?parentId="+waterCompany.getId();
	}
	
	@RequiresPermissions("biz:waterCompany:edit")
	@RequestMapping(value = "delete")
	public String delete(WaterCompany waterCompany, RedirectAttributes redirectAttributes) {
		if (waterCompanyService.getByPid(waterCompany.getId())!=null && waterCompanyService.getByPid(waterCompany.getId()).size()> 0) {
			addMessage(redirectAttributes, "该水司不能删除");
			return "redirect:"+Global.getAdminPath()+"/biz/waterCompany/?parentId="+waterCompany.getId();
		}
		waterCompanyService.delete(waterCompany);
		addMessage(redirectAttributes, "删除水司成功");
		return "redirect:"+Global.getAdminPath()+"/biz/waterCompany/";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<TreeInfo> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
		return waterCompanyService.waterCompanyTree();
	}
	
}