package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.Project;

@MyBatisDao("com.wapwag.woss.modules.sys.dao.ProjectDao")
public interface ProjectDao extends CrudDao<Project> {
    int deleteByPrimaryKey(String projectId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(String projectId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
    
    List<Project> getProjectNameAndId();
    
    List<Project> getProjectNameAndIdAndRole(@Param("userId") String userId);
}