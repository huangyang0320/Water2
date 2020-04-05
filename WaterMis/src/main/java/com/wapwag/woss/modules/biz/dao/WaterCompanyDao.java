package com.wapwag.woss.modules.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.biz.entity.WaterCompany;
import com.wapwag.woss.modules.home.entity.TreeTypeDto;

/**
 * 水司管理
 * @author zhaom
 *
 */
@MyBatisDao
public interface WaterCompanyDao extends CrudDao<WaterCompany> {
	
	List<TreeInfo> waterCompanyTree();
	
	List<WaterCompany> getByPid(String id);
	
	/**
	 * 根据角色 以及权限获取 水司下水务所的节点
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findWaterCompanyByRole(TreeTypeDto treeTypeDto);
	
	/**
	 * 根据父类ID查询集合
	 * @param waterCompanyTreeInfo
	 * @return
	 */
	List<TreeInfo> findWaterCompanyTreeByPid(@Param("waterCompanyTreeInfo") List<TreeInfo> waterCompanyTreeInfo);

	/**
	 * 水司下面的水司   自动将营业所 和 水务所 自动上升到 水司节点
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findWaterCompanyByRoleAndNode(@Param("userId") String userId);
	
	/**
	 * 水司的上节点  父节
	 * @param waterCompanyTreeInfo
	 * @return
	 */
	List<TreeInfo> findWaterCompanyTreeByPidAndPid(@Param("waterCompanyTreeInfo") List<TreeInfo> waterCompanyTreeInfo);
	
	/**
	 * 水务所节点  含有视频的
	 * @param treeTypeDto
	 * @return
	 */
	List<TreeInfo> findWaterCompanyByRoleAndVideo(TreeTypeDto treeTypeDto);
	
	/**
	 * 水司到水司  的 有视频的节点 （废弃）
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findWaterCompanyByRoleAndNodeAndVideo(@Param("userId") String userId);
	
}