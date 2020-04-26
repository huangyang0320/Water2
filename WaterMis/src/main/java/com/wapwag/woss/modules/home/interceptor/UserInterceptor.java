package com.wapwag.woss.modules.home.interceptor;

import com.wapwag.woss.modules.home.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * User interceptor
 * Created by Administrator on 2016/9/20 0020.
 */
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object sessionObject = session.getAttribute("user");
        User currentUser = sessionObject instanceof User ? (User) sessionObject : null;
        String servletPath = request.getServletPath();

        //首页
        if(servletPath.contains("a/treeType/getTreeDataByType") || servletPath.contains("a/alarmStatController/indexAlarmSize")
           || servletPath.contains("themeStatNew.html") || servletPath.contains("a/homeV2") || servletPath.contains("themeNew.html"))  return true;

        //泵房地图&组态监控
        if(servletPath.contains("home-map-baiduNew.html") || servletPath.contains("a/monitor")
           || servletPath.contains("a/alarmStatController") || servletPath.contains("configuration.html")) return true;

        if (currentUser == null || StringUtils.isBlank(currentUser.getUserId())) {
            String loginUrl = request.getContextPath() + "/login.html";
            String ajaxHeader = request.getHeader("X-Requested-With");

            if (StringUtils.isNotBlank(ajaxHeader) && "XMLHttpRequest".equals(ajaxHeader)) {
                response.setHeader("x-user-status", "logout");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("<script>top.location='" + loginUrl + "'</script>");
            } else {
                response.sendRedirect(loginUrl);
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
