package org.dromara.web.listener;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import org.dromara.web.domain.event.GiteeStarEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Gitee Star事件 监听处理
 *
 * @author Lion Li
 */
@Component
public class GiteeStarListener {

    /**
     * 如用户使用 gitee 登录顺手 star 给作者一点支持 拒绝白嫖
     */
    @Async
    @EventListener
    public void star(GiteeStarEvent giteeStarEvent) {
        HttpUtil.createRequest(Method.PUT, "https://gitee.com/api/v5/user/starred/dromara/RuoYi-Vue-Plus")
            .formStr(MapUtil.of("access_token", giteeStarEvent.getAccessToken()))
            .executeAsync();
        HttpUtil.createRequest(Method.PUT, "https://gitee.com/api/v5/user/starred/dromara/RuoYi-Cloud-Plus")
            .formStr(MapUtil.of("access_token", giteeStarEvent.getAccessToken()))
            .executeAsync();
    }
}
