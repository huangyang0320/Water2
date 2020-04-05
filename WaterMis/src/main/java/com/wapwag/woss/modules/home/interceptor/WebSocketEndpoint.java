package com.wapwag.woss.modules.home.interceptor;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wapwag.woss.common.mapper.JsonMapper;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Web socket endpoint
 * Created by Administrator on 2016/10/2.
 */
public class WebSocketEndpoint extends TextWebSocketHandler {

    private static final List<WebSocketSession> sessionList = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(session.getAttributes().get("sessionId"));
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
        System.out.println(session.getAttributes().get("sessionId"));
        session.sendMessage(new TextMessage("主动推送消息成功"));
    }

    public static void sendMessage(String message) {
        message = String.format("{message:%s,status:success}", message);
        for (WebSocketSession session: sessionList) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
