package com.wapwag.woss.modules.home.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.home.dao.DeviceDao;
import com.wapwag.woss.modules.home.dao.ProjectDao;
import com.wapwag.woss.modules.home.dao.PumpDevStatusDao;
import com.wapwag.woss.modules.home.dao.PumpHouseDao;
import com.wapwag.woss.modules.home.dao.RegionalDao;
import com.wapwag.woss.modules.home.dao.VideoDao;
import com.wapwag.woss.modules.home.entity.TreeTypeDto;
import com.wapwag.woss.modules.sys.dao.OfficeDao;

/**
 * 
 * @author zhaom
 * 
 *         返回ztree的统一格式
 * 
 *         [ {id:10, pid:0, name:"父亲节点"}, {id:100, pid:10, name:"儿子节点"},
 *         {id:1000,pid:100,name:"孙子节点"} ]
 *
 */
@Service
public class TreeTypeService {

	@Autowired
	private RegionalDao regionalDao;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private PumpHouseDao pumpHouseDao;

	@Autowired
	private DeviceDao deviceDao;

	// @Autowired
	// private WaterCompanyDao waterCompanyDao;

	@Autowired
	private VideoDao videoDao;

	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private PumpDevStatusDao pumpDevStatusDao;

	public List<TreeInfo> getTreeDataByType(TreeTypeDto treeTypeDto) {
		// 1城市->行政区->泵房
		if (treeTypeDto.getTreeTypeValue().equals("1")) {
			// 只到泵房
			if (!treeTypeDto.getIsSpread()) {
				return treeTypeToPumpHouse1(treeTypeDto.getUserId());
			}
			// 继续下一级别
			if (treeTypeDto.getIsSpread()) {
				// 到设备
				if (treeTypeDto.getIsDeviceOrVideo().equals("1")) {
					return treeTypeToDevice1(treeTypeDto.getUserId());
				}
				// 新的泵房视频完整树
				if (treeTypeDto.getIsDeviceOrVideo().equals("2")) {
					// 收起设备展示视频
					return removeNoVideoList(treeTypeDto.getUserId());
				}
			}
		}
		// 2 城市->城区/农村->泵房
		if (treeTypeDto.getTreeTypeValue().equals("2")) {
			// 只到泵房
			if (!treeTypeDto.getIsSpread()) {
				return treeTypeToPumpHouse2(treeTypeDto.getUserId());
			}
			// 继续下一级别
			if (treeTypeDto.getIsSpread()) {
				// 到设备
				if (treeTypeDto.getIsDeviceOrVideo().equals("1")) {
					return treeTypeToDevice2(treeTypeDto.getUserId());
				}
				// 到新的完整的视频树
				if (treeTypeDto.getIsDeviceOrVideo().equals("2")) {
					// 收起设备展示视频
					return removeNoVideoList2(treeTypeDto.getUserId());
				}
			}
		}
		// 3水司->水务所->泵房
		if (treeTypeDto.getTreeTypeValue().equals("3")) {
			// 只到泵房
			if (!treeTypeDto.getIsSpread()) {
				return treeTypeToPumpHouse3(treeTypeDto);
			}
			// 继续下一级
			if (treeTypeDto.getIsSpread()) {
				// 设备
				if (treeTypeDto.getIsDeviceOrVideo().equals("1")) {
					return treeTypeToDevice3(treeTypeDto);
				}
				// 到新的完整视频树
				if (treeTypeDto.getIsDeviceOrVideo().equals("2")) {
					// 收起设备展示视频
					return removeNoVideoList3(treeTypeDto);
				}
			}
		}
		// //4水司->营业所->泵房
		if (treeTypeDto.getTreeTypeValue().equals("4")) {
			// 只到泵房
			if (!treeTypeDto.getIsSpread()) {
				return treeTypeToPumpHouse4(treeTypeDto);
			}
			// 继续下一级
			if (treeTypeDto.getIsSpread()) {
				// 设备
				if (treeTypeDto.getIsDeviceOrVideo().equals("1")) {
					return treeTypeToDevice4(treeTypeDto);
				}
				// 到新的完整视频树
				if (treeTypeDto.getIsDeviceOrVideo().equals("2")) {
					// 收起设备展示视频
					return removeNoVideoList4(treeTypeDto);
				}
			}
		}
		// 5水司->水司->区域->泵房
		if (treeTypeDto.getTreeTypeValue().equals("5")) {
			// 只到泵房
			if (!treeTypeDto.getIsSpread()) {
				return treeTypeToPumpHouse5(treeTypeDto.getUserId());
			}
			// 继续下一级
			if (treeTypeDto.getIsSpread()) {
				// 设备
				if (treeTypeDto.getIsDeviceOrVideo().equals("1")) {
					return treeTypeToDevice5(treeTypeDto.getUserId());
				}
				// 到新的完整视频树
				if (treeTypeDto.getIsDeviceOrVideo().equals("2")) {
					// 收起设备展示视频
					return removeNoVideoList5(treeTypeDto.getUserId());
				}
			}
		}
		// 6 水司 城区农村 泵房
		if (treeTypeDto.getTreeTypeValue().equals("6")) {
			// 只到泵房
			if (!treeTypeDto.getIsSpread()) {
				return treeTypeToPumpHouse6(treeTypeDto.getUserId());
			}
			// 继续下一级
			if (treeTypeDto.getIsSpread()) {
				// 设备
				if (treeTypeDto.getIsDeviceOrVideo().equals("1")) {
					return treeTypeToDevice6(treeTypeDto.getUserId());
				}
				// 到新的完整视频树
				if (treeTypeDto.getIsDeviceOrVideo().equals("2")) {
					// 收起设备展示视频
					return removeNoVideoList6(treeTypeDto.getUserId());
				}
			}
		}
		return new ArrayList<TreeInfo>();
	}

	/**
	 * 第一种树 只到泵房
	 * 
	 * @param userId
	 * @return
	 */
	public List<TreeInfo> treeTypeToPumpHouse1(String userId) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 区域
		List<TreeInfo> regionalTreeInfo = regionalDao
				.findAllRegionalByRole(userId);
		// 城市--->找父类 确定
		if(regionalTreeInfo!=null && regionalTreeInfo.size()>0){
			List<TreeInfo> cityTreeInfo = regionalDao.findAllCity(regionalTreeInfo);
			allTreeInfo.addAll(cityTreeInfo);

		}
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByRegion(userId);
//		Map<String, String> all_PUMP_STATS = PumpDevStatusTask.ALL_PUMP_STATS();
		
//		for (TreeInfo treeInfo : pumpHouseTreeInfo) {
//			String one_PUMP_STATS = PumpDevStatusTask.ONE_PUMP_STATS(treeInfo.getPumpHouseId());
//			treeInfo.setPumpStatus(one_PUMP_STATS);
//		}
		allTreeInfo.addAll(regionalTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		return removeDuplicate(allTreeInfo);
	}

	/**
	 * 第一种树到设备
	 * 
	 * @param userId
	 * @return
	 */
	public List<TreeInfo> treeTypeToDevice1(String userId) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 区域
		List<TreeInfo> regionalTreeInfo = regionalDao
				.findAllRegionalByRole(userId);
		// 城市
		if(regionalTreeInfo!=null && regionalTreeInfo.size()>0){
			List<TreeInfo> cityTreeInfo = regionalDao.findAllCity(regionalTreeInfo);
			allTreeInfo.addAll(cityTreeInfo);
		}
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByRegion(userId);
		allTreeInfo.addAll(regionalTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		// 设备
		List<TreeInfo> deviceTreeInfo = deviceDao.findDeviceByPumpHouse(userId);
		allTreeInfo.addAll(deviceTreeInfo);
		return removeDuplicate(allTreeInfo);
	}

	/**
	 * 第二种树到泵房
	 * 
	 * @param userId
	 * @return
	 */
	private List<TreeInfo> treeTypeToPumpHouse2(String userId) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 城市即区域
		List<TreeInfo> regionalTreeInfo = regionalDao
				.findAllRegionalByRoleAndType(userId);
		// 城区或农村
		List<TreeInfo> typeTreeInfo = projectDao.findTypeByRegion(userId);
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByType(userId);
		allTreeInfo.addAll(regionalTreeInfo);
		allTreeInfo.addAll(typeTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		return allTreeInfo;
	}

	/**
	 * 第二种到设备
	 * 
	 * @param userId
	 * @return
	 */
	private List<TreeInfo> treeTypeToDevice2(String userId) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 城市即区域
		List<TreeInfo> regionalTreeInfo = regionalDao
				.findAllRegionalByRoleAndType(userId);
		// 需要到城市父类吗
		// 城区或农村
		List<TreeInfo> typeTreeInfo = projectDao.findTypeByRegion(userId);
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByType(userId);
		allTreeInfo.addAll(regionalTreeInfo);
		allTreeInfo.addAll(typeTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		// 设备
		List<TreeInfo> deviceTreeInfo = deviceDao
				.findDeviceByPumpHouseNew(userId);
		allTreeInfo.addAll(deviceTreeInfo);
		return allTreeInfo;
	}

	/**
	 * 第三种到泵房
	 * 
	 * @param userId
	 */
	private List<TreeInfo> treeTypeToPumpHouse3(TreeTypeDto treeTypeDto) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 水务所节点 --->waterCompanyDao.findWaterCompanyByRole(treeTypeDto);
		List<TreeInfo> waterCompanyTreeInfo = officeDao
				.findWaterHouseByRole(treeTypeDto.getUserId());
		// 水司 --->
		// waterCompanyDao.findWaterCompanyTreeByPid(waterCompanyTreeInfo);
		if(waterCompanyTreeInfo!=null && waterCompanyTreeInfo.size()>0){
			List<TreeInfo> pidWaterCompanyTreeInfo = officeDao
					.findOfficeTreeByPid(waterCompanyTreeInfo);
			allTreeInfo.addAll(pidWaterCompanyTreeInfo);
		}
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByOffice(treeTypeDto.getUserId());
		allTreeInfo.addAll(waterCompanyTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		return allTreeInfo;
	}

	/**
	 * 第三种到设备
	 * 
	 * @param treeTypeDto
	 * @return
	 */
	private List<TreeInfo> treeTypeToDevice3(TreeTypeDto treeTypeDto) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 水务所节点
		List<TreeInfo> waterCompanyTreeInfo = officeDao
				.findWaterHouseByRole(treeTypeDto.getUserId());
		// 水司
		if(waterCompanyTreeInfo!=null && waterCompanyTreeInfo.size()>0){
			List<TreeInfo> pidWaterCompanyTreeInfo = officeDao
					.findOfficeTreeByPid(waterCompanyTreeInfo);
			allTreeInfo.addAll(pidWaterCompanyTreeInfo);
		}
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByOffice(treeTypeDto.getUserId());
		allTreeInfo.addAll(waterCompanyTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		// 设备
		List<TreeInfo> deviceTreeInfo = deviceDao
				.findDeviceByPumpHouseAndOffice(treeTypeDto.getUserId());
		allTreeInfo.addAll(deviceTreeInfo);
		return allTreeInfo;
	}

	/**
	 * 第四种到泵房 营业所
	 * 
	 * @param userId
	 */
	private List<TreeInfo> treeTypeToPumpHouse4(TreeTypeDto treeTypeDto) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 营业所节点
		List<TreeInfo> waterCompanyTreeInfo = officeDao
				.findBusinessPlaceByRole(treeTypeDto.getUserId());
		// 水司
		if(waterCompanyTreeInfo!=null && waterCompanyTreeInfo.size()>0){
			List<TreeInfo> pidWaterCompanyTreeInfo = officeDao
					.findOfficeTreeByPid(waterCompanyTreeInfo);
			allTreeInfo.addAll(pidWaterCompanyTreeInfo);
		}
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByOfficeAndBusiness(treeTypeDto.getUserId());
		allTreeInfo.addAll(waterCompanyTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		return allTreeInfo;
	}

	/**
	 * 第四种到设备 营业所
	 * 
	 * @param treeTypeDto
	 * @return
	 */
	private List<TreeInfo> treeTypeToDevice4(TreeTypeDto treeTypeDto) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 营业所节点
		List<TreeInfo> waterCompanyTreeInfo = officeDao
				.findBusinessPlaceByRole(treeTypeDto.getUserId());
		// 水司
		if(waterCompanyTreeInfo!=null && waterCompanyTreeInfo.size()>0){
			List<TreeInfo> pidWaterCompanyTreeInfo = officeDao
					.findOfficeTreeByPid(waterCompanyTreeInfo);
			allTreeInfo.addAll(pidWaterCompanyTreeInfo);
		}
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByOfficeAndBusiness(treeTypeDto.getUserId());
		allTreeInfo.addAll(waterCompanyTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		// 设备
		List<TreeInfo> deviceTreeInfo = deviceDao
				.findDeviceByPumpHouseAndBusiness(treeTypeDto.getUserId());
		allTreeInfo.addAll(deviceTreeInfo);
		return allTreeInfo;
	}

	/**
	 * 第五种到泵房 水司
	 * 
	 * @param treeTypeDto
	 */
	private List<TreeInfo> treeTypeToPumpHouse5(String userId) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 水司节点
		List<TreeInfo> waterCompanyTreeInfo = officeDao
				.findWaterDepartByRole(userId);
		// 水司 父节点 不确定 水司的父类 有几级 递归
		// List<TreeInfo> pidWaterCompanyTreeInfo =
		// officeDao.findOfficeTreeByPid(waterCompanyTreeInfo);
		if(waterCompanyTreeInfo!=null && waterCompanyTreeInfo.size()>0){
			List<TreeInfo> pidWaterCompanyTreeInfo = getOffficePidTree(
					new ArrayList<TreeInfo>(), waterCompanyTreeInfo);
			allTreeInfo.addAll(pidWaterCompanyTreeInfo);
		}
		// 区域
		List<TreeInfo> regionalTreeInfo = regionalDao
				.findAllRegionalByWaterDepart(userId);
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByRegionNew(userId);
	
		allTreeInfo.addAll(waterCompanyTreeInfo);
		allTreeInfo.addAll(regionalTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		return allTreeInfo;
	}

	/**
	 * 第五种到设备 水司
	 * 
	 * @param userId
	 */
	private List<TreeInfo> treeTypeToDevice5(String userId) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 水司节点
		List<TreeInfo> waterCompanyTreeInfo = officeDao
				.findWaterDepartByRole(userId);
		// 水司 父节点 递归
		if(waterCompanyTreeInfo!=null && waterCompanyTreeInfo.size()>0){
			List<TreeInfo> pidWaterCompanyTreeInfo = getOffficePidTree(
					new ArrayList<TreeInfo>(), waterCompanyTreeInfo);
			allTreeInfo.addAll(pidWaterCompanyTreeInfo);
		}
		// 区域
		List<TreeInfo> regionalTreeInfo = regionalDao
				.findAllRegionalByWaterDepart(userId);
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByRegionNew(userId);
		allTreeInfo.addAll(waterCompanyTreeInfo);
		allTreeInfo.addAll(regionalTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		// 设备
		List<TreeInfo> deviceTreeInfo = deviceDao
				.findDeviceByWaterDepart(userId);
		allTreeInfo.addAll(deviceTreeInfo);
		return allTreeInfo;
	}

	/**
	 * 第六种到泵房
	 * 
	 * @param userId
	 */
	private List<TreeInfo> treeTypeToPumpHouse6(String userId) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 水司节点
		List<TreeInfo> waterCompanyTreeInfo = officeDao
				.findWaterDepartByRole(userId);
		// 水司 父节点
//		List<TreeInfo> pidWaterCompanyTreeInfo = officeDao
//				.findOfficeTreeByPid(waterCompanyTreeInfo);
		// 城区或农村
		List<TreeInfo> typeTreeInfo = projectDao.findTypeByWaterDepart(userId);
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByTypeAndWaterDepart(userId);
		allTreeInfo.addAll(waterCompanyTreeInfo);
//		allTreeInfo.addAll(pidWaterCompanyTreeInfo);
		allTreeInfo.addAll(typeTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		// 隐藏节点
		return allTreeInfo;
	}

	/**
	 * 第六种到设备
	 * 
	 * @param userId
	 */
	private List<TreeInfo> treeTypeToDevice6(String userId) {
		List<TreeInfo> allTreeInfo = new ArrayList<TreeInfo>();
		// 水司节点
		List<TreeInfo> waterCompanyTreeInfo = officeDao
				.findWaterDepartByRole(userId);
		// 水司 父节点
//		List<TreeInfo> pidWaterCompanyTreeInfo = officeDao
//				.findOfficeTreeByPid(waterCompanyTreeInfo);
		// 城区或农村
		List<TreeInfo> typeTreeInfo = projectDao.findTypeByWaterDepart(userId);
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByTypeAndWaterDepart(userId);
		allTreeInfo.addAll(waterCompanyTreeInfo);
//		allTreeInfo.addAll(pidWaterCompanyTreeInfo);
		allTreeInfo.addAll(typeTreeInfo);
		allTreeInfo.addAll(pumpHouseTreeInfo);
		// 设备
		List<TreeInfo> deviceTreeInfo = deviceDao
				.findDeviceByWaterDepartAndType(userId);
		allTreeInfo.addAll(deviceTreeInfo);
		return allTreeInfo;

	}

	/**
	 * 第一种 视频 移除没有视频下的 泵房和地区
	 * 
	 * @param allTreeInfo
	 * @return
	 */
	public List<TreeInfo> removeNoVideoList(String userId) {
		List<TreeInfo> newAllTreeInfo = new ArrayList<TreeInfo>();
		// 区域
		List<TreeInfo> findAllRegionalByVideo = regionalDao
				.findAllRegionalByVideo(userId);
		newAllTreeInfo.addAll(findAllRegionalByVideo);
		// 城市 --->找父类
		if(findAllRegionalByVideo!=null && findAllRegionalByVideo.size()>0){
			List<TreeInfo> cityTreeInfo = regionalDao
					.findAllCity(findAllRegionalByVideo);
			newAllTreeInfo.addAll(cityTreeInfo);
		}
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByVideo(userId);
		newAllTreeInfo.addAll(pumpHouseTreeInfo);
		// 视频
		List<TreeInfo> addVideoList = videoDao.findVideoByPumpId(userId);
		newAllTreeInfo.addAll(addVideoList);
		return removeDuplicate(newAllTreeInfo);
	}

	/**
	 * 第二种树下 视频情况
	 * 
	 * @param userId
	 * @return
	 */
	private List<TreeInfo> removeNoVideoList2(String userId) {
		List<TreeInfo> newAllTreeInfo = new ArrayList<TreeInfo>();
		// 区域
		List<TreeInfo> findAllRegionalByRoleAndTypeAndVideo = regionalDao
				.findAllRegionalByRoleAndTypeAndVideo(userId);
		newAllTreeInfo.addAll(findAllRegionalByRoleAndTypeAndVideo);
		// 城区 农村
		List<TreeInfo> findTypeByRegionAndVedio = projectDao
				.findTypeByRegionAndVedio(userId);
		newAllTreeInfo.addAll(findTypeByRegionAndVedio);
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByTypeNew(userId);
		newAllTreeInfo.addAll(pumpHouseTreeInfo);
		// 视频
		List<TreeInfo> addVideoList = videoDao.findVideoByPumpIdNew(userId);
		newAllTreeInfo.addAll(addVideoList);
		// 返回 目前没有发现重复节点数据
		return newAllTreeInfo;
	}

	/**
	 * 第三种树 视频情况
	 * 
	 * @param userId
	 * @return
	 */
	private List<TreeInfo> removeNoVideoList3(TreeTypeDto treeTypeDto) {
		List<TreeInfo> newAllTreeInfo = new ArrayList<TreeInfo>();
		// 水司 ---->waterCompanyDao.findWaterCompanyByRoleAndVideo(treeTypeDto);
		List<TreeInfo> findWaterCompanyByRoleAndVideo = officeDao
				.findWaterHouseByRoleAndVideo(treeTypeDto.getUserId());
		newAllTreeInfo.addAll(findWaterCompanyByRoleAndVideo);
		// 水司父亲节点
		if(findWaterCompanyByRoleAndVideo!=null && findWaterCompanyByRoleAndVideo.size()>0){
			List<TreeInfo> pidWaterCompanyTreeInfo = officeDao
					.findOfficeTreeByPid(findWaterCompanyByRoleAndVideo);
			newAllTreeInfo.addAll(pidWaterCompanyTreeInfo);
		}
		// 泵房
		List<TreeInfo> findPumpHouseByCompanyAndVideo = pumpHouseDao
				.findPumpHouseByOfficeAndVideo(treeTypeDto.getUserId());
		newAllTreeInfo.addAll(findPumpHouseByCompanyAndVideo);
		// 视频
		List<TreeInfo> addVideoList = videoDao
				.findVideoByPumpIdAndOffice(treeTypeDto.getUserId());
		newAllTreeInfo.addAll(addVideoList);
		return newAllTreeInfo;
	}

	/**
	 * 第四种树 营业所视频情况
	 * 
	 * @param userId
	 * @return
	 */
	private List<TreeInfo> removeNoVideoList4(TreeTypeDto treeTypeDto) {
		List<TreeInfo> newAllTreeInfo = new ArrayList<TreeInfo>();
		// 营业所
		List<TreeInfo> findWaterCompanyByRoleAndVideo = officeDao
				.findBusinessByRoleAndVideo(treeTypeDto.getUserId());
		newAllTreeInfo.addAll(findWaterCompanyByRoleAndVideo);
		// 水司父亲节点
		if(findWaterCompanyByRoleAndVideo!=null && findWaterCompanyByRoleAndVideo.size()>0){
			List<TreeInfo> pidWaterCompanyTreeInfo = officeDao
					.findOfficeTreeByPid(findWaterCompanyByRoleAndVideo);
			newAllTreeInfo.addAll(pidWaterCompanyTreeInfo);
		}
		// 泵房
		List<TreeInfo> findPumpHouseByCompanyAndVideo = pumpHouseDao
				.findPumpHouseByBusinessAndVideo(treeTypeDto.getUserId());
		newAllTreeInfo.addAll(findPumpHouseByCompanyAndVideo);
		// 视频
		List<TreeInfo> addVideoList = videoDao
				.findVideoByPumpIdAndBusiness(treeTypeDto.getUserId());
		newAllTreeInfo.addAll(addVideoList);
		return newAllTreeInfo;
	}

	/**
	 * 第五种视频情况
	 * 
	 * @param treeTypeDto
	 * @return
	 */
	private List<TreeInfo> removeNoVideoList5(String userId) {
		List<TreeInfo> newAllTreeInfo = new ArrayList<TreeInfo>();
		// 水司
		List<TreeInfo> findWaterCompanyByRoleAndNodeAndVideo = officeDao
				.findWaterWaterDepartByRole(userId);
		newAllTreeInfo.addAll(findWaterCompanyByRoleAndNodeAndVideo);
		// 水司 父节点
		if(findWaterCompanyByRoleAndNodeAndVideo!=null && findWaterCompanyByRoleAndNodeAndVideo.size()>0){
		List<TreeInfo> pidWaterCompanyTreeInfo = getOffficePidTree(
				new ArrayList<TreeInfo>(),
				findWaterCompanyByRoleAndNodeAndVideo);
		newAllTreeInfo.addAll(pidWaterCompanyTreeInfo);
		}
		// 区域
		List<TreeInfo> regionalTreeInfo = regionalDao
				.findAllRegionalByWaterDepartNew(userId);
		newAllTreeInfo.addAll(regionalTreeInfo);
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByRegionAndVideoNew(userId);
		newAllTreeInfo.addAll(pumpHouseTreeInfo);
		// 视频
		List<TreeInfo> addVideoList = videoDao
				.findVideoByPumpIdAndWaterDepart(userId);
		newAllTreeInfo.addAll(addVideoList);
		return newAllTreeInfo;
	}

	/**
	 * 第六种树 视频情况
	 * 
	 * @param userId
	 * @return
	 */
	private List<TreeInfo> removeNoVideoList6(String userId) {
		List<TreeInfo> newAllTreeInfo = new ArrayList<TreeInfo>();
		// 水司
		List<TreeInfo> findWaterCompanyByRoleAndNodeAndVideo = officeDao
				.findWaterDepartByRoleAndNodeAndVideo(userId);
		newAllTreeInfo.addAll(findWaterCompanyByRoleAndNodeAndVideo);
		// 水司 父节点
//		List<TreeInfo> pidWaterCompanyTreeInfo = officeDao
//				.findOfficeTreeByPid(findWaterCompanyByRoleAndNodeAndVideo);
//		newAllTreeInfo.addAll(pidWaterCompanyTreeInfo);
		// 城区或农村
		List<TreeInfo> typeTreeInfo = projectDao
				.findTypeByWaterDepartAndVideo(userId);
		newAllTreeInfo.addAll(typeTreeInfo);
		// 泵房
		List<TreeInfo> pumpHouseTreeInfo = pumpHouseDao
				.findPumpHouseByTypeAndWaterDepartAndVideo(userId);
		newAllTreeInfo.addAll(pumpHouseTreeInfo);
		// 视频
		List<TreeInfo> addVideoList = videoDao
				.findVideoByWaterTypePumpId(userId);
		newAllTreeInfo.addAll(addVideoList);
		// 隐藏节点
		return newAllTreeInfo;
	}
	
	/**
	 * 
	 * 宁波市 父节点重复 删除一个
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeInfo> removeDuplicate(List<TreeInfo> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getId().equals(list.get(i).getId())) {
					list.remove(j);
				}
			}
		}
		return list;
	}

	/**
	 * 水司父类
	 * @param list
	 * @param waterCompanyTreeInfo
	 * @return
	 */
	public List<TreeInfo> getOffficePidTree(List<TreeInfo> list,
			List<TreeInfo> waterCompanyTreeInfo) {
		List<TreeInfo> findOfficeTreeByPid = officeDao
				.findOfficeTreeByPid(waterCompanyTreeInfo);
		list.addAll(findOfficeTreeByPid);
		if (findOfficeTreeByPid != null && findOfficeTreeByPid.size() > 0) {
			getOffficePidTree(list, findOfficeTreeByPid);
		}
		return list;
	}
	
	/**
	 * 展示视频树
	 * 
	 * @param pumpHouseTreeInfo
	 * @return
	 */
	// public List<TreeInfo> addVideoList(String userId){
	// List<TreeInfo> videoTreeInfo = videoDao.findVideoByPumpId(userId);
	// return videoTreeInfo;
	// }
	//
	/**
	 * 区域 -- 区域类型 ，如 120101--3
	 * 
	 * @param typeTreeInfo
	 * @return
	 */
	// public List<RegionTypeDto> getRegionTypeDtoList(List<TreeInfo>
	// typeTreeInfo){
	// //构造参数
	// List<RegionTypeDto> list = new ArrayList<RegionTypeDto>();
	// for (TreeInfo treeInfo : typeTreeInfo) {
	// RegionTypeDto regionTypeDto = new RegionTypeDto();
	// String id = treeInfo.getId();
	// String[] idConcat = id.split("--");
	// regionTypeDto.setRegionId(idConcat[0]);
	// regionTypeDto.setRegionType(idConcat[1]);
	// list.add(regionTypeDto);
	// }
	// return list;
	// }

	/**
	 * 泵房ID转换 如 h--16120259
	 * 
	 * @param pumpHouseTreeInfo
	 * @return
	 */
	// public List<RegionTypeDto> getTreeInfoList(List<TreeInfo>
	// pumpHouseTreeInfo){
	// //构造参数
	// List<RegionTypeDto> list = new ArrayList<RegionTypeDto>();
	// for (TreeInfo treeInfo : pumpHouseTreeInfo) {
	// RegionTypeDto regionTypeDto = new RegionTypeDto();
	// String[] id = treeInfo.getId().split("--");
	// regionTypeDto.setPumpHouseId(id[1]);
	// list.add(regionTypeDto);
	// }
	// return list;
	// }
	//
	/**
	 * 隐藏 营业所 水务所节点 -->删除节点 替换下面节点的 pid
	 * 
	 * @param allTreeInfo
	 * @return
	 */
	// public List<TreeInfo> getTreeInfoListByHidden(List<TreeInfo>
	// allTreeInfo){
	// List<TreeInfo> allTreeInfoRet = new ArrayList<TreeInfo>();
	// for (TreeInfo treeInfo : allTreeInfo) {
	// if(!StringUtils.isEmpty(treeInfo.getType())){
	// if(treeInfo.getType().equals("2") || treeInfo.getType().equals("3") ){
	// //treeInfo.setIsHidden(true);
	// for (TreeInfo treeInfo2 : allTreeInfo) {
	// //如果所有集合里面的pID有营业所或水务所的ID的时候 自动升级到 他的PID
	// if(treeInfo2.getpId().equals(treeInfo.getId())){
	// treeInfo2.setpId(treeInfo.getpId());
	// }
	// }
	// }
	// }
	// }
	// //删除
	// for (TreeInfo treeInfo3 : allTreeInfo) {
	// if(!StringUtils.isEmpty(treeInfo3.getType())){
	// if(treeInfo3.getType().equals("1")){
	// //allTreeInfo.remove(treeInfo3);
	// allTreeInfoRet.add(treeInfo3);
	// }
	// }else{
	// allTreeInfoRet.add(treeInfo3);
	// }
	// }
	// return allTreeInfoRet;
	// }

	/**
	 * 水司 -- 类型 如 f89023ca0eed46b99afbd7ad18e1ced7--2
	 * 
	 * @param typeTreeInfo
	 * @return
	 */
	// public List<RegionTypeDto> getRegionTypeDtoByCompanyList(List<TreeInfo>
	// typeTreeInfo) {
	// //构造参数
	// List<RegionTypeDto> list = new ArrayList<RegionTypeDto>();
	// for (TreeInfo treeInfo : typeTreeInfo) {
	// RegionTypeDto regionTypeDto = new RegionTypeDto();
	// String id = treeInfo.getId();
	// String[] idConcat = id.split("--");
	// regionTypeDto.setCompanyId(idConcat[0]);
	// regionTypeDto.setRegionType(idConcat[1]);
	// list.add(regionTypeDto);
	// }
	// return list;
	// }
	
	public String selectByDevId(String devId){
		String selectByDevId = pumpDevStatusDao.selectByDevId(devId);
		if(StringUtils.isEmpty(selectByDevId)){
			return "2";//离线
		}
		return selectByDevId;
	}
	
	public String selectByPumpId(String pumpId){
		String selectByPumpId = pumpDevStatusDao.selectByPumpId(pumpId);
		if(StringUtils.isEmpty(selectByPumpId)){
			return "2";//离线
		}
		return selectByPumpId;
	}

}
