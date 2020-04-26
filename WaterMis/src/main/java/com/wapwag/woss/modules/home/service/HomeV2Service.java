package com.wapwag.woss.modules.home.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wapwag.woss.common.utils.DateUtils;
import com.wapwag.woss.common.utils.FullZero;
import com.wapwag.woss.common.utils.MapUtil;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.biz.entity.Device;
import com.wapwag.woss.modules.home.dao.HomeV2Dao;
import com.wapwag.woss.modules.home.entity.HomeDTO;
import com.wapwag.woss.modules.monitor.service.PumpConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HomeV2Service {

    @Autowired
    private HomeV2Dao homeV2Dao;

    DecimalFormat FNUM_4 = new DecimalFormat("##0.0000");
    DecimalFormat FNUM_3 = new DecimalFormat("##0.000");
    DecimalFormat FNUM_2 = new DecimalFormat("##0.00");
    DecimalFormat FNUM_0 = new DecimalFormat("##0");

    /**
     * 获取对应月的  总用水量   总用电量
     * @return
     */
    public Map<String,String> getUseWaterThAnalysis(){
        String tableName="service_values_summary";
        String formatDate="'%Y-%m'";
        Map<String,String> resutMap= new HashMap<>();
        GetLastYearUseWaterThAnalysis getLastYearUseWaterThAnalysis = new GetLastYearUseWaterThAnalysis().invoke();
        String lastYearTotalUseWater = getLastYearUseWaterThAnalysis.getLastYearTotalUseWater();
        String lastYearMonthAvgDay = getLastYearUseWaterThAnalysis.getLastYearMonthAvgDay();
        String lastYearPower = getLastYearUseWaterThAnalysis.getLastYearPower();


        String startTime= DateUtils.getLastMonthFirstDay();//上月第一天
        String lastEndTime= DateUtils.getLastMonthLastDay();//上月最后一天
        String endTime= DateUtils.getDate("yyyy-MM-dd");//当前日期

        //上月总天数
        double lastMonthDays=DateUtils.getDistanceOfTwoDate(startTime,lastEndTime);
        //当月截至到当天 总天数
        double currentMonthDays=DateUtils.getDistanceOfTwoDate(DateUtils.getCurrentMonthFirstDay(),endTime);

        List<HomeDTO> listData = homeV2Dao.getUseWaterThAnalysis(startTime,endTime+" 23:59:59",tableName,formatDate);
        //list to map
        Map<String, HomeDTO> appleMap = listData.stream().collect(Collectors.toMap(HomeDTO::getId, a -> a,(k1, k2)->k1));
        //上月总用水量
        String lastTotalUseWater=appleMap.get(startTime.substring(0,7)+"_m3").getPv();
        //当月总用水量
        String currentTotalUseWater=appleMap.get(endTime.substring(0,7)+"_m3").getPv();

        //上月总用水量耗电
        String lastTotalUsePower=appleMap.get(startTime.substring(0,7)+"_kWh").getPv();
        //当月总用水量耗电
        String currentTotalUsePower=appleMap.get(endTime.substring(0,7)+"_kWh").getPv();

        //上月日均用水
        String lastMonthAvgDay=FNUM_4.format(Float.valueOf(lastTotalUseWater)/lastMonthDays);
        //当月日均用水
        String currentMonthAvgDay=FNUM_4.format(Float.valueOf(lastTotalUseWater)/currentMonthDays);

        //上月单吨能耗
        String lastPower=FNUM_4.format(Float.valueOf(lastTotalUsePower)/Float.valueOf(lastTotalUseWater));
        //当月单吨能耗
        String currentPower=FNUM_4.format(Float.valueOf(currentTotalUsePower)/Float.valueOf(currentTotalUseWater));

        //同比增长率=（本期数-同期数）/|同期数|×100%
        String tRate="0";
        if(StringUtils.isNotBlank(lastYearTotalUseWater))
         tRate=FNUM_2.format((Float.valueOf(currentTotalUseWater)-Float.valueOf(lastYearTotalUseWater))/Float.valueOf(lastYearTotalUseWater));

        //环比增长率=（本期数-上期数）/上期数×100%）
        String hRate=FNUM_2.format((Float.valueOf(currentTotalUseWater)-Float.valueOf(lastTotalUsePower))/Float.valueOf(lastTotalUsePower));

        resutMap.put("lastYearMonthAvgDay",lastYearMonthAvgDay+" m³");
        resutMap.put("lastYearPower",lastYearPower+" KW·h/m³");

        resutMap.put("lastMonthAvgDay",lastMonthAvgDay+" m³");
        resutMap.put("lastPower",lastPower+" KW·h/m³");

        resutMap.put("currentMonthAvgDay",currentMonthAvgDay+" m³");
        resutMap.put("currentPower",currentPower+" KW·h/m³");

        resutMap.put("tRate",tRate);
        resutMap.put("hRate",hRate);

        return resutMap;
    }

    private class GetLastYearUseWaterThAnalysis {
        private String lastYearTotalUseWater;
        private String lastYearMonthAvgDay;
        private String lastYearPower;

        public String getLastYearTotalUseWater() {
            return lastYearTotalUseWater;
        }

        public String getLastYearMonthAvgDay() {
            return lastYearMonthAvgDay;
        }

        public String getLastYearPower() {
            return lastYearPower;
        }

        public GetLastYearUseWaterThAnalysis invoke() {
            String tableName="service_values_summary_day";
            String formatDate="'%Y-%m'";
            //去年同月第一天
            String lastYearStartTime= DateUtils.getLastYearFirstDay();
            //去年同月最后一天
            String lastYearEndTime= DateUtils.getLastYearLastDay();
            List<HomeDTO> listLastYearData = homeV2Dao.getUseWaterThAnalysis(lastYearStartTime,lastYearEndTime,tableName,formatDate);
            //list to map
            Map<String, HomeDTO> listLastYearDataMap = listLastYearData.stream().collect(Collectors.toMap(HomeDTO::getId, a -> a,(k1, k2)->k1));
            //去年同月总用水量
            if(listLastYearDataMap.get(lastYearStartTime.substring(0,7) + "_m3")!=null)
                lastYearTotalUseWater = listLastYearDataMap.get(lastYearStartTime.substring(0,7) + "_m3").getPv();
            //去年同月总用水量耗电
            String lastYearTotalUsePower="";
            if(listLastYearDataMap.get(lastYearStartTime.substring(0,7)+"_kWh")!=null)
                lastYearTotalUsePower=listLastYearDataMap.get(lastYearStartTime.substring(0,7)+"_kWh").getPv();
            //去年同月总天数
            double lastYearMonthDays=DateUtils.getDistanceOfTwoDate(lastYearStartTime,lastYearEndTime);
            //去年同月  日均用水量
            if(StringUtils.isNotBlank(lastYearTotalUseWater))
            lastYearMonthAvgDay = FNUM_4.format(Float.valueOf(lastYearTotalUseWater) / lastYearMonthDays);
            //去年同月单吨能耗
            if(StringUtils.isNotBlank(lastYearTotalUsePower))
            lastYearPower = FNUM_4.format(Float.valueOf(lastYearTotalUsePower) / Float.valueOf(lastYearTotalUseWater));
            return this;
        }
    }

    /**
     * 近两天 小时统计（昨天7点 到 实时）
     * 展示4条曲线，其中2条代表昨日7时至今日6时的数值，另2条代表今日7只至当时的数值
     * @return
     */
    public Map<String,List<String>> getUseWaterHourAnalysis(){
        String tableName="service_values_summary";
        String formatDate="'%Y-%m-%d %H'";
        String startTime= DateUtils.getLastDay("yyyy-MM-dd",-1)+" 05";//昨天日期
        String endTime= DateUtils.getDate("yyyy-MM-dd HH");//当前日期


        Map<String,List<String>> resutMap= new HashMap<>();
        List<HomeDTO> listData = homeV2Dao.getUseWaterThAnalysis(startTime,endTime,tableName,formatDate);
        //历史
        List<HomeDTO> useWaterHourHis=null;
        List<HomeDTO> usePowerHourHis=null;
        List<HomeDTO> useAvgPowerHourHis=null;
        //实时
        List<HomeDTO> useWaterHourReal=null;
        List<HomeDTO> usePowerHourReal=null;
        List<HomeDTO> useAvgPowerHourReal=null;
        if(listData!=null && listData.size()>0){
            useWaterHourHis =new ArrayList<>();
            usePowerHourHis =new ArrayList<>();
            useAvgPowerHourHis =new ArrayList<>();

            useWaterHourReal =new ArrayList<>();
            usePowerHourReal =new ArrayList<>();
            useAvgPowerHourReal =new ArrayList<>();

            for(HomeDTO dto: listData){
               // System.out.println(dto.getDateTime());
                //去掉昨天 7点之前的数据
                String h=dto.getDateTime().substring(11,13);
                //
                if(startTime.equals(dto.getDateTime().substring(0,10)) &&  Integer.parseInt(h)<7){
                    continue;
                }

                //实时：今天7点  到实时
                if(endTime.substring(0,10).equals(dto.getDateTime().substring(0,10)) && Integer.parseInt(h)>=5){
                    if("m3".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        useWaterHourReal.add(dto);
                    }
                    if("kWh".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        usePowerHourReal.add(dto);
                    }
                }else{
                 //历史  昨天7点到今天  6点
                    if("m3".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        useWaterHourHis.add(dto);
                    }
                    if("kWh".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        usePowerHourHis.add(dto);
                    }
                }


            }

            /*if(useWaterHourHis!=null && useWaterHourHis.size()>0 && usePowerHourHis!=null && usePowerHourHis.size()>0) {
                for(int i=0;i<useWaterHourHis.size();i++){
                    if(Float.valueOf(useWaterHourHis.get(i))>0f){
                        useAvgPowerHourHis.add(FNUM_2.format(Float.valueOf(usePowerHourHis.get(i))/Float.valueOf(useWaterHourHis.get(i))));
                    }else{
                        useAvgPowerHourHis.add("0");
                    }

                }
            }*/

            endTime = DateUtils.getDate("yyyy-MM-dd")+" 04";


            LinkedHashMap<String,String> xMap = FullZero.getDefaultYHour(startTime,endTime);
            List<String> xData=xMap.keySet().stream().collect(Collectors.toList());

            resutMap.put("xData", xData);
            resutMap.put("useWaterHis", FullZero.getHourFullZero(startTime,endTime,useWaterHourHis));
            resutMap.put("usePowerHis",FullZero.getHourFullZero(startTime,endTime,usePowerHourHis));
            resutMap.put("useAvgPowerHis",FullZero.getHourFullZero(startTime,endTime,useAvgPowerHourHis));

            resutMap.put("useWaterReal",FullZero.getHourFullZero(startTime,endTime,useWaterHourReal));
            resutMap.put("usePowerReal",FullZero.getHourFullZero(startTime,endTime,usePowerHourReal));
        }

        return resutMap;
    }


    public Map<String,List<String>> getUseWaterHourAnalysisNew(){
        String tableName="service_values_summary";
        String formatDate="'%Y-%m-%d %H'";
        String cTime= DateUtils.getDate("yyyy-MM-dd HH").substring(11,13);//当前日期

        Map<String,List<String>> resutMap= new HashMap<>();
        if(Integer.parseInt(cTime)>0 && Integer.parseInt(cTime)<5){
            String startTime= DateUtils.getLastDay("yyyy-MM-dd",-2)+" 05";//前天日期
            String endTime=DateUtils.getLastDay("yyyy-MM-dd",-1)+" 05";;//昨天前日期
            List<HomeDTO> listData = homeV2Dao.getUseWaterThAnalysis(startTime,endTime,tableName,formatDate);
            //历史
            List<HomeDTO> useWaterHourHis=null;
            List<HomeDTO> usePowerHourHis=null;
            List<HomeDTO> useAvgPowerHourHis=null;

            if(listData!=null && listData.size()>0){
                useWaterHourHis =new ArrayList<>();
                usePowerHourHis =new ArrayList<>();
                useAvgPowerHourHis =new ArrayList<>();
                for(HomeDTO dto: listData){
                    //历史  前天7点到昨天6点
                    if("m3".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        useWaterHourHis.add(dto);
                    }
                    if("kWh".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        usePowerHourHis.add(dto);
                    }
                }
                endTime = DateUtils.getDate("yyyy-MM-dd")+" 04";

                LinkedHashMap<String,String> xMap = FullZero.getDefaultYHour(startTime,endTime);
                List<String> xData=xMap.keySet().stream().collect(Collectors.toList());

                resutMap.put("xData", xData);
                resutMap.put("useWaterHis", FullZero.getHourFullZero(startTime,endTime,useWaterHourHis));
                resutMap.put("usePowerHis",FullZero.getHourFullZero(startTime,endTime,usePowerHourHis));
                resutMap.put("useAvgPowerHis",FullZero.getHourFullZero(startTime,endTime,useAvgPowerHourHis));
            }

            //实时
            List<HomeDTO> useWaterHourReal=null;
            List<HomeDTO> usePowerHourReal=null;
            List<HomeDTO> useAvgPowerHourReal=null;
            startTime= DateUtils.getLastDay("yyyy-MM-dd",-1)+" 05";//昨天前日期
            endTime= DateUtils.getDate("yyyy-MM-dd HH");//当前日期
            List<HomeDTO> listDataRead = homeV2Dao.getUseWaterThAnalysis(startTime,endTime,tableName,formatDate);
            useWaterHourReal =new ArrayList<>();
            usePowerHourReal =new ArrayList<>();

            for(HomeDTO dto: listDataRead){
                //实时：今天7点  到实时
                    if("m3".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        useWaterHourReal.add(dto);
                    }
                    if("kWh".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        usePowerHourReal.add(dto);
                    }
            }
            endTime = DateUtils.getDate("yyyy-MM-dd")+" 04";
            resutMap.put("useWaterReal",FullZero.getHourFullZero(startTime,endTime,useWaterHourReal));
            resutMap.put("usePowerReal",FullZero.getHourFullZero(startTime,endTime,usePowerHourReal));
        }
        return resutMap;
    }




    /**
     * 近两月 统计
     * 当选择月曲线时，展示4条曲线，其中2条代表上月1号-31号的数值，另2条代表当前月1号-当日的数值
     * @return
     */
    public Map<String,List<String>> getUseWaterDayAnalysis(){
        String tableName="service_values_summary_day";
        String formatDate="'%Y-%m-%d'";
        String startTime= DateUtils.getLastMonthFirstDay();//上月第一天
        String endTime= DateUtils.getDate("yyyy-MM-dd HH:mm:ss");//当前日期

        Map<String,List<String>> resutMap= new HashMap<>();
        List<HomeDTO> listData = homeV2Dao.getUseWaterThAnalysis(startTime,endTime,tableName,formatDate);
        //历史
        List<HomeDTO> useWaterHourHis=null;
        List<HomeDTO> usePowerHourHis=null;
        List<HomeDTO> useAvgPowerHourHis=null;
        //实时
        List<HomeDTO> useWaterHourReal=null;
        List<HomeDTO> usePowerHourReal=null;
        List<HomeDTO> useAvgPowerHourReal=null;
        if(listData!=null && listData.size()>0){
            useWaterHourHis =new ArrayList<>();
            usePowerHourHis =new ArrayList<>();
            useAvgPowerHourHis =new ArrayList<>();

            useWaterHourReal =new ArrayList<>();
            usePowerHourReal =new ArrayList<>();
            useAvgPowerHourReal =new ArrayList<>();

            for(HomeDTO dto: listData){
                if((startTime.substring(0,7)).equals(dto.getDateTime().substring(0,7))){
                    //上月数据
                    if("m3".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        useWaterHourHis.add(dto);
                    }
                    if("kWh".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        usePowerHourHis.add(dto);
                    }
                }else{
                    if("m3".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        useWaterHourReal.add(dto);
                    }
                    if("kWh".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        usePowerHourReal.add(dto);
                    }
                }



            }


            endTime = DateUtils.getDate("yyyy-MM-dd");

            LinkedHashMap<String,String> xMap = FullZero.getDefaultYDay(startTime,endTime);
            List<String> xData=xMap.keySet().stream().collect(Collectors.toList());

            resutMap.put("xData", xData);
            resutMap.put("useWaterHis", FullZero.getDayFullZero(startTime,endTime,useWaterHourHis));
            resutMap.put("usePowerHis",FullZero.getDayFullZero(startTime,endTime,usePowerHourHis));
           // resutMap.put("useAvgPowerDayHis",FullZero.getDayFullZero(startTime,endTime,useAvgPowerHourHis));

            resutMap.put("useWaterReal",FullZero.getDayFullZero(startTime,endTime,useWaterHourReal));
            resutMap.put("usePowerReal",FullZero.getDayFullZero(startTime,endTime,usePowerHourReal));
        }

        return resutMap;
    }


    /**
     * 近两年 统计
     * 当选择年曲线时，展示4条曲线，其中2条代表上一年度1月-12月的数值，另2条代表今年1月-当月的数值
     * @return
     */
    public Map<String,List<String>> getUseWaterMonthAnalysis(){
        String tableName="service_values_summary_day";
        String formatDate="'%Y-%m'";
        String startTime= DateUtils.getLastYear();//去年
        String endTime= DateUtils.getDate("yyyy-MM-dd HH:mm:ss");//当前日期

        Map<String,List<String>> resutMap= new HashMap<>();
        List<HomeDTO> listData = homeV2Dao.getUseWaterThAnalysis(startTime,endTime,tableName,formatDate);
        //历史
        List<HomeDTO> useWaterHourHis=null;
        List<HomeDTO> usePowerHourHis=null;
        List<HomeDTO> useAvgPowerHourHis=null;
        //实时
        List<HomeDTO> useWaterHourReal=null;
        List<HomeDTO> usePowerHourReal=null;
        List<HomeDTO> useAvgPowerHourReal=null;
        if(listData!=null && listData.size()>0){
            useWaterHourHis =new ArrayList<>();
            usePowerHourHis =new ArrayList<>();
            useAvgPowerHourHis =new ArrayList<>();

            useWaterHourReal =new ArrayList<>();
            usePowerHourReal =new ArrayList<>();
            useAvgPowerHourReal =new ArrayList<>();

            for(HomeDTO dto: listData){
                if((startTime.substring(0,4)).equals(dto.getDateTime().substring(0,4))){
                    //上月数据
                    if("m3".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        useWaterHourHis.add(dto);
                    }
                    if("kWh".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        usePowerHourHis.add(dto);
                    }
                }else{
                    if("m3".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        useWaterHourReal.add(dto);
                    }
                    if("kWh".equals(dto.getUnit())){
                        dto.setPv(FNUM_3.format(Float.valueOf(dto.getPv())));
                        usePowerHourReal.add(dto);
                    }
                }



            }


            startTime=DateUtils.getLastYear()+"-01";
            endTime=DateUtils.getLastYear()+"-12";

            LinkedHashMap<String,String> xMap = FullZero.getDefaultYMonth(startTime,endTime);
            List<String> xData=xMap.keySet().stream().collect(Collectors.toList());

            resutMap.put("xData", xData);
            resutMap.put("useWaterHis", FullZero.getMonthFullZero(startTime,endTime,useWaterHourHis));
            resutMap.put("usePowerHis",FullZero.getMonthFullZero(startTime,endTime,usePowerHourHis));
            // resutMap.put("useAvgPowerDayHis",FullZero.getDayFullZero(startTime,endTime,useAvgPowerHourHis));

            resutMap.put("useWaterReal",FullZero.getMonthFullZero(startTime,endTime,useWaterHourReal));
            resutMap.put("usePowerReal",FullZero.getMonthFullZero(startTime,endTime,usePowerHourReal));
        }

        return resutMap;
    }


    public List<HomeDTO> getMapAreaName(){
        return  homeV2Dao.getMapAreaName();
    }

    public Map<String, Object> getUseWaterMonthAnalysisByArea() {
        String tableName = "service_values_summary";
        String formatDate = "'%Y-%m-%d'";
        String startTime = DateUtils.getCurrentMonthFirstDay();//当月第一天
        String endTime = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");//当前日期

        Map<String,Object> resutMap= new HashMap<>();
        List<HomeDTO> listData = homeV2Dao.getUseWaterMonthAnalysisByArea(startTime,endTime,tableName,formatDate);


        if(listData!=null && listData.size()>0){
            List<HomeDTO> listMapData = homeV2Dao.getMapAreaName();
            Map<String, String> m3Map = listMapData.stream().collect(Collectors.toMap(e->e.getAreaName(),e->e.getDefaultVal(),(key1 , key2)-> key2));
            Map<String, String> kwhMap = listMapData.stream().collect(Collectors.toMap(e->e.getAreaName(),e->e.getDefaultVal(),(key1 , key2)-> key2));

            for(HomeDTO dto: listData) {
                    if (StringUtils.isNotBlank(dto.getUnit()) && "m3".equals(dto.getUnit())) {
                        m3Map.put(dto.getAreaName(),FNUM_3.format(Float.valueOf(dto.getPv())));
                    }else if (StringUtils.isNotBlank(dto.getUnit()) && "kWh".equals(dto.getUnit())) {
                        kwhMap.put(dto.getAreaName(),FNUM_3.format(Float.valueOf(dto.getPv())));
                }
            }

            JSONArray jsonArrayM3=new JSONArray();
            m3Map.forEach((k, v) -> {
                JSONObject obj=new JSONObject();
                obj.put("name",k);
                obj.put("value",v);
                obj.put("itemStyle","");
                jsonArrayM3.add(obj);
            });

            JSONArray jsonArrayKwh=new JSONArray();
            kwhMap.forEach((k, v) -> {
                JSONObject obj=new JSONObject();
                obj.put("name",k);
                obj.put("value",v);
                obj.put("itemStyle","");
                jsonArrayKwh.add(obj);
            });



            resutMap.put("m3", jsonArrayM3);
          //  resutMap.put("totalM3", totalM3);
            resutMap.put("kWh",jsonArrayKwh);
        }
        return resutMap;
    }

    public Map<String,Object> getUseWaterDayAddAnalysisByPumpHouse() {
        String tableName = "service_values_summary_day";
        String formatDate = "'%Y-%m-%d'";
        String startTime = DateUtils.getLastDay("yyyy-MM-dd",-2);//前天
        String endTime = DateUtils.getLastDay("yyyy-MM-dd",-1)+" 23:59:59";//昨天

        Map<String,Object> resutMap= new HashMap<>();
        List<HomeDTO> listData = homeV2Dao.getUseWaterDayAddAnalysisByPumpHouse(startTime,endTime,tableName,formatDate);
        if(listData!=null && listData.size()>0){
            List<HomeDTO> listMapData = homeV2Dao.getMapAreaName();
            Map<String, Float> m3MapQ = listMapData.stream().collect(Collectors.toMap(e->e.getPumpName(),e->Float.valueOf(e.getDefaultVal()),(key1 , key2)-> key2));
            m3MapQ.remove(null);
            Map<String, Float> kwhMapQ = listMapData.stream().collect(Collectors.toMap(e->e.getPumpName(),e->Float.valueOf(e.getDefaultVal()),(key1 , key2)-> key2));
            kwhMapQ.remove(null);
            Map<String, Float> m3MapZ = listMapData.stream().collect(Collectors.toMap(e->e.getPumpName(),e->Float.valueOf(e.getDefaultVal()),(key1 , key2)-> key2));
            m3MapZ.remove(null);
            Map<String, Float> kwhMapZ = listMapData.stream().collect(Collectors.toMap(e->e.getPumpName(),e->Float.valueOf(e.getDefaultVal()),(key1 , key2)-> key2));
            kwhMapZ.remove(null);
            for(HomeDTO dto: listData) {
                //前天
                if (dto!=null && StringUtils.isNotBlank(dto.getUnit()) && startTime.equals(dto.getDateTime()) && "m3".equals(dto.getUnit())) {
                    m3MapQ.put(dto.getAreaName(),Float.valueOf(dto.getPv()));
                }else if (dto!=null && StringUtils.isNotBlank(dto.getUnit()) && startTime.equals(dto.getDateTime()) && "kWh".equals(dto.getUnit())) {
                    kwhMapQ.put(dto.getAreaName(),Float.valueOf(dto.getPv()));
                }else if (dto!=null && StringUtils.isNotBlank(dto.getUnit()) && (DateUtils.getLastDay("yyyy-MM-dd",-1)).equals(dto.getDateTime()) && "m3".equals(dto.getUnit())) {
                //昨天
                    m3MapZ.put(dto.getAreaName(),Float.valueOf(dto.getPv()));
                }else if (dto!=null && StringUtils.isNotBlank(dto.getUnit()) && (DateUtils.getLastDay("yyyy-MM-dd",-1)).equals(dto.getDateTime()) && "kWh".equals(dto.getUnit())) {
                    kwhMapZ.put(dto.getAreaName(),Float.valueOf(dto.getPv()));
                }
            }
            //排序top10(降序)
            Map top10Desc = MapUtil.sortByValueDesc(m3MapZ,10);
            resutMap.put("top10_Deac_Name",top10Desc.keySet().stream().collect(Collectors.toList()));
            resutMap.put("top10_Deac_Value",top10Desc.values().stream().collect(Collectors.toList()));

            //排序top10(升序)
            Map top10Asc = MapUtil.sortByValueAsc(m3MapZ,10);
            resutMap.put("top10_Asc_Name",top10Asc.keySet().stream().collect(Collectors.toList()));
            resutMap.put("top10_Asc_Value",top10Asc.values().stream().collect(Collectors.toList()));



            //排序top10(增量)
            Map<String, Float> mapAdd = new HashMap<>();
            m3MapZ.remove(null);
            for(String key1 : m3MapZ.keySet()){
                float map1value = m3MapZ.get(key1)==null?0f:Float.valueOf(m3MapZ.get(key1));
                float map2value = m3MapQ.get(key1)==null?0f:Float.valueOf(m3MapQ.get(key1));
                    mapAdd.put(key1+"-"+map1value,(map1value-map2value));
            }
            Map top10AddDesc =  MapUtil.sortByValueAsc(mapAdd,10);
            JSONArray addNameList=new JSONArray();
            JSONArray addList=new JSONArray();
            JSONArray addOldList=new JSONArray();
            top10AddDesc.forEach((k, v) -> {
                addNameList.add(k.toString().split("-")[0]);
                addList.add(FNUM_3.format(v));
                addOldList.add(FNUM_3.format(Float.valueOf(k.toString().split("-")[1])));
            });
            resutMap.put("top10_Add_Deac_Name",addNameList);
            resutMap.put("top10_Add_Deac_Value",addList);
            resutMap.put("top10_Add_Deac_Default_Value",addOldList);


            //排序top10(减量)
            Map<String, Float> mapJ = new HashMap<>();
            m3MapZ.remove(null);
            for(String key1 : m3MapZ.keySet()){
                float map1value = m3MapZ.get(key1)==null?0f:Float.valueOf(m3MapZ.get(key1));
                float map2value = m3MapQ.get(key1)==null?0f:Float.valueOf(m3MapQ.get(key1));
                mapJ.put(key1+"-"+map1value,(map2value-map1value));
            }
            Map top10JianDesc =  MapUtil.sortByValueDesc(mapJ,10);
            JSONArray jianNameList=new JSONArray();
            JSONArray jianList=new JSONArray();
            JSONArray jianOldList=new JSONArray();
            top10JianDesc.forEach((k, v) -> {
                jianNameList.add(k.toString().split("-")[0]);
                jianList.add(FNUM_3.format(v));
                jianOldList.add(FNUM_3.format(Float.valueOf(k.toString().split("-")[1])));
            });
            resutMap.put("top10_Jian_Deac_Name",jianNameList);
            resutMap.put("top10_Jian_Deac_Value",jianList);
            resutMap.put("top10_Jian_Deac_Default_Value",jianOldList);


            /*JSONArray jsonArrayM3=new JSONArray();
            m3Map.forEach((k, v) -> {
                JSONObject obj=new JSONObject();
                obj.put("name",k);
                obj.put("value",v);
                obj.put("itemStyle","");
                jsonArrayM3.add(obj);
            });

            JSONArray jsonArrayKwh=new JSONArray();
            kwhMap.forEach((k, v) -> {
                JSONObject obj=new JSONObject();
                obj.put("name",k);
                obj.put("value",v);
                obj.put("itemStyle","");
                jsonArrayKwh.add(obj);
            });*/



            /*resutMap.put("m3", jsonArrayM3);
            //  resutMap.put("totalM3", totalM3);
            resutMap.put("kWh",jsonArrayKwh);*/
        }
        return resutMap;
    }


    public Map<String,Object> getFreeWaterDayAddAnalysisByDeviceId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String tableName ="service_values_"+sdf.format(new Date());

        List<HomeDTO> listData = homeV2Dao.getFreeWaterDayAddAnalysisByDeviceId(tableName);
        Map<String, List<HomeDTO>> map = listData.stream().collect(Collectors.groupingBy(HomeDTO::getAreaName));

        List<HomeDTO> listMapData = homeV2Dao.getMapAreaName();
        Map<String, String> mapData = listMapData.stream().collect(Collectors.toMap(e->e.getAreaName(),e->e.getDefaultVal(),(key1 , key2)-> key2));

        map.forEach((k, v) -> {
            float totalAreaPv=0f;
            for(HomeDTO dto:v){
                //计算
                if(StringUtils.isNotBlank(dto.getPv()) && StringUtils.isNotBlank(dto.getSxGg()) && 3 == dto.getSxGg().split("\\*").length){
                    String[]  c=dto.getSxGg().split("\\*");
                    // 除以1000是 mm转 m
                    totalAreaPv += Float.valueOf(dto.getPv()) * (Float.valueOf(c[0])/ 1000f) * (Float.valueOf(c[1])/1000f);
                }
            }
            mapData.put(k,FNUM_3.format(totalAreaPv));
        });

        JSONArray jsonArrayM3=new JSONArray();
        mapData.forEach((k, v) -> {
            JSONObject obj=new JSONObject();
            obj.put("name",k);
            obj.put("value",v);
            jsonArrayM3.add(obj);
        });
        Map<String,Object> resutMap= new HashMap<>();
        resutMap.put("m3",jsonArrayM3);
        return resutMap;
    }


        public JSONArray getMapAreaPumpHouse() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String tableName ="service_values_"+sdf.format(new Date());

            List<HomeDTO> listData = homeV2Dao.getMapAreaPumpHouse();
            //list to map
            Map<String, HomeDTO> map = listData.stream().collect(Collectors.toMap(HomeDTO::getAreaName, a -> a,(k1, k2)->k1));

            List<HomeDTO> listMapData = homeV2Dao.getMapAreaName();
            Map<String, String> mapData = listMapData.stream().collect(Collectors.toMap(e->e.getAreaName(),e->e.getDefaultVal(),(key1 , key2)-> key2));

        map.forEach((k, v) -> {
            mapData.put(k,v.getPv());
        });

        JSONArray jsonArray=new JSONArray();
        mapData.forEach((k, v) -> {
            JSONObject obj=new JSONObject();
            obj.put("name",k);
            obj.put("value",v);
            obj.put("itemStyle","");
            jsonArray.add(obj);
        });
        return jsonArray;
    }


    /**
     * 组态中获取历史 点位数据
     * @param deviceId
     * @param code
     * @return
     */
    private static final String SV = "service_values_hour_";
    public JSONObject getCurrentDataByDeviceIdAndCode(String deviceId,String code){
        /*String tableName = PumpConfigurationService.createTableName();*/
        String tableName = createTableName();
        List<HomeDTO> list = homeV2Dao.getCurrentDataByDeviceIdAndCode(tableName,deviceId,code);
        if(list!=null && list.size()>0){
            List<String> xData=new ArrayList();
            List<String> yData=new ArrayList();
            JSONObject obj=new JSONObject();
            for(HomeDTO dto:list){
                xData.add(dto.getDateTime());
                yData.add(FNUM_3.format(Float.valueOf(dto.getPv())));
            }
            obj.put("xData",xData);
            obj.put("yData",yData);
            return obj;
        }
        return null;

    }


    /**
     * 创建搜索表名
     * @return
     */
    public static String createTableName() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String format = df.format(new Date());
        String time = format.split(" ")[0].replace("-", "");
        String tableName = SV + time.substring(0, 8);
        return tableName;
    }
}
