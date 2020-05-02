package com.wapwag.woss.modules.sys.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色DAO接口
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);

	List<String> checkWarnPower(String userId);
	
	//public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	/*public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);*/
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	/*public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);*/

	/**
	 * 获取角色下的所有人
	 * @param roleId
	 * @return
	 */
	public List<String> getUserIdByRoleId(@Param(value = "roleId") String roleId);

	/**
	 *获取所有角色
	 * @return
	 */
	public List<Role> getAllRole();

}
