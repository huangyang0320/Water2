package com.wapwag.woss.modules.sys.entity;

import java.util.List;

/**
 * 用户选择树返回实体
 */
public class UserTreeSelection  {

	private static final long serialVersionUID = 1L;
	private String treeTypes;//用户拥有树
	private String treeSelection;//用户默认树
	private String isControl;//是否有下控权限
	private List<String> treeTypesList;
	private List<DictVo> dicts;

	public String getTreeTypes() {
		return treeTypes;
	}

	public void setTreeTypes(String treeTypes) {
		this.treeTypes = treeTypes;
	}

	public List<DictVo> getDicts() {
		return dicts;
	}

	public void setDicts(List<DictVo> dicts) {
		this.dicts = dicts;
	}

	public String getTreeSelection() {
		return treeSelection;
	}

	public void setTreeSelection(String treeSelection) {
		this.treeSelection = treeSelection;
	}

	public List<String> getTreeTypesList() {
		return treeTypesList;
	}

	public void setTreeTypesList(List<String> treeTypesList) {
		this.treeTypesList = treeTypesList;
	}

	public String getIsControl() {
		return isControl;
	}

	public void setIsControl(String isControl) {
		this.isControl = isControl;
	}
	
}