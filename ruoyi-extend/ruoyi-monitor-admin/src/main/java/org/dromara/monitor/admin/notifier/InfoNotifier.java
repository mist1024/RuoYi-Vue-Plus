package org.dromara.monitor.admin.notifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mail.utils.MailUtils;

import org.dromara.monitor.admin.config.properties.NotifyProperties;
import org.dromara.monitor.admin.event.NotifierEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 信息通知
 *
 * @author AprilWind
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class InfoNotifier {
    private final NotifyProperties notifyProperties;

    /**
     * 服务上线或者离线
     *
     * @param notifier 通知事件
     */
    @Async
    @EventListener(condition = "#notifier.status == 'UP' || #notifier.status == 'OFFLINE'")
    public void infoNotification(NotifierEvent notifier) {
        sendWebHook(notifier);
    }

    /**
     * 服务下线或者发送异常
     *
     * @param notifier 通知事件
     */
    @Async
    @EventListener(condition = "#notifier.status == 'DOWN' || #notifier.status == 'UNKNOWN'")
    public void errNotification(NotifierEvent notifier) {
        sendMail(notifier);
        sendWebHook(notifier);
    }

    /**
     * 发送邮件通知
     *
     * @param notifier 包含通知信息的对象
     */
    public void sendMail(NotifierEvent notifier) {
        NotifyProperties.Mail mail = notifyProperties.getMail();
        if (mail.getEnabled()) {
            String message = StringUtils.format(mail.getTemplate(), notifier.getRegistName(), notifier.getInstanceId(),
                notifier.getStatusName(), notifier.getStatus(), notifier.getServiceUrl());
            try {
                MailUtils.sendHtml(mail.getTo(), notifier.getRegistName() + notifier.getStatusName(), message);
                log.info("邮件已发送至: {}", mail.getTo());
            } catch (Exception e) {
                log.error("邮件发送失败: ", e);
            }
        }
    }

    /**
     * 发送WebHook通知
     *
     * @param notifier 包含通知信息的对象
     */
    public void sendWebHook(NotifierEvent notifier) {
        NotifyProperties.WebHook webHook = notifyProperties.getWebHook();
        if (webHook.getEnabled()) {
            String title = notifier.getRegistName() + notifier.getStatusName();
            String message = StringUtils.format(webHook.getTemplate(), title,
                notifier.getRegistName(), notifier.getInstanceId(), notifier.getStatus(), notifier.getServiceUrl());
            try {
                sendWebHookMessage(webHook.getUrl(), title, message);
                log.info("WebHook消息已发送至: {}", webHook.getUrl());
            } catch (Exception e) {
                log.error("WebHook消息发送失败: ", e);
            }
        }
    }

    private void sendWebHookMessage(String url, String title, String markdownMessage) throws Exception {
        // 构造消息体
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("msgtype", "markdown");

        Map<String, String> markdownContent = new HashMap<>();
        markdownContent.put("title", title + "通知");
        markdownContent.put("text", markdownMessage);
        messageBody.put("markdown", markdownContent);

        // 将消息体转换为JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(messageBody);

        // 创建HTTP客户端
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonMessage))
            .build();

        // 发送请求
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 处理响应
        if (response.statusCode() == 200) {
            log.info("WebHook消息发送成功");
        } else {
            log.error("WebHook消息发送失败: {}", response.body());
        }
    }

}
