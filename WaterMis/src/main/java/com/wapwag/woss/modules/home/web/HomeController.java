package com.wapwag.woss.modules.home.web;

import com.wapwag.woss.modules.home.entity.AlarmInfo;
import com.wapwag.woss.modules.home.entity.DeviceMeta;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.home.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * PumpController
 * Created by Administrator on 2016/8/2.
 */
@RestController
@RequestMapping("${adminPath}/home")
@SessionAttributes("user")
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @RequestMapping("/device/{deviceId}/metadata")
    public List<DeviceMeta> queryDeviceMeta(@PathVariable String deviceId) {
        return homeService.queryDeviceMeta(deviceId);
    }

    @RequestMapping("/pumphouseId/metadata/consume")
    public List<Map<String, String>> queryDeviceConsumeMeta(@RequestParam String pumphouseIds) {
        return homeService.queryDeviceConsumeMeta(pumphouseIds);
    }

    /**
     * query all the pumps in the current area.
     * @param userId all the projects the specified user have the permission to see
     * @return the longitude and latitude of all the pumps in the current area
     */
    @RequestMapping("/devices/{userId}")
    public Map<String, Object> queryAllDevice(User user, @PathVariable String userId) {
        userId = user.getUserId();
        return homeService.queryAllDevice(userId);
    }

    /**
     * query the details of the specified device
     * @param deviceId the device id which you want to query
     * @return return the details of the specified device
     */
    @RequestMapping("/device/{deviceId}")
    public Map<String, Object> queryDevice(@PathVariable String deviceId) {
        return homeService.queryDevice(deviceId);
    }

    /**
     * query the lastest alarms of the device
     * @return return the lastest alarms of all the device which the user has the priviliges
     */
    @RequestMapping("/alarms/latest")
    public List<AlarmInfo> queryLatestAlarms(User user) {
        return homeService.queryLatestAlarms(user.getUserId());
    }

    /**
     * query the lastest alarms of the device
     * @return return the lastest alarms of all the device which the user has the priviliges
     */
    @RequestMapping("/alarms/latest/count")
    public long queryLatestAlarmsCount(User user) {
        return homeService.queryLatestAlarmsCount(user.getUserId());
    }

}
