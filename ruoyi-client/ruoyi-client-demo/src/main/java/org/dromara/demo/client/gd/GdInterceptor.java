package org.dromara.demo.client.gd;

import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.Interceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求拦截 请求前添加key
 *
 * @author ye
 */
@Slf4j
public class GdInterceptor implements Interceptor<String> {

    /**
     * 密钥 xxxxxxxxxxxxxxxxxxxxxx
     */
    private static final String KEY = "xxxxxxxxxxxxxxxxxxx";


    /**
     * 该方法在请求发送之前被调用, 若返回false则不会继续发送请求
     *
     * @param req Forest请求对象
     */
    @Override
    public boolean beforeExecute(ForestRequest req) {
        // 添加URL的Query参数
        req.addQuery("key", KEY);
        // 继续执行请求返回true
        return true;
    }

}
