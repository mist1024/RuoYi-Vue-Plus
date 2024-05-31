package org.dromara.guide.client;

import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.Interceptor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.SpringUtils;

/**
 * 请求拦截 请求前添加key
 *
 * @author AprilWind
 */
@Slf4j
public class GuideTrafficIntercepto<T> implements Interceptor<T> {

    private static final String KEY = SpringUtils.getProperty("client.guide.clientKey");

    /**
     * 该方法在请求发送之前被调用, 若返回false则不会继续发送请求
     *
     * @param request Forest请求对象
     */
    @Override
    public boolean beforeExecute(ForestRequest request) {
        // 用户在高德开放平台官网申请Web服务API类型KEY
        request.addQuery("clientKey", KEY);
        // 时间戳：秒时间，例如1621243952 单位秒
        request.addQuery("timestamp", System.currentTimeMillis() / 1000);
        // 根据授权密钥计算出的动态鉴权信息
        request.addQuery("digest", "鉴权动态密钥");
        return true;
    }
}
