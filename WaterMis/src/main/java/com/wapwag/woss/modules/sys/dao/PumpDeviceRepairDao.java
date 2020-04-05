package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.PumpDeviceRepair;

@MyBatisDao
public interface PumpDeviceRepairDao extends CrudDao<PumpDeviceRepair> {
    int deleteByPrimaryKey(String breakdownId);

    int insert(PumpDeviceRepair record);

    int insertSelective(PumpDeviceRepair record);

    PumpDeviceRepair selectByPrimaryKey(String breakdownId);

    int updateByPrimaryKeySelective(PumpDeviceRepair record);

    int updateByPrimaryKey(PumpDeviceRepair record);
    
    List<PumpDeviceRepair> exportAll(PumpDeviceRepair record);
    
}