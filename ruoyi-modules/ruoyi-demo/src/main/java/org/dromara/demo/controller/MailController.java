package org.dromara.demo.controller;

import cn.hutool.extra.mail.MailAccount;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.mail.utils.MailUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;


/**
 * 邮件发送案例
 *
 * @author Michelle.Chung
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/mail")
public class MailController {
    private final MailAccount mailAccount;
    /**
     * 发送邮件
     *
     * @param to      接收人
     * @param subject 标题
     * @param text    内容
     */
    @GetMapping("/sendSimpleMessage")
    public R<Void> sendSimpleMessage(String to, String subject, String text) {
        MailUtils.sendText(mailAccount, to, subject, text);
        return R.ok();
    }

    /**
     * 发送邮件（带附件）
     *
     * @param to       接收人
     * @param subject  标题
     * @param text     内容
     * @param filePath 附件路径
     */
    @GetMapping("/sendMessageWithAttachment")
    public R<Void> sendMessageWithAttachment(String to, String subject, String text, String filePath) {
        MailUtils.sendText(mailAccount, to, subject, text, new File(filePath));
        return R.ok();
    }

}
