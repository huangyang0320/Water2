package com.wapwag.woss.modules.ticket.web;


import com.wapwag.woss.common.web.BaseController;
import com.wapwag.woss.modules.biz.entity.ProductComponent;
import com.wapwag.woss.modules.ticket.Entity.ProductComponentData;
import com.wapwag.woss.modules.ticket.service.ProductComponentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${adminPath}/productComponent")
@SessionAttributes("user")
@Api(description = "工单配件相关API")
public class ProductComponentController extends BaseController {

	@Autowired
	private ProductComponentService productComponentService;

	@RequestMapping("/getProductList")
	@ResponseBody
	@ApiOperation(value = "获取对应泵房设备配件的集合", httpMethod = "POST", response = ProductComponentData.class )
	public Object getDeviceList(HttpServletRequest request,HttpServletResponse response){
		return productComponentService.findAllList();
	}

	@RequestMapping("/getProductReasonList/{deviceIds}")
	@ResponseBody
	@ApiOperation(value = "获取对应泵房设备配件的维保原因/方案集合", httpMethod = "POST", response = ProductComponent.class )
	public List<ProductComponentData> findProductComponentReasonListById(@PathVariable(value = "deviceIds") String deviceIds){
		List<String> list=new ArrayList<>();
		if(StringUtils.isNotBlank(deviceIds)){
			String[] strs=deviceIds.split(",");
			for(String id: strs){
				list.add(id);
			}
		}
		return productComponentService.findProductComponentReasonListById(list);
	}


}
