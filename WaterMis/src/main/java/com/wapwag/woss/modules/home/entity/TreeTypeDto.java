package com.wapwag.woss.modules.home.entity;

import java.io.Serializable;

/**
 * 前台传入树的参数要求
 * @author zhaom
 *
 */
public class TreeTypeDto implements Serializable{
	
	private static final long serialVersionUID = -4214111410171570119L;
	private String treeTypeValue;
	private Boolean isSpread;//默认节点到泵房，是否展开到设备节点
	//private Boolean isVideo;//默认节点到泵房，是否展开到视频节点 
	private String userId;//用户id对应得角色约束
	private String waterCompanyType;// 水司节点类型  1 水司  2水务所  3营业所
	private String isDeviceOrVideo;  //1 设备  2视频
	
	public String getTreeTypeValue() {
		return treeTypeValue;
	}
	public void setTreeTypeValue(String treeTypeValue) {
		this.treeTypeValue = treeTypeValue;
	}
	public Boolean getIsSpread() {
		return isSpread;
	}
	public void setIsSpread(Boolean isSpread) {
		this.isSpread = isSpread;
	}
//	public Boolean getIsVideo() {
//		return isVideo;
//	}
//	public void setIsVideo(Boolean isVideo) {
//		this.isVideo = isVideo;
//	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWaterCompanyType() {
		return waterCompanyType;
	}
	public void setWaterCompanyType(String waterCompanyType) {
		this.waterCompanyType = waterCompanyType;
	}
	public String getIsDeviceOrVideo() {
		return isDeviceOrVideo;
	}
	public void setIsDeviceOrVideo(String isDeviceOrVideo) {
		this.isDeviceOrVideo = isDeviceOrVideo;
	}
	
	 

}
