package com.wapwag.woss.modules.home.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.home.entity.TreeTypeDto;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.home.service.TreeTypeService;
import com.wapwag.woss.modules.sys.entity.Dict;
//import com.wapwag.woss.modules.sys.entity.User;
import com.wapwag.woss.modules.sys.utils.DictUtils;

/**
 * TreeTypeController 前台选择哪种树的类型
 * 
 * @author zhaom
 *
 */
@RestController
@RequestMapping("${adminPath}/treeType")
@SessionAttributes("user")
public class TreeTypeController {

	@Autowired
	private TreeTypeService treeTypeService;

	/**
	 * 获取树的种类 给前台展示
	 * 
	 * @return
	 */
	@RequestMapping("/queryTreeTypes")
	public List<Dict> queryTreeTypes() {
		return DictUtils.getDictList("treeTypes");
	}

	// @RequiresPermissions("user")
	@RequestMapping("/getTreeDataByType")
	@ResponseBody
	public List<TreeInfo> getTreeDataByType(
			@RequestBody TreeTypeDto treeTypeDto, User user) {
		treeTypeDto.setUserId(user.getUserId());
		if (StringUtils.isEmpty(treeTypeDto.getTreeTypeValue())) {
			treeTypeDto.setTreeTypeValue("1");// 默认第一种树
		}
		// 树的value类型
		if (treeTypeDto.getIsSpread() == null) {
			treeTypeDto.setIsSpread(true);// 默认展开
			treeTypeDto.setIsDeviceOrVideo("1");// 默认展开到设备1
		}
		// 水务所还是营业所
//		if (treeTypeDto.getTreeTypeValue().equals("3")) {
//			//treeTypeDto.setWaterCompanyType("2");// 只查水务所节点的项目
//		}
//		if (treeTypeDto.getTreeTypeValue().equals("4")) {
//			//treeTypeDto.setWaterCompanyType("3");// 只查营业所节点的项目
//		}
		//处理下 泵房在线  设备 在线
		List<TreeInfo> treeDataByType = treeTypeService.getTreeDataByType(treeTypeDto);
//		for (TreeInfo treeInfo : treeDataByType) {
//			if(!StringUtils.isEmp(treeInfo.getPumpHouseId())){
//				treeInfo.setPumpStatus(treeTypeService.selectByPumpId(treeInfo.getPumpHouseId()));
//			}
//			if(!StringUtils.isEmp(treeInfo.getDeviceId())){
//				treeInfo.setDevStatus(treeTypeService.selectByDevId(treeInfo.getDeviceId()));
//			}
//		}

/*		TreeInfo t=new TreeInfo();
		t.setId("111");
		t.setName("11111");
		t.setpId("12312");
		t.setLongi("11");
		t.setLati("12");
		treeDataByType.add(t);*/
		return treeDataByType;
	}

}
