package com.wapwag.woss.modules.home.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.wapwag.woss.modules.home.entity.HundredVO;

public class MapSortUtil {
	
	
	
	/**
     * 使用 Map按value进行排序
     * @param map
     * @return
     */
    public static Map<HundredVO,Double> sortMapByValue(Map<HundredVO,Double> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<HundredVO,Double> sortedMap = new LinkedHashMap<HundredVO,Double>();
        List<Map.Entry<HundredVO,Double>> entryList = new ArrayList<Map.Entry<HundredVO,Double>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<HundredVO,Double>> iter = entryList.iterator();
        Map.Entry<HundredVO,Double> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
}

class MapValueComparator implements Comparator<Map.Entry<HundredVO,Double>> {

    @Override
    public int compare(Entry<HundredVO,Double> me1, Entry<HundredVO,Double> me2) {

        return me1.getValue().compareTo(me2.getValue());
    }
}