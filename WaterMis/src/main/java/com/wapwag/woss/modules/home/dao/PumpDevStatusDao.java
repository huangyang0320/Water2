package com.wapwag.woss.modules.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.home.web.PumpDevStatus;

@MyBatisDao
public interface PumpDevStatusDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PumpDevStatus record);

    int insertSelective(PumpDevStatus record);

    PumpDevStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PumpDevStatus record);

    int updateByPrimaryKey(PumpDevStatus record);
    
    void insertBatch(@Param("list") List<PumpDevStatus> list);
    
    void deleteByDevId(@Param("list") List<PumpDevStatus> list);
    
    void deleteByPumpId(@Param("list") List<PumpDevStatus> list);
    
    void delete();
    
    String selectByDevId(@Param("devId") String devId);
    
    String selectByPumpId(@Param("pumpId") String pumpId);
    
    void deleteBycreateTime(@Param("createtime") String createtime);
    
    PumpDevStatus selectTimeByDevId(@Param("devId") String devId);
    
    List<PumpDevStatus> selectTimeByTime(@Param("createtime") String createtime);

}