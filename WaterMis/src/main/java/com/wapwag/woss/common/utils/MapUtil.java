package com.wapwag.woss.common.utils;

import java.util.*;

public class MapUtil {
    /**
     * 按map的value升序排序
     *
     * @param map
     * @param top
     *
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAsc(
            Map<K, V> map, int top) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            if (top-- == 0) {
                break;
            }
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 按map value降序排序
     *
     * @param map
     * @param top
     *
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(
            Map<K, V> map, int top) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            if (top-- == 0) {
                break;
            }
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 按key排序
     *
     * @param unsort_map
     * @return
     */
    public static SortedMap<String, Object> mapSortByKey(
            Map<String, Object> unsort_map) {
        TreeMap<String, Object> result = new TreeMap<String, Object>();

        Object[] unsort_key = unsort_map.keySet().toArray();
        Arrays.sort(unsort_key);

        for (int i = 0; i < unsort_key.length; i++) {
            result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
        }
        return result.tailMap(result.firstKey());
    }


}
