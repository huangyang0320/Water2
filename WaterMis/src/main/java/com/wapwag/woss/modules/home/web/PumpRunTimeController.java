package com.wapwag.woss.modules.home.web;

import com.wapwag.woss.modules.home.entity.RunTimeDTO;
import com.wapwag.woss.modules.home.service.PumpRunTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 泵机运行时间  泵机带图
 * guoln
 * 2018-09-21
 */
@RestController
@RequestMapping("${adminPath}/pumpRunTime")
public class PumpRunTimeController {

    @Autowired
    PumpRunTimeService pumpRunTimeService;

    @ResponseBody
    @RequestMapping("/qryRunTime")
    public Map<String, List<Map<String, Object>>> getPumpRunTime(@RequestBody RunTimeDTO queryDTO) {
        return pumpRunTimeService.selectPumpRunTime(queryDTO);
    }

    @ResponseBody
    @RequestMapping("/qryStatTime")
    public List<Map<String, String>> getPumpStatTime(@RequestBody RunTimeDTO queryDTO) {
        return pumpRunTimeService.selectPumpStatTime(queryDTO);
    }
}
