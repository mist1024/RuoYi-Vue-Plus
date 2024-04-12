package org.dromara.common.shortlink.utils;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.shortlink.enums.ValidityType;
import org.dromara.common.shortlink.properties.ShortLinkProperties;

import java.time.Duration;

/**
 * 短链接生成工具类
 *
 * @author AprilWind
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortLinkUtils {

    private static final IdentifierGenerator IDENTIFIER_GENERATOR = SpringUtils.getBean(IdentifierGenerator.class);
    private static final ShortLinkProperties SHORT_LINK_PROPERTIES = SpringUtils.getBean(ShortLinkProperties.class);

    /**
     * 根据默认主机名（或域名）生成完整短链接
     *
     * @param longUrl 完整的长链接
     * @param validityType 有效期类型枚举
     * @return 带有默认主机名（或域名）的完整短链接
     */
    public static String generateShortUrl(String longUrl, ValidityType validityType) {
        return generateShortUrl(SHORT_LINK_PROPERTIES.getHost(), longUrl, validityType);
    }

    /**
     * 根据指定主机名（或域名）生成完整短链接
     *
     * @param host 主机名（或域名）
     * @param longUrl 完整的长链接
     * @param validityType 有效期类型枚举
     * @return 带有指定主机名（或域名）的完整短链接
     */
    public static String generateShortUrl(String host, String longUrl, ValidityType validityType) {
        String url = generateShortLinkIdentifier(longUrl, validityType);
        return host + url;
    }

    /**
     * 生成短链接标识符并将长链接存储到缓存中
     *
     * @param longUrl      完整的长链接
     * @param validityType 有效期类型枚举
     * @return 生成的短链接标识符
     */
    public static String generateShortLinkIdentifier(String longUrl, ValidityType validityType) {
        // 使用标识符生成器生成短链接标识符
        String shortLinkIdentifier = IDENTIFIER_GENERATOR.nextId(null).toString();
        return generateShortLinkIdentifier(shortLinkIdentifier, longUrl, validityType);
    }

    /**
     * 生成指定短链接标识符并将长链接存储到缓存中
     *
     * @param shortLinkIdentifier 指定的短链接标识符
     * @param longUrl             完整的长链接
     * @param validityType        有效期类型枚举
     * @return 生成的指定短链接标识符
     */
    public static String generateShortLinkIdentifier(String shortLinkIdentifier, String longUrl, ValidityType validityType) {
        Validator.validateUrl(longUrl, "请输入有效的url");
        RedisUtils.setCacheObject(GlobalConstants.SHORT_LINK_KEY + shortLinkIdentifier, longUrl, Duration.ofSeconds(validityType.getValidityType()));
        return shortLinkIdentifier;
    }

    /**
     * 根据短链接获取对应的完整长链接，如果找不到长链接则返回错误链接
     *
     * @param shortUrl 短链接标识符
     * @return 对应的完整长链接，如果未找到则返回错误链接
     */
    public static String getLongLink(String shortUrl) {
        return getLongLink(shortUrl, SHORT_LINK_PROPERTIES.getErroHost());
    }

    /**
     * 根据短链接获取对应的完整长链接，如果找不到长链接则返回指定的错误链接
     *
     * @param shortUrl 短链接标识符
     * @param errUrl   错误链接
     * @return 对应的完整长链接，如果未找到则返回错误链接
     */
    public static String getLongLink(String shortUrl, String errUrl) {
        String longLink = RedisUtils.getCacheObject(GlobalConstants.SHORT_LINK_KEY + shortUrl);
        return StringUtils.isNotEmpty(longLink) ? longLink : errUrl;
    }

}
