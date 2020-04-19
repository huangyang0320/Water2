package com.wapwag.woss.modules.ticket.web;


import com.wapwag.woss.common.web.BaseController;
import com.wapwag.woss.modules.ticket.Entity.ProductComponentData;
import com.wapwag.woss.modules.ticket.service.ProductComponentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


}
