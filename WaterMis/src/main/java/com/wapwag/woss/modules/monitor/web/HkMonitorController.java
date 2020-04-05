package com.wapwag.woss.modules.monitor.web;

import com.wapwag.woss.common.hksdk.service.IGetResourceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${adminPath}/hk/monitor")
public class HkMonitorController {

    @Autowired
    private IGetResourceListService getResourceListServiceImpl;
    @RequestMapping("/getDeviceList")
    @ResponseBody
    public Object getDeviceList() {
        return getResourceListServiceImpl.getDeviceList();
    }

}
