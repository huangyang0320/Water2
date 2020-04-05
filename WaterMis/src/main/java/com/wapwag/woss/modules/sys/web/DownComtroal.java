package com.wapwag.woss.modules.sys.web;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.sys.service.CountService;

/**
 * 下控制
 * 
 * @author gongll
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/controal")
public class DownComtroal {

	@Autowired
	private CountService countService;

	private static String downUrl = "";
	/**
	 * 实时设备参数统计
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "door/{deviceId}/{operType}")
	public String controalDoor(@PathVariable String deviceId, @PathVariable String operType) {
		String doorUUid = countService.getDoorUUId(deviceId);
		if (StringUtils.isEmp(doorUUid)) {
			return "1";
		}
		if (StringUtils.isEmp(downUrl)) {
			downUrl = countService.getDoorControlUrl();
		}
		HttpClient httpClient = new DefaultHttpClient();
		String url = downUrl + doorUUid + "/"+operType;
		// get method
		HttpGet httpGet = new HttpGet(url);
	  
	    //response
	    HttpResponse response = null;  
	    String msg = "操作失败，请稍后再试！";
	    try{
	        response = httpClient.execute(httpGet);
	        if ("0".equals(EntityUtils.toString(response.getEntity()))) {
	        	return "0";
			}
	        msg = EntityUtils.toString(response.getEntity());
	    }catch (Exception e) {} 
	    
		return msg;
	}
}
