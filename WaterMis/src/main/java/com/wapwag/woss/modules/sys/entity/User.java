package com.wapwag.woss.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.wapwag.woss.common.config.Global;
import com.wapwag.woss.common.persistence.DataEntity;
import com.wapwag.woss.common.utils.Collections3;
import com.wapwag.woss.common.utils.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 用户Entity
 */
@ApiModel(value = "user对象",description = "user用户对象")
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;
	private Office office; // 归属部门
	@ApiModelProperty(name = "密码",value = "password",example = "123456789**")
	private String password;// 密码
	private String no; // 工号
	private String name; // 姓名
	private String email; // 邮箱
	private String phone; // 电话
	private String mobile; // 手机
	private String userType;// 用户类型
	private String loginIp; // 最后登陆IP
	private Date loginDate; // 最后登陆日期
	private String loginFlag; // 是否允许登陆
	private String photo; // 头像
	private String description;// 描述
	private String sex;// 性别
	private String alarmRate;// 告警设置

	private String oldLoginName;// 原登录名
	private String newPassword; // 新密码

	private String oldLoginIp; // 上次登陆IP
	private Date oldLoginDate; // 上次登陆日期
	private Role role; // 根据角色查询用户条件
	private String treeSelection;//用户选择树
	
	   /**
     * 前台展示树的类型 1城市->行政区->泵房 
     *            2 城市->城区/农村->泵房 
     *            3水司->水务所->泵房
                  4水司->营业所->泵房
                  5水司->水司->行政区->泵房
     */
	private String treeTypes; //树的类型

	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表
	
	private String isControl; //0 没有控制权限  1有控制权限


	public User() {
		super();
		this.loginFlag = Global.YES;
	}
	public User(String id, String name){
		super(id);
		this.name = name;
	}

	public User(String id) {
		super(id);
	}

	public User(Role role) {
		super();
		this.role = role;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	@JsonIgnore
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}


	@JsonIgnore
	@Length(min = 1, max = 100, message = "密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	@Length(min = 1, max = 100, message = "工号长度必须介于 1 和 100 之间")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Email(message = "邮箱格式不正确")
	@Length(min = 0, max = 200, message = "邮箱长度必须介于 1 和 200 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min = 0, max = 200, message = "电话长度必须介于 1 和 200 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 200, message = "手机长度必须介于 1 和 200 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemarks() {
		return remarks;
	}

	@Length(min = 0, max = 100, message = "用户类型长度必须介于 1 和 100 之间")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldLoginIp() {
		if (oldLoginIp == null) {
			return loginIp;
		}
		return oldLoginIp;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOldLoginDate() {
		if (oldLoginDate == null) {
			return loginDate;
		}
		return oldLoginDate;
	}

	public void setOldLoginDate(Date oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAlarmRate() {
		return alarmRate;
	}

	public void setAlarmRate(String alarmRate) {
		this.alarmRate = alarmRate;
	}
	
	public String getTreeTypes() {
		return treeTypes;
	}

	public void setTreeTypes(String treeTypes) {
		this.treeTypes = treeTypes;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	
	@JsonIgnore
	public List<String> getTreeTypesList() {  
	    List<String> list = Lists.newArrayList();  
	    if (treeTypes != null){  
	        for (String s : StringUtils.split(treeTypes, ",")) {  
	            list.add(s);  
	        }  
	    }  
	    return list;  
	}  
	
	public void setTreeTypesList(List<String> treeTypesList) {  
		treeTypes = ","+StringUtils.join(treeTypesList, ",")+",";
	}  

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}

	public boolean isAdmin() {
		return isAdmin(this.id);
	}

	public static boolean isAdmin(String id) {
		return id != null && "1".equals(id);
	}

	public String getTreeSelection() {
		return treeSelection;
	}

	public void setTreeSelection(String treeSelection) {
		this.treeSelection = treeSelection;
	}

	@Override
	public String toString() {
		return id;
	}
	public String getIsControl() {
		return isControl;
	}
	public void setIsControl(String isControl) {
		this.isControl = isControl;
	}
	
	
}