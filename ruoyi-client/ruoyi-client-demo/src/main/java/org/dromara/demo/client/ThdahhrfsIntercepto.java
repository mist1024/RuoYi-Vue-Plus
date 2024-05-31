package org.dromara.demo.client;

import cn.hutool.crypto.digest.DigestUtil;
import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestRequestType;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.demo.config.ThdahhrfsProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 请求拦截 请求前添加key
 *
 * @author AprilWind
 */
@Slf4j
public class ThdahhrfsIntercepto<T> implements Interceptor<T> {

    public static final String TIMESTAMP = "Timestamp";
    public static final String APPID = "Appid";
    public static final String APP_SECRET = "AppSecret";
    public static final String U_KEY = "Ukey";
    public static final String SIGN = "Sign";
    private static final ThdahhrfsProperties THDAHHRFS = SpringUtils.getBean(ThdahhrfsProperties.class);
    private static String IP = SpringUtils.getProperty("forest.variables.thdahhrfs.ip");
    /**
     * 该方法在请求发送之前被调用, 若返回false则不会继续发送请求
     */
    @Override
    public boolean beforeExecute(ForestRequest req) {
        // 取值：unix时间戳
        long timestamp = System.currentTimeMillis();
        String appid = THDAHHRFS.getAppid();
        // 对AppSecret加密后的字符串
        String appSecret = THDAHHRFS.getAppSecret();
        // 对Appid进行MD5加密生成32位的字符串
        String uKey = DigestUtil.md5Hex(appid);

        req.addHeader(TIMESTAMP, timestamp);
        req.addHeader(APPID, appid);
        req.addHeader(APP_SECRET, appSecret);
        req.addHeader(U_KEY, uKey);

        String signString = "";
        if (ForestRequestType.POST == req.getType() || ForestRequestType.GET == req.getType()) {
            //添加请求参数到签名字符串中
            Map<String, Object> signData = new HashMap<>();
            if(ForestRequestType.POST == req.getType()){
                //按照键的Unicode码点排序
                String body = sortJsonByKey(JsonUtils.parseObject(req.body().encodeToString(), Map.class));
                req.body().clear();
                req.addBody(body);
                signData.put("DATA", body);
            }else if(ForestRequestType.GET == req.getType()){
                for (String key:req.getQuery().keySet()){
                    signData.put(key.toUpperCase(),req.getQuery(key));
                }
            }
            signData.put("URL", "{URL}");
            signData.put(TIMESTAMP.toUpperCase(), String.valueOf(timestamp));
            signData.put(APPID.toUpperCase(), appid);
            signData.put(U_KEY.toUpperCase(), uKey);
            signString = sortJsonByKey(signData).replace("{URL}", req.getUrl().replace(IP, ""))
                .replace("\\\\\\", "\\\\");
            log.info("--------------签名字符串未加密前信息---------------：{}", signString);
        }
        //通过MD5计算得到的32位hash值后，并转16进制且对结果转大写生成的字符串
        req.addHeader(SIGN, DigestUtil.md5Hex(signString).toUpperCase());
        return true;
    }

    //@Override
    //public void afterExecute(ForestRequest request, ForestResponse response) {
    //    Interceptor.super.afterExecute(request, response);
    //}

    /**
     * 该方法在请求成功响应时被调用
     */
    @Override
    public void afterExecute(ForestRequest request, ForestResponse res) {
        if (res.getStatusCode() != 200) {
            throw new ServiceException("HTTP响应状态码：" + res.getStatusCode());
        }
        // 解析响应体为Map对象
        Map<String, Object> result = JsonUtils.parseObject(res.getContent(), Map.class);
        // 检查是否缺少 'code' 字段
        if (!result.containsKey("code")) {
            throw new ServiceException("获取请求体失败：缺少 'code' 字段");
        }
        // 获取 'code' 字段的值并转换为整数
        int code = (int) result.get("code");
        // 检查是否缺少 'data' 字段
        if (!result.containsKey("data")) {
            throw new ServiceException("获取请求体失败：缺少 'data' 字段");
        }
        // 如果 'code' 不等于 200，抛出异常
        if (code != 200) {
            String errorMsg = result.containsKey("msg") ? result.get("msg").toString() : "未知错误";
            throw new ServiceException(errorMsg);
        }
        // 将 'data' 字段的值设置为结果
        res.setResult(JsonUtils.toJsonString(result.get("data")));
    }

    /**
     * 该方法在请求发送失败时被调用
     */
    @Override
    public void onError(ForestRuntimeException ex, ForestRequest req, ForestResponse res) {
        throw new ServiceException("HTTP响应状态码：" + res.getStatusCode());
    }

    /**
     * 按照键的Unicode码点排序
     */
    private static String sortJsonByKey(Map<String, Object> map) {
        return JsonUtils.toJsonString((new TreeMap<>(map)));
    }

}
