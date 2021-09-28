package com.ruoyi.framework.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.dto.UserOnlineDTO;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.utils.RedisUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.AddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自定义侦听器的实现
 */
@Component
@Slf4j
public class MySaTokenListener implements SaTokenListener {

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, SaLoginModel loginModel) {
        /*这里的loginType在移动端登录的时候是login*/
        System.out.println(UserType.SYS_USER.getUserType());//sys_user:
        if (loginType.equals(UserType.SYS_USER.getUserType())) {
            /*系统用户才处理*/
            UserAgent userAgent = UserAgentUtil.parse(ServletUtils.getRequest().getHeader("User-Agent"));
            String ip = ServletUtils.getClientIP();
            SysUser user = SecurityUtils.getUser();
            String tokenValue = StpUtil.getTokenValue();
            UserOnlineDTO userOnlineDTO = new UserOnlineDTO()
                .setIpaddr(ip)
                .setLoginLocation(AddressUtils.getRealAddressByIP(ip))
                .setBrowser(userAgent.getBrowser().getName())
                .setOs(userAgent.getOs().getName())
                .setLoginTime(System.currentTimeMillis())
                .setTokenId(tokenValue)
                .setUserName(user.getUserName());
            if (StringUtils.isNotNull(user.getDept())) {
                userOnlineDTO.setDeptName(user.getDept().getDeptName());
            }
            RedisUtils.setCacheObject(Constants.ONLINE_TOKEN_KEY + tokenValue, userOnlineDTO);
            log.info("user doLogin, useId:{}, token:{}", loginId, tokenValue);
        }
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        RedisUtils.deleteObject(Constants.ONLINE_TOKEN_KEY + tokenValue);
        log.info("user doLogout, useId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doLogoutByLoginId(String loginType, Object loginId, String tokenValue, String device) {
        RedisUtils.deleteObject(Constants.ONLINE_TOKEN_KEY + tokenValue);
        log.info("user doLogoutByLoginId, useId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue, String device) {
        RedisUtils.deleteObject(Constants.ONLINE_TOKEN_KEY + tokenValue);
        log.info("user doReplaced, useId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 每次被封禁时触发
     */
    @Override
    public void doDisable(String loginType, Object loginId, long disableTime) {
    }

    /**
     * 每次被解封时触发
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId) {
    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
    }


}
