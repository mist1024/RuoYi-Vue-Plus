package com.ruoyi.gateway.constant;

/**
 * 错误消息常量
 * @author Wenchao Gong
 * @date 2021-10-18
 */
public interface ErrorMessageConstant {
    String AK_REQUIRE = "没有找到授权码";
    String CONTENT_TYPE_NOT_SUPPORTED = "请求ContentType不支持";
    String METHOD_NOT_SUPPORTED = "请求Method不支持";
    String RATE_LIMIT = "调用次数过多";
    String RULE_EXPIRED = "规则已过期";
    String RULE_NOT_EXIST = "规则不存在";
}
