package com.ruoyi.websocket;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/*
 *@WebSocket
 *@author ye
 *@create 2023/3/30 9:45
 */
@Slf4j
@Component
@ServerEndpoint("/webSocket/{token}")
public class WebSocket {
    /*
    * 存储session集合
    * */
    private static ConcurrentHashMap<Long, Session> sessionMap = new ConcurrentHashMap<>();
    /**
     * 存储用户集合
     */
    private static ConcurrentHashMap<Long, LoginUser> userMap = new ConcurrentHashMap<>();
    
    
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "token") String token){
        System.out.println("【webSocket连接成功】，token为：" + token);
        LoginUser loginUser = LoginHelper.getLoginUser(token);
        if (ObjectUtil.isNull(loginUser)) {
            log.error("token失效或无法解析");
        }
        setMap(session, loginUser);
        
    }
    
    private void setMap(Session session, LoginUser loginUser) {
        //获取用户id
        Long userId = loginUser.getUserId();
        //存储会话到会话集合
        sessionMap.put(userId, session);
        //存储用户信息到用户集合
        userMap.put(userId, loginUser);
        //获取会话大小 即在线人数
        int size = sessionMap.size();
        log.warn("用户连接:{},昵称：{}，当前在线人数为：{}", userId, loginUser.getUsername(), size);
    }
    
    @OnClose
    public void onClose(Session session){
        System.out.println("【webSocket退出成功】" );
        removeMap(session);
    }
    
    private void removeMap(Session session) {
        Long userId = getUserIdBySession(session);
        if (ObjectUtil.isNull(userId)) {
            return;
        }
        sessionMap.remove(userId);
        userMap.remove(userId);
    }
    
    private Long getUserIdBySession(Session session) {
        for (Long userId : sessionMap.keySet()) {
            if (sessionMap.get(userId).getId().equals(session.getId())) {
                return userId;
            }
        }
        return null;
    }
    
    @OnError
    public void onError(Session session, Throwable throwable){
        
        System.out.println("error:");
        throwable.printStackTrace();
    }
    
    @OnMessage
    public void onMessage(Session session, String message){
        System.out.println("【webSocket接收成功】内容为：" + message);
        LoginUser loginUser = getUserBySession(session);
        if (ObjectUtil.isNull(loginUser)) {
            return;
        }
        if (UserType.SYS_USER.getUserType().equals(loginUser.getUserType())) {
            //系统用户
            handlePCMsg(loginUser, message);
        }else {
            //app
            handleAPPMsg(loginUser, message);
        }
        
    }
    
    private void handleAPPMsg(LoginUser loginUser, String message) {
        log.info("APP用户：{},消息：", loginUser.getUsername(), message);
    }
    
    private void handlePCMsg(LoginUser loginUser, String message) {
        log.info("系统用户：{},消息：", loginUser.getUsername(), message);
    }
    
    
    private LoginUser getUserBySession(Session session) {
        Long userId = getUserIdBySession(session);
        if (ObjectUtil.isNull(userId)) {
            return null;
        }
        return userMap.get(userId);
    }
    
    
    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, Long toUserId) {
        log.info("发送消息到：{}，消息内容：{}", toUserId, message);
        if (ObjectUtil.isNull(toUserId) || StringUtils.isBlank(message)) {
            log.error("消息体不完整");
            return;
        }
        if (sessionMap.contains(toUserId)) {
            try {
                sendMessage(sessionMap.get(toUserId), message);
            } catch (Exception e) {
                log.error("发送给用户{}的消息出错", toUserId);
            }
        }else {
            //用户不在线
            log.error("用户：{}不在线", toUserId);
            //后续处理
        }
    }
    
    public static void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
