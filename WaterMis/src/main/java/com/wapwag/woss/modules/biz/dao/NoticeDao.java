package com.wapwag.woss.modules.biz.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.NoticeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息Dao
 * Created by Administrator on 2020/4/17.
 */
@MyBatisDao("com.wapwag.woss.modules.biz.dao.NoticeDao")
public interface NoticeDao extends CrudDao<NoticeDto> {

    public int findDetailsCount(@Param("userId")String userId);

    public List<NoticeDto> findDetails(NoticeDto noticeDto);

}
