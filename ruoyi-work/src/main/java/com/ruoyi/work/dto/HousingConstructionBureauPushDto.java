package com.ruoyi.work.dto;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.work.utils.CCSMessageDigest;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.*;

@Component
public class HousingConstructionBureauPushDto {

    //默认编码 成都市
    private static final String DEFAULT_REGIONCODE = "510100";

    /*    public String send(String url, Map<String,Object> map, String interfaceId) throws Exception {
     *//* Map<Object, Object> params = new HashMap<>();
        params.put("virtualcode", buyHouses.getDistrict().equals("3") ? "010" : "009");//区县统一编码
        params.put("id", buyHouses.getId());
        params.put("userId", buyHouses.getUserId());
        params.put("userName", buyHouses.getUserName());
        params.put("phone", buyHouses.getPhone());
        params.put("sex", buyHouses.getSex());
        params.put("education", buyHouses.getEducation());//学历
        params.put("type", buyHouses.getType());
        params.put("status", "10");
        params.put("district", buyHouses.getDistrict());//企业所在地
        params.put("socialCode", buyHouses.getSocialCode());//统一社会信用代码
        params.put("companyName", buyHouses.getCompanyName());//
        params.put("companyAddress", buyHouses.getCompanyAddress());
        params.put("licenseUrl", buyHouses.getLicenseUrl());
        params.put("nationality", buyHouses.getNationality());
        params.put("cardId", buyHouses.getCardId());
        params.put("frontUrl", buyHouses.getFrontUrl());
        params.put("reverseUrl", buyHouses.getReverseUrl());
        params.put("homepageUrl", buyHouses.getHomepageUrl());
        params.put("insidepageUrl", buyHouses.getInsidepageUrl());
        params.put("maritalStatus", buyHouses.getMaritalStatus());
        params.put("maritalUrl", buyHouses.getMaritalUrl());
        params.put("laborContractUrl", buyHouses.getLaborContractUrl());
        params.put("socialSecurityUrl", buyHouses.getSocialSecurityUrl());
        params.put("homeRecordUrl", buyHouses.getHomeRecordUrl());
        params.put("declarationUrl", buyHouses.getDeclarationUrl());
        params.put("commitmentUrl", buyHouses.getCommitmentUrl());
        params.put("cardType", "中国籍".equals(buyHouses.getNationality()) ? 1 : 4);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        params.put("creatTime", sdf.format(buyHouses.getAffTime()));
        params.put("qyStatus", "4");
        params.put("gyStatus", "4");
        params.put("shStatus", "4");
        params.put("buyHousesMemberList", "null");
        params.put("buyHousesLogList", "null");
        params.put("buyHousesLogList", "null");*//*
//        RestTemplate restTemplate = new RestTemplate(generateHttpRequestFactory());
        //调用接口
        String result2 = HttpRequest.post(url)
//                .header("Referer","http://119.96.86.131:8010")
            .body(JSONObject.toJSONString(httpRequestBody))
            .execute().body();
        System.out.println("result2 = " + result2);
        String result = restTemplate.postForObject(url, createRequestBody(map, interfaceId), String.class);
        System.out.println(result);
        return result;
    }*/

    public  String openUrl(String url,Map<String, Object> param, String interfaceId) {
        final HttpRequestBody httpRequestBody = new HttpRequestBody();
        httpRequestBody.setRegioncode(DEFAULT_REGIONCODE);
        httpRequestBody.setData(param);
        final String nonce = new SecureRandom().nextInt() + "";
        httpRequestBody.setNonce(nonce);
        httpRequestBody.setApiKey(interfaceId);
        httpRequestBody.setPassword("GaoxinTalentHouse123");
        httpRequestBody.setLoginid("GaoxinTalentHouse");
        final String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        httpRequestBody.setTimestamp(timestamp);
        final String authenticationKey =  Base64.encode("GaoxinTalentHouse: GaoxinTalentHouse123:" + timestamp);
        httpRequestBody.setAuthenticationKey(authenticationKey);
        //签名
        final CCSMessageDigest digest = CCSMessageDigest.getInstance();
        String signature = null;
        try {
            signature = digest.getSignature(timestamp, nonce, "GaoxinTalentHouse");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequestBody.setSignature(signature);
        //调用接口
        try {
            String result2 = HttpRequest.post(url)
                .timeout(5000)
                .body(JSONUtil.toJsonPrettyStr(httpRequestBody))
                .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(result2);
            String aFalse = jsonObject.getStr("result");
            String aStatus = jsonObject.getStr("status");
            String aMessage = jsonObject.getStr("message");
            if ("false".equals(aFalse) || "error".equals(aStatus)){
                throw new ServiceException("推送市级系统失败!原因:"+aMessage);
            }
            System.out.println("result2 = " + result2);
            return result2;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String send3(HashMap params,String url){
        //基本信息数据
        HttpRequestBody httpRequestBody = new HttpRequestBody();
        httpRequestBody.setApiKey("001");
        AES aes = new AES("CBC", "PKCS7Padding",
            // 密钥，可以自定义
            "0123456789ABHAEQ".getBytes(),
            // iv加盐，按照实际需求添加
            "DYgjCEIMVrj2W9xN".getBytes());
        String encryptHex = aes.encryptHex(JSONUtil.toJsonPrettyStr(params));
        httpRequestBody.setData(encryptHex);
        final String nonce = new SecureRandom().nextInt() + "";
        httpRequestBody.setNonce(nonce);
        httpRequestBody.setPassword("GaoxinTalentHouse123");
        httpRequestBody.setLoginid("GaoxinTalentHouse");
        final String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        httpRequestBody.setTimestamp(timestamp);
        final String authenticationKey =  Base64.encode("GaoxinTalentHouse: GaoxinTalentHouse123:" + timestamp);
        httpRequestBody.setAuthenticationKey(authenticationKey);
        //签名
        final CCSMessageDigest digest = CCSMessageDigest.getInstance();
        String signature = null;
        try {
            signature = digest.getSignature(timestamp, nonce, "GaoxinTalentHouse");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequestBody.setSignature(signature);
        //调用接口
        try {
            String result2 = HttpRequest.post(url)
                .body(JSONUtil.toJsonPrettyStr(httpRequestBody))
                .execute().body();
            System.out.println("result2 = " + result2);
            JSONObject jsonObject = JSONUtil.parseObj(result2);
            String rsCode = jsonObject.getStr("rsCode");
            String rsCause = jsonObject.getStr("rsCause");
            if (!"0".equals(rsCode)){
                throw new ServiceException("人才通回调接口失败!原因:"+rsCause);
            }
            return  result2;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /*    public String send1(String url, Map<String,Object> params, String interfaceId) throws Exception {
     *//*BuyHouses byKey = buyHousesService.getByKey(disqualificationDto.getId());
        Map<Object, Object> params = new HashMap<>();
        params.put("virtualcode", byKey.getDistrict() == "3" ? "010" : "009");//区县统一编码
        params.put("userName", byKey.getUserName());
        params.put("cardId", byKey.getCardId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        params.put("cancelTime", sdf.format(new Date()));
        params.put("reason", disqualificationDto.getReason());
        params.put("note", disqualificationDto.getNote());
        // String s1 = HttpRequest.https(url,createRequestBody(params,interfaceId));
        RestTemplate restTemplate = new RestTemplate(generateHttpRequestFactory());*//*

        String result = restTemplate.postForObject(url, createRequestBody(params, interfaceId), String.class);
        return result;
    }*/


    public String send3(LinkedHashMap params,String url){
        //基本信息数据
        HttpRequestBody httpRequestBody = new HttpRequestBody();
        httpRequestBody.setApiKey("001");
        AES aes = new AES("CBC", "PKCS7Padding",
            // 密钥，可以自定义
            "0123456789ABHAEQ".getBytes(),
            // iv加盐，按照实际需求添加
            "DYgjCEIMVrj2W9xN".getBytes());
        String encryptHex = aes.encryptHex(JSONUtil.toJsonPrettyStr(params));
        httpRequestBody.setData(encryptHex);
        final String nonce = new SecureRandom().nextInt() + "";
        httpRequestBody.setNonce(nonce);
        httpRequestBody.setPassword("GaoxinTalentHouse123");
        httpRequestBody.setLoginid("GaoxinTalentHouse");
        final String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        httpRequestBody.setTimestamp(timestamp);
        final String authenticationKey =  Base64.encode("GaoxinTalentHouse: GaoxinTalentHouse123:" + timestamp);
        httpRequestBody.setAuthenticationKey(authenticationKey);
        //签名
        final CCSMessageDigest digest = CCSMessageDigest.getInstance();
        String signature = null;
        try {
            signature = digest.getSignature(timestamp, nonce, "GaoxinTalentHouse");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequestBody.setSignature(signature);
        //调用接口
        String result2 = HttpRequest.post(url)
//                .header("Referer","http://119.96.86.131:8010")
            .body(JSONUtil.toJsonPrettyStr(httpRequestBody))
            .execute().body();
        System.out.println("result2 = " + result2);
        return  result2;
    }
}

