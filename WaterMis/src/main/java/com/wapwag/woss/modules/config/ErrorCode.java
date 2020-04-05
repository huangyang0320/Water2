package com.wapwag.woss.modules.config;

/**
 * 错误代码枚举
 * 
 * @author ChangWei Li
 * @version 2017-10-25 10:37
 */
public enum ErrorCode {

    /**
     * JSON格式错误
     */
    JSON_ILEEGAL_FORMAT("json_illegal_format", "JSON格式错误，请检查请求参数的格式是否符合标准的JSOn格式"),

	/**
	 * token不存在
	 */
	JWT_NOT_EXIST("jwt_not_exist", "token不存在"),
	/**
	 * 不是合法的jwt token
	 */
	JWT_ILLEGAL_TOKEN("jwt_illegal_token", "非法的token"),
	/**
	 * Jwt token超时
	 */
	JWT_TOKEN_EXPIRED("jwt_token_expired", "token超时"),
	
	/**
	 * 切换上一级组织失败，上一级组织不完全包含当前组织的所有区域
	 */
	SYS_MOD_PARENT_ID_ERROR("sys_mod_parent_id_error", "切换上一级组织失败，上一级组织不完全包含当前组织的所有区域"),

	/**
	 * 删除区域分组失败，子组织含有被删除的区域，请先操作自组织！
	 */
	SYS_DEL_REGION_ERROR("sys_del_region_error", "删除区域分组失败，子组织含有被删除的区域，请先操作自组织！"),

	/**
	 * 修改区域分组失败，子组织含有被删除的区域，请先操作自组织！
	 */
	SYS_MOD_REGION_ERROR("sys_mod_region_error", "修改区域分组失败，子组织含有被删除的区域，请先操作自组织！"),

	/**
	 * 非法参数
	 */
	EXCEPTION_ILLEGAL_ARGUMENT("illegal_argument", "非法参数"),

	/**
	 * 非法状态
	 */
	EXCEPTION_ILLEGAL_STATE("illegal_state", "非法状态"),

	/**
	 * 用户不存在
	 */
	USER_NOT_EXIST("user_not_exist", "用户不存在"),

	/**
     * 用户被禁止登陆
     */
    USER_NOT_ENABLE("user_not_enable", "用户被禁止登陆，请联系管理员"),

    /**
     * 用户没有分配角色
     */
    USER_NOT_HAVE_ROLE("user_not_have_role", "用户没有分配角色，请联系管理员"),

	/**
	 * 用户认证失败
	 */
	USER_AUTH_FAILED("user_auth_failed", "用户名或密码错误"),

	/**
	 * 更新操作失败
	 */
	CRUD_UPDATE_NO_RECORD("update_no_record", "更新操作失败"),

	CTRL_ERROR("ctrl_error", "下控失败"),

	CTRL_ERROR_NULL("ctrl_error", "下控失败,无该测点"),

	CTRL_FORBIDDEN("ctrl_forbidden", "没有下控权限"),

	CTRL_NULL_DOOR("ctrl_null_door", "设备没有配置门禁"),

	/**
	 * 确认密码不一致
	 */
	USER_CONFIRM_PASSWORD_FAIL("user_confirm_password_fail", "两次输入密码不一致"),

	/**
	 * 用户名已存在
	 */
	USER_DUPLICATE_LOGIN_NAME("user_duplicate_login_name", "用户名已存在"),

	/**
	 * 组织不存在
	 */
	ORG_NOT_EXIST("org_not_exist", "组织不存在"),

    /**
     * 当前组织不能创建下级组织
     */
    ORG_CAN_NOT_CREATE_CHILD("org_can_not_create_child", "当前组织不能创建下级组织"),

    /**
     * 当前组织不能创建用户
     */
    ORG_CAN_NOT_CREATE_USER("org_can_not_create_user", "当前组织不能创建用户"),

    /**
     * 当前组织不能被删除，存在下级组织、用户或者项目
     */
    ORG_CAN_NOT_BE_DELETED("org_can_not_be_deleted", "当前组织不能被删除，存在下级组织、用户或者项目"),

    /**
     * 当前组织的所能创建的子组织级别超过最大值
     */
    ORG_CHILD_MAX_LEVEL_EXCEED("org_child_max_level_exceed", "创建的子组织级别超过最大值"),

    /**
     * 当前组织的所能创建的子组织数目超过最大值
     */
    ORG_CHILD_MAX_CHILD_EXCEED("org_child_max_child_exceed", "创建的子组织数目超过最大值"),

	/**
	 * 部门不存在
	 */
	DEPT_NOT_EXIST("dept_not_exist", "部门不存在"),

    /**
     * 部门不能移动到下级部门
     */
    DEPT_CAN_NOT_MOVE_CHILD("dept_can_not_move_child", "部门不能移动到下级部门"),

    /**
     * 部门不能移动到其它组织
     */
    DEPT_CAN_NOT_MOVE_OUTTER_ORG("dept_can_not_move_outter_org", "部门不能移动到其它组织"),

	/**
	 * 员工不存在
	 */
	EMP_NOT_EXIST("emp_not_exist", "员工不存在"),

	/**
	 * 组织管理员已经存在
	 */
	ORG_ADMIN_USER_EXIST("org_admin_user_exist", "组织管理员已经存在"),

	/**
	 * 用户权限不足
	 */
	USER_LACK_OF_RIGHT("user_lack_of_right", "用户权限不足"),

	/**
	 * 删除操作失败
	 */
	CRUD_DELETE_NO_RECORD("delete_no_record", "删除操作失败"),

	/**
	 * 导入失败，设备ID不存在
	 */
	IMP_DEVICE_ID_NULL("imp_device_id_null", "导入失败，设备ID不存在"),

	/**
	 * 区域编号已经存在
	 */
	SYS_REGION_ID_EXIT("sys_region_id_exit", "区域编号已经存在"),

	/**
	 * 删除失败，该项目含有设备位置
	 */
	ORG_PROJECT_DEL_ERROR("org_project_del_error", "删除失败，该项目含有设备位置"),

	/**
	 * 删除失败，该设备位置含有设备
	 */
	DEVICE_LOCATION_DEL_ERROR("device_location_del_error", "删除失败，该设备位置含有设备"),

	/**
	 * 导入失败，设备不存在
	 */
	IMP_DEVICE_ID_NOT_EXIT("imp_device_id_not_exit", "导入失败，设备不存在"),

	/**
	 * 导入失败,点表信息匹配失败
	 */
	IMP_DEVICE_PARAM_iILLEGAL("imp_device_param_illegal", "导入失败,点表信息匹配失败"),

	/**
	 * 导入失败,点表列表为空
	 */
	IMP_DEVICE_PARAM_NULL("imp_device_param_null", "导入失败,点表列表为空"),
	
	/**
	 * 设备测点已经存在
	 */
	DEVICE_SERVICE_POINT_EXYT("device_service_point_exit", "设备测点已经存在"),

	/**
	 * 请求数据不存在
	 */
	SYS_NOT_EXIST("sys_not_exist", "请求数据不存在"),

	/**
	 * 没有操作权限
	 */
	SYS_NOT_ROLE("sys_not_role", "没有操作权限"),

	CTRL_INVALID_VALUE("ctrl_invalid_value", "数据超出设定范围"),

	CTRL_INVALID_NULL("ctrl_invalid_null", "未配置下控范围"),

	/**
	 * 删除区域失败,该区域含有附属区域
	 */
	DELETE_HAS_CHILD("delete_has_child", "删除区域失败,该区域含有附属区域"),
	
	OLD_PWD_ERROR("old_pwd_error","修改失败，原密码输入错误"),
	
	
	DATA_ALREADY_EXISTS("data_already_exists","数据已存在"),
	
	POINT_VALUE_EMPTY("point_value_empty", "下控参数值为空"),
	
    CTRL_CHANEL_EMPTY("ctrl_chanel_empty", "下控渠道未确定"),

	;

	private String code;

	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
