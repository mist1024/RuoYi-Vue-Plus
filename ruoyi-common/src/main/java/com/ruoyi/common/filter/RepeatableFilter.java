package com.ruoyi.common.filter;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.service.ConfigService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repeatable 过滤器
 *
 * @author ruoyi
 */
public class RepeatableFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest
            && StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE) ) {
            requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) request, response);
        }
        if (!isSafe((HttpServletRequest) request)) {
            ((HttpServletResponse) response).setStatus(403);
            return;
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 判断请求是正确
     *
     * @param request
     * @return
     */
    private boolean isSafe(HttpServletRequest request) {
        ConfigService sysConfigService = SpringUtils.getBean(ConfigService.class);
        String configValue = sysConfigService.getConfigValue("sys:preventing:hotlinking");
        List<String> strings = Arrays.asList(configValue.split(","));
        if (ObjectUtil.isEmpty(configValue)) {
            // 未设置任何链接,表示所有来源都可以访问
            return true;
        }
        String referer = request.getHeader("referer");
        if (referer == null || "".equals(referer)) {
            // 获取不到防盗链头部信息 -> 拦截
            System.out.println("referer为空进行拦截");
            return false;
        }
        for (String white : strings) {
            if (white.contains("*.")) {
                // 这里处理*.xxx.com,表示xxx.com下的所有二级域名均可访问,所以就截取掉'*.'符号
                white = white.replace("*.", "");
            }
            // 这里就简单的判断referer请求头中的链接是否包含我们白名单中的域名
            if (referer.contains(white)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理相应数据
     *
     * @param response
     */
    public void handleResponse(HttpServletResponse response) {
        // 访问链接有问题
        try {
            Map<String, String> resultMsg = new HashMap<>();
            resultMsg.put("code", String.valueOf(HttpStatus.FORBIDDEN));
            resultMsg.put("msg", "资源不允许");
            ObjectMapper objectMapper = new ObjectMapper();
            String msg = objectMapper.writeValueAsString(resultMsg);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(403);
            response.getWriter().write(msg);
        } catch (IOException e) {
            System.out.println("防盗链过滤器处理response失败");
        }
    }

}
