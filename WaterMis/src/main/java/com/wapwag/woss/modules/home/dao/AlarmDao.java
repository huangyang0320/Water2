package com.wapwag.woss.modules.home.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.home.entity.AlarmInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AlarmInfo dao
 * Created by Administrator on 2016/8/2.
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.AlarmInfo")
public interface AlarmDao extends CrudDao<AlarmInfo> {

    public long getCount(@Param("userId") String userId,
                            @Param("startDate") String startDate,
                            @Param("endDate") String endDate);

    List<AlarmInfo> findLatestAlarms(@Param("userId") String userId,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate);
}
