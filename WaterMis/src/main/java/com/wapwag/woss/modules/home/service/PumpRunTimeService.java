package com.wapwag.woss.modules.home.service;

import com.alibaba.fastjson.JSON;
import com.wapwag.woss.common.utils.DateUtils;
import com.wapwag.woss.modules.home.dao.PumpRunTimeMapper;
import com.wapwag.woss.modules.home.dao.StatRunMapper;
import com.wapwag.woss.modules.home.entity.RunTimeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * guoln
 */
@Service
public class PumpRunTimeService {

    private final Logger logger = LoggerFactory.getLogger(PumpRunTimeService.class);

    @Autowired
    PumpRunTimeMapper pumpRunTimeMapper;

    @Autowired
    StatRunMapper statRunMapper;

    /**
     * 查看泵机运行时间
     * @param runTimeDTO
     * @return
     */
    public Map<String, List<Map<String, Object>>> selectPumpRunTime(RunTimeDTO runTimeDTO){

        String startTime = runTimeDTO.getStartDate();
        String endTime = runTimeDTO.getEndDate();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int timeCount = 24*60;
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            timeCount = (int)(end.getTime() - start.getTime())/(1000*60) + 1;
        } catch (ParseException e) {
            logger.error(""+e);
        }

        List<Map<String, Object>> name = new ArrayList<Map<String, Object>>();
        List<String> namelist = new ArrayList<String>();

        List<int[]> arrayList = new ArrayList<int[]>();
        for (String deviceId : runTimeDTO.getDeviceIds()) {
            String pHName = pumpRunTimeMapper.getPHNameByDevId(deviceId);
            List<String> pumps = statRunMapper.getPumpByDevCode(deviceId); //1#,2#...
            for (String pump : pumps) {
                namelist.add(pHName+"_"+deviceId+"_"+pump+"泵");
                List<Map<String, Object>> timeList = pumpRunTimeMapper.qryRunTime(deviceId, pump, startTime, endTime);
                int[] array = new int[timeCount];
                for (Map<String, Object> time : timeList) {
                    Date startDate = (Date)time.get("startDate");
                    Date endDate = (Date)time.get("endDate");
                    String mark = (String)time.get("mark");
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    int startHour = cal.get(Calendar.HOUR_OF_DAY);
                    int startMinute = cal.get(Calendar.MINUTE);
                    int startMinuteCount = startHour * 60 + startMinute;
                    cal.setTime(endDate);
                    int endHour = cal.get(Calendar.HOUR_OF_DAY);
                    int endMinute = cal.get(Calendar.MINUTE);
                    int endMinuteCount = endHour * 60 + endMinute;
                    if ("3".equals(mark)){ //高频运行
                        for (int i = startMinuteCount; i <= endMinuteCount; i++) {
                            array[i] = 3;
                        }
                    }
                    if ("1".equals(mark) || "2".equals(mark)){ //低频、中频运行
                        for (int i = startMinuteCount; i <= endMinuteCount; i++) {
                            array[i] = 1;
                        }
                    }
                }
                int[] arryWithFive = convert2FiveMinute(array);
                arrayList.add(arryWithFive);
            }
        }
        Map<String, Object> nameMap = new HashMap<String, Object>();
        nameMap.put("name", namelist);
        name.add(nameMap);

        //将数组list转换为二维数组，便于后面循环取值
        timeCount = timeCount/5; //以5分钟为单位转化后，这里同样要除以5
        int[][] dataArray = new int[arrayList.size()][timeCount];
        for (int i = 0; i < arrayList.size(); i++) {
            dataArray[i] = arrayList.get(i);
        }
        //将数组倒叙，因为HCharts将数据从后向前渲染，所以需将数组倒叙
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < timeCount/2; j++) {
                int count = 0;
                count = dataArray[i][j];
                dataArray[i][j] = dataArray[i][timeCount-1-j];
                dataArray[i][timeCount-1-j] = count;
            }
        }

        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < timeCount; i++) {
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            for (int j = 0; j < dataArray.length; j++) {
                Map<String, Object> map = new HashMap<>();
                map.put("y", 1);
                if (dataArray[j][i] == 1){
                    map.put("color", "#abc93b");
                }else if (dataArray[j][i] == 3){
                    map.put("color", "#275ab6");
                }else {
                    map.put("color", "#808080");
                }
                data.add(map);
            }
            dataMap.put("data", data);
            datas.add(dataMap);
        }

        Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String,Object>>>();
        result.put("xAxis", name);
        result.put("series", datas);
        return result;
    }

    /**
     * 将以分钟为单位的数组转换为 以5分钟为一个单位，数组长度由1440 变为 1440/5=288
     * @param array
     * @return
     */
    private int[] convert2FiveMinute(int[] array) {
        int[] ret = new int[array.length/5];
        int varCount=0;
        int highCount=0;
        int j=0;
        for(int i=0; i<array.length;i++){
            if(array[i]==1){
                varCount ++;
            }else if(array[i]==3){
                highCount ++;
            }
            if((i+1)%5==0){
                if(varCount>2){
                    ret[j] = 1;
                }else if(highCount>2){
                    ret[j] = 3;
                }else{
                    ret[j] = 0;
                }
                j++;
                varCount=0;
                highCount=0;
            }
        }
        return ret;
    }

    /**
     * 查询泵机不同状态的运行时间（工频、变频、停机）
     * @param runTimeDTO
     */
    public List<Map<String, String>> selectPumpStatTime(RunTimeDTO runTimeDTO){

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for (String deviceId : runTimeDTO.getDeviceIds()) {

            String pHName = pumpRunTimeMapper.getPHNameByDevId(deviceId);

            Map<String, Object> qryObject = new HashMap<String, Object>();
            qryObject.put("devCode", deviceId);
            qryObject.put("start", runTimeDTO.getStartDate());
            qryObject.put("end", runTimeDTO.getEndDate());
            qryObject.put("maxVal", 60);
            qryObject.put("except", 60);

            Map<String, Object> statVal = new HashMap<String, Object>();
            List<Map<String, Object>> vals = pumpRunTimeMapper.qryFreRunTime(qryObject);
            for (int i = 0; i < vals.size(); i++) {
                statVal.put(vals.get(i).get("name").toString(), vals.get(i).get("value"));
            }
            System.err.println(JSON.toJSONString(statVal));

            List<String> pumps = statRunMapper.getPumpByDevCode(deviceId); //1#,2#...
            for (String pump : pumps) {
                if (null == pump) {
                    continue;
                }
                BigDecimal lowFre = new BigDecimal(statVal.get(pump + "_低频") == null ? "0" : (statVal.get(pump + "_低频")+""));
                BigDecimal midFre = new BigDecimal(statVal.get(pump + "_中频") == null ? "0" : (statVal.get(pump + "_中频")+""));
                BigDecimal highFre = new BigDecimal(statVal.get(pump + "_高频") == null ? "0" : (statVal.get(pump + "_高频")+""));
                BigDecimal varFre = lowFre.add(midFre);

                String startTime = runTimeDTO.getStartDate();
                String endTime = runTimeDTO.getEndDate();
                Date start = DateUtils.parseDate(startTime);
                Date end = DateUtils.parseDate(endTime);
                int timeCount = (int)(end.getTime() - start.getTime())/(1000*60) + 1;
                //结束时间为当天时，还没到 23：59：59，最后时间应为当前时间点
                if (DateUtils.getDate().equals(DateUtils.formatDate(end, "yyyy-MM-dd"))){
                    timeCount = (int)(new Date().getTime() - start.getTime())/(1000*60) + 1;
                }
                String varFreTime = timeFormat(varFre.intValue());
                String highFreTime = timeFormat(highFre.intValue());
                String stopTime = timeFormat(timeCount-varFre.intValue()-highFre.intValue());

                String[] pumpStatus = {"varFre", "highFre", "stop"};
                for (String status : pumpStatus) {
                    Map<String, String> map= new HashMap<String, String>();
                    map.put("pHName", pHName);
                    map.put("deviceId", deviceId);
                    map.put("pumpNo", pump+"泵");
                    if ("varFre".equals(status)){
                        map.put("status", "变频运行");
                        map.put("time", varFreTime);
                    }
                    if ("highFre".equals(status)){
                        map.put("status", "工频运行");
                        map.put("time", highFreTime);
                    }
                    if ("stop".equals(status)){
                        map.put("status", "停止");
                        map.put("time", stopTime);
                    }
                    result.add(map);
                }
            }
        }
        return result;
    }

    private String timeFormat(int minute){
        String result = "-";
        if (minute < 60){
            result = minute + "分";
        }else if (minute < 60*24){
            result = minute/60 + "小时" + minute%60 + "分";
        }else {
            result = minute/(60*24) + "天" + (minute%(60*24))/60 + "小时" + (minute%(60*24))%60 + "分";
        }
        return result;
    }

}
