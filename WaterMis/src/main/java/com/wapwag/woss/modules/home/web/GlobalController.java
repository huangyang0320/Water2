package com.wapwag.woss.modules.home.web;

import com.wapwag.woss.modules.home.interceptor.WebSocketEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Global controller
 * Created by Administrator on 2016/9/24 0024.
 */
@Controller
public class GlobalController {

    @RequestMapping("${adminPath}")
    public String index(HttpSession session) {
        String map = (String) session.getAttribute("map");
        if ("baidu".equals(map)) {
            return "redirect:index-baidu.html";
        } else {
            return "redirect:index-arcgis.html";
        }
    }

    @RequestMapping("/push")
    @ResponseBody
    public String pushMessage(String message) {
        WebSocketEndpoint.sendMessage(message);
        return "推送成功";
    }

}
