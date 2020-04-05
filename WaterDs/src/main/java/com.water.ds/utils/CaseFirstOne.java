package com.water.ds.utils;

import org.apache.commons.lang3.StringUtils;

public class CaseFirstOne {
    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if(StringUtils.isNotBlank(s)){
            if (Character.isLowerCase(s.charAt(0))) {
                return s;
            } else {
                return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
            }
        }
        return null;

    }


}
