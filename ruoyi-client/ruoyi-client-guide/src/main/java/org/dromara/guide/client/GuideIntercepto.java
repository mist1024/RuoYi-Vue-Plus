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
public class GuideIntercepto<T> implements Interceptor<T> {

    private static final String KEY = SpringUtils.getProperty("client.guide.key");

    /**
     * 该方法在请求发送之前被调用, 若返回false则不会继续发送请求
     *
     * @param request Forest请求对象
     */
    @Override
    public boolean beforeExecute(ForestRequest request) {
        request.addQuery("key", KEY);
        return true;
    }
}
