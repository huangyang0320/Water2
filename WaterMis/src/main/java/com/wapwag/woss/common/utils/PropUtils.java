package com.wapwag.woss.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtils {
	protected static final String REDIS_RESOURCES_PROPERTIES_FILE_NAME = "cache.properties";
	
	/**
	 * 取得所有属性值
	 * 
	 * @param propertiesFileName
	 * @return
	 * @throws IOException
	 */
	protected static Properties getProperties(String propertiesFileName) throws IOException {
		Properties prop = new Properties();
		InputStream is = PropUtils.class.getClassLoader().getResourceAsStream(propertiesFileName);

		try {
			prop.load(is);
		} catch (IOException e) {
			throw e;
		}

		return prop;
	}

	/**
	 * 通用方法获取Properties 文件
	 * @param propertiesName
	 * @param key
	 * @return
	 */
	public static String getPropertiesString(String propertiesName, String key){
		try {
			return getProperties(propertiesName).getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 取得redis文件
	 *
	 * @return
	 * @throws IOException
	 */
	public static Properties getRedisResourcesProperties() {
		try {
			return getProperties(REDIS_RESOURCES_PROPERTIES_FILE_NAME);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得redis文件的属性值
	 *
	 * @return
	 * @throws IOException
	 */
	public static String getRedisValue(String key) {
		Properties properties = getRedisResourcesProperties();
		return properties.getProperty(key);
	}
	
}
