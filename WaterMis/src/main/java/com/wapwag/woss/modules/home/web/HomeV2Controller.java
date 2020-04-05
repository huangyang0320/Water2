package com.wapwag.woss.modules.home.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wapwag.woss.modules.biz.entity.Device;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.home.service.HomeV2Service;
import com.wapwag.woss.modules.sys.entity.UserDto;
import com.wapwag.woss.modules.sys.service.CountService;
import com.wapwag.woss.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 2020-02-09
 * yanxiaoneng
 */
@RestController
@RequestMapping("${adminPath}/homeV2")
@SessionAttributes("user")
public class HomeV2Controller {
    @Autowired
    private HomeV2Service homeV2Service;

    @Autowired
    private CountService countService;
    @Autowired
    private SystemService systemService;



    @RequestMapping("/syncHrEmployeeData")
    @ResponseBody
    public Object syncHrEmployeeData() {
        //同步前获取 已经授权的用户信息
        List<UserDto> userDto = systemService.getAllUserData();
        //开始清理  并且同步
        JSONObject obj = systemService.syncHrEmployeeData();
        // 权限还原
        int i =systemService.updateUserData(userDto);
        obj.put("RestoreAuthority","还原之前设置的权限："+i+" 个用户");

        return obj;
    }
    /**
     * 同比 环比
     * @return
     */
    @RequestMapping("/getUseWaterDayData")
    @ResponseBody
    public Object getUseWaterDayData() {
        return homeV2Service.getUseWaterThAnalysis();
    }

    /**
     * 统计地区下面的设备数量（饼图）
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDeviceAreaData")
    public JSONArray countDeviceArea(User user){
        //饼图
      /* List<Device> list=countService.statisticDevivceArea(user.getUserId());
       JSONArray jsonArray=new JSONArray();
       if(list !=null && list.size()>0){
           for(Device d : list){
               JSONObject obj=new JSONObject();
               obj.put("name",d.getName());
               obj.put("value",d.getMemo());
               obj.put("itemStyle","");
               jsonArray.add(obj);
           }
       }*/
        return homeV2Service.getMapAreaPumpHouse();
    }

    /**
     * 智能运维
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOperationsData")
    public Map<String,List<Device>> getOperationsData(User user){
        Map<String,List<Device>> map=new HashMap<>();

        //告警
        List<Device> resultAlarm = countService.countAlarmTimesStat(user.getUserId(),"user");
        if (null == resultAlarm) {
            resultAlarm = new ArrayList<Device>();
        }

        //维保
        List<Device> resultMaintenance = countService.countMaintenanceTrend(user.getUserId());
        if (null == resultMaintenance) {
            resultMaintenance = new ArrayList<Device>();
        }

        //当月 每天 工单完成  todo....

        map.put("alarm",resultAlarm);
        map.put("maintenance",resultMaintenance);
        return map;


    }

    /**
     * 趋势曲线  日
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUseWaterHourAnalysis")
    public Map<String,List<String>> getUseWaterHourAnalysis(User user){
        return homeV2Service.getUseWaterHourAnalysis();
    }

    /**
     * 趋势曲线  月
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUseWaterDayAnalysis")
    public Map<String,List<String>> getUseWaterDayAnalysis(User user){
        return homeV2Service.getUseWaterDayAnalysis();
    }

    /**
     * /**
     * 趋势曲线  年
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUseWaterMonthAnalysis")
    public Map<String,List<String>> getUseWaterMonthAnalysis(User user){
        return homeV2Service.getUseWaterMonthAnalysis();
    }

    /**
     * 地图的 当月用水量  耗电量
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUseWaterMonthAnalysisByArea")
    public Map<String, Object> getUseWaterMonthAnalysisByArea(User user){
        return homeV2Service.getUseWaterMonthAnalysisByArea();
    }
    /**
     * 地图的 当月用水量  耗电量
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPumpMapFreeWater")
    public Map<String, Object> getPumpMapFreeWater(User user){
        return homeV2Service.getFreeWaterDayAddAnalysisByDeviceId();
    }



    /**
     * 排名
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUseWaterDayAddAnalysisByPumpHouse")
    public Map<String,Object> getUseWaterDayAddAnalysisByPumpHouse(User user){
        return homeV2Service.getUseWaterDayAddAnalysisByPumpHouse();
    }



    /**
     * 排名
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCurrentDataByDeviceIdAndCode/{deviceId:.+}/data/{code}")
    public Map<String,Object> getCurrentDataByDeviceIdAndCode(@PathVariable("deviceId") String deviceId,@PathVariable("code") String code, User user){
        return homeV2Service.getCurrentDataByDeviceIdAndCode(deviceId,code);
    }

}
