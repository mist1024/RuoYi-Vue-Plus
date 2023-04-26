package com.ruoyi.rsaencrypt.utls;
import com.antherd.smcrypto.sm2.Sm2;

/**
 * RSA加解密工具类
 * @author Administrator
 */
public class RSAUtil {


    public static final String PRIVATE_KEY = "9cefdfcb925a32e10206d3a693ba204632c59e5f9a171faebb814885191dd35e";
    public static final String VUE_PUBLIC_KEY = "048af4184056315a9ecfcc14280b32504ba194ec8dd0e06b298c4a1aa557e7e9a5d15e1293392f741f7fb31a82e55785b7f1880d61b57def1e38e3f06435fb0502";
    public static final String PUBLIC_KEY1 = "0435661bb2d13bba88f47af0bbe243fcded8f27ac298932661787f88ea283c2b31fe427e1aa8410826a963e9114fe5ffab4ad278aeeb7f1f161e735d1f50570e78";
    /**
     * 公钥加密
     */
    public static String publicKeyEncryptBase64(String data){
        return Sm2.doEncrypt(data, VUE_PUBLIC_KEY);
    }

    /**
     * 私钥解密
     */
    public static String privateKeyDecryptStr(String data){
        return Sm2.doDecrypt(data,PRIVATE_KEY);
    }
}
