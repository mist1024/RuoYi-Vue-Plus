package com.ruoyi.gateway.constant;

/**
 * 错误消息常量
 * @author Wenchao Gong
 * @date 2021-10-18
 */
public interface ErrorMessageConstant {
    String AK_REQUIRE = "没有找到授权码[%s]";
    String CONTENT_TYPE_NOT_SUPPORTED = "请求ContentType[%s]不支持";
    String METHOD_NOT_SUPPORTED = "请求Method[%s]不支持";
    String RATE_LIMIT = "调用次数[>%d/%s]过多";
    String RULE_EXPIRED = "规则已过期[%s]";
    String RULE_NOT_EXIST = "授权码[%s]对应服务[%s]规则不存在";
}
