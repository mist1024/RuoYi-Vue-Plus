package org.dromara.web.service;


import org.dromara.common.core.domain.model.LoginBody;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.web.domain.vo.LoginVo;

/**
 * 授权策略
 *
 * @author Michelle.Chung
 */
public interface IAuthStrategy {

    String BASE_NAME = "AuthStrategy";

    /**
     * 登录
     */
    static LoginVo login(LoginBody loginBody) {
        // 授权类型和客户端id
        String clientId = loginBody.getClientId();
        String grantType = loginBody.getGrantType();
        String beanName = grantType + BASE_NAME;
        if (!SpringUtils.containsBean(beanName)) {
            throw new ServiceException("授权类型不正确!");
        }
        IAuthStrategy instance = SpringUtils.getBean(beanName);
        instance.validate(loginBody);
        return instance.login(clientId, loginBody);
    }

    /**
     * 参数校验
     */
    void validate(LoginBody loginBody);

    /**
     * 参数校验
     */
    LoginVo login(String clientId, LoginBody loginBody);

}
