package com.wapwag.woss.modules.home.util.hik;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.wapwag.woss.common.task.CountValueUtil;

/**
 * 海康api接口
 * 
 * @author gongll
 * @date 2018年8月22日
 */
public class OpenApiHttp {

	/** 日志 */
	private static Logger LOG = LoggerFactory.getLogger(OpenApiHttp.class);
	
	// 获取默认uuid 请求地址
	private static final String USER_UUID_URL = "/openapi/service/base/user/getDefaultUserUuid";
	
	/** 获取配置用户 */
	private static final String GET_ALL_NET = "/openapi/service/base/netZone/getNetZones";
	
	/** 获取配置用户 */
	private static final String GET_ALL_USER = "/openapi/service/base/user/getUsers";
	
	private static final String GET_VIDEO_PREVIEW = "/openapi/service/vss/preview/getPreviewParamByCameraUuid";
	
	private static final String GET_BACK_PREVIEW = "/openapi/service/vss/playback/getPlaybackParamByPlanUuid";
	private static final String GET_PLAN_RECORD = "/openapi/service/vss/playback/getRecordPlansByCameraUuids";
	
	private static final int PAGE_SIZE = 50;
	
	// 查询设备状态 请求地址
	private static final String GWT_DOOT_STATUS = "/openapi/service/acs/status/getDoorStatusByUuids";
	
	// 下空地址
	private static final String SYNC_CONTROL = "/openapi/service/acs/control/synControl";

	/**
	 * 获取预览资源
	 * 
	 * @param hostUrl
	 * @param appKey
	 * @return
	 */
	public static String getPreview(HikLogin info,String cameraUuid) {

		String url = info.getUrl() + GET_VIDEO_PREVIEW;

		// 组装请求对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", info.getAppKey());
		map.put("time", System.currentTimeMillis());
		map.put("opUserUuid", info.getUserUuid());
		map.put("cameraUuid", cameraUuid);
		map.put("netZoneUuid", info.getNetZoneUUid());

		String params = JSON.toJSONString(map);

		String reqJSon = url
				+ "?token="
				+ Digests
						.buildToken(url + "?" + params, null, info.getSecret());
		try {
			String data = HttpClientSSLUtils.doPost(reqJSon, params);
			
			Map<String, Object> result = JSON.parseObject(data);
			if ("0".equals(result.get("errorCode") + "")) {
				return result.get("data").toString();
			}
			
		} catch (Exception e) {
			LOG.error("海康接口异常,获取默认失败", e);
		}

		return "";
	}
	
	/**
	 * 获取回放资源
	 * 
	 * @param hostUrl
	 * @param appKey
	 * @return
	 */
	public static String getBackResouse(HikLogin info,String cameraUuid) {
		
		Map<String, Object> resouse = getRecordPlansByCameraUuids(info, cameraUuid);
		if (null == resouse.get("recordPlanUuid") || null == resouse.get("planType") ) {
			return "";
		}

		String url = info.getUrl() + GET_BACK_PREVIEW;

		// 组装请求对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", info.getAppKey());
		map.put("time", System.currentTimeMillis());
		map.put("opUserUuid", info.getUserUuid());
		
		map.put("recordPlanUuid", resouse.get("recordPlanUuid"));
		map.put("planType", resouse.get("planType"));
		map.put("netZoneUuid", info.getNetZoneUUid());

		String params = JSON.toJSONString(map);

		String reqJSon = url
				+ "?token="
				+ Digests
						.buildToken(url + "?" + params, null, info.getSecret());
		try {
			String data = HttpClientSSLUtils.doPost(reqJSon, params);
			
			Map<String, Object> result = JSON.parseObject(data);
			if ("0".equals(result.get("errorCode") + "")) {
				return result.get("data").toString();
			}
			
		} catch (Exception e) {
			LOG.error("海康接口异常,获取默认失败", e);
		}

		return "";
	}
	
	/**
	 * 获取录像记录
	 * 
	 * @param hostUrl
	 * @param appKey
	 * @return
	 */
	private static Map<String, Object> getRecordPlansByCameraUuids(HikLogin info,String cameraUuid) {

		Object isNormal = 0;
		String url = info.getUrl() + GET_PLAN_RECORD;

		// 组装请求对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", info.getAppKey());
		map.put("time", System.currentTimeMillis());
		map.put("opUserUuid", info.getUserUuid());
		map.put("cameraUuids", cameraUuid);
		
		map.put("pageNo", 1);
		map.put("pageSize", 150);
		
		map.put("netZoneUuid", info.getNetZoneUUid());

		String params = JSON.toJSONString(map);

		String reqJSon = url
				+ "?token="
				+ Digests
						.buildToken(url + "?" + params, null, info.getSecret());
		try {
			String data = HttpClientSSLUtils.doPost(reqJSon, params);
			
			ResultInfo resultInfo = JSON.parseObject(data,ResultInfo.class);
			if (null != resultInfo && null != resultInfo.getClass() && null != resultInfo.getData().getList() &&
					resultInfo.getData().getList().size() > 0) {
				for(Map<String, Object> val:resultInfo.getData().getList()){
					if (isNormal.equals(val.get("enabled"))) {
						return val;
					}
				}
			}
			
		} catch (Exception e) {
			LOG.error("海康接口异常,获取默认失败", e);
		}

		return new HashMap<String,Object>();
	}

	/**
	 * 获取默认uuid
	 * 
	 * @param hostUrl
	 * @param appKey
	 * @return
	 */
	private static String getDefaultUserUuid(HikLogin info) {

		String url = info.getUrl() + USER_UUID_URL;

		// 组装请求对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", info.getAppKey());
		map.put("time", System.currentTimeMillis());

		String params = JSON.toJSONString(map);

		String reqJSon = url
				+ "?token="
				+ Digests
						.buildToken(url + "?" + params, null, info.getSecret());
		try {
			String data = HttpClientSSLUtils.doPost(reqJSon, params);
			Map<String, Object> result = JSON.parseObject(data);
			if ("0".equals(result.get("errorCode") + "")) {
				return result.get("data").toString();
			}
		} catch (Exception e) {
			LOG.error("海康接口异常,获取默认失败", e);
		}

		return "";
	}
	
	/**
	 * 获取当前系统配置用户uuid，可以限制平台权限
	 * 
	 * @param hostUrl
	 * @param appKey
	 * @return
	 */
	public static String getOperUser(HikLogin info) {
		
		String defaultUser = getDefaultUserUuid(info);
		if (StringUtils.isEmpty(defaultUser)) {
			return "";
		}

		List<Map<String, Object>> temp;
		String url = info.getUrl() + GET_ALL_USER;

		// 组装请求对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", info.getAppKey());
		map.put("time", System.currentTimeMillis());
		map.put("opUserUuid", defaultUser);
		map.put("pageNo", 1);
		map.put("pageSize", PAGE_SIZE);

		String params = JSON.toJSONString(map);

		String reqJSon = url
				+ "?token="
				+ Digests
						.buildToken(url + "?" + params, null, info.getSecret());
		try {
			String data = HttpClientSSLUtils.doPost(reqJSon, params);
			
			int pageNum = 1;
			ResultInfo result = JSON.parseObject(data, ResultInfo.class);
			if (null != result && "0".equals(result.getErrorCode()) && null != result.getData()
					&& null != result.getData().getList() && result.getData().getList().size() > 0) {
				
				temp = result.getData().getList();
				for (Map<String, Object> user:temp) {
					if (info.getUserName().equals(user.get("loginAccount") + "")) {
						return user.get("userUuid") + "";
					}
				}
				
				pageNum = result.getData().getTotal() / PAGE_SIZE;
				if (0 < result.getData().getTotal() % PAGE_SIZE) {
					pageNum++;
				}
				pageNum++;
				for (int i = 2; i < pageNum; i++) {
					map.put("pageNo", i);// 设置分页参数
					
					params = JSON.toJSONString(map);
					reqJSon = url
							+ "?token="
							+ Digests
									.buildToken(url + "?" + params, null, info.getSecret());
					data = HttpClientSSLUtils.doPost(reqJSon, params);
					result = JSON.parseObject(data, ResultInfo.class);
					
					if (null != result && "0".equals(result.getErrorCode()) && null != result.getData()
							&& null != result.getData().getList() && result.getData().getList().size() > 0) {
						
						temp = result.getData().getList();
						for (Map<String, Object> user:temp) {
							if (info.getUserName().equals(user.get("loginAccount") + "")) {
								return user.get("userUuid") + "";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("海康接口异常,获取配置用户信息失败", e);
		}

		return "";
	}
	
	/**
	 * 获取系统配置网络站点
	 * 
	 * @param hostUrl
	 * @param appKey
	 * @return
	 */
	public static String getNetZoneUUid(HikLogin info) {
		
		String defaultUser = getDefaultUserUuid(info);
		if (StringUtils.isEmpty(defaultUser)) {
			return "";
		}
		

		List<Map<String, Object>> temp;
		String url = info.getUrl() + GET_ALL_NET;

		// 组装请求对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", info.getAppKey());
		map.put("time", System.currentTimeMillis());
		map.put("opUserUuid", defaultUser);

		String params = JSON.toJSONString(map);

		String reqJSon = url
				+ "?token="
				+ Digests
						.buildToken(url + "?" + params, null, info.getSecret());
		try {
			String data = HttpClientSSLUtils.doPost(reqJSon, params);
			
			NetZone result = JSON.parseObject(data, NetZone.class);
			if (null != result && "0".equals(result.getErrorCode()) && null != result.getData()
					&& result.getData().size() > 0) {
				
				temp = result.getData();
				//{"errorCode":0,"errorMessage":null,"data":[{"netZoneUuid":"a883c143efe44f3dadd3a93e955b6f65","netZoneName":"10.10.10.2"},{"netZoneUuid":"45754013f5f04d4497d6fdf446678a1f","netZoneName":"外网"}]}
				/*********** 请小心修改 gongll  *********/
				for (Map<String, Object> user:temp) {
					//{netZoneName=10.10.10.2, netZoneUuid=a883c143efe44f3dadd3a93e955b6f65}
					if (info.getVideoIP().equals(user.get("netZoneName") + "")) {//外网 内网 都可以  
						return user.get("netZoneUuid") + "";
					}
				}
				
			}
		} catch (Exception e) {
			LOG.error("海康接口异常,获取网络站点失败", e);
		}

		return "";
	}
	
	/**
	 * 根据UUID查询门禁状态
	 * 
	 * @param hostUrl
	 * @param appKey
	 * @return
	 */
	public static String getDoorStatusByUuids(HikLogin info,String uuid) {

		String url = info.getUrl() + GWT_DOOT_STATUS;

		// 组装请求对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", info.getAppKey());
		map.put("time", System.currentTimeMillis());
		map.put("opUserUuid", info.getUserUuid());
		map.put("doorUuids", uuid);

		String params = JSON.toJSONString(map);

		String reqJSon = url
				+ "?token="
				+ Digests
						.buildToken(url + "?" + params, null, info.getSecret());
		try {
			String data = HttpClientSSLUtils.doPost(reqJSon, params);
			//{"errorCode":0,"errorMessage":"","data":[{"doorUuid":"a86817a9db2a470ebae3c8f9f645e60b","doorName":"徐州测试门禁_门1","doorStatus":2,
			//"deviceUuid":"d06a6320926e43b695b454ddf2f4559f","deviceName":"徐州测试门禁","deviceType":201927424,"doorNo":1}]}
			//{"errorCode":0,"errorMessage":"","data":[]}
			Map<String, Object> result = JSON.parseObject(data);
			if ("0".equals(result.get("errorCode") + "")) {
				data = result.get("data").toString();
				data = data.replace("[", "").replace("]", "");
				if(!org.apache.commons.lang.StringUtils.isEmpty(data)){
					result = JSON.parseObject(data);
				}
				data = result.get("doorStatus") + "";
				
				if ("1".equals(data) || "3".equals(data)) {
					return "1";
				}
			}
		} catch (Exception e) {
			LOG.error("海康接口异常,获取默认失败", e);
		}

		return "0";
	}
	
	/**
	 * 根据UUID远程下控
	 * 
	 * @param hostUrl
	 * @param appKey
	 * @return
	 */
	public static String synControl(HikLogin info,String uuid,String command) {

		String url = info.getUrl() + SYNC_CONTROL;

		// 组装请求对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appkey", info.getAppKey());
		map.put("time", System.currentTimeMillis());
		map.put("opUserUuid", info.getUserUuid());
		map.put("doorUuid", uuid);
		map.put("command", Integer.parseInt(command));

		String params = JSON.toJSONString(map);

		String reqJSon = url
				+ "?token="
				+ Digests
						.buildToken(url + "?" + params, null, info.getSecret());
		try {
			String data = HttpClientSSLUtils.doPost(reqJSon, params);
			Map<String, Object> result = JSON.parseObject(data);
			data = result.get("errorCode").toString();
			return data;
		} catch (Exception e) {
			LOG.error("海康接口异常,获取默认失败", e);
		}

		return "";
	}
}