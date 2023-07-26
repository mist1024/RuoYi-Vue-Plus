package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 请求RSA数据加解密业务对象 rsa_security
 *
 * @author ruoyi
 * @date 2023-05-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class RsaSecurityBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;
    /**
     * 需要加密的接口
     */
    @NotBlank(message = "需要加密的接口不能为空", groups = { AddGroup.class, EditGroup.class })
    private String path;

    /**
     * 入参是否解密，默认不解密
     */
    @NotNull(message = "入参是否解密，默认不解密不能为空", groups = { AddGroup.class, EditGroup.class })
    private String inDecode;

    /**
     * 出参是否加密，默认加密
     */
    @NotNull(message = "出参是否加密，默认加密不能为空", groups = { AddGroup.class, EditGroup.class })
    private String outEncode;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 接口限制请求
     */
    private String restricted;


}
