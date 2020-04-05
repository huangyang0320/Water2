package com.wapwag.woss.modules.home.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.home.entity.DeviceInfo;
import com.wapwag.woss.modules.home.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User dao
 * Created by Administrator on 2016/9/20 0020.
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.UserDao")
public interface UserDao extends CrudDao<DeviceInfo> {

    public User login(@Param("loginName") String loginName);

    /**
     * 获取告警人列表
     * @param list
     * @return
     */
    List<User> getAlarmSendUserList(@Param(value = "list") List<String> list);

}
