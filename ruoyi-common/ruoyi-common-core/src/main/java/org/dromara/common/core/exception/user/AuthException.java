package org.dromara.common.core.exception.user;

import org.dromara.common.core.exception.base.BaseException;

import java.io.Serial;

/**
 * 认证异常类
 *
 * @author Michelle.Chung
 */
public class AuthException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AuthException(String code, Object... args) {
        super("auth", code, args, null);
    }

}
