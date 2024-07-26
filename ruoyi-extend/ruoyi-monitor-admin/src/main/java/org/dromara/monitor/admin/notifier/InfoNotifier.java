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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
     * 处理服务通知事件
     * <p>
     * 此方法会在服务状态发生变化时触发，并且会异步地发送 WebHook 通知
     * 适用于服务的正常状态变更，不包括异常情况
     *
     * @param notifier 通知事件对象，包含服务的状态和其他相关信息
     */
    @Async
    @EventListener
    public void infoNotification(NotifierEvent notifier) {
        sendWebHook(notifier);
    }

    /**
     * 处理服务下线，离线或异常的通知事件
     * <p>
     * 当服务的状态为“OFFLINE”、“DOWN”或“UNKNOWN”时，会触发此方法
     * 该方法会异步发送邮件通知，以便及时处理服务问题
     *
     * @param notifier 通知事件对象，包含服务的状态和其他相关信息
     */
    @Async
    @EventListener(condition = "#notifier.status == 'OFFLINE' || #notifier.status == 'DOWN' || #notifier.status == 'UNKNOWN'")
    public void errNotification(NotifierEvent notifier) {
        sendMail(notifier);
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
                sendWebHookMessage(webHook, title, message);
                log.info("WebHook消息已发送至: {}", webHook.getUrl());
            } catch (Exception e) {
                log.error("WebHook消息发送失败: ", e);
            }
        }
    }

    /**
     * 发送WebHook消息
     *
     * @param webHook         WebHook配置信息
     * @param title           消息标题
     * @param markdownMessage 消息内容
     * @throws Exception 处理过程中可能抛出的异常
     */
    private void sendWebHookMessage(NotifyProperties.WebHook webHook, String title, String markdownMessage) throws Exception {
        String jsonMessage = createJsonMessage(title, markdownMessage);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = createHttpRequest(webHook, jsonMessage);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        handleResponse(response);
    }

    /**
     * 创建消息体的JSON字符串
     *
     * @param title           消息标题
     * @param markdownMessage 消息内容
     * @return JSON格式的消息体
     * @throws Exception 处理过程中可能抛出的异常
     */
    private String createJsonMessage(String title, String markdownMessage) throws Exception {
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("msgtype", "markdown");
        Map<String, String> markdownContent = new HashMap<>();
        markdownContent.put("title", title + "通知");
        markdownContent.put("text", markdownMessage);
        messageBody.put("markdown", markdownContent);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(messageBody);
    }

    /**
     * 根据WebHook配置创建HTTP请求
     *
     * @param webHook     WebHook配置信息
     * @param jsonMessage JSON格式的消息体
     * @return 创建的HTTP请求
     * @throws Exception 处理过程中可能抛出的异常
     */
    private HttpRequest createHttpRequest(NotifyProperties.WebHook webHook, String jsonMessage) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonMessage));

        if ("2".equals(webHook.getType())) {
            String signedUrl = generateSignedUrl(webHook);
            requestBuilder.uri(new URI(signedUrl));
        } else {
            requestBuilder.uri(new URI(webHook.getUrl()));
        }

        return requestBuilder.build();
    }

    /**
     * 生成带签名的URL
     *
     * @param webHook WebHook配置信息
     * @return 带签名的URL
     * @throws Exception 处理过程中可能抛出的异常
     */
    private String generateSignedUrl(NotifyProperties.WebHook webHook) throws Exception {
        long timestamp = System.currentTimeMillis();
        String secret = webHook.getSecret();
        String stringToSign = timestamp + "\n" + secret;

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = URLEncoder.encode(Base64.getEncoder().encodeToString(signData), StandardCharsets.UTF_8);

        return String.format("%s&timestamp=%d&sign=%s", webHook.getUrl(), timestamp, sign);
    }

    /**
     * 处理HTTP响应
     *
     * @param response HTTP响应
     */
    private void handleResponse(HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            log.info("WebHook消息发送成功");
        } else {
            log.error("WebHook消息发送失败: {}", response.body());
        }
    }

}
