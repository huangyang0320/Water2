package com.wapwag.woss.modules.home.dao;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.home.entity.HomeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@MyBatisDao("com.wapwag.woss.modules.home.dao.HomeV2Dao")
public interface HomeV2Dao {
    /**
     * 获取用水量/耗电  同比 环比  日均量
     * @param startTime
     * @param endTime
        * @return
        */
        List<HomeDTO> getUseWaterThAnalysis(@Param("startTime") String startTime,@Param("endTime") String endTime,
@Param("tableName") String tableName,@Param("formatDate") String formatDate);


        /**
         * 获取用水量/耗电  同比 环比  日均量
         * @param startTime
         * @param endTime
         * @return
         */
        List<HomeDTO> getUseWaterMonthAnalysisByArea(@Param("startTime") String startTime,@Param("endTime") String endTime,
@Param("tableName") String tableName,@Param("formatDate") String formatDate);



        /**
         * 根据泵房获取昨日用水量/耗电量（增长前10，减少后10）
         * @param startTime
         * @param endTime
         * @return
         */
        List<HomeDTO> getUseWaterDayAddAnalysisByPumpHouse(@Param("startTime") String startTime,@Param("endTime") String endTime,
@Param("tableName") String tableName,@Param("formatDate") String formatDate);


    /**
     * 实时蓄水量
     * @param tableName
     * @return
     */
    List<HomeDTO> getFreeWaterDayAddAnalysisByDeviceId(@Param("tableName") String tableName);



        /**
         * 获取  地图 areaName
         * @return
         */
     List<HomeDTO> getMapAreaName();

    List<HomeDTO>  getMapAreaPumpHouse();

    /**
     * 组态中获取历史 点位数据
     * @param tableName
     * @param deviceId
     * @param code
     * @return
     */
    List<HomeDTO> getCurrentDataByDeviceIdAndCode(@Param("tableName") String tableName,@Param("deviceId") String deviceId,
                                                  @Param("code") String code);
        }
