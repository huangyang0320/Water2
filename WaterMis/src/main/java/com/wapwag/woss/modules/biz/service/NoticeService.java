package com.wapwag.woss.modules.biz.service;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.dao.NoticeDao;
import com.wapwag.woss.modules.biz.entity.NoticeDto;
import com.wapwag.woss.modules.sys.entity.User;
import com.wapwag.woss.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 通知Service
 * Created by Administrator on 2020/4/17.
 */
@Service
@Transactional(readOnly = true)
public class NoticeService extends CrudService<NoticeDao, NoticeDto> {

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
    public void save(NoticeDto noticeDto) {
        if(noticeDto.getIsNewRecord()){
            User user = UserUtils.getUser();
            if (null!=user && StringUtils.isNotBlank(user.getId())) {
                noticeDto.setDeleteBy(user);
            }
            noticeDto.setDeleteDate(new Date());
        }
        super.save(noticeDto);
    }

    @Transactional(readOnly = false)
    public void delete(NoticeDto noticeDto) {
        User user = UserUtils.getUser();
        if (null!=user && StringUtils.isNotBlank(user.getId())) {
            noticeDto.setDeleteBy(user);
        }
        noticeDto.setDeleteDate(new Date());
        super.delete(noticeDto);
    }
}
