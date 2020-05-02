package com.wapwag.woss.modules.sys.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.security.Digests;
import com.wapwag.woss.common.security.shiro.session.SessionDAO;
import com.wapwag.woss.common.service.BaseService;
import com.wapwag.woss.common.service.ServiceException;
import com.wapwag.woss.common.utils.CacheUtils;
import com.wapwag.woss.common.utils.Encodes;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.common.web.Servlets;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.home.entity.Employee;
import com.wapwag.woss.modules.home.entity.TreeTypeDto;
import com.wapwag.woss.modules.home.service.TreeTypeService;
import com.wapwag.woss.modules.sys.dao.MenuDao;
import com.wapwag.woss.modules.sys.dao.RoleDao;
import com.wapwag.woss.modules.sys.dao.UserDao;
import com.wapwag.woss.modules.sys.entity.*;
import com.wapwag.woss.modules.sys.security.SystemAuthorizingRealm;
import com.wapwag.woss.modules.sys.utils.LogUtils;
import com.wapwag.woss.modules.sys.utils.MapToEntity;
import com.wapwag.woss.modules.sys.utils.UserUtils;

import com.water.ds.db.DbSqlServerHr;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	@Autowired
	TreeTypeService treeTypeService;

	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	//-- User Service --//
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUserById(String id) {
		return userDao.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * @param name
	 * @return
	 */
	public User getUserByName(String name) {
		return UserUtils.getByName(name);
	}
	
	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param officeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null){
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}

	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userDao.insert(user);
		}else{
			// 清除原用户机构用户缓存
			User oldUser = userDao.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
			}
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userDao.insertUserRole(user);
			}else{
				throw new ServiceException(user.getName() + "没有设置角色！");
			}
			// 清除用户缓存
			UserUtils.clearCache(user);
		}
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.delete(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String name, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setName(name);
		UserUtils.clearCache(user);
	}
	
	@Transactional(readOnly = false)
	public int updatePassword(User user) {
		return userDao.updatePasswordById(user);
	}
	
	@Transactional(readOnly = false)
	public int updateAlarmRateById(String userId,String alarm) {
		User user = new User();
		user.setId(userId);
		user.setAlarmRate(alarm);
		int result = userDao.updateAlarmRateById(user);
		UserUtils.modCacheUser(user.getId());
		return result;
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
		//return "123".equals("123");
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	public Role getRoleByEnname(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}
	
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}
	
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}
	/*	// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0){
			roleDao.insertRoleOffice(role);
		}*/
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
	}
	
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//
	
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {
		
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds(); 
		
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuDao.insert(menu);
		}else{
			menu.preUpdate();
			menuDao.update(menu);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("\r\n======================================================================\r\n");
//		sb.append("\r\n    欢迎使用 " + Global.getConfig("productName") + "  - Powered By http://www.tuisitang.com\r\n");
//		sb.append("\r\n======================================================================\r\n");
//		System.out.println(sb.toString());
		return true;
	}
	
	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	public void afterPropertiesSet() throws Exception {
		
	}
	/**
	 * 获取用户选择树和拥有树
	 * @param userId
	 * @return
	 */
	public String findUserTreeSelection(String userId,String delFlag){
	    if(userId==null||"null".equals(userId)||"".equals(userId))return "";
		UserTreeSelection userTree = userDao.findUserTreeHave(userId,delFlag);
        List<String> result=null;
		if(userTree!=null){
			result = Arrays.asList(userTree.getTreeTypes().split(","));
			userTree.setTreeTypesList(result);
		}
        List<DictVo> dicts=userDao.listUserHaveTree(result);
        userTree.setDicts(dicts);
        String date=new Gson().toJson(userTree);
		return date;
	}
	/**
	 * 修改用户默认树选项
	 * @param id
	 * @return data
	 */
    @Transactional(readOnly = false)
	public String updateUserDefaultTree(String id,String treeSelection){
    	if (id==null||treeSelection==null||"".equals(id)||"".equals(treeSelection))return "";
    	TreeTypeDto treeTypeDto = new TreeTypeDto();
        treeTypeDto.setTreeTypeValue(treeSelection);
		treeTypeDto.setIsSpread(false);// 默认到泵房
		treeTypeDto.setUserId(id);
 		List<TreeInfo> treeDataByType = treeTypeService.getTreeDataByType(treeTypeDto );
 		Map<String,String> map=new HashMap<String,String>();
 		if(treeDataByType==null || treeDataByType.size()==0){
 			map.put("msg","该类型下暂无数据，无法切换");
			map.put("status","-2");
			String data = new Gson().toJson(map);
			return data;
 		}
		int i= userDao.updateUserDefaultTree(id,treeSelection);
		if(i>0) {
			map.put("msg","修改成功");
			map.put("status","0");
		}else{
			map.put("msg","修改失败");
			map.put("status","-1");
		}
		String data = new Gson().toJson(map);
		return data;
	}

	public List<UserDto> getAllUserData(){
		return userDao.getAllUserData();
	}

	@Transactional(readOnly = false)
	public int updateUserData(List<UserDto> userDtoList){
    	int i =0;
    	if(userDtoList!=null && userDtoList.size()>0){
    		for(UserDto user: userDtoList){
				i+=userDao.updateUserData(user);
			}
		}
    	return i;
	}

	/**
	 *
	 * @return
	 */
	@Transactional(readOnly = false)
	public JSONObject syncHrEmployeeData() {
		long startTime=System.currentTimeMillis();
		JSONObject reObj=new JSONObject();
		try{
			List<Map<String,Object>> list = DbSqlServerHr.syncHrData();
			if(list!=null && list.size()>0){

				//获取user表的所有数据，与人事系统匹配的update，不匹配的insert

				List<Employee> listEmp=new ArrayList<>();
				Employee emp=null;
				int nullNum=0;
				for(Map<String,Object> map:list){

					//过滤账号为空的数据
					if( map.get("account")==null || "".equals(map.get("account").toString())){
						nullNum++;
						continue;
					}
					emp =new Employee();
					listEmp.add(MapToEntity.mapToObjModle(emp,map));
					emp.setId(UUID.randomUUID().toString());
					emp.setCreateDate(new Date());
					emp.setUpdateDate(new Date());
				}
				int delU = userDao.deleteUserData();
				int delE = userDao.deleteEmployeeData();
				int u = userDao.batchInsert(listEmp);
				int e = userDao.batchEmployeeInsert(listEmp);
				long endTime=System.currentTimeMillis();
				if(u>0 && e>0){

					reObj.put("status","success");
					reObj.put("deleteUserNum","清理user数据："+delU+"条");
					reObj.put("deleteEmployeeNum","清理employee数据："+delE+"条");
					reObj.put("total","人事系统总数据："+list.size()+"条");
					reObj.put("successNum","同步成功："+(list.size()-nullNum)+"条");
					reObj.put("accountNull","域账号为空的数据："+nullNum+"条");
					reObj.put("execTime","耗时："+(endTime-startTime)/1000+"秒");
					logger.error(reObj.toJSONString());
					return reObj;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		reObj.put("status","error");
		reObj.put("msg","人事系统同步失败：0 条数据...");
		return reObj;

	}

	/**
	 * 核查用户是否存在告警忽略权限
	 * @param userId
	 * @return
	 */
	public JSONObject checkWarnPower(String userId)
	{
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("status",true);
         List<String> list = roleDao.checkWarnPower(userId);
         if(CollectionUtils.isNotEmpty(list))
		 {
			 for (String menuId:list)
			 {
				 if("044e8fc873ad4580b8a96073a237d45a".equals(menuId))
				 {
					 result.put("code",1);
					 return result;
				 }
			 }
		 }
		 return result;
	}


	/**
	 * 获取角色下的所有人
	 * @param roleId
	 * @return
	 */
	public List<String> getUserIdByRoleId(String roleId){
		return roleDao.getUserIdByRoleId(roleId);
	}

	/**
	 *获取所有角色
	 * @return
	 */
	public List<Role> getAllRole(){
		return roleDao.getAllRole();
	}


	public static void main(String[] args) {
		List<Map<String,Object>> list = DbSqlServerHr.syncHrData();
		if(list!=null && list.size()>0){
			List<Employee> listEmp=new ArrayList<>();
			for(Map<String,Object> map:list){
				listEmp.add(MapToEntity.mapToObjModle(new Employee(),map));
			}
			System.out.println();
		}
	}


}
