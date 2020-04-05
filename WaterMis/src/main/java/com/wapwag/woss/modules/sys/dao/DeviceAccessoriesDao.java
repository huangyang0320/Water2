package com.wapwag.woss.modules.sys.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.DeviceAccessories;

@MyBatisDao("com.wapwag.woss.modules.sys.dao.DeviceAccessoriesDao")
public interface DeviceAccessoriesDao extends CrudDao<DeviceAccessories> {
    int deleteByPrimaryKey(String accessoriesId);

    int insert(DeviceAccessories record);

    int insertSelective(DeviceAccessories record);

    DeviceAccessories selectByPrimaryKey(String accessoriesId);

    int updateByPrimaryKeySelective(DeviceAccessories record);

    int updateByPrimaryKey(DeviceAccessories record);
}