package com.wapwag.woss.modules.home.service;

import com.google.common.collect.Maps;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.home.dao.RegionalDao;
import com.wapwag.woss.modules.home.dao.UserDao;
import com.wapwag.woss.modules.home.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User service
 * Created by Administrator on 2016/9/20 0020.
 */
@Service
public class UserService {

    private final UserDao userDao;

    private final RegionalDao regionalDao;

    @Autowired
    public UserService(UserDao userDao, RegionalDao regionalDao) {
        this.userDao = userDao;
        this.regionalDao = regionalDao;
    }

    public boolean getUserAvaliableDeviceCount(User user) {
        Integer result = regionalDao.findAllDeviceCount(user.getUserId());
        return result != null && result > 0;
        //return true;
    }

    public User login(String loginName, String password) {
        Map<String, Object> resultMap = Maps.newHashMap();
        User user = userDao.login(loginName);
        if (user != null && StringUtils.isNotBlank(user.getLoginName())) {
            return user;
        }
        return null;
    }

    /**
     * 获取告警人列表
     * @param list
     * @return
     */
    public List<User> getAlarmSendUserList(List<String> list){
        return userDao.getAlarmSendUserList(list);
    }

    public void freezeOperation(User user)
    {
        userDao.freezeOperation(user);
    }
}
