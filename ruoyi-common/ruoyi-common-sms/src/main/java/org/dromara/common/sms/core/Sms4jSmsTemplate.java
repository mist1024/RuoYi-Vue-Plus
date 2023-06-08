package org.dromara.common.sms.core;

import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.sms.config.properties.SmsProperties;
import org.dromara.common.sms.entity.SmsResult;
import org.dromara.common.sms.exception.SmsException;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tencent 短信模板
 *
 * @author Lion Li
 * @version 4.2.0
 */
public class Sms4jSmsTemplate implements SmsTemplate {

    private SmsProperties properties;

    @SneakyThrows(Exception.class)
    public Sms4jSmsTemplate(SmsProperties smsProperties) {
        this.properties = smsProperties;
    }

    @Override
    public SmsResult send(String phones, String templateId, Map<String, String> param) {
        if (StringUtils.isBlank(phones)) {
            throw new SmsException("手机号不能为空");
        }
        try {
            SmsResponse resp;
            if (StringUtils.isBlank(templateId)) {
                String message = JSONUtil.toJsonStr(param);
                resp = SmsFactory.createSmsBlend(properties.getSupplierType()).sendMessage(phones, message);
            } else {
                LinkedHashMap<String, String> messages = new LinkedHashMap<>(param);
                resp = SmsFactory.createSmsBlend(properties.getSupplierType()).sendMessage(phones, templateId, messages);
            }

            SmsResult.SmsResultBuilder builder = SmsResult.builder();
            if ("OK".equals(resp.getCode())) {
                builder.isSuccess(true).message(resp.getMessage()).response(JsonUtils.toJsonString(resp));
            } else {
                builder.isSuccess(false).message(resp.getErrMessage()).response(JsonUtils.toJsonString(resp));
            }
            return builder.build();
        } catch (Exception e) {
            throw new SmsException(e.getMessage());
        }
    }

}
