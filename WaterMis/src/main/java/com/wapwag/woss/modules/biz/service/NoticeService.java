package com.wapwag.woss.modules.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.dao.NoticeDao;
import com.wapwag.woss.modules.biz.entity.NoticeDto;
import com.wapwag.woss.modules.sys.entity.User;
import com.wapwag.woss.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 通知Service
 * Created by Fyc on 2020/4/17.
 */
@Service
@Transactional(readOnly = true)
public class NoticeService extends CrudService<NoticeDao, NoticeDto> {

    @Autowired
    private NoticeDao noticeDao;

    public NoticeDto get(String id) {
        return super.get(id);
    }

    public List<NoticeDto> findList(NoticeDto noticeDto) {
        return super.findList(noticeDto);
    }

    public Page<NoticeDto> findPage(Page<NoticeDto> page, NoticeDto noticeDto) {
        return super.findPage(page, noticeDto);
    }

    @Transactional(readOnly = false)
    public void insertNoticeDetails(NoticeDto noticeDto){
        try {
            super.save(noticeDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public JSONObject saveNotice(NoticeDto noticeDto) {
        JSONObject result=new JSONObject();
        if(noticeDto.getIsNewRecord()){
            User user = UserUtils.getUser();
            if (null!=user && StringUtils.isNotBlank(user.getId())) {
                noticeDto.setDeleteBy(user);
            }
            noticeDto.setDeleteDate(new Date());
        }

        try{
            super.save(noticeDto);
        }catch(Exception e){
            result.put("code","201");
            result.put("status",false);
            result.put("message","操作失败!");
        }
            result.put("code","200");
            result.put("status",true);
            result.put("message","操作成功!");
        return result;
    }

    @Transactional(readOnly = false)
    public JSONObject deleteNotice(NoticeDto noticeDto) {
        JSONObject result=new JSONObject();
        User user = UserUtils.getUser();
        if (null!=user && StringUtils.isNotBlank(user.getId())) {
            noticeDto.setDeleteBy(user);
        }
        noticeDto.setDeleteDate(new Date());

        try{
            super.delete(noticeDto);
        }catch(Exception e){
            result.put("code","201");
            result.put("status",false);
            result.put("message","操作失败!");
        }
        result.put("code","200");
        result.put("status",true);
        result.put("message","操作成功!");
        return result;
    }

    public int findDetailsCount(String userId){
        return noticeDao.findDetailsCount(userId);
    }

    public List<NoticeDto> findDetails(NoticeDto noticeDto){
        return noticeDao.findDetails(noticeDto);
    }
    @Transactional(readOnly = false)
    public int updateNoticeDto(NoticeDto noticeDto){
        return noticeDao.update(noticeDto);
    }
}
