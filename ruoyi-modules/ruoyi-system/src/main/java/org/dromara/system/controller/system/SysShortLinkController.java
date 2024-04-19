package org.dromara.system.controller.system;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.shortlink.enums.ValidityType;
import org.dromara.common.shortlink.properties.ShortLinkProperties;
import org.dromara.common.shortlink.utils.ShortLinkUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 短链接 控制层
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/resource/short")
public class SysShortLinkController {

    private final ShortLinkProperties shortLinkProperties;

    /**
     * 根据短链接获取长链接并重定向
     *
     * @param shortLinkIdentifier 短链接标识符
     * @return 重定向视图
     */
    @GetMapping("/link/{shortLinkIdentifier}")
    public RedirectView getLongLink(@PathVariable("shortLinkIdentifier") String shortLinkIdentifier) {
        String longLink = ShortLinkUtils.getLongLink(shortLinkIdentifier, shortLinkProperties.getErrorAddress());
        return new RedirectView(longLink);
    }

    /**
     * 生成短链接并返回
     *
     * @param longUrl 长链接
     * @return 完整的短链接
     */
    @PostMapping("/shorturl")
    public R<String> generateShortUrl(HttpServletRequest request, @NotBlank(message = "参数不能为空") String longUrl) throws MalformedURLException {
        String shorturl;
        if (Boolean.FALSE.equals(shortLinkProperties.getEnabled())) {
            shorturl = ShortLinkUtils.generateShortUrl(shortLinkProperties.getAddress(), longUrl, ValidityType.THREE_DAYS);
        } else {
            String requestUrl = request.getRequestURL().toString().replace("shorturl", "link/");
            String host = new URL(requestUrl).getHost();
            String address = requestUrl.replace(host, host + shortLinkProperties.getApi());
            shorturl = ShortLinkUtils.generateShortUrl(address, longUrl, ValidityType.THREE_DAYS);
        }
        return R.ok(shorturl);
    }

}
