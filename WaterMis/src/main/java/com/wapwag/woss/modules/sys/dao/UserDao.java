package com.wapwag.woss.modules.sys.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.home.entity.Employee;
import com.wapwag.woss.modules.sys.entity.DictVo;
import com.wapwag.woss.modules.sys.entity.User;
import com.wapwag.woss.modules.sys.entity.UserDto;
import com.wapwag.woss.modules.sys.entity.UserTreeSelection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户DAO接口
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param Name
	 * @return
	 */
	public User getByName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新告警设置
	 * @param user
	 * @return
	 */
	public int updateAlarmRateById                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   (User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	/**
	 * 查看客户所拥有的树
	 * @param userId
	 */
	public UserTreeSelection findUserTreeHave(@Param("userId") String userId,@Param("delFlag")String delFlag);

	/**
	 * 查看客户所拥有的树
	 * @param treeTypes
	 */
	List<DictVo> listUserHaveTree(List<String> treeTypes);

	/**
	 * 更新客户选择树
	 * @param id
	 */
	int updateUserDefaultTree(@Param("id")String id,@Param("treeSelection")String treeSelection);

	/**
	 * 批量插入数据
	 *
	 * @param list
	 * @return
	 */
	int batchInsert(List<Employee> list);

	/**
	 * 批量同步HR人事数据
	 *
	 * @param list
	 * @return
	 */
	int batchEmployeeInsert(List<Employee> list);

	/**
	 * 同步前清理user数据
	 * @return
	 */
	int deleteUserData();
	/**
	 * 同步前清理employee数据
	 * @return
	 */
	int deleteEmployeeData();

	/**
	 * 同步  清理前  获取已经被授权的用
	 * @return
	 */
	List<UserDto> getAllUserData();


	/**
	 * 同步清理后  把权限还原
	 * @return
	 */
	int updateUserData(UserDto user);


}
