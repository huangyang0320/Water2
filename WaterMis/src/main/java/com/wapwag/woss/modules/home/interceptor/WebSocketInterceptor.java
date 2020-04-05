package com.wapwag.woss.modules.home.interceptor;

import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Spring integrate with websocket
 * Created by Administrator on 2016/10/2.
 */
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("Websocket开始握手");
        if (request instanceof ServletServerHttpRequest) {
            String sessionId = ((ServletServerHttpRequest) request).getServletRequest().getRequestedSessionId();
            attributes.put("sessionId", sessionId);
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        System.out.println("Websocket连接建立成功");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
