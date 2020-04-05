package com.wapwag.woss.common.task;

import com.alibaba.fastjson.JSONObject;
import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.modules.sys.entity.UserDto;
import com.wapwag.woss.modules.sys.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SyncHrTask {

    private static Logger LOG = LoggerFactory.getLogger(SyncHrTask.class);
    private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);
    public static void syncHrEmployeeData() {
        try {
            LOG.info("SyncHrTask ....");
            //同步前获取 已经授权的用户信息
            List<UserDto> userDto = systemService.getAllUserData();
            //开始清理  并且同步
            JSONObject obj = systemService.syncHrEmployeeData();
            // 权限还原
            int i =systemService.updateUserData(userDto);
            obj.put("RestoreAuthority","还原之前设置的权限："+i+" 个用户");
            LOG.info("end SyncHrTask ....");
            LOG.info(obj.toJSONString());

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
