//*********************************************************************
//系统名称：带电工器具智能库房系统
//Copyright(C)2000-2008 SURRISE 
//Branch. All rights reserved. 
//版本信息：TRAWMS-V1.0 
//作者：牟际生                      $权重：100%     $手机：13770703048#
//版本                                                    日期                          作者          变更记录
//TRAWMS-V1.0        2017年8月17日      牟际生　         新建
//*********************************************************************
package com.water.ds.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 *<pre>
 * 概述:  读取配置参数的方法
 * 功能: 
 * 作者: 颜小能
 * 创建时间: 2020年3月12日
 * 类调用特殊情况: 无
 * 修改内容
 * 修改人：
 * 修改时间：
 *</pre>
 */
public class PropertyUtil {
	private static final Logger log=Logger.getLogger(PropertyUtil.class);

    private static Properties props;
    static{
        loadProps();
    }

    synchronized static private void loadProps(){
        props = new Properties();
        InputStream in = null;
        try {
        	/* 第一种，通过类加载器进行获取properties文件流 */
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("jdbcsqlsrver.properties");
            /* 第二种，通过类进行获取properties文件流*/
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            //props.load(in);
            props.load(new InputStreamReader(PropertyUtil.class.getClassLoader().getResourceAsStream("jdbcsqlsrver.properties"), "UTF-8"));
        } catch (FileNotFoundException e) {
            log.error("jdbcsqlsrver.properties文件未找到");
        } catch (IOException e) {
            log.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("jdbcsqlsrver.properties文件流关闭出现异常");
            }
        }
        log.info("加载properties文件内容完成...........");
        log.info("properties文件内容：" + props);
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }

    public static void main(String[] args) {
        System.out.println(getProperty("jdbc.url"));
    }
}
