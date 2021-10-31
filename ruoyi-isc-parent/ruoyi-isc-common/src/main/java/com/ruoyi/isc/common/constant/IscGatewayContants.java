package com.ruoyi.isc.common.constant;

import com.ruoyi.isc.common.utils.beans.IscRule;
import org.redisson.client.codec.Codec;
import org.redisson.codec.TypedJsonJacksonCodec;

/**
 * Isc Gateway 公共常量
 * @author Wenchao Gong
 * @date 2021-10-30
 */
public interface IscGatewayContants {

    /** AccessKey 默认名称 */
    String ACCESS_KEY_NAME_DEFAULT = "ak";

    /** 路由刷新通知主题 */
    String TOPIC_GATEWAY_REFRESH_ROUTE = "TOPIC_GATEWAY_REFRESH_ROUTE";

    /** 规则刷新通知主题 */
    String TOPIC_GATEWAY_RULE = "TOPIC_GATEWAY_RULE";

    /** AccessKeyName 路由配置名称 */
    String CONFIG_ACCESS_KEY_NAME_KEY = "accessKeyName";

    /** 添加参数 路由配置名称 */
    String CONFIG_ADD_PARAM_KEY = "addParam";

    /** 规则编解码器 */
    Codec RULE_CODES_INSTANCE = new TypedJsonJacksonCodec(String.class, IscRule.class);
}
