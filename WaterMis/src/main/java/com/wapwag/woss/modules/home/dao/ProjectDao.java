package com.wapwag.woss.modules.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.RegionTypeDto;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.home.entity.ProjectInfo;

/**
 * ProjectDao
 * Created by Administrator on 2016/8/2.
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.ProjectDao")
public interface ProjectDao extends CrudDao<ProjectInfo> {
	
	//根据区域树查询下面的项目节点
	List<TreeInfo> findProjectByRegion(@Param("regionalTreeInfo") List<TreeInfo> regionalTreeInfo);
	
	//获取城区农村节点
	List<TreeInfo> findTypeByRegion(@Param("userId") String userId);

	//根据农村城区获取下面的项目节点
	List<TreeInfo> findProjectByType(@Param("regionTypeDto") List<RegionTypeDto> regionTypeDto);
	
	//获取水司下面城区农村节点
	List<TreeInfo> findTypeByCompany(@Param("companyTreeInfo") List<TreeInfo> companyTreeInfo);
	
	//区域农村下的视频
	//补充 需要关联 角色  设备 决定 视频 
	List<TreeInfo> findTypeByRegionAndVedio(@Param("userId") String userId);
	
	//水司 区域下有视频的节点
	List<TreeInfo>  findTypeByCompanyAndVideo(@Param("companyTreeInfo") List<TreeInfo> companyTreeInfo);
	
	//水司 下面 城区 农村
	List<TreeInfo> findTypeByWaterDepart(@Param("userId") String userId);
	
	//水司 区域 农村  视频
	List<TreeInfo> findTypeByWaterDepartAndVideo(@Param("userId") String userId);



}
