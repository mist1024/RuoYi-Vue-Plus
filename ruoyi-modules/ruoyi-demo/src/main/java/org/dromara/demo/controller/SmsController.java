package org.dromara.demo.controller;

import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.sms.config.properties.SmsProperties;
import org.dromara.common.sms.core.SmsTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信演示案例
 * 请先阅读文档 否则无法使用
 *
 * @author Lion Li
 * @version 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/sms")
public class SmsController {

    private final SmsProperties smsProperties;

    /**
     * 发送短信Tencent
     *
     * @param phones     电话号
     * @param templateId 模板ID
     */
    @GetMapping("/sendSms4j")
    public R<Object> sendSms4j(String phones, String templateId) {
        if (!smsProperties.getEnabled()) {
            return R.fail("当前系统没有开启短信功能！");
        }
        if (!SpringUtils.containsBean("sms4jSmsTemplate")) {
            return R.fail("Sms4j依赖未引入！");
        }
        SmsTemplate smsTemplate = SpringUtils.getBean(SmsTemplate.class);
        Map<String, String> map = new HashMap<>(1);
//        map.put("2", "测试测试");
        map.put("code", "123456");
        Object send = smsTemplate.send(phones, templateId, map);
        return R.ok(send);
    }

}
