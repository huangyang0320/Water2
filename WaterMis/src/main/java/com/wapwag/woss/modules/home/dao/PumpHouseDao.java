package com.wapwag.woss.modules.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.RegionTypeDto;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.home.entity.PumpHouseInfo;

/**
 * PumpHouseInfo dao
 * Created by Administrator on 2016/8/2.
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.PumpHouseDao")
public interface PumpHouseDao extends CrudDao<PumpHouseInfo> {
	
	/**
	 * 根据区域查找泵房
	 * 补充 还是需要根据角色关联
	 * @param projectTreeInfo
	 * @return
	 */
	List<TreeInfo> findPumpHouseByRegion(@Param("userId") String userId);
	
	/**
	 * 城区农村下的泵房
	 * 补充 关联角色
	 * @param regionTypeDto
	 * @return
	 */
    List<TreeInfo> findPumpHouseByType(@Param("userId") String userId);
    
    /**
     * 城区农村下的泵房  有视频 关联角色
     * @param userId
     * @return
     */
    List<TreeInfo> findPumpHouseByTypeNew(@Param("userId") String userId);
    
    /**
     * 水司水务所下的泵房
     * @param regionTypeDto
     * @return
     */
    List<TreeInfo> findPumpHouseByCompany(@Param("waterCompanyTreeInfo") List<TreeInfo> waterCompanyTreeInfo);
    
    /**
     * 水司水务所下的泵房  废弃 水司表  用 组织机构表  20180627
     * @param userId
     * @return
     */
    List<TreeInfo> findPumpHouseByOffice(@Param("userId") String userId);
    
    /**
     * 营业所树 下的泵房
     * @param userId
     * @return
     */
    List<TreeInfo>  findPumpHouseByOfficeAndBusiness(@Param("userId") String userId);
    
    /**
     * 水司 区域 下的泵房
     * @param userId
     * @return
     */
    List<TreeInfo> findPumpHouseByRegionNew(@Param("userId") String userId);
    
    /**
     * 水司 下的 城区 农村  下的泵房
     * @param regionTypeDto
     * @return
     */
    List<TreeInfo> findPumpHouseByTypeAndCompany(@Param("regionTypeDto") List<RegionTypeDto> regionTypeDto);
    
    /**
     * 区域下的视频树 泵房
     * 补充  角色下的设备   设备下的 视频
     * @param regionalTreeInfo
     * @return
     */
    List<TreeInfo> findPumpHouseByVideo(@Param("userId") String userId);
    
    /**
     * 城区农村下含有视频的泵房
     * @param regionTypeDto
     * @return
     */
    List<TreeInfo> findPumpHouseByTypeAndVedio(@Param("regionTypeDto") List<RegionTypeDto> regionTypeDto);
    
    /**
     * 水司水务所下的泵房 有视频
     * @param regionTypeDto
     * @return
     */
    List<TreeInfo> findPumpHouseByCompanyAndVideo (@Param("waterCompanyTreeInfo") List<TreeInfo> waterCompanyTreeInfo);
    
    /**
     * 水务所 下的视频
     * @param userId
     * @return
     */
    List<TreeInfo> findPumpHouseByOfficeAndVideo(@Param("userId") String userId);
    
    /**
     * 营业所下的视频
     * @param userId
     * @return
     */
    List<TreeInfo> findPumpHouseByBusinessAndVideo(@Param("userId") String userId);
    
    /**
     * 水司下的视频
     * @param userId
     * @return
     */
    List<TreeInfo>  findPumpHouseByRegionAndVideoNew(@Param("userId") String userId);

    /**
     * 区域下的泵房 有视频的
     * @param regionalTreeInfo
     * @return
     */
    List<TreeInfo> findPumpHouseByRegionAndVideo(@Param("regionalTreeInfo") List<TreeInfo> regionalTreeInfo);
    
    /**
     * 水司 下的 城区 农村  下的泵房  有视频
     * @param regionTypeDto
     * @return
     */
    List<TreeInfo> findPumpHouseByTypeAndCompanyAndVideo(@Param("regionTypeDto") List<RegionTypeDto> regionTypeDto);
    
    /**
     * 水司  城区 农村 下的 泵房
     * @param userId
     * @return
     */
    List<TreeInfo> findPumpHouseByTypeAndWaterDepart(@Param("userId") String userId);
    
    /**
     * 水司  城区 农村 下的 泵房 视频 
     * @param userId
     * @return
     */
    List<TreeInfo> findPumpHouseByTypeAndWaterDepartAndVideo(@Param("userId") String userId);

    /**
     * 根据用户id查找该用户权限下所有的泵房id
     * @param userId
     * @return
     */
    List<String> selectPumpIdByUser(@Param("userId") String userId);
}
