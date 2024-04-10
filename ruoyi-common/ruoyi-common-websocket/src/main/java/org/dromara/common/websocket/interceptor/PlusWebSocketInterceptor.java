package org.dromara.common.websocket.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

import static org.dromara.common.websocket.constant.WebSocketConstants.LOGIN_USER_KEY;
import static org.dromara.common.websocket.constant.WebSocketConstants.TOKEN_ID_KEY;

/**
 * WebSocket握手请求的拦截器
 *
 * @author zendwang
 */
@Slf4j
public class PlusWebSocketInterceptor implements HandshakeInterceptor {

    /**
     * 握手前
     *
     * @param request    request
     * @param response   response
     * @param wsHandler  wsHandler
     * @param attributes attributes
     * @return 是否握手成功
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        String tokenId = LoginHelper.getTokenId();
        attributes.put(LOGIN_USER_KEY, loginUser);
        attributes.put(TOKEN_ID_KEY, tokenId);
        return true;
    }

    /**
     * 握手后
     *
     * @param request   request
     * @param response  response
     * @param wsHandler wsHandler
     * @param exception 异常
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
