package com.wapwag.woss.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.sys.entity.BootPage;
import com.wapwag.woss.modules.sys.entity.Coordinate;
import com.wapwag.woss.modules.sys.service.CoordinateService;
import com.wapwag.woss.modules.sys.service.CountService;
import com.wapwag.woss.modules.sys.service.SystemService;

/**
 * 系统管理
 * 
 * @author gongll
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/system")
public class SystemController {
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private CoordinateService coordinateService; 
	
	@Autowired
	private CountService countService; 

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "modPassword")
	public User modPassword(User newUser,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null == user || null == user.getPassword()) {
			user = new User();
			return user;
		}
		if (StringUtils.isNotBlank(newUser.getPassword())
				&& StringUtils.isNotBlank(newUser.getNewPassword())) {
			if (SystemService.validatePassword(newUser.getPassword(),
					user.getPassword())) {
				systemService.updatePasswordById(user.getId(), user.getName(),
						newUser.getNewPassword());
				user.setRemarks("0");
			} else {
				user.setRemarks("1");
			}
		} else {
			// 参数格式错误
			user.setRemarks("2");
		}
		return user;
	}

	/**
	 * 修改用户告警提示时间间隔
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "alarmConfig")
	public User alarmConfig(User user,HttpSession session) {
		User oldUser = (User) session.getAttribute("user");
		if (null == oldUser || StringUtils.isEmp(oldUser.getUserId())) {
			return user;
		}
		
		if (systemService.updateAlarmRateById(oldUser.getUserId(),user.getAlarmRate()) > 0) {
			user.setRemarks("0");
			oldUser.setAlarmRate(user.getAlarmRate());
			session.setAttribute("user", oldUser);
		}
		return user;

	}

	/**
	 * 查询用户告警设置
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "qryAlarmConfig")
	public User qryalarmConfig(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null == user || StringUtils.isEmp(user.getUserId())) {
			return new User();
		}
		return user;
	}

	/**
	 * 获取所有用户
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllUser")
	public BootPage getAllUser(QryObject qryObject) {
		qryObject.setPageNumber((qryObject.getPageNumber()-1)*qryObject.getPageSize());
		
		BootPage bootPage = new BootPage();
        bootPage.setTotal(coordinateService.coordinateDao());
        bootPage.setRows(coordinateService.getUsers(qryObject));
		return bootPage;

	}
	
	/**
	 * 获取用户的实时轨迹
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getLastCoordinateByUserId/{userId}")
	public List<Coordinate> getLastCoordinateByUserId(@PathVariable String userId){
		return coordinateService.getLastCoordinateByUserId(userId);
	}
	
	/**
	 * 获取用户的历史轨迹
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCoordinateByUserId/{userId}/{time}")
	public List<Coordinate> getCoordinateByUserId(@PathVariable String userId,@PathVariable String time){
		QryObject qryObject = new QryObject();
		qryObject.setBeginTime(time.split(",")[0]);
		qryObject.setEndTime(time.split(",")[1]);
		qryObject.setUserId(userId);
		return coordinateService.getCoordinateByUserId(qryObject);
	}
}
