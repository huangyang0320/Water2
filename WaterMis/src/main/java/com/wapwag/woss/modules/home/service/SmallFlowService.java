package com.wapwag.woss.modules.home.service;

import com.alibaba.fastjson.JSON;
import com.wapwag.woss.common.utils.DateUtils;
import com.wapwag.woss.modules.home.dao.SmallFlowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * guoln
 */
@Service
public class SmallFlowService {

    private final Logger logger = LoggerFactory.getLogger(SmallFlowService.class);

    @Autowired
    SmallFlowMapper smallFlowMapper;

    public List<Map<String, Object>> getSmallFlowData(String deviceId, String dimen, String startDate, String endDate){
        System.err.println(startDate);
        System.err.println(endDate);

        List<Map<String, Object>> mapData = smallFlowMapper.getSmallFlowData(deviceId, startDate, endDate);

        float[] powerData = initData(dimen, startDate);
        float[] flowData = initData(dimen, startDate);

        for (Map<String, Object> map : mapData) {
            float pv = (float) map.get("pv");
            String startTime = (String) map.get("startTime");
            String code = (String) map.get("code");
            String uint = (String) map.get("uint");
            int val = timeVal(dimen, startTime);
            if ("day".equals(dimen)){
                if ("Active_power".equals(code)){
                    powerData[val] += pv;
                }
                if ("CumulativeFlow".equals(code)){
                    flowData[val] += pv;
                }
            }
            if ("month".equals(dimen) || "year".equals(dimen)){
                if ("Active_power".equals(code)){
                    powerData[val-1] += pv;
                }
                if ("CumulativeFlow".equals(code)){
                    flowData[val-1] += pv;
                }
            }
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", "耗电量");
        data.put("data", powerData);
        result.add(data);
        data = new HashMap<String, Object>();
        data.put("name", "流量");
        data.put("data", flowData);
        data.put("yAxis", 1);
        result.add(data);
        System.err.println(JSON.toJSONString(result));
        return result;
    }

    private float[] initData(String dimen, String startDate){
        int count = 0;
        Date date = DateUtils.parseDate(startDate);
        Calendar cal = DateUtils.toCalendar(date);
        if ("day".equals(dimen)){
            count = cal.getActualMaximum(Calendar.HOUR_OF_DAY) + 1;
        }
        if ("month".equals(dimen)){
            count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if ("year".equals(dimen)){
            count = cal.getActualMaximum(Calendar.MONTH) + 1;
        }
        return new float[count];
    }

    private int timeVal(String dimen, String dateStr){
        Date date = DateUtils.parseDate(dateStr);
        Calendar cal = DateUtils.toCalendar(date);
        Integer val = null;
        if ("day".equals(dimen)){
            val = cal.get(Calendar.HOUR_OF_DAY);
        }
        if ("month".equals(dimen)){
            val = cal.get(Calendar.DAY_OF_MONTH);
        }
        if ("year".equals(dimen)){
            val = cal.get(Calendar.MONTH) + 1;//month为 0-11
        }
        return val;
    }


}
