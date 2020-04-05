package com.wapwag.woss.modules.biz.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wapwag.woss.modules.biz.entity.AccListLifeDto;
import com.wapwag.woss.modules.biz.service.LifepredictionService;

@RestController
@RequestMapping(value = "${adminPath}/biz/lifeprediction")
public class LifepredictionController {
	
	@Autowired
	private LifepredictionService lifepredictionService;
	
	@RequestMapping("/initData/{deviceId}")
	public Map<String,Object> initData(@PathVariable("deviceId")String deviceId) {
		return lifepredictionService.initData(deviceId);
	}
	
	@RequestMapping("/getComponentLife/{deviceId}")
	public AccListLifeDto getAccLifes(@PathVariable("deviceId") String deviceId,@RequestParam("componentId") String componentId) {
		return lifepredictionService.getComponentLife(deviceId, componentId);
	}
	
	@RequestMapping("/getDeviceLifes/{deviceId}")
	public AccListLifeDto getDeviceLifes(@PathVariable("deviceId") String deviceId) {
		return lifepredictionService.getDeviceLife(deviceId);
	}
}
