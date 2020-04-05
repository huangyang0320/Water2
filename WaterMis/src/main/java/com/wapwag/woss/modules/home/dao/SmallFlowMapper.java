package com.wapwag.woss.modules.home.dao;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * guoln
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.SmallFlowMapper")
public interface SmallFlowMapper {

    List<Map<String, Object>> getSmallFlowData(@Param("deviceId") String deviceId, @Param("startTime") String startTime,
                                               @Param("endTime") String endTime);

}
