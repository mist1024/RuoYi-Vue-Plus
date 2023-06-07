package com.ruoyi.work.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.work.dto.HttpRequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public final class CCSMessageDigest {
    private static final Logger logger = LoggerFactory.getLogger(CCSMessageDigest.class);
    private final MessageDigest digest;

    private CCSMessageDigest() {
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (Exception e) {
            throw new InternalError("init MessageDigest error:"
                    + e.getMessage());
        }
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static CCSMessageDigest getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder sbDes = new StringBuilder();
        String tmp = null;
        for (int i = 0; i < b.length; i++) {
            tmp = (Integer.toHexString(b[i] & 0xFF));
            if (tmp.length() == 1) {
                sbDes.append("0");
            }
            sbDes.append(tmp);
        }
        return sbDes.toString();
    }

    public static void main(String[] args) {

        String signature = "f86944503c10e7caefe35d6bc19a67e6e8d0e564";// 加密需要验证的签名
        String timestamp = "1371608072";// 时间戳
        String nonce = "1372170854";// 随机数

        CCSMessageDigest wxDigest = CCSMessageDigest.getInstance();
        boolean bValid = wxDigest.validate(signature, timestamp, nonce,
                "111111");
        if (bValid) {
            System.out.println("token 验证成功!");
        } else {
            System.out.println("token 验证失败!");
        }
    }

    /**
     * 加密
     *
     * @param strSrc
     * @return
     * @throws UnsupportedEncodingException
     */
    private String encrypt(String strSrc) throws UnsupportedEncodingException {
        String strDes = null;
        byte[] bt = strSrc.getBytes(StandardCharsets.UTF_8);
        digest.update(bt);
        strDes = byte2hex(digest.digest());
        return strDes;
    }

    /**
     * 校验请求的签名是否合法
     * <p>
     * 加密/校验流程： 1. 将token、timestamp、nonce三个参数进行字典序排序 2. 将三个参数字符串拼接成一个字符串进行sha1加密
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public boolean validate(String signature, String timestamp, String nonce,
                            String token) {
        String expectedSignature = null;
        try {
            expectedSignature = getSignature(timestamp, nonce, token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        // 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于
        return signature.equals(expectedSignature);
    }

  /*  public R<?> checkSign (HttpRequestBody httpRequestBody) {
        CCSMessageDigest wxDigest = CCSMessageDigest.getInstance();
        String signature = httpRequestBody.getSignature();
        String nonce = httpRequestBody.getNonce();
        String encode = httpRequestBody.getAuthenticationKey();
        String[] timestamp = Base64.decodeStr(encode).split(":");
        boolean gaoxinTalentHouse = wxDigest.validate(signature, timestamp[2], nonce, "GaoxinTalentHouse");
        if (!gaoxinTalentHouse) {
            System.out.println("验签失败" );
            return R.fail( "验签失败");
        }
        AES aes = new AES("CBC", "PKCS7Padding",
                // 密钥，可以自定义
                "0123456789ABHAEQ".getBytes(),
                // iv加盐，按照实际需求添加
                "DYgjCEIMVrj2W9xN".getBytes());
        String data = httpRequestBody.getData().toString();
        String decryptStr = aes.decryptStr(data);
        BuyHouses buyHouses = JSONUtil.toBean(decryptStr, BuyHouses.class);
        if (ObjectUtil.isNull(buyHouses.getCardId())){
            return R.fail("数据不可为空");
        }
        return R.ok("操作成功",buyHouses);
    }*/

    /**
     * @param timestamp
     * @param nonce
     * @param token
     * @return String
     * @throws
     * @Title: getSignature
     * @Description: TODO(获取加密后的签名)
     */
    public String getSignature(String timestamp, String nonce, String token) throws UnsupportedEncodingException {
        // 1. 将token、timestamp、nonce三个参数进行字典序排序
        String[] arrTmp = {token, timestamp, nonce};
        Arrays.sort(arrTmp);
        StringBuffer sb = new StringBuffer();
        // 2.将三个参数字符串拼接成一个字符串进行sha1加密
        for (int i = 0; i < arrTmp.length; i++) {
            sb.append(arrTmp[i]);
        }
        String expectedSignature = encrypt(sb.toString());
        return expectedSignature;
    }

    /**
     * 单例持有类
     */
    private static class SingletonHolder {
        static final CCSMessageDigest INSTANCE = new CCSMessageDigest();
    }
}
