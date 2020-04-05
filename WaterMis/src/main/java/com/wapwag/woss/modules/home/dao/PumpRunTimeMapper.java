package com.wapwag.woss.modules.home.dao;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * guoln
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.PumpRunTimeMapper")
public interface PumpRunTimeMapper {

    /**
     * 根据设备id查询泵机的运行时间
     * @param deviceId
     * @param serviceId 泵机编号[1#,2#,3#...]
     * @return
     */
    List<Map<String, Object>> qryRunTime(@Param("deviceId") String deviceId, @Param("serviceId") String serviceId,
                                         @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 统计泵机不同频率的运行时间
     * @param qry
     * @return
     */
    List<Map<String, Object>> qryFreRunTime(Map<String, Object> qry);

    /**
     * 根据设备id获取泵房名称
     * @param deviceId
     * @return
     */
    String getPHNameByDevId(String deviceId);

}
