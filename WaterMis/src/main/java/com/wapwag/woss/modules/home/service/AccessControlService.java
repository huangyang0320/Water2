package com.wapwag.woss.modules.home.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.wapwag.woss.modules.home.dao.VideoDao;
import com.wapwag.woss.modules.home.util.hik.AccessData;
import com.wapwag.woss.modules.home.util.hik.HikLogin;
import com.wapwag.woss.modules.home.util.hik.OpenApiHttp;

/**
 * 门禁控制
 *
 * @author gongll
 *
 */
@Service
public class AccessControlService {

	// 海康登录对象
	private static HikLogin HIK_LOGIN = null;
	private static final Long INIT_MAX_TIME = 600000L;

	private final VideoDao videoMapper;

	@Autowired
	public AccessControlService(VideoDao videoMapper) {
		this.videoMapper = videoMapper;
	}


	/**
	 * 根据泵房id查询门径数据
	 * @param pumpId
	 * @return
	 */
	public List<AccessData> getAccessDataByPumpId(String pumpId){

		List<AccessData> list = videoMapper.getAccessDataByPumpId(pumpId);
		if (null == list || list.size() < 1) {
			return new ArrayList<AccessData>();
		}

		String status = "0";
		for (int i = 0; i < list.size(); i++) {
			status = getDoorStatusByUuid(list.get(i).getUuid());
			list.get(i).setStatus(status);
		}

		return list;
	}
	/**
	 * * 根据uuid远程开门【0：关门，1：开门】
	 *
	 * @param uuid
	 * @return 1:成功，0:失败
	 */
	public String ctrlHKAccess(String uuid, String command) {
		if (null == HIK_LOGIN || System.currentTimeMillis() - HIK_LOGIN.getInitTime() > INIT_MAX_TIME) {
			createLogin();
		}

		if (null == HIK_LOGIN || StringUtils.isEmpty(uuid)) {
			return "0";
		}

		if (!"0".equals(command) && !"1".equals(command)) {
			return "0";
		}

		String ctrlResule = OpenApiHttp.synControl(HIK_LOGIN, uuid, "1".equals(command) ? "0" : "3");
		if ("0".equals(ctrlResule)) {
			return "1";
		}
		return "0";
	}

	/**
	 * * 根据uuid查询门禁状态
	 *
	 * @param uuid
	 * @return 【1：开门状态，0：关门状态】
	 */
	public String getDoorStatusByUuid(String uuid) {

		if (null == HIK_LOGIN || System.currentTimeMillis() - HIK_LOGIN.getInitTime() > INIT_MAX_TIME) {
			createLogin();
		}

		if (null == HIK_LOGIN || StringUtils.isEmpty(uuid)) {
			return "0";
		}

		return OpenApiHttp.getDoorStatusByUuids(HIK_LOGIN, uuid);
	}

	/**
	 * 初始化海康登录数据
	 */
	private void createLogin() {

		List<Map<String, Object>> conf = videoMapper.hikConf();
		Map<String, String> temp = new HashMap<String, String>();

		for (int i = 0; i < conf.size(); i++) {

			temp.put(conf.get(i).get("label").toString(), conf.get(i).get("value").toString());
		}

		HikLogin info = new HikLogin();
		if (null != temp.get("ApiKey") && null != temp.get("AppSecret")) {
			info.setAppKey(temp.get("ApiKey"));
			info.setSecret(temp.get("AppSecret"));
		} else {
			return;
		}

		if (null != temp.get("ApiUrl") && null != temp.get("ApiPort")) {
			info.setUrl("http://" + temp.get("ApiUrl") + ":" + temp.get("ApiPort"));
		} else {
			return;
		}

		if (null != temp.get("AppUser")) {
			info.setUserName(temp.get("AppUser"));
		} else {
			return;
		}

		// 获取系统配置用户uuid
		String operUser = OpenApiHttp.getOperUser(info);
		if (!StringUtils.isEmpty(operUser)) {
			info.setUserUuid(operUser);
		} else {
			return;
		}

		info.setInitTime(System.currentTimeMillis());
		HIK_LOGIN = info;
	}
}
