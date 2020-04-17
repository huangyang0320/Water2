package com.wapwag.woss.modules.biz.web;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.common.web.BaseController;
import com.wapwag.woss.modules.biz.entity.NoticeDto;
import com.wapwag.woss.modules.biz.service.NoticeService;
import com.wapwag.woss.modules.sys.entity.BootPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通知Controller
 * Created by Administrator on 2020/4/17.
 */
@RestController
@RequestMapping(value = "${adminPath}/biz/notice")
public class NoticeController extends BaseController{

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/get")
    @ApiOperation(value = "获取通知", httpMethod = "GET", response =NoticeDto.class , notes = "通过标识获取通知")
    public Object get(@RequestParam(required=false) String id) {
        NoticeDto entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = noticeService.get(id);
        }
        if (entity == null){
            entity = new NoticeDto();
        }
        return entity;
    }

    @RequestMapping("/list")
    @ApiOperation(value = "获取通知列表", httpMethod = "POST", response =NoticeDto.class , notes = "获取通知列表")
    public Object list(NoticeDto noticeDto, HttpServletRequest request, HttpServletResponse response,Model model){
        Page<NoticeDto> page = noticeService.findPage(new Page<NoticeDto>(request, response), noticeDto);
        BootPage bootPage = new BootPage();
        bootPage.setTotal(page.getCount());
        bootPage.setRows(page.getList());
        return bootPage;
    }

    @RequestMapping("/save")
    @ApiOperation(value = "保存通知", httpMethod = "POST", response =NoticeDto.class , notes = "保存通知")
    public Object save(@RequestBody NoticeDto noticeDto, RedirectAttributes redirectAttributes) {
        return noticeService.saveNotice(noticeDto);
    }

    @RequestMapping("/delete")
    @ApiOperation(value = "删除通知", httpMethod = "GET", response =NoticeDto.class , notes = "删除通知")
    public Object delete(NoticeDto noticeDto, RedirectAttributes redirectAttributes) {
        return noticeService.deleteNotice(noticeDto);
    }
}
