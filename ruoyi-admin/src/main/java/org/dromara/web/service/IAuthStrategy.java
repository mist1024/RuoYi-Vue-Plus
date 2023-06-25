package org.dromara.web.service;


import org.dromara.common.core.domain.model.LoginBody;
import org.dromara.web.domain.vo.LoginVo;

/**
 * 授权策略
 *
 * @author Michelle.Chung
 */
public interface IAuthStrategy {

    /**
     * 参数校验
     */
    void validate(LoginBody loginBody);

    /**
     * 登录
     */
    LoginVo login(String clientId, LoginBody loginBody);

}
