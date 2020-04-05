package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;

import com.wapwag.woss.common.persistence.DataEntity;
import com.wapwag.woss.modules.sys.entity.Role;

/**
 * 授权信息Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class PermissionInfo extends DataEntity<PermissionInfo> {
	
	private static final long serialVersionUID = 1L;
	private Role role;		// role_id
	private String authType;		// auth_type
	private String authobjectId;		// authobject_id
	
	public PermissionInfo() {
		super();
	}

	public PermissionInfo(String id){
		super(id);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@Length(min=0, max=50, message="auth_type长度必须介于 0 和 50 之间")
	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	@Length(min=0, max=50, message="authobject_id长度必须介于 0 和 50 之间")
	public String getAuthobjectId() {
		return authobjectId;
	}

	public void setAuthobjectId(String authobjectId) {
		this.authobjectId = authobjectId;
	}
	
}