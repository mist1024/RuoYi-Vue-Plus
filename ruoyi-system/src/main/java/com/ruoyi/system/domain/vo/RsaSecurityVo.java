package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 请求RSA数据加解密视图对象 rsa_security
 *
 * @author ruoyi
 * @date 2023-05-17
 */
@Data
@ExcelIgnoreUnannotated
public class RsaSecurityVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 需要加密的接口
     */
    @ExcelProperty(value = "需要加密的接口")
    private String path;

    /**
     * 入参是否解密，默认不解密
     */
    @ExcelProperty(value = "入参是否解密，默认不解密", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String inDecode;

    /**
     * 出参是否加密，默认加密
     */
    @ExcelProperty(value = "出参是否加密，默认加密")
    private String outEncode;

    /**
     * 公钥
     */
    @ExcelProperty(value = "公钥")
    private String publicKey;

    /**
     * 私钥
     */
    @ExcelProperty(value = "私钥")
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
