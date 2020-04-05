package com.wapwag.woss.modules.home.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wapwag.woss.modules.home.entity.HikQryInfo;
import com.wapwag.woss.modules.home.service.VideoService;

/**
 * 视频Controller
 * 
 * @author gongll
 * @version 2018-02-05 14:32
 *
 */
@RestController
@RequestMapping("${adminPath}/hik")
@SessionAttributes("user")
public class HKVideoController {
 
    private final VideoService videoService;

    @Autowired
	public HKVideoController(VideoService videoService){
		this.videoService = videoService;
	}
	
	@ResponseBody
	@RequestMapping("/hikBackResouseXML")
    public String hikBackResouseXML(@RequestBody HikQryInfo info) {
    	return videoService.hikBackResouseXML(info);
    }
	

	@ResponseBody
	@RequestMapping("/getResouseByCode/{code}")
    public String getResouseByCode(@PathVariable String code){
		return videoService.hikRealSourseXML(code);
    }
	@ResponseBody
	@RequestMapping("/getAppKeyByCode//{code}")
    public String getAppKeyByCode(@PathVariable String code){
		String appKey = videoService.hikAppKey(code);
		return appKey;
    }
    
	@ResponseBody
	@RequestMapping("/getResouseByCode8700/{code}")
    public String getResouseByCode8700(@PathVariable String code){
		return videoService.getVideoUrlByVideoUUid(code);
    }
	
	@ResponseBody
	@RequestMapping("/getBackResouseByCode8700/{code}")
    public String getBackResouseByCode8700(@PathVariable String code){
		return videoService.getBackResouseByCode8700(code);
    }
}
