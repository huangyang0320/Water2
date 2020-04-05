package com.wapwag.woss.modules.home.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wapwag.woss.common.utils.StringUtils;



/**
 * 视频地址临时池
 * @author gongll
 *@date 2018年4月26日
 */
@Component
public class VideoPool {

	public static Map<String, VideoTemp> videoResources = new HashMap<String, VideoTemp>();
	
	private static final long  ONE_HOUR = 60*60*1000;
	
	public static void addVide(String code,String videoXml){
		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(videoXml)) {
			return;
		}
		VideoTemp info = new VideoTemp();
		info.setVideoTime(System.currentTimeMillis());
		info.setVideoXml(videoXml);
		videoResources.put(code, info);
	}
	
	public static String getVideXml(String code){
		if (null != videoResources.get(code)) {
			videoResources.get(code).setVideoTime(System.currentTimeMillis());
			return videoResources.get(code).getVideoXml();
		}
		return "";
	}
	
	/**
	 * 定时清理过期的数据
	 * 
	 */
	@Scheduled(cron = "01 0/10 * * * ?")
	public void clearOutTimeVideo()  {
		Iterator<String> iterator = videoResources.keySet().iterator();
		String code;
		long nowTime = System.currentTimeMillis();
		while (iterator.hasNext()) {
			code = iterator.next();
			if (null == videoResources.get(code) 
					|| nowTime - videoResources.get(code).getVideoTime() > ONE_HOUR) {
				videoResources.remove(code);
			}
		}
	}
}
