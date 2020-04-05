package com.wapwag.woss.modules.sys.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统资源可配置化
 * guoln
 */
@Controller
@RequestMapping(value = "/config")
public class SysConfigurableController {

    @Value(value = "${onlyORChinaMap}")
    private String mapType;

    @Value(value = "${projectPic}")
    private String projectPic;

    @Value(value = "${longiAndLati}")
    private String longiAndLati;

    @ResponseBody
    @RequestMapping("getMapType")
    public Map<String, String> getMapType(){
        Map<String, String> map = new HashMap<>();
        map.put("SER_TYPE", mapType);
        return map;
    }

    @ResponseBody
    @RequestMapping("getProjectPic")
    public Map<String, String> getProjectPic(){
        Map<String, String> map = new HashMap<>();
        map.put("projectPic", projectPic);
        return map;
    }

    @ResponseBody
    @RequestMapping("getMapPoint")
    public Map<String, String> getMapPoint(){
        Map<String, String> map = new HashMap<>();
        String[] str = longiAndLati.split(",");
        map.put("longi", str[0]);
        map.put("lati", str[1]);
        if ("quanguo".equals(projectPic)){
            map.put("level", "6");
        } else {
            map.put("level", "12");
        }
        return map;
    }

}
