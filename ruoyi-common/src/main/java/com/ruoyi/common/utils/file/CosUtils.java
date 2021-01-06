package com.ruoyi.common.utils.file;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.transfer.Download;
import com.ruoyi.common.config.CosProperties;
import com.ruoyi.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Optional;

import static cn.hutool.core.util.IdUtil.fastSimpleUUID;

/**
 * @author tottimctj
 * @since 2020-12-28
 */

@Component
public class CosUtils {


    @Autowired
    COSCredentials cosCredentials;

    @Autowired
    ClientConfig clientConfig;

    @Autowired
    private CosProperties properties;

    /**
     * 正则异常字符
     */
    String SPECIAL_CHARACTERS = "[`~! @#$%^&*()+=|{}':;',//[//]<>/?~！@#￥%……&*（）_——+|{}【】‘；：”“’。，、？]";

    /**
     * 表单提交文件
     *
     * @param type
     * @param file
     * @return
     */
    public String upload(String type, MultipartFile file) {


        // 指定要上传到 COS 上对象键
        String key = ReUtil.replaceAll(StrUtil.trim(fastSimpleUUID() + Optional.of(file.getOriginalFilename()).orElse(StrUtil.EMPTY)), SPECIAL_CHARACTERS, StrUtil.EMPTY);
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cosCredentials, clientConfig);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        file.getContentType();
        try {
            PutObjectResult putObjectResult = cosClient.putObject(properties.getBucketName(), type + "/" + key, file.getInputStream(), metadata);
            file.getInputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            cosClient.shutdown();
        }
        return type + "/" + key;

    }


    /**
     * 上传文件
     *
     * @param type
     * @param fileKey
     * @param file
     * @return
     */
    public String uploadFile(String type, String fileKey, File file) {

        // 指定要上传到 COS 上对象键
        String key = ReUtil.replaceAll(StrUtil.trim(Optional.of(fileKey).orElse(StrUtil.EMPTY)), SPECIAL_CHARACTERS, StrUtil.EMPTY);
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cosCredentials, clientConfig);
        try {
            PutObjectResult putObjectResult = cosClient.putObject(properties.getBucketName(), type + "/" + key, file);
        } catch (Exception e) {
        } finally {

            cosClient.shutdown();
        }
        return type + "/" + key;

    }

    /**
     * 根据文件key下载文件
     *
     * @param fileKey
     * @param response
     */
    public void getFile(String fileKey, HttpServletResponse response) {

        COSClient cosClient = new COSClient(cosCredentials, clientConfig);
        GetObjectRequest getObjectRequest = new GetObjectRequest(properties.getBucketName(), fileKey);
        // 限流使用的单位是bit/s, 这里设置下载带宽限制为 10MB/s
        getObjectRequest.setTrafficLimit(80 * 1024 * 1024);

        COSObject cosObject = cosClient.getObject(getObjectRequest);

        // 文件类型
        response.setContentType(cosObject.getObjectMetadata().getContentType());
        // 文件大小
        response.setContentLengthLong(cosObject.getObjectMetadata().getContentLength());
        // 文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + cosObject.getKey());
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            while (true) {
                int line = cosObject.getObjectContent().read();
                if (line == -1) {
                    break;
                } else {
                    os.write(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 关闭输入流
        cosClient.shutdown();
    }


}
