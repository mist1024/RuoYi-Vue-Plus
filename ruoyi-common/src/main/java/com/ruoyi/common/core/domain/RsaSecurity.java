package com.ruoyi.common.core.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 请求RSA数据加解密对象 rsa_security
 *
 * @author ruoyi
 * @date 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("rsa_security")
public class RsaSecurity extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 需要加密的接口
     */
    private String path;
    /**
     * 入参是否解密，默认不解密
     */
    private String inDecode;
    /**
     * 出参是否加密，默认加密
     */
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
