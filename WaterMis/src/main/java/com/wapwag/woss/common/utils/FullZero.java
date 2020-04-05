package com.wapwag.woss.common.utils;

import com.wapwag.woss.modules.home.entity.HomeDTO;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FullZero {

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static LinkedHashMap<String,String> getDefaultYHour(String startTime,String endTime){
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat sd = new SimpleDateFormat("HH");
        LinkedHashMap<String,String> map=new LinkedHashMap<>();

        try{
            Date dBegin=sd2.parse(startTime);
            Date dEnd=sd2.parse(endTime);

            map.put(sd.format(dBegin),"");
            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime()))
            {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.HOUR_OF_DAY, 1);
                map.put(sd.format(calBegin.getTime()),"");
                //System.out.println(sd.format(calBegin.getTime()));
            }
            //System.out.println(map);
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static List<String> getHourFullZero(String startTime,String endTime,List<HomeDTO> dataList){
        LinkedHashMap<String,String> map = FullZero.getDefaultYHour(startTime,endTime);

        for(HomeDTO dto:dataList){
            if(StringUtils.isNotBlank(dto.getDateTime()) && StringUtils.isNotBlank(dto.getPv())){
                map.put(dto.getDateTime().substring(11,13),dto.getPv());
            }
        }
        return map.values().stream()
                .collect(Collectors.toList());
    }









    public static LinkedHashMap<String,String> getDefaultYDay(String lastMonth,String currentMonth){
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("dd");
        LinkedHashMap<String,String> map=new LinkedHashMap<>();

        //取两个月中 天数多 的为xData
        String startTime="";
        String endTime="";
        int lastDays =DateUtils.getDaysOfMonth(lastMonth);
        int currentDays =DateUtils.getDaysOfMonth(currentMonth);
        if(lastDays>currentDays){
            startTime=lastMonth.substring(0,7)+"-01";
            endTime=lastMonth.substring(0,7)+"-"+lastDays;
        }else{
            startTime=currentMonth.substring(0,7)+"-01";
            endTime=currentMonth.substring(0,7)+"-"+currentDays;
        }


        try{
            Date dBegin=sd2.parse(startTime);
            Date dEnd=sd2.parse(endTime);

            map.put(sd.format(dBegin),"");
            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime()))
            {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                map.put(sd.format(calBegin.getTime()),"");
                //System.out.println(sd.format(calBegin.getTime()));
            }
            //System.out.println(map);
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static List<String> getDayFullZero(String startTime,String endTime,List<HomeDTO> dataList){
        LinkedHashMap<String,String> map = FullZero.getDefaultYDay(startTime,endTime);

        for(HomeDTO dto:dataList){
            if(StringUtils.isNotBlank(dto.getDateTime()) && StringUtils.isNotBlank(dto.getPv())){
                map.put(dto.getDateTime().substring(8,10),dto.getPv());
            }
        }
        return map.values().stream()
                .collect(Collectors.toList());
    }




    public static LinkedHashMap<String,String> getDefaultYMonth(String startTime,String endTime){
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new SimpleDateFormat("MM");
        LinkedHashMap<String,String> map=new LinkedHashMap<>();

        try{
            Date dBegin=sd2.parse(startTime);
            Date dEnd=sd2.parse(endTime);

            map.put(sd.format(dBegin),"");
            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime()))
            {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.DAY_OF_YEAR, 1);
                map.put(sd.format(calBegin.getTime()),"");
                //System.out.println(sd.format(calBegin.getTime()));
            }
            //System.out.println(map);
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static List<String> getMonthFullZero(String startTime,String endTime,List<HomeDTO> dataList){
        LinkedHashMap<String,String> map = FullZero.getDefaultYMonth(startTime,endTime);

        for(HomeDTO dto:dataList){
            if(StringUtils.isNotBlank(dto.getDateTime()) && StringUtils.isNotBlank(dto.getPv())){
                map.put(dto.getDateTime().substring(5,7),dto.getPv());
            }
        }
        return map.values().stream()
                .collect(Collectors.toList());
    }

}
