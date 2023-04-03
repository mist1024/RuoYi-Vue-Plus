package org.dromara.common.mail.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

import java.io.File;

/**
 * @author yuyang
 * @date 2023-04-03
 */
public class MailUtils {
    /**
     * 使用配置文件中设置的账户发送文本邮件，发送给单个或多个收件人<br>
     * 多个收件人可以使用逗号“,”分隔，也可以通过分号“;”分隔
     *
     * @param to      收件人
     * @param subject 标题
     * @param content 正文
     * @param files   附件列表
     * @return message-id
     * @since 3.2.0
     */
    public static String sendText(MailAccount mailAccount, String to, String subject, String content, File... files) {
        return MailUtil.send(mailAccount, to, subject, content, false, files);
    }
}
