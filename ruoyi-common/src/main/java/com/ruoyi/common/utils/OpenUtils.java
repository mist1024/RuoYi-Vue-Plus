package com.ruoyi.common.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.GaoXinCardInfo;
import com.ruoyi.common.exception.ServiceException;

/**
 * @author Administrator
 * 对外所需要的工具类
 */


public class OpenUtils {
    private static final String appsecret ="JXYB7KpXlH9i0CL6";
    private static final String aesKey = "74242EAFE97F18BFAE9D2682590B6614";
    private static final String iv = "74242EAFE97F18BF";
    private static final String id="2023062804031";
//    private static final String url ="http://162.14.100.54:9010/index.php/Api/renju/get_user_info";//测试
    private static final String url ="http://118.122.86.24:8016/index.php/Api/renju/get_user_info";//  正式地址

    public static R<?> getGaoXinCardInfo(String cardId){
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,aesKey.getBytes(),iv.getBytes());
        String param=aes.encryptBase64(
            new JSONObject()
                .set("id_card",cardId)
                .toStringPretty());
        MD5 md5 = SecureUtil.md5();
        String sign=md5.digestHex(id+param+appsecret);
        JSONObject jsonObject = new JSONObject()
            .set("id",id)
            .set("param",param)
            .set("sign",sign);
        try {
            String content = HttpRequest.post(url)
                .timeout(5000)
                .body(jsonObject.toStringPretty())
                .execute()
                .body();
            System.out.println("content = " + content);
            JSONObject jsonObject1 = JSONUtil.parseObj(content);
            Integer code = jsonObject1.getInt("code");
            Object data = jsonObject1.get("data");
            String message = jsonObject1.getStr("message");
            if (code==200){
                if (ObjectUtil.isNull(data)){
                    return R.fail("暂未获取到当前"+cardId+"人才认定信息");
                }else {
                    GaoXinCardInfo gaoXinCardInfo = JsonUtils.parseObject(JSONUtil.toJsonPrettyStr(data), GaoXinCardInfo.class);
                    return R.ok(gaoXinCardInfo);
                }
            }else if (code==400){
                return R.fail(message);
            }else {
                return R.fail("人才认定接口请求未知异常");
            }
        } catch (Exception e) {
            throw new RuntimeException("人才认定接口请求错误");
        }
    }
}
