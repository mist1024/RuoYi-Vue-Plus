package com.ruoyi.oss.core;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.util.IdUtil;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.oss.constant.OssConstant;
import com.ruoyi.oss.entity.UploadResult;
import com.ruoyi.oss.enumd.AccessPolicyType;
import com.ruoyi.oss.enumd.PolicyType;
import com.ruoyi.oss.exception.OssException;
import com.ruoyi.oss.properties.OssProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

/**
 * S3 存储协议 所有兼容S3协议的云厂商均支持
 * 阿里云 腾讯云 七牛云 minio
 *
 * @author Lion Li
 */
public class OssClient {

    private final String configKey;

    private final OssProperties properties;

    private AmazonS3 client;

    public OssClient(String configKey, OssProperties ossProperties) {
        this.configKey = configKey;
        this.properties = ossProperties;
        try {
            //AmazonS3环境
            if (!isLocalEnv()) {
                AwsClientBuilder.EndpointConfiguration endpointConfig =
                    new AwsClientBuilder.EndpointConfiguration(properties.getEndpoint(), properties.getRegion());

                AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
                AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
                ClientConfiguration clientConfig = new ClientConfiguration();
                if (OssConstant.IS_HTTPS.equals(properties.getIsHttps())) {
                    clientConfig.setProtocol(Protocol.HTTPS);
                } else {
                    clientConfig.setProtocol(Protocol.HTTP);
                }
                AmazonS3ClientBuilder build = AmazonS3Client.builder()
                    .withEndpointConfiguration(endpointConfig)
                    .withClientConfiguration(clientConfig)
                    .withCredentials(credentialsProvider)
                    .disableChunkedEncoding();
                if (!StringUtils.containsAny(properties.getEndpoint(), OssConstant.CLOUD_SERVICE)) {
                    // minio 使用https限制使用域名访问 需要此配置 站点填域名
                    build.enablePathStyleAccess();
                }
                this.client = build.build();
            }
            createBucket();
        } catch (Exception e) {
            if (e instanceof OssException) {
                throw e;
            }
            throw new OssException("配置错误! 请检查系统配置:[" + e.getMessage() + "]");
        }
    }

    public void createBucket() {
        try {
            //本地环境
            if (isLocalEnv()) {
                //创建目录
                PathUtil.mkdir(Paths.get(getLocalEnvDir()));
                return;
            }
            String bucketName = properties.getBucketName();
            if (client.doesBucketExistV2(bucketName)) {
                return;
            }
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            AccessPolicyType accessPolicy = getAccessPolicy();
            createBucketRequest.setCannedAcl(accessPolicy.getAcl());
            client.createBucket(createBucketRequest);
            client.setBucketPolicy(bucketName, getPolicy(bucketName, accessPolicy.getPolicyType()));
        } catch (Exception e) {
            throw new OssException("创建Bucket失败, 请核对配置信息:[" + e.getMessage() + "]");
        }
    }

    public UploadResult upload(byte[] data, String path, String contentType) {
        return upload(new ByteArrayInputStream(data), path, contentType);
    }

    public UploadResult upload(InputStream inputStream, String path, String contentType) {
        if (!(inputStream instanceof ByteArrayInputStream)) {
            inputStream = new ByteArrayInputStream(IoUtil.readBytes(inputStream));
        }
        try {
            //本地环境
            if (isLocalEnv()) {
                FileUtils.copyInputStreamToFile(inputStream, getLocalEnvFile(path));
            } else {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(contentType);
                metadata.setContentLength(inputStream.available());
                PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), path, inputStream, metadata);
                // 设置上传对象的 Acl 为公共读
                putObjectRequest.setCannedAcl(getAccessPolicy().getAcl());
                client.putObject(putObjectRequest);
            }
        } catch (Exception e) {
            throw new OssException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
        return UploadResult.builder().url(getUrl() + "/" + path).filename(path).build();
    }

    public void delete(String path) {
        try {
            path = path.replace(getUrl() + "/", "");
            //本地环境
            if (isLocalEnv()) {
                FileUtils.delete(getLocalEnvFile(path));
                return;
            }
            client.deleteObject(properties.getBucketName(), path);
        } catch (Exception e) {
            throw new OssException("删除文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

    public UploadResult uploadSuffix(byte[] data, String suffix, String contentType) {
        return upload(data, getPath(properties.getPrefix(), suffix), contentType);
    }

    public UploadResult uploadSuffix(InputStream inputStream, String suffix, String contentType) {
        return upload(inputStream, getPath(properties.getPrefix(), suffix), contentType);
    }

    /**
     * 获取文件元数据
     *
     * @param path 完整文件路径
     */
    public ObjectMetadata getObjectMetadata(String path) {
        try {
            //本地环境
            if (isLocalEnv()) {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                BasicFileAttributes fileAttributes = Files.readAttributes(getLocalEnvFile(path).toPath(), BasicFileAttributes.class);
                //文件大小
                objectMetadata.setContentLength(fileAttributes.size());
                //文件创建时间
                //fileAttributes.creationTime()
                //文件最后修改时间
                objectMetadata.setLastModified(new Date(fileAttributes.lastModifiedTime().toMillis()));
                return objectMetadata;
            }
            path = path.replace(getUrl() + "/", "");
            S3Object object = client.getObject(properties.getBucketName(), path);
            return object.getObjectMetadata();
        } catch (IOException ex) {
            throw new OssException("获取文件元数据失败，请检查配置信息:[" + ex.getMessage() + "]");
        }
    }

    public InputStream getObjectContent(String path) {
        try {
            path = path.replace(getUrl() + "/", "");
            //本地环境
            if (isLocalEnv()) {
                return Files.newInputStream(getLocalEnvFile(path).toPath());
            }
            S3Object object = client.getObject(properties.getBucketName(), path);
            return object.getObjectContent();
        } catch (IOException ex) {
            throw new OssException("获取ObjectContent失败，请检查配置信息:[" + ex.getMessage() + "]");
        }
    }

    public String getUrl() {
        String domain = properties.getDomain();
        String endpoint = properties.getEndpoint();
        String header = OssConstant.IS_HTTPS.equals(properties.getIsHttps()) ? "https://" : "http://";
        // 云服务商直接返回
        if (StringUtils.containsAny(endpoint, OssConstant.CLOUD_SERVICE)) {
            if (StringUtils.isNotBlank(domain)) {
                return header + domain;
            }
            return header + properties.getBucketName() + "." + endpoint;
        }

        //请求路径
        String path = "/" + properties.getBucketName();
        //本地，单独处理
        if (isLocalEnv()) {
            path = OssConstant.LOCAL_RESOURCE_PATH + "/" + properties.getBucketName();
        }
        // minio 单独处理
        if (StringUtils.isNotBlank(domain)) {
            return header + domain + path;
        }
        return header + endpoint + path;
    }

    public String getPath(String prefix, String suffix) {
        // 生成uuid
        String uuid = IdUtil.fastSimpleUUID();
        // 文件路径
        String path = DateUtils.dateTime() + "/" + uuid;
        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path + suffix;
    }


    public String getConfigKey() {
        return configKey;
    }

    /**
     * 获取私有URL链接
     *
     * @param objectKey 对象KEY
     * @param second    授权时间
     */
    public String getPrivateUrl(String objectKey, Integer second) {
        //本地环境
        if (isLocalEnv()) {
            return objectKey;
        }
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(properties.getBucketName(), objectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(new Date(System.currentTimeMillis() + 1000L * second));
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    /**
     * 检查配置是否相同
     */
    public boolean checkPropertiesSame(OssProperties properties) {
        return this.properties.equals(properties);
    }

    /**
     * 获取当前桶权限类型
     *
     * @return 当前桶权限类型code
     */
    public AccessPolicyType getAccessPolicy() {
        return AccessPolicyType.getByType(properties.getAccessPolicy());
    }

    private static String getPolicy(String bucketName, PolicyType policyType) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n\"Statement\": [\n{\n\"Action\": [\n");
        if (policyType == PolicyType.WRITE) {
            builder.append("\"s3:GetBucketLocation\",\n\"s3:ListBucketMultipartUploads\"\n");
        } else if (policyType == PolicyType.READ_WRITE) {
            builder.append("\"s3:GetBucketLocation\",\n\"s3:ListBucket\",\n\"s3:ListBucketMultipartUploads\"\n");
        } else {
            builder.append("\"s3:GetBucketLocation\"\n");
        }
        builder.append("],\n\"Effect\": \"Allow\",\n\"Principal\": \"*\",\n\"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("\"\n},\n");
        if (policyType == PolicyType.READ) {
            builder.append("{\n\"Action\": [\n\"s3:ListBucket\"\n],\n\"Effect\": \"Deny\",\n\"Principal\": \"*\",\n\"Resource\": \"arn:aws:s3:::");
            builder.append(bucketName);
            builder.append("\"\n},\n");
        }
        builder.append("{\n\"Action\": ");
        switch (policyType) {
            case WRITE:
                builder.append("[\n\"s3:AbortMultipartUpload\",\n\"s3:DeleteObject\",\n\"s3:ListMultipartUploadParts\",\n\"s3:PutObject\"\n],\n");
                break;
            case READ_WRITE:
                builder.append("[\n\"s3:AbortMultipartUpload\",\n\"s3:DeleteObject\",\n\"s3:GetObject\",\n\"s3:ListMultipartUploadParts\",\n\"s3:PutObject\"\n],\n");
                break;
            default:
                builder.append("\"s3:GetObject\",\n");
                break;
        }
        builder.append("\"Effect\": \"Allow\",\n\"Principal\": \"*\",\n\"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("/*\"\n}\n],\n\"Version\": \"2012-10-17\"\n}\n");
        return builder.toString();
    }

    /**
     * 本地环境
     */
    private boolean isLocalEnv() {
        return OssConstant.LOCAL_ENV.equals(properties.getOssConfigId());
    }

    /**
     * 获取本地环境文件目录
     */
    private String getLocalEnvDir() {
        return OssProperties.getLocalResourceDir() + properties.getBucketName();
    }

    /**
     * 获取本地环境文件对象
     *
     * @param path 文件相对路径
     */
    private File getLocalEnvFile(String path) {
        return new File(FilenameUtils.normalize(getLocalEnvDir() + File.separator + path));
    }

}
