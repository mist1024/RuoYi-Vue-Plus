package com.ruoyi.common.utils;

import cn.hutool.core.bean.BeanUtil;
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
                if (ObjectUtil.isNull(data) || ObjectUtil.isEmpty(data)){
                    return R.fail("暂未获取到当前"+cardId+"人才认定信息,如有疑问请联系客服人员! 电话:19940594954");
                }else {
                    GaoXinCardInfo gaoXinCardInfo = JsonUtils.parseObject(JSONUtil.toJsonPrettyStr(data), GaoXinCardInfo.class);
                    //判断值是否为空
                    if (ObjectUtil.isEmpty(gaoXinCardInfo.getName())){
                        return R.fail("获取人才认定数据接口返回异常,异常信息:姓名为空!请联系客服人员! 电话:19940594954");
                    }else if (ObjectUtil.isEmpty(gaoXinCardInfo.getCard_id())){
                        return R.fail("获取人才认定数据接口返回异常,异常信息:身份证为空!请联系客服人员! 电话:19940594954");
                    }else if (ObjectUtil.isEmpty(gaoXinCardInfo.getSex())){
                        return R.fail("获取人才认定数据接口返回异常,异常信息:性别为空!请联系客服人员! 电话:19940594954");
                    }else if (ObjectUtil.isEmpty(gaoXinCardInfo.getNationality())){
                        return R.fail("获取人才认定数据接口返回异常,异常信息:国籍为空!请联系客服人员! 电话:19940594954");
                    }else if (ObjectUtil.isEmpty(gaoXinCardInfo.getEducation())){
                        return R.fail("获取人才认定数据接口返回异常,异常信息:学历为空!请联系客服人员! 电话:19940594954");
                    }else if (ObjectUtil.isEmpty(gaoXinCardInfo.getDistrict())){
                        return R.fail("获取人才认定数据接口返回异常,异常信息:单位区域为空!请联系客服人员! 电话:19940594954");
                    }else if (ObjectUtil.isEmpty(gaoXinCardInfo.getPhone())){
                        return R.fail("获取人才认定数据接口返回异常,异常信息:手机号为空!请联系客服人员! 电话:19940594954");
                    }else if (ObjectUtil.isEmpty(gaoXinCardInfo.getCompany_name())){
                        return R.fail("获取人才认定数据接口返回异常,异常信息:公司名称为空!请联系客服人员! 电话:19940594954");
                    }else if (ObjectUtil.isEmpty(gaoXinCardInfo.getType())){
                        return R.fail("获取人才认定数据接口返回异常,异常信息:人才类型为空!请联系客服人员! 电话:19940594954");
                    }
                    gaoXinCardInfo.setType(gaoXinCardInfo.getType());
                    gaoXinCardInfo.setNationality(gaoXinCardInfo.getNationality());
                    return R.ok(gaoXinCardInfo);
                }
            }else if (code==400){
                return R.fail("获取人才认定数据接口返回异常,异常信息:"+message+"请联系客服人员! 电话:19940594954");
            }else {
                return R.fail("人才认定接口请求未知异常");
            }
        } catch (Exception e) {
            throw new RuntimeException("人才认定接口请求错误");
        }
    }
}
