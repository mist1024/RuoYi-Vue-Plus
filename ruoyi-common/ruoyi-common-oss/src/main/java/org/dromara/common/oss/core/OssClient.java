package org.dromara.common.oss.core;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import org.dromara.common.core.constant.Constants;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.file.FileUtils;
import org.dromara.common.oss.constant.OssConstant;
import org.dromara.common.oss.entity.CreatePartUploadResult;
import org.dromara.common.oss.entity.PartInfo;
import org.dromara.common.oss.entity.PartUploadResult;
import org.dromara.common.oss.entity.UploadResult;
import org.dromara.common.oss.enumd.AccessPolicyType;
import org.dromara.common.oss.enumd.PolicyType;
import org.dromara.common.oss.exception.OssException;
import org.dromara.common.oss.properties.OssProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.BlockingInputStreamAsyncRequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.*;
import software.amazon.awssdk.transfer.s3.progress.LoggingTransferListener;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * S3 存储协议 所有兼容S3协议的云厂商均支持
 * 阿里云 腾讯云 七牛云 minio
 *
 * @author AprilWind
 */
public class OssClient {

    /**
     * 服务商
     */
    private final String configKey;

    /**
     * 配置属性
     */
    private final OssProperties properties;

    /**
     * Amazon S3 异步客户端
     */
    private final S3AsyncClient client;

    /**
     * 用于管理 S3 数据传输的高级工具
     */
    private final S3TransferManager transferManager;

    /**
     * AWS S3 预签名 URL 的生成器
     */
    private final S3Presigner presigner;

    /**
     * 构造方法
     *
     * @param configKey     配置键
     * @param ossProperties Oss配置属性
     */
    public OssClient(String configKey, OssProperties ossProperties) {
        this.configKey = configKey;
        this.properties = ossProperties;
        try {
            // 创建 AWS 认证信息
            StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey()));

            //创建AWS基于 CRT 的 S3 客户端
            this.client = S3AsyncClient.crtBuilder()
                .credentialsProvider(credentialsProvider)
                .endpointOverride(URI.create(getEndpoint()))
                .region(of())
                .targetThroughputInGbps(20.0)
                .minimumPartSizeInBytes(10 * 1025 * 1024L)
                .checksumValidationEnabled(false)
                .build();

            //AWS基于 CRT 的 S3 AsyncClient 实例用作 S3 传输管理器的底层客户端
            this.transferManager = S3TransferManager.builder().s3Client(this.client).build();

            // 检查是否连接到 MinIO，MinIO 使用 HTTPS 限制使用域名访问，需要启用路径样式访问
            S3Configuration config = S3Configuration.builder().chunkedEncodingEnabled(false)
                // minio 使用https限制使用域名访问 需要此配置 站点填域名
                .pathStyleAccessEnabled(!StringUtils.containsAny(properties.getEndpoint(), OssConstant.CLOUD_SERVICE)).build();

            // 创建 预签名 URL 的生成器 实例，用于生成 S3 预签名 URL
            this.presigner = S3Presigner.builder()
                .region(of())
                .credentialsProvider(credentialsProvider)
                .endpointOverride(URI.create(getDomain()))
                .serviceConfiguration(config)
                .build();

            // 创建存储桶
            createBucket();
        } catch (Exception e) {
            if (e instanceof OssException) {
                throw e;
            }
            throw new OssException("配置错误! 请检查系统配置:[" + e.getMessage() + "]");
        }
    }

    /**
     * 同步创建存储桶
     * 如果存储桶不存在，会进行创建；如果存储桶存在，不执行任何操作
     *
     * @throws OssException 当创建存储桶时发生异常时抛出
     */
    public void createBucket() {
        String bucketName = properties.getBucketName();
        try {
            // 尝试获取存储桶的信息
            client.headBucket(
                    x -> x.bucket(bucketName)
                        .build())
                .join();
        } catch (Exception ex) {
            if (ex.getCause() instanceof NoSuchBucketException) {
                try {
                    // 存储桶不存在，尝试创建存储桶
                    client.createBucket(
                            x -> x.bucket(bucketName))
                        .join();

                    // 设置存储桶的访问策略（Bucket Policy）
                    client.putBucketPolicy(
                            x -> x.bucket(bucketName)
                                .policy(getPolicy(bucketName, getAccessPolicy().getPolicyType())))
                        .join();
                } catch (S3Exception e) {
                    // 存储桶创建或策略设置失败
                    throw new OssException("创建Bucket失败, 请核对配置信息:[" + e.getMessage() + "]");
                }
            } else {
                throw new OssException("判断Bucket是否存在失败，请核对配置信息:[" + ex.getMessage() + "]");
            }
        }
    }

    /**
     * 上传文件到 Amazon S3，并返回上传结果
     *
     * @param filePath  本地文件路径
     * @param key       在 Amazon S3 中的对象键
     * @param md5Digest 本地文件的 MD5 哈希值（可选）
     * @return UploadResult 包含上传后的文件信息
     * @throws OssException 如果上传失败，抛出自定义异常
     */
    public UploadResult upload(Path filePath, String key, String md5Digest) {
        try {
            // 构建上传请求对象
            FileUpload fileUpload = transferManager.uploadFile(
                x -> x.putObjectRequest(
                        y -> y.bucket(properties.getBucketName())
                            .key(key)
                            .contentMD5(StringUtils.isNotEmpty(md5Digest) ? md5Digest : null)
                            .build())
                    .addTransferListener(LoggingTransferListener.create())
                    .source(filePath).build());

            // 等待上传完成并获取上传结果
            CompletedFileUpload uploadResult = fileUpload.completionFuture().join();
            String eTag = uploadResult.response().eTag();

            // 提取上传结果中的 ETag，并构建一个自定义的 UploadResult 对象
            return UploadResult.builder().url(getUrl() + StringUtils.SLASH + key).filename(key).eTag(eTag).build();
        } catch (Exception e) {
            // 捕获异常并抛出自定义异常
            throw new OssException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
        } finally {
            // 无论上传是否成功，最终都会删除临时文件
            FileUtils.del(filePath);
        }
    }

    /**
     * 上传 InputStream 到 Amazon S3
     *
     * @param inputStream 要上传的输入流
     * @param key         在 Amazon S3 中的对象键
     * @param length      输入流的长度
     * @return UploadResult 包含上传后的文件信息
     * @throws OssException 如果上传失败，抛出自定义异常
     */
    public UploadResult upload(InputStream inputStream, String key, Long length) {
        // 如果输入流不是 ByteArrayInputStream，则将其读取为字节数组再创建 ByteArrayInputStream
        if (!(inputStream instanceof ByteArrayInputStream)) {
            inputStream = new ByteArrayInputStream(IoUtil.readBytes(inputStream));
        }
        try {
            // 创建异步请求体（length如果为空会报错）
            BlockingInputStreamAsyncRequestBody body = AsyncRequestBody.forBlockingInputStream(length);

            // 使用 transferManager 进行上传
            Upload upload = transferManager.upload(
                x -> x.requestBody(body)
                    .putObjectRequest(
                        y -> y.bucket(properties.getBucketName())
                            .key(key)
                            .build())
                    .build());

            // 将输入流写入请求体
            body.writeInputStream(inputStream);

            // 等待文件上传操作完成
            CompletedUpload uploadResult = upload.completionFuture().join();
            String eTag = uploadResult.response().eTag();

            // 提取上传结果中的 ETag，并构建一个自定义的 UploadResult 对象
            return UploadResult.builder().url(getUrl() + StringUtils.SLASH + key).filename(key).eTag(eTag).build();
        } catch (Exception e) {
            throw new OssException("上传文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

    /**
     * 创建分片任务
     *
     * @param key 在 Amazon S3 中的对象键
     * @return CreatePartUploadResult 创建上传分片任务返回体，包含上传后的文件信息
     * @throws OssException 如果创建失败，抛出自定义异常
     */
    public CreatePartUploadResult createPartUpload(String key) {
        try {
            // 创建分片上传
            CompletableFuture<CreateMultipartUploadResponse> createPartUploadFuture = client.createMultipartUpload(builder -> builder.bucket(properties.getBucketName())
                .key(key)
                .acl(getAccessPolicy().getObjectCannedACL())
                .build());
            // 等待创建分片上传任务完成
            CreateMultipartUploadResponse createPartUploadResult = createPartUploadFuture.join();
            // 获取分片上传任务 uploadId
            String uploadId = createPartUploadResult.uploadId();
            return CreatePartUploadResult.builder().uploadId(uploadId).url(getUrl() + StringUtils.SLASH + key).filename(key).build();
        } catch (Exception e) {
            throw new OssException("创建分片上传任务失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

    /**
     * 创建分片任务
     *
     * @param suffix 对象键的后缀
     * @return CreatePartUploadResult 创建上传分片任务返回体，包含上传后的文件信息
     * @throws OssException 如果创建失败，抛出自定义异常
     */
    public CreatePartUploadResult createPartUploadSuffix(String suffix) {
        return createPartUpload(getPath(properties.getPrefix(), suffix));
    }

    /**
     * 分片文件上传
     *
     * @param data       文件字节数组
     * @param key        在 Amazon S3 中的对象键
     * @param uploadId   oss分片上传id
     * @param partNumber 分片序号
     * @return PartUploadResult 分片上传结果，包含上传后的分片文件信息
     * @throws OssException 如果上传失败，抛出自定义异常
     */
    public PartUploadResult partUpload(byte[] data, String key, String uploadId, Integer partNumber) {
        try {
            // 创建异步请求体
            AsyncRequestBody requestBody = AsyncRequestBody.fromBytes(data);
            // 使用 s3client 进行分片上传
            CompletableFuture<UploadPartResponse> partUploadFuture = client.uploadPart(builder -> builder.bucket(properties.getBucketName())
                .key(key)
                .uploadId(uploadId)
                .partNumber(partNumber)
                .build(), requestBody);

            // 等待文件上传操作完成
            UploadPartResponse partUploadResult = partUploadFuture.join();
            String eTag = partUploadResult.eTag();

            // 提取上传结果中的 ETag，并构建一个自定义的 PartUploadResult 对象
            return PartUploadResult.builder().uploadId(uploadId).partNumber(partNumber).partSize((long) data.length).eTag(eTag).build();
        } catch (Exception e) {
            throw new OssException("上传分片文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

    /**
     * 分片文件上传
     *
     * @param inputStream 要上传的输入流
     * @param key         在 Amazon S3 中的对象键
     * @param uploadId    oss分片上传id
     * @param partNumber  分片序号
     * @param partSize    分片大小
     * @return PartUploadResult 分片上传结果，包含上传后的分片文件信息
     * @throws OssException 如果上传失败，抛出自定义异常
     */
    public PartUploadResult partUpload(InputStream inputStream, String key, String uploadId, Integer partNumber, Long partSize) {
        if (!(inputStream instanceof ByteArrayInputStream)) {
            inputStream = new ByteArrayInputStream(IoUtil.readBytes(inputStream));
        }
        try {
            // 创建异步请求体（length如果为空会报错）
            BlockingInputStreamAsyncRequestBody requestBody = AsyncRequestBody.forBlockingInputStream(partSize);
            // 使用 s3client 进行分片上传
            CompletableFuture<UploadPartResponse> partUploadFuture = client.uploadPart(builder -> builder.bucket(properties.getBucketName())
                .key(key)
                .uploadId(uploadId)
                .partNumber(partNumber)
                .build(), requestBody);

            // 将输入流写入请求体
            requestBody.writeInputStream(inputStream);

            // 等待文件上传操作完成
            UploadPartResponse partUploadResult = partUploadFuture.join();
            String eTag = partUploadResult.eTag();

            // 提取上传结果中的 ETag，并构建一个自定义的 PartUploadResult 对象
            return PartUploadResult.builder().uploadId(uploadId).partNumber(partNumber).partSize(partSize).eTag(eTag).build();
        } catch (Exception e) {
            throw new OssException("上传分片文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

    /**
     * 终止分片上传任务
     *
     * @param uploadId 分片上传任务ID
     * @param key      文件在 Amazon S3 中的对象键
     * @return boolean 终止分片上传任务结果
     * @throws OssException 如果终止分片上传失败，抛出自定义异常
     */
    public boolean abortPartUpload(String uploadId, String key) {
        try {
            // 使用 s3client 请求终止分片上传任务
            CompletableFuture<AbortMultipartUploadResponse> abortPartUploadFuture = client.abortMultipartUpload(builder -> builder.bucket(properties.getBucketName())
                .uploadId(uploadId)
                .key(key)
                .build());
            // 等待终止分片上传任务请求响应
            AbortMultipartUploadResponse abortPartUploadResult = abortPartUploadFuture.join();
            // 没有异常即成功
            return true;
        } catch (Exception e) {
//            throw new OssException("终止分片上传任务失败，请检查配置信息:[" + e.getMessage() + "]");
            return false;
        }
    }

    /**
     * 列出已经上传的分片
     *
     * @param uploadId 分片上传任务ID
     * @param key      文件在 Amazon S3 中的对象键
     * @return List<Part> 已经上传的分片信息
     */
    public List<Part> listParts(String uploadId, String key) {
        // 使用 s3client 请求获取已经上传的分片信息
        CompletableFuture<ListPartsResponse> listPartsFuture = client.listParts(builder -> builder.bucket(properties.getBucketName())
            .uploadId(uploadId)
            .key(key)
            .build());
        // 等待获取已经上传的分片信息请求响应
        ListPartsResponse listPartsResult = listPartsFuture.join();
        return listPartsResult.parts();
    }

    /**
     * 完成分片上传 - 合并分片
     *
     * @param uploadId       分片上传任务ID
     * @param key            文件在 Amazon S3 中的对象键
     * @param completedParts 已经上传的分片信息
     * @return UploadResult 包含上传后的文件信息
     */
    public UploadResult completePartUpload(String uploadId, String key, Collection<CompletedPart> completedParts) {
        // 使用 s3client 请求完成分片上传任务
        CompletableFuture<CompleteMultipartUploadResponse> completePartUploadFuture = client.completeMultipartUpload(builder -> builder.bucket(properties.getBucketName())
            .uploadId(uploadId)
            .key(key)
            .multipartUpload(CompletedMultipartUpload.builder().parts(completedParts).build())
            .build());
        // 等待完成分片上传任务请求响应
        CompleteMultipartUploadResponse completePartUploadResult = completePartUploadFuture.join();
        // 提取上传结果中的 ETag，并构建一个自定义的 UploadResult 对象
        return UploadResult.builder().url(getUrl() + "/" + key).filename(key).eTag(completePartUploadResult.eTag()).build();
    }

    /**
     * 完成分片上传 - 合并分片
     *
     * @param uploadId     分片上传任务ID
     * @param key          文件在 Amazon S3 中的对象键
     * @param partInfoList 已经上传的分片信息
     * @return UploadResult 包含上传后的文件信息
     */
    public UploadResult completePartUpload(String uploadId, String key, List<PartInfo> partInfoList) {
        List<CompletedPart> completedParts = partInfoList.stream()
            .map(partInfo -> CompletedPart.builder()
                .partNumber(partInfo.getPartNumber())
                .eTag(partInfo.getETag())
                .build())
            .toList();
        return completePartUpload(uploadId, key, completedParts);
    }

    /**
     * 完成分片上传 - 合并分片（根据已上传的分片结果合并）
     *
     * @param uploadId 分片上传任务ID
     * @param key      文件在 Amazon S3 中的对象键
     * @return UploadResult 包含上传后的文件信息
     */
    public UploadResult completePartUpload(String uploadId, String key) {
        // 获取已上传的文件分片列表
        List<Part> parts = listParts(uploadId, key);
        // 构建 CompletedPart 列表
        List<CompletedPart> completedParts = parts.stream()
            .map(part -> CompletedPart.builder().partNumber(part.partNumber()).eTag(part.eTag()).build())
            .toList();
        // 合并分片并返回结果
        return completePartUpload(uploadId, key, completedParts);
    }

    /**
     * 下载文件从 Amazon S3 到临时目录
     *
     * @param path 文件在 Amazon S3 中的对象键
     * @return 下载后的文件在本地的临时路径
     * @throws OssException 如果下载失败，抛出自定义异常
     */
    public Path fileDownload(String path) {
        // 构建临时文件
        Path tempFilePath = FileUtils.createTempFile().toPath();
        // 使用 S3TransferManager 下载文件
        FileDownload downloadFile = transferManager.downloadFile(
            x -> x.getObjectRequest(
                    y -> y.bucket(properties.getBucketName())
                        .key(removeBaseUrl(path))
                        .build())
                .addTransferListener(LoggingTransferListener.create())
                .destination(tempFilePath)
                .build());
        // 等待文件下载操作完成
        downloadFile.completionFuture().join();
        return tempFilePath;
    }

    /**
     * 删除云存储服务中指定路径下文件
     *
     * @param path 指定路径
     */
    public void delete(String path) {
        try {
            client.deleteObject(
                x -> x.bucket(properties.getBucketName())
                    .key(removeBaseUrl(path))
                    .build());
        } catch (Exception e) {
            throw new OssException("删除文件失败，请检查配置信息:[" + e.getMessage() + "]");
        }
    }

    /**
     * 获取私有URL链接
     *
     * @param objectKey 对象KEY
     * @param second    授权时间
     */
    public String getPrivateUrl(String objectKey, Integer second) {
        // 使用 AWS S3 预签名 URL 的生成器 获取对象的预签名 URL
        URL url = presigner.presignGetObject(
                x -> x.signatureDuration(Duration.ofSeconds(second))
                    .getObjectRequest(
                        y -> y.bucket(properties.getBucketName())
                            .key(objectKey)
                            .build())
                    .build())
            .url();
        return url.toString();
    }

    /**
     * 上传 byte[] 数据到 Amazon S3，使用指定的后缀构造对象键。
     *
     * @param data   要上传的 byte[] 数据
     * @param suffix 对象键的后缀
     * @return UploadResult 包含上传后的文件信息
     * @throws OssException 如果上传失败，抛出自定义异常
     */
    public UploadResult uploadSuffix(byte[] data, String suffix) {
        return upload(new ByteArrayInputStream(data), getPath(properties.getPrefix(), suffix), Long.valueOf(data.length));
    }

    /**
     * 上传 InputStream 到 Amazon S3，使用指定的后缀构造对象键。
     *
     * @param inputStream 要上传的输入流
     * @param suffix      对象键的后缀
     * @param length      输入流的长度
     * @return UploadResult 包含上传后的文件信息
     * @throws OssException 如果上传失败，抛出自定义异常
     */
    public UploadResult uploadSuffix(InputStream inputStream, String suffix, Long length) {
        return upload(inputStream, getPath(properties.getPrefix(), suffix), length);
    }

    /**
     * 上传文件到 Amazon S3，使用指定的后缀构造对象键
     *
     * @param file   要上传的文件
     * @param suffix 对象键的后缀
     * @return UploadResult 包含上传后的文件信息
     * @throws OssException 如果上传失败，抛出自定义异常
     */
    public UploadResult uploadSuffix(File file, String suffix) {
        return upload(file.toPath(), getPath(properties.getPrefix(), suffix), null);
    }

    /**
     * 获取文件输入流
     *
     * @param path 完整文件路径
     * @return 输入流
     */
    public InputStream getObjectContent(String path) throws IOException {
        // 下载文件到临时目录
        Path tempFilePath = fileDownload(path);
        // 创建输入流
        InputStream inputStream = Files.newInputStream(tempFilePath);
        // 删除临时文件
        FileUtils.del(tempFilePath);
        // 返回对象内容的输入流
        return inputStream;
    }

    /**
     * 获取 S3 客户端的终端点 URL
     *
     * @return 终端点 URL
     */
    public String getEndpoint() {
        // 根据配置文件中的是否使用 HTTPS，设置协议头部
        String header = getIsHttps();
        // 拼接协议头部和终端点，得到完整的终端点 URL
        return header + properties.getEndpoint();
    }

    /**
     * 获取 S3 客户端的终端点 URL（自定义域名）
     *
     * @return 终端点 URL
     */
    public String getDomain() {
        // 从配置中获取域名、终端点、是否使用 HTTPS 等信息
        String domain = properties.getDomain();
        String endpoint = properties.getEndpoint();
        String header = getIsHttps();

        // 如果是云服务商，直接返回域名或终端点
        if (StringUtils.containsAny(endpoint, OssConstant.CLOUD_SERVICE)) {
            return StringUtils.isNotEmpty(domain) ? header + domain : header + endpoint;
        }

        // 如果是 MinIO，处理域名并返回
        if (StringUtils.isNotEmpty(domain)) {
            return domain.startsWith(Constants.HTTPS) || domain.startsWith(Constants.HTTP) ? domain : header + domain;
        }

        // 返回终端点
        return header + endpoint;
    }

    /**
     * 根据传入的 region 参数返回相应的 AWS 区域
     * 如果 region 参数非空，使用 Region.of 方法创建并返回对应的 AWS 区域对象
     * 如果 region 参数为空，返回一个默认的 AWS 区域（例如，us-east-1），作为广泛支持的区域
     *
     * @return 对应的 AWS 区域对象，或者默认的广泛支持的区域（us-east-1）
     */
    public Region of() {
        //AWS 区域字符串
        String region = properties.getRegion();
        // 如果 region 参数非空，使用 Region.of 方法创建对应的 AWS 区域对象，否则返回默认区域
        return StringUtils.isNotEmpty(region) ? Region.of(region) : Region.US_EAST_1;
    }

    /**
     * 获取云存储服务的URL
     *
     * @return 文件路径
     */
    public String getUrl() {
        String domain = properties.getDomain();
        String endpoint = properties.getEndpoint();
        String header = getIsHttps();
        // 云服务商直接返回
        if (StringUtils.containsAny(endpoint, OssConstant.CLOUD_SERVICE)) {
            return header + (StringUtils.isNotEmpty(domain) ? domain : properties.getBucketName() + "." + endpoint);
        }
        // MinIO 单独处理
        if (StringUtils.isNotEmpty(domain)) {
            // 如果 domain 以 "https://" 或 "http://" 开头
            return (domain.startsWith(Constants.HTTPS) || domain.startsWith(Constants.HTTP)) ?
                domain + StringUtils.SLASH + properties.getBucketName() : header + domain + StringUtils.SLASH + properties.getBucketName();
        }
        return header + endpoint + StringUtils.SLASH + properties.getBucketName();
    }

    /**
     * 生成一个符合特定规则的、唯一的文件路径。通过使用日期、UUID、前缀和后缀等元素的组合，确保了文件路径的独一无二性
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 文件路径
     */
    public String getPath(String prefix, String suffix) {
        // 生成uuid
        String uuid = IdUtil.fastSimpleUUID();
        // 生成日期路径
        String datePath = DateUtils.datePath();
        // 拼接路径
        String path = StringUtils.isNotEmpty(prefix) ?
            prefix + StringUtils.SLASH + datePath + StringUtils.SLASH + uuid : datePath + StringUtils.SLASH + uuid;
        return path + suffix;
    }

    /**
     * 移除路径中的基础URL部分，得到相对路径
     *
     * @param path 完整的路径，包括基础URL和相对路径
     * @return 去除基础URL后的相对路径
     */
    public String removeBaseUrl(String path) {
        return path.replace(getUrl() + StringUtils.SLASH, "");
    }

    /**
     * 服务商
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * 获取是否使用 HTTPS 的配置，并返回相应的协议头部。
     *
     * @return 协议头部，根据是否使用 HTTPS 返回 "https://" 或 "http://"
     */
    public String getIsHttps() {
        return OssConstant.IS_HTTPS.equals(properties.getIsHttps()) ? Constants.HTTPS : Constants.HTTP;
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
     * 生成 AWS S3 存储桶访问策略
     *
     * @param bucketName 存储桶
     * @param policyType 桶策略类型
     * @return 符合 AWS S3 存储桶访问策略格式的字符串
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
                      "Principal": "*",
                      "Action": [
                        "s3:GetBucketLocation",
                        "s3:ListBucket",
                        "s3:ListBucketMultipartUploads"
                      ],
                      "Resource": "arn:aws:s3:::bucketName"
                    },
                    {
                      "Effect": "Allow",
                      "Principal": "*",
                      "Action": [
                        "s3:AbortMultipartUpload",
                        "s3:DeleteObject",
                        "s3:GetObject",
                        "s3:ListMultipartUploadParts",
                        "s3:PutObject"
                      ],
                      "Resource": "arn:aws:s3:::bucketName/*"
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
                      "Principal": "*",
                      "Action": ["s3:GetBucketLocation"],
                      "Resource": "arn:aws:s3:::bucketName"
                    },
                    {
                      "Effect": "Deny",
                      "Principal": "*",
                      "Action": ["s3:ListBucket"],
                      "Resource": "arn:aws:s3:::bucketName"
                    },
                    {
                      "Effect": "Allow",
                      "Principal": "*",
                      "Action": "s3:GetObject",
                      "Resource": "arn:aws:s3:::bucketName/*"
                    }
                  ]
                }
                """;
        };
        return policy.replaceAll("bucketName", bucketName);
    }

}
