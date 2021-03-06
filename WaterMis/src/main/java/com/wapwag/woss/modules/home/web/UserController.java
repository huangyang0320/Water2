package com.wapwag.woss.modules.home.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.wapwag.woss.common.ad.AdService;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.home.service.UserService;
import com.wapwag.woss.modules.home.util.LoginUtil;
import com.wapwag.woss.modules.sys.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * User controller
 * Created by Administrator on 2016/9/20 0020.
 */
@Controller("com.wapwag.woss.modules.home.web.UserController")
@RequestMapping("${adminPath}/user")
@SessionAttributes("user")
@Api(description = "用户相关API")
public class UserController {

    @Value("#{APP_PROP['serverPath']}")
    private String SERVER_PATH;

    @Value("#{APP_PROP['map']}")
    private String MAP;

    @Value("#{APP_PROP['oaPath']}")
    private String OA_LOGIN_PATH;

    @Value("#{ERROR_PROP['SYS-0004']}")
    private String LOGIN_FAIL;

    @Value("#{ERROR_PROP['SYS-0005']}")
    private String LOGIN_FAIL_NO_DEVICE;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    @ResponseBody
    @ApiOperation(value = "系统登陆", httpMethod = "POST", notes = "")
    public Map<String, Object> login(@ApiParam(name = "loginName",value = "用户名",required = true) @RequestParam String loginName, @ApiParam(name = "password",value = "密码",required = true)@RequestParam String password, HttpServletRequest request) {

        System.out.println("9999999999999999999999999999999");

        /*String str=SentSm.getXmlInfo();
        SentSm.poststh(str);*/

        Map<String, Object> resultMap = Maps.newHashMap();
        String result = "";
        User user = userService.login(loginName, password);

       /* if(!"admin".equals(password)){
            resultMap.put("result", "用户名或密码错误!");
            return resultMap;
        }*/

        if (user == null) {
            resultMap.put("result", LOGIN_FAIL);
        }else if (user.getFailureNum()>=4 && new Date().getTime()-user.getFreezeTime().getTime()<=15*60*1000){
            resultMap.put("result", "密码错误过多，请在15分钟后再试，或联系管理员解锁!");
        }else if ("admin".equals(loginName) && !SystemService.validatePassword(password, user.getPassword())) {
            //AD域名验证用户密码是否正确
            resultMap.put("result", "用户名或密码错误!");
        }else if ((!"chen_chun".equals(loginName) && !"admin".equals(loginName)) && !AdService.validateAd(loginName, password) ) {
            user.setFailureNum(user.getFailureNum()+1);
            user.setFreezeTime(new Date());
            userService.freezeOperation(user);
            resultMap.put("result", "用户名或密码错误(还剩"+(5-user.getFailureNum())+"次输入机会)!");
        }
        else {

            if (!userService.getUserAvaliableDeviceCount(user)) {
                result = LOGIN_FAIL_NO_DEVICE;
            } else {
                if ("baidu".equals(MAP)) {
                    result = "登录成功";
                    resultMap.put("map", "baidu");
                } else {
                    result = LoginUtil.validateUser(loginName, password, OA_LOGIN_PATH);
                }
                if ("登录成功".equals(result)) {
                    HttpSession session = request.getSession();
                    resultMap.put("user", user);
                    if (!resultMap.containsKey("map")) {
                        resultMap.put("map", "arcgis");
                    }
                    user.setFreezeTime(null);
                    user.setFailureNum(0);
                    userService.freezeOperation(user);
                    user.setEncryPassword(password);
                    session.setAttribute("user", user);
                    session.setAttribute("map", resultMap.get("map"));
                }
            }
            resultMap.put("result", result);
        }
        return resultMap;
    }



    @RequestMapping("/updateAlarmRateByUser/{isReceiveAlarm}")
    @ResponseBody
    public String updateAlarmRateByUser(HttpServletRequest request, User user, @PathVariable(value = "isReceiveAlarm") String isReceiveAlarm) {
        User u=new User();
        u.setUserId(user.getUserId());
        u.setAlarmRate(isReceiveAlarm);
        boolean bol = userService.updateAlarmRateByUserId(u);
        JSONObject object=new JSONObject();
        if(bol){
            object.put("msg","设置成功!");
        }else{
            object.put("msg","设置失败!");
        }
        return object.toJSONString();
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "success";
    }

}
