package com.wapwag.woss.modules.biz.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wapwag.woss.modules.ticket.Entity.TicketDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.wapwag.woss.modules.biz.entity.PumpHouse;
import com.wapwag.woss.modules.biz.service.PumpHouseService;

/**
 * 泵房Controller
 * @author yuxt
 * @version 2016-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/pumpHouse")
@Api(description = "获取泵房相关API")
public class PumpHouseController extends BaseController {

	@Autowired
	private PumpHouseService pumpHouseService;
	
	@ModelAttribute
	public PumpHouse get(@RequestParam(required=false) String id) {
		PumpHouse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pumpHouseService.get(id);
		}
		if (entity == null){
			entity = new PumpHouse();
		}
		return entity;
	}
	
	@RequiresPermissions("biz:pumpHouse:view")
	@RequestMapping(value = {"list", ""})
	@ApiOperation(value = "获取泵房集合信息", httpMethod = "POST", response = PumpHouse.class , notes = "")
	public String list(PumpHouse pumpHouse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PumpHouse> page = pumpHouseService.findPage(new Page<PumpHouse>(request, response), pumpHouse); 
		model.addAttribute("page", page);
		return "modules/biz/pumpHouseList";
	}

	@RequiresPermissions("biz:pumpHouse:view")
	@RequestMapping(value = "form")
	public String form(PumpHouse pumpHouse, Model model) {
		model.addAttribute("pumpHouse", pumpHouse);
		return "modules/biz/pumpHouseForm";
	}

	@RequiresPermissions("biz:pumpHouse:edit")
	@RequestMapping(value = "save")
	public String save(PumpHouse pumpHouse, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, pumpHouse)){
			return form(pumpHouse, model);
		}
		pumpHouseService.save(pumpHouse);
		addMessage(redirectAttributes, "保存泵房成功");
		return "redirect:"+Global.getAdminPath()+"/biz/pumpHouse/?repage";
	}
	
	@RequiresPermissions("biz:pumpHouse:edit")
	@RequestMapping(value = "delete")
	public String delete(PumpHouse pumpHouse, RedirectAttributes redirectAttributes) {
		pumpHouseService.delete(pumpHouse);
		addMessage(redirectAttributes, "删除泵房成功");
		return "redirect:"+Global.getAdminPath()+"/biz/pumpHouse/?repage";
	}

}