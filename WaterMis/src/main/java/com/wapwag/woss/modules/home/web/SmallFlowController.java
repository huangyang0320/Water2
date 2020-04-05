package com.wapwag.woss.modules.home.web;

import com.wapwag.woss.modules.home.service.SmallFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 小流量保压
 * guoln
 * 2018-10-15
 */
@RestController
@RequestMapping("${adminPath}/smallFlow")
public class SmallFlowController {

    @Autowired
    SmallFlowService smallFlowService;

    @ResponseBody
    @RequestMapping("/getSmallFlow")
    public List<Map<String, Object>> getSmallFlowData(@RequestParam String deviceId, @RequestParam String dimen,
                                                      @RequestParam String startDate, @RequestParam String endDate) {

        return smallFlowService.getSmallFlowData(deviceId, dimen, startDate, endDate);
    }


}
