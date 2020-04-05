package com.wapwag.woss.modules.home.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapSort {

	/**
    * 使用 Map按key进行排序
    * @param map
    * @return
    */
   public synchronized static Map<String,List<Map<String, String>>> sortMapByKey(Map<String,List<Map<String, String>>> map) {
       if (map == null || map.isEmpty()) {
           return null;
       }

       Map<String,List<Map<String, String>>> sortMap = new TreeMap<String,List<Map<String, String>>>(
               new MapKeyComparator());

       sortMap.putAll(map);

       return sortMap;
   }
}
class MapKeyComparator implements Comparator<String>{

    @Override
    public int compare(String str1, String str2) {
        
        return str2.compareTo(str1);
    }
}