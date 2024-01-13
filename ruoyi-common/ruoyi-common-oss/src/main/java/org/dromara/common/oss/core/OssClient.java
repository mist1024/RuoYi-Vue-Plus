package org.dromara.common.oss.core;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import io.netty.handler.ssl.SslProvider;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.oss.constant.OssConstant;
import org.dromara.common.oss.entity.UploadResult;
import org.dromara.common.oss.enumd.AccessPolicyType;
import org.dromara.common.oss.enumd.PolicyType;
import org.dromara.common.oss.exception.OssException;
import org.dromara.common.oss.properties.OssProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.client.config.SdkAdvancedAsyncClientOption;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;

import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * S3-v2 存储协议 所有兼容S3协议的云厂商均支持
 * 阿里云 腾讯云 七牛云 minio
 *
 * @author Lion Li / David Wei
 */
public class OssClient {

    private final String configKey;

    private final OssProperties properties;

    private final S3AsyncClient client;

    private final S3Presigner presigner;

    public OssClient(String configKey, OssProperties ossProperties) {
        this.configKey = configKey;
        this.properties = ossProperties;
        try {
            StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey()));

            S3AsyncClientBuilder client = S3AsyncClient.builder()
                .httpClient(
                    NettyNioAsyncHttpClient.builder()
                        .sslProvider(SslProvider.OPENSSL)
                        .build()
                )
                .asyncConfiguration(
                    b -> b.advancedOption(SdkAdvancedAsyncClientOption
                            .FUTURE_COMPLETION_EXECUTOR,
                        Executors.newFixedThreadPool(10)
                    )
                )
                .credentialsProvider(credentialsProvider)
                .endpointOverride(URI.create(getEndpoint()))
                .region(Region.of(properties.getRegion()));

            S3Presigner.Builder presigner = S3Presigner.builder()
                .credentialsProvider(credentialsProvider)
                .endpointOverride(URI.create(getPresignerEndpoint()))
                .region(Region.of(properties.getRegion()));

            if (!StringUtils.containsAny(properties.getEndpoint(), OssConstant.CLOUD_SERVICE)) {
                // minio 使用https限制使用域名访问 需要此配置 站点填域名
                client.forcePathStyle(true);
            }

            this.client = client.build();
            this.presigner = presigner.build();

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
            String bucketName = properties.getBucketName();
            try {
                client.headBucket(HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build())
                    .get();
                return;
            } catch (Exception e) {
                // 桶不存在，捕获异常。
                // 继续桶创建操作
            }
            AccessPolicyType accessPolicy = getAccessPolicy();
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .acl(accessPolicy.getBucketCannedACL())
                .build();

            PutBucketPolicyRequest putBucketPolicyRequest = PutBucketPolicyRequest.builder()
                .bucket(bucketName)
                .policy(getPolicy(bucketName, accessPolicy.getPolicyType()))
                .build();
            client.createBucket(createBucketRequest).get();
            client.putBucketPolicy(putBucketPolicyRequest).get();
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
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(properties.getBucketName())
                .key(path)
                .contentType(contentType)
                .acl(getAccessPolicy().getObjectCannedACL()) // 设置上传对象的 Acl
                .build();
            client.putObject(putObjectRequest, AsyncRequestBody.fromInputStream(
                inputStream,
                (long) inputStream.available(),
                Executors.newFixedThreadPool(1)
            )).get();
        } catch (Exception e) {
            throw new OssException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
        return UploadResult.builder().url(getUrl() + "/" + path).filename(path).build();
    }

    public UploadResult upload(File file, String path) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(properties.getBucketName())
                .key(path)
                .acl(getAccessPolicy().getObjectCannedACL())// 设置上传对象的 Acl
                .build();
            client.putObject(putObjectRequest, AsyncRequestBody.fromFile(file)).get();
        } catch (Exception e) {
            throw new OssException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
        return UploadResult.builder().url(getUrl() + "/" + path).filename(path).build();
    }

    public void delete(String path) {
        path = path.replace(getUrl() + "/", "");
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(properties.getBucketName())
                .key(path)
                .build();
            client.deleteObject(deleteObjectRequest).get();
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

    public UploadResult uploadSuffix(File file, String suffix) {
        return upload(file, getPath(properties.getPrefix(), suffix));
    }

    /**
     * 获取文件元数据
     *
     * @param path 完整文件路径
     */
    public HeadObjectResponse getObjectMetadata(String path) {
        path = path.replace(getUrl() + "/", "");
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
            .bucket(properties.getBucketName())
            .key(path)
            .build();
        return client.headObject(headObjectRequest).join();
    }

    public String getEndpoint() {
        String endpoint = properties.getEndpoint();
        String header = OssConstant.IS_HTTPS.equals(properties.getIsHttps()) ? "https://" : "http://";
        return header + endpoint;
    }

    /**
     * 获取预签名地址的 Endpoint 地址
     * 注意：此地址不能带bucket，「云服务商」使用非路径形式，生成的url会自动带上bucket前缀
     * @return
     */
    public String getPresignerEndpoint() {
        String domain = properties.getDomain();
        String endpoint = properties.getEndpoint();
        String header = OssConstant.IS_HTTPS.equals(properties.getIsHttps()) ? "https://" : "http://";
        if (StringUtils.isNotBlank(domain)) {
            return header + domain;
        }
        return header + endpoint;
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
        // minio 单独处理
        if (StringUtils.isNotBlank(domain)) {
            return header + domain + "/" + properties.getBucketName();
        }
        return header + endpoint + "/" + properties.getBucketName();
    }

    public String getPath(String prefix, String suffix) {
        // 生成uuid
        String uuid = IdUtil.fastSimpleUUID();
        // 文件路径
        String path = DateUtils.datePath() + "/" + uuid;
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
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(properties.getBucketName())
                .key(objectKey)
                .build();
            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(second))
                .getObjectRequest(getObjectRequest)
                .build();

            PresignedGetObjectRequest presignedGetObjectRequest = presigner
                .presignGetObject(getObjectPresignRequest);
            URL url = presignedGetObjectRequest.url();

            return url.toString();
        } catch (Exception e) {
            return null;
        }
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

    /**
     * 获取 minio 的 policy 定义
     * @param bucketName
     * @param policyType
     * @return
     */
    private static String getPolicy(String bucketName, PolicyType policyType) {
        String policy = switch (policyType) {
            case WRITE -> """
                {
                    "Version": "2012-10-17",
                    "Statement": []
                }
                """;
            case READ_WRITE -> """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Principal": {
                                "AWS": [
                                    "*"
                                ]
                            },
                            "Action": [
                                "s3:GetBucketLocation",
                                "s3:ListBucket",
                                "s3:ListBucketMultipartUploads"
                            ],
                            "Resource": [
                                "arn:aws:s3:::bucketName"
                            ]
                        },
                        {
                            "Effect": "Allow",
                            "Principal": {
                                "AWS": [
                                    "*"
                                ]
                            },
                            "Action": [
                                "s3:AbortMultipartUpload",
                                "s3:DeleteObject",
                                "s3:GetObject",
                                "s3:ListMultipartUploadParts",
                                "s3:PutObject"
                            ],
                            "Resource": [
                                "arn:aws:s3:::bucketName/*"
                            ]
                        }
                    ]
                }
                """;
            case READ -> """
                {
                  "Version": "2012-10-17",
                  "Statement": [
                    {
                      "Effect": "Allow",
                      "Principal": {
                        "AWS": [
                          "*"
                        ]
                      },
                      "Action": [
                        "s3:GetBucketLocation"
                      ],
                      "Resource": [
                        "arn:aws:s3:::bucketName"
                      ]
                    },
                    {
                      "Effect": "Deny",
                      "Principal": "*",
                      "Action": [
                        "s3:ListBucket"
                      ],
                      "Resource": [
                        "arn:aws:s3:::bucketName"
                      ]
                    },
                    {
                      "Effect": "Allow",
                      "Principal": {
                        "AWS": [
                          "*"
                        ]
                      },
                      "Action": [
                        "s3:GetObject"
                      ],
                      "Resource": [
                        "arn:aws:s3:::bucketName/*"
                      ]
                    }
                  ]
                }
                """;
        };

        return policy.replaceAll("bucketName", bucketName);
    }
}
