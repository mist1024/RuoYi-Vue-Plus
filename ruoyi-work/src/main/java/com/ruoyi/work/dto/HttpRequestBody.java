package com.ruoyi.work.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
public class HttpRequestBody implements Serializable {

    private static final long serialVersionUID = -6634653494662614388L;

    // 用户名
    private String loginid;
    // 密码
    private String password;
    // 请求体
    private Object data;
    // 区域编码
    private String regioncode;
    // usbKey
    private String key;
    // 加密需要验证的签名
    private String signature;
    // 时间戳
    private String timestamp;
    // 随机数
    private String nonce;
    //
    private String apiKey;
    // 文件上传
//    @JSONField(serialize = false)
    private List<MultipartFile> addFiles;
    // base64编码后的字符串
    // loginid:password:timestamp
    private String authenticationKey;
//setter getter 省略
}
