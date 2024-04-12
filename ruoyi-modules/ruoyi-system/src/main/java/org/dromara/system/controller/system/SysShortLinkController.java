package org.dromara.system.controller.system;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.dromara.common.shortlink.enums.ValidityType;
import org.dromara.common.shortlink.utils.ShortLinkUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 短链接 控制层
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/resource/short")
public class SysShortLinkController {

    /**
     * 根据短链接获取长链接并重定向
     *
     * @param shortUrl 短链接
     * @return 重定向视图
     */
    @GetMapping("/link")
    public RedirectView getLongLink(@NotBlank(message = "参数不能为空") String shortUrl) {
        String longLink = ShortLinkUtils.getLongLink(shortUrl, "/error");
        return new RedirectView(longLink);
    }

    /**
     * 生成短链接并返回
     *
     * @param longUrl 长链接
     * @return 完整的短链接
     */
    @PostMapping("/shorturl")
    public String generateShortUrl(@NotBlank(message = "参数不能为空") String longUrl) {
        return ShortLinkUtils.generateShortUrl(longUrl, ValidityType.THREE_DAYS);
    }

}
