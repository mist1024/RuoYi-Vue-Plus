package org.dromara.system.controller.system;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Duration;

/**
 * 短链接 控制层
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/resource/short")
public class SysShortLinkController {
    private final IdentifierGenerator identifierGenerator;

    /**
     * 根据短链接获取长链接并重定向
     *
     * @param shortUrl 短链接
     * @return 重定向视图
     */
    @GetMapping("/link")
    public RedirectView getLongLink(@NotBlank(message = "参数不能为空") String shortUrl) {
        // 从 Redis 获取长链接
        String longLink = RedisUtils.getCacheObject(GlobalConstants.SHORT_LINK_KEY + shortUrl);
        if (StringUtils.isNotEmpty(longLink)) {
            // 如果长链接存在，则重定向到长链接
            return new RedirectView(longLink);
        }
        // 如果长链接不存在，则重定向到错误页面
        return new RedirectView("/error");
    }

    /**
     * 生成短链接并返回
     *
     * @param longUrl 长链接
     * @return 完整的短链接
     */
    @PostMapping("/shorturl")
    public String generateShortUrl(@NotBlank(message = "参数不能为空") String longUrl) {
        // 使用标识符生成器生成短链接
        String shortUrl = identifierGenerator.nextId(null).toString();
        // 将短链接与长链接的映射关系存储到 Redis 中，并设置过期时间为 3 分钟
        RedisUtils.setCacheObject(GlobalConstants.SHORT_LINK_KEY + shortUrl, longUrl, Duration.ofMinutes(3));
        // 获取短链接的域名部分
        String host = SpringUtils.getProperty("short.host");
        // 拼接完整的短链接并返回
        return host + shortUrl;
    }

}
