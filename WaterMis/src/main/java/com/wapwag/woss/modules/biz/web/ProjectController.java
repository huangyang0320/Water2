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
import com.wapwag.woss.modules.biz.entity.Project;
import com.wapwag.woss.modules.biz.service.ProjectService;

/**
 * 项目Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/project")
public class ProjectController extends BaseController {

	@Autowired
	private ProjectService projectService;
	
	@ModelAttribute
	public Project get(@RequestParam(required=false) String id) {
		Project entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectService.get(id);
		}
		if (entity == null){
			entity = new Project();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:project:view")
	@RequestMapping(value = {"list", ""})
	public String list(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Project> page = projectService.findPage(new Page<Project>(request, response), project); 
		model.addAttribute("page", page);
		return "modules/biz/projectList";
	}

	@RequiresPermissions("biz:project:view")
	@RequestMapping(value = "form")
	public String form(Project project, Model model) {
		model.addAttribute("project", project);
		return "modules/biz/projectForm";
	}

	@RequiresPermissions("biz:project:edit")
	@RequestMapping(value = "save")
	public String save(Project project, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, project)){
			return form(project, model);
		}
		projectService.save(project);
		addMessage(redirectAttributes, "保存项目成功");
		return "redirect:"+Global.getAdminPath()+"/biz/project/?repage";
	}
	
	@RequiresPermissions("biz:project:edit")
	@RequestMapping(value = "delete")
	public String delete(Project project, RedirectAttributes redirectAttributes) {
		projectService.delete(project);
		addMessage(redirectAttributes, "删除项目成功");
		return "redirect:"+Global.getAdminPath()+"/biz/project/?repage";
	}

}