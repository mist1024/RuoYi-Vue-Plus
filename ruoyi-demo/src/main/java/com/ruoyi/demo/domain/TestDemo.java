package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.EncryptField;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.enums.AlgorithmType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试单表对象 test_demo
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("test_demo")
public class TestDemo extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 排序号
     */
    @OrderBy(asc = false, sort = 1)
    private Integer orderNum;

    /**
     * key键
     */
    @EncryptField(algorithm=AlgorithmType.SM2, privateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgZSlOvw8FBiH+aFJWLYZP/VRjg9wjfRarTkGBZd/T3N+gCgYIKoEcz1UBgi2hRANCAAR5DGuQwJqkxnbCsP+iPSDoHWIF4RwcR5EsSvT8QPxO1wRkR2IhCkzvRb32x2CUgJFdvoqVqfApFDPZzShqzBwX", publicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEeQxrkMCapMZ2wrD/oj0g6B1iBeEcHEeRLEr0/ED8TtcEZEdiIQpM70W99sdglICRXb6KlanwKRQz2c0oaswcFw==")
    private String testKey;

    /**
     * 值
     */
    @EncryptField(algorithm=AlgorithmType.AES, password = "10rfylhtccpuyke5")
    private String value;

    /**
     * 版本
     */
    @Version
    private Long version;

    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;

}
