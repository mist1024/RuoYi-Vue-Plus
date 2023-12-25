package org.dromara.common.satoken.handler;

import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.satoken.utils.LoginHelper;

/**
 * @Author RebelTinker
 * @Date 2023/12/25 09:37
 **/
public class UserInfoThreadHandler {

    private static final ThreadLocal<LoginUser> userThreadLocal = new ThreadLocal<>();

    /**
     * 添加当前登录用户方法
     */
    public static void addCurrentUser() {

        //登录对象从这里获取然后set进ThreadLocal
        LoginUser loginUser = LoginHelper.getLoginUser();

        userThreadLocal.set(loginUser);
    }

    /**
     * 获取用户信息
     *
     * @return LoginUser
     */
    public static LoginUser getCurrentUser() {
        return userThreadLocal.get();
    }

    /**
     * 防止内存泄漏
     */
    public static void remove() {
        userThreadLocal.remove();
    }
}
