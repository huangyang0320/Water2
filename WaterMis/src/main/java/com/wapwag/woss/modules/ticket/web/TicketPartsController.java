package com.wapwag.woss.modules.ticket.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.modules.biz.entity.WorkOrder;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.sys.entity.BootPage;
import com.wapwag.woss.modules.ticket.Entity.TicketDto;
import com.wapwag.woss.modules.ticket.Entity.TicketParts;
import com.wapwag.woss.modules.ticket.service.TicketPartsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ---------------------------
 *  (TicketPartsController)         
 * ---------------------------
 */
@RestController
@RequestMapping("${adminPath}/ticketParts")
@SessionAttributes("user")
@Api(description = "工单相关API")
public class TicketPartsController {

	@Autowired
	private TicketPartsService ticketPartsService;

	@RequestMapping("/getTicketPartsList")
	@ResponseBody
	@ApiOperation(value = "获取工单相关配件集合", httpMethod = "POST", response =TicketParts.class , notes = "通过工单ID获取工单配件集合")
	public List<TicketParts> getTicketPartsList(TicketParts ticketParts, User user, Model model, HttpServletRequest request, HttpServletResponse response){
		Page<TicketParts> pages = ticketPartsService.findPage(new Page<TicketParts>(request, response), ticketParts);

		return pages.getList();
	}

	@RequestMapping("/addOrUpdateTicketParts")
	@ResponseBody
	@ApiOperation(value = "添加修改工单配件记录", httpMethod = "POST", response =TicketParts.class , notes = "添加修改工单配件记录")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "成功"),
			@ApiResponse(code = 500, message = "服务错误", response = TicketParts.class)})
	public Object addOrUpdateTicketParts(@RequestBody TicketParts[] ticketParts, User user){
		Object msg;
		try {
			for(int i=0;i<ticketParts.length;i++){
				ticketPartsService.addOrUpdate(ticketParts[i],user);
			}
			JSONObject result=new JSONObject();
			result.put("code","200");
			result.put("status","success");
			result.put("message","配件记录编辑成功!");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	@RequestMapping("/deleteTicketParts")
	@ResponseBody
	@ApiOperation(value = "删除工单配件记录", httpMethod = "POST", response =TicketParts.class , notes = "删除工单配件记录")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "成功"),
			@ApiResponse(code = 500, message = "服务错误", response = TicketParts.class)})
	public Object deleteTicketParts(TicketParts ticketParts, User user){
		Object msg;
		try {
			return ticketPartsService.deleteTicket(ticketParts,user);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

}
