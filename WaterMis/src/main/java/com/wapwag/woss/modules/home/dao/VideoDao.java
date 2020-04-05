package com.wapwag.woss.modules.home.dao;

import java.util.List;
import java.util.Map;

import com.wapwag.woss.modules.home.util.hik.AccessData;
import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.home.entity.VideoInfo;

/**
 * VideoInfo dao
 * Created by Administrator on 2016/8/2.
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.VideoDao")
public interface VideoDao extends CrudDao<VideoInfo> {
	VideoInfo getVideoByCode(String videoNo);
	
	/**
	 * 泵房下面的视频树
	 * @param pumpHouseTreeInfo
	 * @return
	 */
	List<TreeInfo> findVideoByPumpId(@Param("userId") String userId);
	
	/**
	 * 农村区域下的 角色 设备  视频树
	 * 第二个视频树
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findVideoByPumpIdNew(@Param("userId") String userId);

	/**
	 * 水务所下面泵房 视频的树
	 * @param userId
	 * @return
	 */
	List<TreeInfo>  findVideoByPumpIdAndOffice(@Param("userId") String userId);
	
	/**
	 * 营业所下面泵房 视频的树
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findVideoByPumpIdAndBusiness(@Param("userId") String userId);
	
	/**
	 * 泵房下面的所有视频信息
	 * @param pumpHouseId
	 * @return
	 */
	List<VideoInfo> findVideoListByPumpId(@Param("pumpHouseId") String pumpHouseId);
	
	/**
	 * 水司下的视频树
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findVideoByPumpIdAndWaterDepart(@Param("userId") String userId);
	
	/**
	 * 水司 区域  农村  下的 视频
	 * @param userId
	 * @return
	 */
	List<TreeInfo> findVideoByWaterTypePumpId(@Param("userId") String userId);
	
	List<Map<String, Object>> hikConf();

	List<AccessData> getAccessDataByPumpId(@Param("pumpId") String pumpId);
	
}
