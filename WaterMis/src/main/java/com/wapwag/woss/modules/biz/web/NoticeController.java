package com.wapwag.woss.modules.biz.web;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.common.web.BaseController;
import com.wapwag.woss.modules.biz.entity.NoticeDto;
import com.wapwag.woss.modules.biz.service.NoticeService;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.sys.entity.BootPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 通知Controller
 * Created by Administrator on 2020/4/17.
 */
@RestController
@Api(produces = "通知相关API")
@SessionAttributes("user")
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

    @RequestMapping("/getDetails")
    @ResponseBody
    public int getDetails(User user,HttpServletRequest request) {
        return noticeService.findDetailsCount(user.getUserId());
    }

    @RequestMapping("/getDetailsList")
    @ResponseBody
    public List<NoticeDto> getDetailsList(User user, HttpServletRequest request) {
        NoticeDto noticeDto=new NoticeDto();
        noticeDto.setNoticeGroupRefId(user.getUserId());
        return noticeService.findDetails(noticeDto);
    }

    @RequestMapping("/getDetailsById")
    @ResponseBody
    public List<NoticeDto> getDetailsById(NoticeDto noticeDto,User user, HttpServletRequest request) {
        //先查询后进行修改消息状态
        List<NoticeDto> details = noticeService.findDetails(noticeDto);
        //修改一查看的消息状态
        noticeDto.setNoticeStatus("02");
        noticeService.updateNoticeDto(noticeDto);
        return details;
    }

    @RequestMapping("/resetStatus")
    @ResponseBody
    public int resetStatus(User user, HttpServletRequest request) {
        int res=0;
        NoticeDto noticeDto=new NoticeDto();
        noticeDto.setNoticeGroupRefId(user.getUserId());
        List<NoticeDto> details = noticeService.findDetails(noticeDto);
        for(NoticeDto d:details){
            NoticeDto noticeDto2=new NoticeDto();
            noticeDto2.setId(d.getId());
            noticeDto2.setNoticeStatus("02");
            noticeService.updateNoticeDto(noticeDto2);
        }
        res=1;
        return res;
    }





}
