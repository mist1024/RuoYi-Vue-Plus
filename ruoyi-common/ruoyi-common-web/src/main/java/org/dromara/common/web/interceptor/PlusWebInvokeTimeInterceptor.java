package org.dromara.common.web.interceptor;

import com.alibaba.ttl.TransmittableThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * web的调用时间统计拦截器
 * dev环境有效
 *
 * @author Lion Li
 * @since 3.3.0
 */
@Slf4j
public class PlusWebInvokeTimeInterceptor implements HandlerInterceptor {

    private final String prodProfile = "prod";

    private final TransmittableThreadLocal<StopWatch> invokeTimeTL = new TransmittableThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!prodProfile.equals(SpringUtils.getActiveProfile())) {
            if (handler instanceof HandlerMethod handlerMethod) {
                String apiUrl = getRequestUrl(handlerMethod);
                log.info("[PLUS]开始请求 => URL[{}]", request.getMethod() + " " + apiUrl);
                parameterMap(request);
                parameterBodyMap(request, apiUrl);
                StopWatch stopWatch = new StopWatch();
                invokeTimeTL.set(stopWatch);
                stopWatch.start();
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (!prodProfile.equals(SpringUtils.getActiveProfile())) {
            StopWatch stopWatch = invokeTimeTL.get();
            stopWatch.stop();
            if (handler instanceof HandlerMethod handlerMethod) {
                String apiUrl = getRequestUrl(handlerMethod);
                log.info("[PLUS]结束请求 => URL[{}],耗时:[{}]毫秒", request.getMethod() + " " + apiUrl, stopWatch.getTime());

            }
            invokeTimeTL.remove();
        }
    }

    /**
     * 判断本次请求的数据类型是否为json
     *
     * @param request request
     * @return boolean
     * @since 5.0.0
     */
    @Deprecated
    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType != null) {
            return StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
        }
        return false;
    }

    /**
     * 获取请求的真实接口地址
     *
     * @param handlerMethod handlerMethod
     * @return 返回接口地址
     */
    private static String getRequestUrl(HandlerMethod handlerMethod) {
        StringBuilder apiURL = new StringBuilder();
        //获取类上的RequestMapping注解
        RequestMapping mapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
        if (null != mapping) {
            apiURL.append(mapping.value()[0]);
        }
        Method method = handlerMethod.getMethod();
        //获取方法上的RequestMapping注解
        RequestMapping methodRequestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (methodRequestMapping != null) {
            String[] value = methodRequestMapping.value();
            if (value.length > 0) {
                apiURL.append(value[0]);
            }
        }
        return apiURL.toString();
    }

    /**
     * 获取GET请求中，使用entity中的参数
     *
     * @param request request
     */
    private void parameterMap(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, Object> getParameters = new LinkedHashMap<>();
        if (parameterNames.hasMoreElements()) {
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                String value = request.getParameter(name);
                getParameters.put(name, value);
            }
            log.info("[PLUS]请求参数 => 参数内容:[{}]", JsonUtils.toJsonString(getParameters));
        }
    }

    /**
     * 获取restful风格参数和body请求参数
     *
     * @param request 请求
     * @param apiURL  接口地址
     */
    private static void parameterBodyMap(HttpServletRequest request, String apiURL) {
        //获取restful风格参数
        Map<String, Object> parameterMap = findCommonCharacters(request.getRequestURI(), apiURL);
        if (ObjectUtils.isNotEmpty(parameterMap)) {
            log.info("[PLUS]请求参数 => 参数内容:[{}]", JsonUtils.toJsonString(parameterMap));
        }
        //获取body参数
        int contentLength = request.getContentLength();
        if (contentLength > 0) {
            try {
                // 获取请求体的输入流
                BufferedReader reader = request.getReader();
                StringBuilder body = new StringBuilder();
                String line;
                // 逐行读取请求体内容
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
                if (ObjectUtils.isNotEmpty(body)) {
                    log.info("[PLUS]请求参数 => 参数内容:[{}]", body);
                }
            } catch (IOException e) {
                log.error("[PLUS]拦截器获取body参数异常", e);
            }
        }
    }

    /**
     * 获取restfull风格参数
     *
     * @param str1 全路径
     * @param str2 接口路径
     * @return Map<String, Object>
     */
    private static Map<String, Object> findCommonCharacters(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return Collections.emptyMap();
        }
        int index = StringUtils.indexOfDifference(str1, str2);
        if (index == -1) {
            return Collections.emptyMap();
        }
        String values = StringUtils.substring(str1, index);
        String params = StringUtils.substring(str2, index);
        Map<String, Object> parameterMap = new LinkedHashMap<>();
        parameterMap.put(params.replace("{","").replace("}",""), values);
        return parameterMap;
    }
}
