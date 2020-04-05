package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.TreeDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.sys.entity.Office;

/**
 * 机构DAO接口
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	/**
	 * 水务所 节点
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findWaterHouseByRole(@Param("userId") String userId);
	
	/**
	 * 营业所节点
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findBusinessPlaceByRole(@Param("userId") String userId);
	
	/**
	 * 水司节点树
	 * @param userId
	 * @return
	 */
	List<TreeInfo>  findWaterDepartByRole(@Param("userId") String userId);
	
	/**
	 * 求父节点信息
	 * @param officeTreeInfo
	 * @return
	 */
	List<TreeInfo> findOfficeTreeByPid(@Param("officeTreeInfo") List<TreeInfo> officeTreeInfo);
	
	/**
	 * 水务所 视频 树
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findWaterHouseByRoleAndVideo(@Param("userId") String userId);
	
	/**
	 * 营业所视频树
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findBusinessByRoleAndVideo(@Param("userId") String userId);
	
	/**
	 * 新的 水司 到水司 的视频树
	 * @param userId
	 * @return
	 */
	List<TreeInfo>  findWaterWaterDepartByRole(@Param("userId") String userId);
	
	/**
	 * 水司  区域 农村 视频树
	 * @param userId
	 * @return
	 */
	List<TreeInfo>  findWaterDepartByRoleAndNodeAndVideo(@Param("userId") String userId);
	
	
	
	
	
}
