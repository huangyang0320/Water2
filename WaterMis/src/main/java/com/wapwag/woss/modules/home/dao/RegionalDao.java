package com.wapwag.woss.modules.home.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.home.entity.RegionalInfo;

@MyBatisDao("com.wapwag.woss.modules.home.dao.RegionalDao")
public interface RegionalDao {

    Integer findAllDeviceCount(@Param("userId") String userId);

    List<RegionalInfo> findAllDevice(@Param("userId") String userId);
    
    //项目或泵房下面没有项目的   有设备没有泵房或项目的   以及当前用户角色能看的设备 最终树
    List<TreeInfo> findAllRegionalByRole(@Param("userId") String userId);
    
    //过了掉城市农村类型为空的
    List<TreeInfo> findAllRegionalByRoleAndType(@Param("userId") String userId);
    
    //根据水司 获取 区域列表
    List<TreeInfo> findAllRegionalByCompany(@Param("waterCompanyTreeInfo") List<TreeInfo> waterCompanyTreeInfo);
    
    //求区域父类的城市
    List<TreeInfo> findAllCity(@Param("cityTreeInfo") List<TreeInfo> cityTreeInfo);
    
    //求区域下的视频树
    List<TreeInfo> findAllRegionalByVideo(@Param("userId") String userId);
    
    //求区域下的视频树  农村城市
    List<TreeInfo> findAllRegionalByRoleAndTypeAndVideo(@Param("userId") String userId);
    
    //根据水司 获取 区域列表 有视频
    List<TreeInfo> findAllRegionalByCompanyAndVideo(@Param("waterCompanyTreeInfo") List<TreeInfo> waterCompanyTreeInfo);
    
    //水司下面的区域集合
    List<TreeInfo> findAllRegionalByWaterDepart(@Param("userId") String userId);
    
    //水司下面的区域 视频数
    List<TreeInfo> findAllRegionalByWaterDepartNew(@Param("userId") String userId);
    

}