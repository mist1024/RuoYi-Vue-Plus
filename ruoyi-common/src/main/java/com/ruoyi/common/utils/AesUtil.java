package com.ruoyi.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AesUtil {
    //密钥 (需要前端和后端保持一致)
    private static final String KEY = "FFff123456.!-12s";
    private static final String IV = "FFff123456.!-12s";

    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new Base64().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV);
    }

    /**
     * BASE64解密
     * @throws Exception
     */
    public static String decryptBASE64(String key) throws Exception {
        if (!StringUtils.isEmpty(key)){
            return new String(new BASE64Decoder().decodeBuffer(key), StandardCharsets.UTF_8);
        }
        return "";

    }

    /**
     * BASE64加密
     */
    public static String encryptBASE64(String key) throws Exception {
        if (!StringUtils.isEmpty(key)) {
            return (new BASE64Encoder()).encodeBuffer(key.getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        String s = encryptBASE64("http://192.168.0.200:8010/upload/GXTalents/images/843329b85e974ef5bae06d9c01bc909e.jpg");
        System.out.println(s);
        String s1 = decryptBASE64("aHR0cHM6Ly9neC5jaGVuZ2R1dGFsZW50LmNuOjgwMTAvdXBsb2FkL0dYVGFsZW50cy9pbWFnZXMvMTM2ODBlMmI0ZTY4NDI1N2ExM2UyNjI5MGQ1NDljODMucG5n");
        System.out.println(s1);
    }
}
