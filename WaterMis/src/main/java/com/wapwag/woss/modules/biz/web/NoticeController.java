package com.wapwag.woss.modules.biz.web;

import com.wapwag.woss.common.config.Global;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.common.web.BaseController;
import com.wapwag.woss.modules.biz.entity.NoticeDto;
import com.wapwag.woss.modules.biz.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通知Controller
 * Created by Administrator on 2020/4/17.
 */
@Controller
@RequestMapping(value = "${adminPath}/biz/notice")
public class NoticeController extends BaseController{

    @Autowired
    private NoticeService noticeService;

    @ModelAttribute
    @ApiOperation(value = "获取通知", httpMethod = "GET", response =NoticeDto.class , notes = "通过标识获取通知")
    public NoticeDto get(@RequestParam(required=false) String id) {
        NoticeDto entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = noticeService.get(id);
        }
        if (entity == null){
            entity = new NoticeDto();
        }
        return entity;
    }

    @RequiresPermissions("biz:notice:view")
    @RequestMapping(value = {"list", ""})
    @ApiOperation(value = "获取通知列表", httpMethod = "GET", response =NoticeDto.class , notes = "获取通知列表")
    public String list(NoticeDto noticeDto, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<NoticeDto> page = noticeService.findPage(new Page<NoticeDto>(request, response), noticeDto);
        model.addAttribute("page", page);
        return "modules/biz/noticeList";
    }

    @RequiresPermissions("biz:notice:view")
    @RequestMapping(value = "form")
    @ApiOperation(value = "通知表单", httpMethod = "GET", response =NoticeDto.class , notes = "通知表单")
    public String form(NoticeDto noticeDto, Model model) {
        model.addAttribute("notice", noticeDto);
        return "modules/biz/noticeForm";
    }

    @RequiresPermissions("biz:notice:edit")
    @RequestMapping(value = "save")
    @ApiOperation(value = "保存通知", httpMethod = "GET", response =NoticeDto.class , notes = "保存通知")
    public String save(NoticeDto noticeDto, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, noticeDto)){
            return form(noticeDto, model);
        }
        noticeService.save(noticeDto);
        addMessage(redirectAttributes, "保存通知成功");
        return "redirect:"+ Global.getAdminPath()+"/biz/notice/?repage";
    }

    @RequiresPermissions("biz:notice:edit")
    @RequestMapping(value = "delete")
    @ApiOperation(value = "删除通知", httpMethod = "GET", response =NoticeDto.class , notes = "删除通知")
    public String delete(NoticeDto noticeDto, RedirectAttributes redirectAttributes) {
        noticeService.delete(noticeDto);
        addMessage(redirectAttributes, "删除通知成功");
        return "redirect:"+Global.getAdminPath()+"/biz/notice/?repage";
    }
}
