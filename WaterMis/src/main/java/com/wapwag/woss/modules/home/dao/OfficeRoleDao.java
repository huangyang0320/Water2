package com.wapwag.woss.modules.home.dao;

import java.util.List;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.home.entity.OfficeRole;

@MyBatisDao
public interface OfficeRoleDao {
	
    int deleteByPrimaryKey(Integer id);

    int insert(OfficeRole record);

    int insertSelective(OfficeRole record);

    OfficeRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OfficeRole record);

    int updateByPrimaryKey(OfficeRole record);
    
    int deleteByOfficeId(String officeId);
    
    List<OfficeRole> selectByofficeId(String officeId);
}