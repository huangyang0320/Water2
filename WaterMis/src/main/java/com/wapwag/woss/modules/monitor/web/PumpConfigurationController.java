package com.wapwag.woss.modules.monitor.web;

import static com.wapwag.woss.modules.config.ErrorCode.CTRL_ERROR;
import static com.wapwag.woss.modules.config.RestResult.fail;
import static com.wapwag.woss.modules.config.RestResult.success;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.wapwag.woss.modules.monitor.pumpNode.PumpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.wapwag.woss.common.web.BaseController;
import com.wapwag.woss.modules.biz.entity.SysDict;
import com.wapwag.woss.modules.config.ErrorCode;
import com.wapwag.woss.modules.config.RestResult;
import com.wapwag.woss.modules.home.service.AccessControlService;
import com.wapwag.woss.modules.monitor.pumpNode.CtrlDTO;
import com.wapwag.woss.modules.monitor.service.PumpConfigurationService;

/**
 * @author zx
 * @version 2018-05-21
 * 组态
 */
@RestController
@RequestMapping("${adminPath}/monitor/configuration")
@SessionAttributes("user")
public class PumpConfigurationController extends BaseController {
	
    @Autowired
    private PumpConfigurationService pumpConfigurationService;
    
    @Autowired
    private AccessControlService accessControlService;

    /**
     * 获取泵房相关组态和点位
     * @param phid
     * @param response
     * @return
     */
    @RequestMapping(value = {"getPumpNode"})
    public String getPumpNode(String phid, HttpServletResponse response) {
        String pumpNode=pumpConfigurationService.getPumpNode(phid);
        String req=renderString(response,pumpNode,"application/json");
        return req;
    }

    @RequestMapping(value = {"getServiceSetValues"})
    public String getServiceSetValues(@RequestBody PumpService pumpService, HttpServletResponse response){
        String serviceValues= pumpConfigurationService.getServiceSetValues(pumpService);
        String req=renderString(response,serviceValues,"application/json");
        return req;
    }

    /**
     * 获取组态点位相关实时数据
     * @param phid
     * @param response
     * @return
     */
    @RequestMapping(value = {"getServiceValues"})
    public String getServiceValues(String phid, HttpServletResponse response) {
        String serviceValues= pumpConfigurationService.getServiceValues(phid);
        String req=renderString(response,serviceValues,"application/json");
        return req;
    }

    /**
     * 调取远程组态下控
     * @param ctrlDTO
     * @param user
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "static-access" })
	@RequestMapping(value = {"remoteCtrl"})
    public RestResult remoteCtrl(@RequestBody CtrlDTO ctrlDTO,com.wapwag.woss.modules.home.entity.User user) throws IOException {
        String userId =user.getUserId();
        String delFlag =user.DEL_FLAG_NORMAL;
        if("DoorAccess".equals(ctrlDTO.getPointCode())){
            String state=accessControlService.ctrlHKAccess(ctrlDTO.getDeviceId(), ctrlDTO.getPointValue());
            if(state.equals("1"))return success(null);
            return fail(CTRL_ERROR);
        }
        List<SysDict> selectSysDictByType = pumpConfigurationService.selectSysDictByType("control_chanle");
		SysDict sysDict = selectSysDictByType.get(0);
		if(sysDict.getValue().equals("1")){//下控渠道 1 xuzhou 2dashatian 3luzhou
			return pumpConfigurationService.remoteCtrl(ctrlDTO,userId,delFlag);
		}
		if(sysDict.getValue().equals("2")){
			//return remoteCodeService.remoteCtrl(ctrlDTO,ipAddress);
		}
		if(sysDict.getValue().equals("3")){
			return pumpConfigurationService.remoteCtrlByluzhou(ctrlDTO, userId);
		}
		return fail(ErrorCode.CTRL_CHANEL_EMPTY);
    }
    
    /**
     * 组态下控展示
     * @param deviceCode
     * @return
     */
    @RequestMapping(value = {"getCtrlParms"})
    public String getCtrlParms(String deviceCode,String pumpId) {
        return pumpConfigurationService.getCtrlParms(deviceCode,pumpId);
    }

    /**
     * 组态门禁展示
     * @param pumpId
     * @return
     */
    @RequestMapping(value = {"entranceGuard"})
    public String entranceGuard(String pumpId,HttpServletResponse response) {
        String door=new Gson().toJson(accessControlService.getAccessDataByPumpId(pumpId));
        String req=renderString(response,door,"application/json");
        return req;
    }
}
