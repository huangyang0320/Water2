package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.PermissionInfo;
import com.wapwag.woss.modules.biz.dao.PermissionInfoDao;

/**
 * 授权信息Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class PermissionInfoService extends CrudService<PermissionInfoDao, PermissionInfo> {

	public PermissionInfo get(String id) {
		return super.get(id);
	}
	
	public List<PermissionInfo> findList(PermissionInfo permissionInfo) {
		return super.findList(permissionInfo);
	}
	
	public Page<PermissionInfo> findPage(Page<PermissionInfo> page, PermissionInfo permissionInfo) {
		return super.findPage(page, permissionInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(PermissionInfo permissionInfo) {
		super.save(permissionInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PermissionInfo permissionInfo) {
		super.delete(permissionInfo);
	}
	
}