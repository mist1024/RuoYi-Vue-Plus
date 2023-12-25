package org.dromara.common.web.interceptor;

import cn.hutool.core.util.ObjectUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.UserConstants;
import org.dromara.common.core.context.ThreadLocalHolder;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户信息拦截器
 *
 * @author Michelle.Chung
 */
@Slf4j
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ObjectUtil.isNotNull(ThreadLocalHolder.get(UserConstants.LOGIN_USER_KEY))) {
            // 清除用户信息
            ThreadLocalHolder.remove(UserConstants.LOGIN_USER_KEY);
        }
    }

}
