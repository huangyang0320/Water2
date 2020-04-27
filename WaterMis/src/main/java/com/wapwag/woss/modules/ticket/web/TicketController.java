package com.wapwag.woss.modules.ticket.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.utils.PdfUtil;
import com.wapwag.woss.modules.biz.entity.WorkOrder;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.sys.entity.BootPage;
import com.wapwag.woss.modules.sys.entity.PumpHouse;
import com.wapwag.woss.modules.sys.service.PumpDeviceService;
import com.wapwag.woss.modules.ticket.Entity.ProductComponentData;
import com.wapwag.woss.modules.ticket.Entity.TicketComDto;
import com.wapwag.woss.modules.ticket.Entity.TicketDto;
import com.wapwag.woss.modules.ticket.service.ProductComponentService;
import com.wapwag.woss.modules.ticket.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 2020-02-09
 * yanxiaoneng
 */
@RestController
@RequestMapping("${adminPath}/ticket")
@SessionAttributes("user")
@Api(description = "工单相关API")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PumpDeviceService deviceService;

    @Autowired
    private ProductComponentService productComponentService;


    @RequestMapping("/getDept")
    @ResponseBody
    @ApiOperation(value = "获取工单处理的部门集合", httpMethod = "POST", response = TicketDto.class, notes = "")
    public Object getDept() {
        return ticketService.getDeptList();
    }

    @RequestMapping("/getUserListByDeptId/{deptId}")
    @ResponseBody
    @ApiOperation(value = "获取工单处理的部门集合", httpMethod = "POST", response = TicketDto.class, notes = "")
    public Object getUserListByDeptId(@PathVariable(value = "deptId") String deptId) {
        return ticketService.getUserListByDeptId(deptId);
    }

    @RequestMapping("/createWorkOrder")
    @ResponseBody
    @ApiOperation(value = "创建工单", httpMethod = "POST", response = WorkOrder.class, notes = "封装工单表单数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 500, message = "服务错误", response = WorkOrder.class)})
    public Object createWorkOrder(WorkOrder workOrder, User user) {
        Object msg;
        try {
            return ticketService.createWorkOrder(getTicketDto(workOrder, user));
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }


/*
    @RequestMapping("/getTicketList")
    @ResponseBody
    @ApiOperation(value = "获取工单集合", httpMethod = "POST", response =WorkOrder.class , notes = "通过类型/级别/内容获取工单集合")
    public Object getTicketList(TicketDto ticketDto, User user, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<TicketDto> pages = ticketService.findPage(new Page<TicketDto>(request, response), ticketDto);
        BootPage bootPage = new BootPage();
        bootPage.setTotal(pages.getCount());
        bootPage.setRows(pages.getList());
        return bootPage;

       // return  ticketService.findPage(new Page<TicketDto>(request, response), ticketDto);
    }*/


    @RequestMapping("/getHandleTicketList")
    @ResponseBody
    @ApiOperation(value = "获取代办工单集合", httpMethod = "POST", response = WorkOrder.class, notes = "通过类型/级别/内容获取工单集合")
    public Object getHandleTicketList(TicketDto ticketDto, User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        ticketDto.setIsMyHandle("1");//代办标记
        if (StringUtils.isBlank(ticketDto.getHandleUserId())) {
            ticketDto.setHandleUserId(user.getUserId());
        }
        String sortName = request.getParameter("sortName");
        String sortOrder = request.getParameter("sortOrder");

        Page<TicketDto> pages = ticketService.findPage(new Page<TicketDto>(request, response), ticketDto);

        //循环给对应的泵房,设备设置值
        List<TicketDto> list = pages.getList();
        List<PumpHouse> pumpList = deviceService.findPumpHouse(null);
        List<ProductComponentData> productList = productComponentService.findAllList();
        Map<String, String> pumpMap = new HashMap<String, String>();
        Map<String, String> productMap = new HashMap<String, String>();
        for (PumpHouse pump : pumpList) {
            pumpMap.put(pump.getPumpHouseId(), pump.getPumpHouseName());
        }
        for (ProductComponentData product : productList) {
            productMap.put(product.getId(), product.getComponentName());
        }

        for (TicketDto l : list) {
            String pumpId = l.getPumpId();
            String deviceCode = l.getDeviceCode();
            if (!TextUtils.isEmpty(pumpId)) {
                List<String> pumpIdAry = Arrays.asList(pumpId.split(","));
                String pumpStr = "";
                for (String pId : pumpIdAry) {
                    String value = pumpMap.get(pId);
                    pumpStr += value + ",";
                }
                if (!TextUtils.isEmpty(pumpStr)) {
                    pumpStr = pumpStr.substring(0, pumpStr.length() - 1);
                    l.setPumpName(pumpStr);
                }
            }

            if ("1".equals(l.getTicketType())) {
                l.setDeviceName1(l.getDeviceName());
            }

            if (!TextUtils.isEmpty(deviceCode)) {
                List<String> deviceCodeAry = Arrays.asList(deviceCode.split(","));
                String deviceCodeStr = "";

                for (String dId : deviceCodeAry) {
                    String value = productMap.get(dId);
                    deviceCodeStr += value + ",";
                }
                if (!TextUtils.isEmpty(deviceCodeStr)) {
                    deviceCodeStr = deviceCodeStr.substring(0, deviceCodeStr.length() - 1);
                    l.setDeviceName(deviceCodeStr);
                }
            }

        }
        BootPage bootPage = new BootPage();
        bootPage.setTotal(pages.getCount());
        bootPage.setRows(pages.getList());
        return bootPage;

        // return  ticketService.findPage(new Page<TicketDto>(request, response), ticketDto);
    }

    @RequestMapping("/getAllTicketListPage")
    @ResponseBody
    @ApiOperation(value = "获取所有工单集合", httpMethod = "POST", response = WorkOrder.class, notes = "通过类型/级别/内容获取工单集合")
    public Object getAllTicketListPage(TicketDto ticketDto, User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        ticketDto.setIsMyHandle("1");//代办标记
        if (StringUtils.isBlank(ticketDto.getHandleUserId())) {
            ticketDto.setHandleUserId(user.getUserId());
        }
        Page<TicketDto> pages = ticketService.findAllListPage(new Page<TicketDto>(request, response), ticketDto);
        //循环给对应的泵房,设备设置值
        List<TicketDto> list = pages.getList();
        List<PumpHouse> pumpList = deviceService.findPumpHouse(null);
        List<ProductComponentData> productList = productComponentService.findAllList();
        Map<String, String> pumpMap = new HashMap<String, String>();
        Map<String, String> productMap = new HashMap<String, String>();
        for (PumpHouse pump : pumpList) {
            pumpMap.put(pump.getPumpHouseId(), pump.getPumpHouseName());
        }
        for (ProductComponentData product : productList) {
            productMap.put(product.getId(), product.getComponentName());
        }

        for (TicketDto l : list) {
            String pumpId = l.getPumpId();
            String deviceCode = l.getDeviceCode();
            if (!TextUtils.isEmpty(pumpId)) {
                List<String> pumpIdAry = Arrays.asList(pumpId.split(","));
                String pumpStr = "";
                for (String pId : pumpIdAry) {
                    String value = pumpMap.get(pId);
                    pumpStr += value + ",";
                }
                if (!TextUtils.isEmpty(pumpStr)) {
                    pumpStr = pumpStr.substring(0, pumpStr.length() - 1);
                    l.setPumpName(pumpStr);
                }
            }

            if ("1".equals(l.getTicketType())) {
                l.setDeviceName1(l.getDeviceName());
            }

            if (!TextUtils.isEmpty(deviceCode)) {
                List<String> deviceCodeAry = Arrays.asList(deviceCode.split(","));
                String deviceCodeStr = "";

                for (String dId : deviceCodeAry) {
                    String value = productMap.get(dId);
                    deviceCodeStr += value + ",";
                }
                if (!TextUtils.isEmpty(deviceCodeStr)) {
                    deviceCodeStr = deviceCodeStr.substring(0, deviceCodeStr.length() - 1);
                    l.setDeviceName(deviceCodeStr);
                }
            }

        }
        String sortName = request.getParameter("sortName");
        String sortOrder = request.getParameter("sortOrder");
        BootPage bootPage = new BootPage();
        bootPage.setTotal(pages.getCount());
        bootPage.setRows(pages.getList());
        return bootPage;

        // return  ticketService.findPage(new Page<TicketDto>(request, response), ticketDto);
    }


    @RequestMapping("/signIn")
    @ResponseBody
    @ApiOperation(value = "接单", httpMethod = "POST", response = WorkOrder.class, notes = "通过工单号与接单人 接单")
    public Object signIn(User user, @RequestParam(value = "ticketId") String ticketId, @RequestParam(value = "userId", required = false) String userId) {
        if (StringUtils.isBlank(userId)) {
            userId = user.getUserId();
        }
        return ticketService.signIn(ticketId, userId, "3", "1");
    }


    @RequestMapping("/getTicketInfo")
    @ResponseBody
    @ApiOperation(value = "获取工单", httpMethod = "POST", response = WorkOrder.class, notes = "根据ticketI获取工单数据")
    public Object getTicketInfo(@RequestParam(value = "ticketId") String ticketId) {
        TicketDto ticketInfo = ticketService.getTicketInfo(ticketId);
        //如果是告警就把对应的设备值赋给它
        if ("1".equals(ticketInfo.getTicketType())) {
            ticketInfo.setDeviceName1(ticketInfo.getDeviceName());
        }
        List<PumpHouse> pumpList = deviceService.findPumpHouse(null);
        List<ProductComponentData> productList = productComponentService.findAllList();
        Map<String, String> pumpMap = new HashMap<String, String>();
        Map<String, String> productMap = new HashMap<String, String>();
        for (PumpHouse pump : pumpList) {
            pumpMap.put(pump.getPumpHouseId(), pump.getPumpHouseName());
        }
        for (ProductComponentData product : productList) {
            productMap.put(product.getId(), product.getComponentName());
        }
        String pumpId = ticketInfo.getPumpId();
        String deviceCode = ticketInfo.getDeviceCode();
        if (!TextUtils.isEmpty(pumpId)) {
            List<String> pumpIdAry = Arrays.asList(pumpId.split(","));
            String pumpStr = "";
            for (String pId : pumpIdAry) {
                String value = pumpMap.get(pId);
                pumpStr += value + ",";
            }
            if (!TextUtils.isEmpty(pumpStr)) {
                pumpStr = pumpStr.substring(0, pumpStr.length() - 1);
                ticketInfo.setPumpName(pumpStr);
            }
        }

        if (!TextUtils.isEmpty(deviceCode)) {
            List<String> deviceCodeAry = Arrays.asList(deviceCode.split(","));
            String deviceCodeStr = "";
            for (String dId : deviceCodeAry) {
                String value = productMap.get(dId);
                deviceCodeStr += value + ",";
            }
            if (!TextUtils.isEmpty(deviceCodeStr)) {
                deviceCodeStr = deviceCodeStr.substring(0, deviceCodeStr.length() - 1);
                ticketInfo.setDeviceName(deviceCodeStr);
            }
        }
        return ticketInfo;
    }

    @RequestMapping("/getTicketLogList")
    @ResponseBody
    @ApiOperation(value = "获取工单处理日志数据", httpMethod = "POST", response = WorkOrder.class, notes = "根据ticketI获取工单处理日志数据")
    public Object getTicketLogList(@RequestParam(value = "ticketId") String ticketId) {
        return ticketService.getTicketLogList(ticketId);
    }

    @RequestMapping("/handleWorkOrder")
    @ResponseBody
    @ApiOperation(value = "处理工单", httpMethod = "POST", response = WorkOrder.class, notes = "封装处理工单表单数据")
    public Object handleWorkOrder(@RequestBody TicketDto ticketDto, User user) {
        Object msg;
        try {
            com.wapwag.woss.modules.sys.entity.User u = new com.wapwag.woss.modules.sys.entity.User();
            u.setId(user.getUserId());
            ticketDto.setCreateDate(new Date());
            ticketDto.setUpdateDate(new Date());
            ticketDto.setCreateBy(u);
            ticketDto.setUpdateBy(u);
            return ticketService.handleWorkOrder(ticketDto);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;

    }


    @RequestMapping("/getPumpList")
    @ResponseBody
    @ApiOperation(value = "获取泵房集合", httpMethod = "POST", response = TicketComDto.class)
    public Object getPumpList(@RequestParam(value = "projectId", required = false) String projectId) {
        return ticketService.getPumpList(projectId);
    }

    @RequestMapping("/getDeviceList")
    @ResponseBody
    @ApiOperation(value = "获取对应泵房设备的集合", httpMethod = "POST", response = TicketComDto.class)
    public Object getDeviceList(@RequestParam(value = "id") String id) {
        return ticketService.getDeviceList(id);
    }


    /**
     * 转换对象
     *
     * @param workOrder
     * @param user
     * @return
     */
    private TicketDto getTicketDto(WorkOrder workOrder, User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TicketDto t = new TicketDto();
        com.wapwag.woss.modules.sys.entity.User u = new com.wapwag.woss.modules.sys.entity.User();
        u.setId(user.getUserId());
        try {
            t.setTicketId(workOrder.getTicketId());
            t.setTicketType(workOrder.getWorkType());
            t.setTitle(workOrder.getAlarmContent());
            t.setTicketLevel(workOrder.getAlarmLevel());
            t.setAddress(workOrder.getAddress());
            t.setChannel(workOrder.getChannel());
            if (workOrder.getAlarmTime() != null) {
                t.setEventTime(sdf.parse(workOrder.getAlarmTime()));
            }

            t.setDeviceCode(workOrder.getDeviceId());
            t.setPumpId(workOrder.getPhId());
            //t.setChannel(ss.getString(""));
            t.setReportName(user.getName());
            t.setStartTime(sdf.parse(workOrder.getPlanStartTime()));
            t.setEndTime(sdf.parse(workOrder.getPlanEndTime()));
            t.setCreateDate(new Date());
            t.setUpdateDate(new Date());
            t.setCreateBy(u);
            t.setUpdateBy(u);
            t.setStatus(workOrder.getStatus());
            t.setTicketReason(workOrder.getAlarmReason());
            t.setTicketDescription(workOrder.getPlanContent());
            t.setDeptId(workOrder.getDeptId());
            t.setMgName(workOrder.getMgName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }

    @RequestMapping("/getTicketId")
    @ResponseBody
    @ApiOperation(value = "获取工单编号", httpMethod = "POST", response = TicketComDto.class)
    public String getTicketId(TicketDto ticketDto) {
        String res = "";
        if (ticketDto != null) {
            String ticketType = ticketDto.getTicketType();
            res = ticketService.getTicketId(ticketType);
        }
        return res;
    }


    @RequestMapping("/getUserId")
    @ResponseBody
    @ApiOperation(value = "获取userId", httpMethod = "POST")
    public String getUserId(User user) {
        return user.getUserId();
    }


    @RequestMapping("/deleteTicket")
    @ResponseBody
    @ApiOperation(value = "删除工单", httpMethod = "POST", response = TicketDto.class)
    public boolean deleteTicket(TicketDto ticketDto) {
        ticketDto.setValidFlag("0");
        return ticketService.updateTicketInfo(ticketDto);
    }

    @RequestMapping("/updateTicketInfo")
    @ResponseBody
    @ApiOperation(value = "修改工单", httpMethod = "POST", response = TicketDto.class)
    public Object updateTicketInfo(WorkOrder workOrder, User user) {
        Object msg;
        try {
            return ticketService.updateWorkOrder(getTicketDto(workOrder, user));
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }

    @RequestMapping(value="/exportTicketsPdf", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "导出pdf版工单", httpMethod = "POST", response = TicketDto.class)
    public  void  importPdf(TicketDto ticketDto,HttpServletRequest request,HttpServletResponse response) {
        //根据Id查询数据
        if(ticketDto!=null&&!TextUtils.isEmpty(ticketDto.getIds())){
            String[] arys = ticketDto.getIds().split(",");
            List<String> idsList = Arrays.asList(arys);
            if(idsList!=null&&idsList.size()>0){
                ticketDto.setIdsList(idsList);
            }
        }
        //循环给对应的泵房,设备设置值
        List<TicketDto> list = ticketService.getTicketList(ticketDto);
        List<PumpHouse> pumpList = deviceService.findPumpHouse(null);
        List<ProductComponentData> productList = productComponentService.findAllList();
        List<TicketDto> deptList = ticketService.getDeptList();
        Map<String,Object> map=new HashMap<>();
        for(TicketDto l:deptList){
            map.put(l.getDeptId(),l);
        }
        Map<String, String> pumpMap = new HashMap<String, String>();
        Map<String, String> productMap = new HashMap<String, String>();
        for (PumpHouse pump : pumpList) {
            pumpMap.put(pump.getPumpHouseId(), pump.getPumpHouseName());
        }
        for (ProductComponentData product : productList) {
            productMap.put(product.getId(), product.getComponentName());
        }

        for (TicketDto l : list) {
            String pumpId = l.getPumpId();
            String deviceCode = l.getDeviceCode();
            if (!TextUtils.isEmpty(pumpId)) {
                List<String> pumpIdAry = Arrays.asList(pumpId.split(","));
                String pumpStr = "";
                for (String pId : pumpIdAry) {
                    String value = pumpMap.get(pId);
                    pumpStr += value + ",";
                }
                if (!TextUtils.isEmpty(pumpStr)) {
                    pumpStr = pumpStr.substring(0, pumpStr.length() - 1);
                    l.setPumpName(pumpStr);
                }
            }

            if ("1".equals(l.getTicketType())) {
                l.setDeviceName1(l.getDeviceName1());
            }

            if (!TextUtils.isEmpty(deviceCode)) {
                List<String> deviceCodeAry = Arrays.asList(deviceCode.split(","));
                String deviceCodeStr = "";

                for (String dId : deviceCodeAry) {
                    String value = productMap.get(dId);
                    deviceCodeStr += value + ",";
                }
                if (!TextUtils.isEmpty(deviceCodeStr)) {
                    deviceCodeStr = deviceCodeStr.substring(0, deviceCodeStr.length() - 1);
                    l.setDeviceName(deviceCodeStr);
                }
            }
            //设置部门和负责人
            TicketDto obj = (TicketDto)map.get(l.getDeptId());
            l.setDeptName(obj.getDeptName());
            l.setMgName(obj.getMgName());
        }
        PdfUtil pdfUtil=new PdfUtil();
        pdfUtil.CreatePdf(list,"工单PDF",response);
    }
}
