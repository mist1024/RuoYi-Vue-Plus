package org.dromara.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.constant.CacheNames;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.service.OssService;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StreamUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.file.FileUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.oss.core.OssClient;
import org.dromara.common.oss.entity.PartUploadResult;
import org.dromara.common.oss.entity.UploadResult;
import org.dromara.common.oss.enumd.AccessPolicyType;
import org.dromara.common.oss.factory.OssFactory;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.SysOss;
import org.dromara.system.domain.bo.MultipartBo;
import org.dromara.system.domain.bo.SysOssBo;
import org.dromara.system.domain.vo.MultipartVo;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.ISysOssService;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件上传 服务层实现
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysOssServiceImpl implements ISysOssService, OssService {

    private final SysOssMapper baseMapper;

    /**
     * 查询OSS对象存储列表
     *
     * @param bo        OSS对象存储分页查询对象
     * @param pageQuery 分页查询实体类
     * @return 结果
     */
    @Override
    public TableDataInfo<SysOssVo> queryPageList(SysOssBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysOss> lqw = buildQueryWrapper(bo);
        Page<SysOssVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<SysOssVo> filterResult = StreamUtils.toList(result.getRecords(), this::matchingUrl);
        result.setRecords(filterResult);
        return TableDataInfo.build(result);
    }

    /**
     * 根据一组 ossIds 获取对应的 SysOssVo 列表
     *
     * @param ossIds 一组文件在数据库中的唯一标识集合
     * @return 包含 SysOssVo 对象的列表
     */
    @Override
    public List<SysOssVo> listByIds(Collection<Long> ossIds) {
        List<SysOssVo> list = new ArrayList<>();
        for (Long id : ossIds) {
            SysOssVo vo = SpringUtils.getAopProxy(this).getById(id);
            if (ObjectUtil.isNotNull(vo)) {
                try {
                    list.add(this.matchingUrl(vo));
                } catch (Exception ignored) {
                    // 如果oss异常无法连接则将数据直接返回
                    list.add(vo);
                }
            }
        }
        return list;
    }

    /**
     * 根据一组 ossIds 获取对应文件的 URL 列表
     *
     * @param ossIds 以逗号分隔的 ossId 字符串
     * @return 以逗号分隔的文件 URL 字符串
     */
    @Override
    public String selectUrlByIds(String ossIds) {
        List<String> list = new ArrayList<>();
        for (Long id : StringUtils.splitTo(ossIds, Convert::toLong)) {
            SysOssVo vo = SpringUtils.getAopProxy(this).getById(id);
            if (ObjectUtil.isNotNull(vo)) {
                try {
                    list.add(this.matchingUrl(vo).getUrl());
                } catch (Exception ignored) {
                    // 如果oss异常无法连接则将数据直接返回
                    list.add(vo.getUrl());
                }
            }
        }
        return String.join(StringUtils.SEPARATOR, list);
    }

    private LambdaQueryWrapper<SysOss> buildQueryWrapper(SysOssBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysOss> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getFileName()), SysOss::getFileName, bo.getFileName());
        lqw.like(StringUtils.isNotBlank(bo.getOriginalName()), SysOss::getOriginalName, bo.getOriginalName());
        lqw.eq(StringUtils.isNotBlank(bo.getFileSuffix()), SysOss::getFileSuffix, bo.getFileSuffix());
        lqw.eq(StringUtils.isNotBlank(bo.getUrl()), SysOss::getUrl, bo.getUrl());
        lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
            SysOss::getCreateTime, params.get("beginCreateTime"), params.get("endCreateTime"));
        lqw.eq(ObjectUtil.isNotNull(bo.getCreateBy()), SysOss::getCreateBy, bo.getCreateBy());
        lqw.eq(StringUtils.isNotBlank(bo.getService()), SysOss::getService, bo.getService());
        lqw.orderByAsc(SysOss::getOssId);
        return lqw;
    }

    /**
     * 根据 ossId 从缓存或数据库中获取 SysOssVo 对象
     *
     * @param ossId 文件在数据库中的唯一标识
     * @return SysOssVo 对象，包含文件信息
     */
    @Cacheable(cacheNames = CacheNames.SYS_OSS, key = "#ossId")
    @Override
    public SysOssVo getById(Long ossId) {
        return baseMapper.selectVoById(ossId);
    }


    /**
     * 文件下载方法，支持一次性下载完整文件
     *
     * @param ossId    OSS对象ID
     * @param response HttpServletResponse对象，用于设置响应头和向客户端发送文件内容
     */
    @Override
    public void download(Long ossId, HttpServletResponse response) throws IOException {
        SysOssVo sysOss = SpringUtils.getAopProxy(this).getById(ossId);
        if (ObjectUtil.isNull(sysOss)) {
            throw new ServiceException("文件数据不存在!");
        }
        FileUtils.setAttachmentResponseHeader(response, sysOss.getOriginalName());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
        OssClient storage = OssFactory.instance(sysOss.getService());
        try (InputStream inputStream = storage.getObjectContent(sysOss.getUrl())) {
            int available = inputStream.available();
            IoUtil.copy(inputStream, response.getOutputStream(), available);
            response.setContentLength(available);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 上传 MultipartFile 到对象存储服务，并保存文件信息到数据库
     *
     * @param file 要上传的 MultipartFile 对象
     * @return 上传成功后的 SysOssVo 对象，包含文件信息
     * @throws ServiceException 如果上传过程中发生异常，则抛出 ServiceException 异常
     */
    @Override
    public SysOssVo upload(MultipartFile file) {
        String originalfileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        // 保存文件信息
        return buildResultEntity(originalfileName, suffix, storage.getConfigKey(), uploadResult);
    }

    /**
     * 上传文件到对象存储服务，并保存文件信息到数据库
     *
     * @param file 要上传的文件对象
     * @return 上传成功后的 SysOssVo 对象，包含文件信息
     */
    @Override
    public SysOssVo upload(File file) {
        String originalfileName = file.getName();
        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult = storage.uploadSuffix(file, suffix);
        // 保存文件信息
        return buildResultEntity(originalfileName, suffix, storage.getConfigKey(), uploadResult);
    }

    @NotNull
    private SysOssVo buildResultEntity(String originalfileName, String suffix, String configKey, UploadResult uploadResult) {
        SysOss oss = new SysOss();
        oss.setUrl(uploadResult.getUrl());
        oss.setFileSuffix(suffix);
        oss.setFileName(uploadResult.getFilename());
        oss.setOriginalName(originalfileName);
        oss.setService(configKey);
        baseMapper.insert(oss);
        SysOssVo sysOssVo = MapstructUtils.convert(oss, SysOssVo.class);
        return this.matchingUrl(sysOssVo);
    }

    /**
     * 删除OSS对象存储
     *
     * @param ids     OSS对象ID串
     * @param isValid 判断是否需要校验
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // 做一些业务上的校验,判断是否需要校验
        }
        List<SysOss> list = baseMapper.selectBatchIds(ids);
        for (SysOss sysOss : list) {
            OssClient storage = OssFactory.instance(sysOss.getService());
            storage.delete(sysOss.getUrl());
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 初始化分片上传任务
     *
     * @param multipartBo 初始化分片的参数对象
     * @return 分片上传对象信息
     */
    @Override
    public MultipartVo initiateMultipart(MultipartBo multipartBo) {
        OssClient storage = OssFactory.instance();
        String md5Digest = multipartBo.getMd5Digest();
        String osskey = GlobalConstants.OSS_CONTINUATION + LoginHelper.getUserId() + md5Digest;
        MultipartVo multipartVo = new MultipartVo();

        // 检查是否存在缓存，如果存在且超时时间在2小时内，则从缓存中获取上传信息
        if (RedisUtils.getTimeToLive(osskey) > 60 * 60 * 2 * 1000) {
            multipartVo = RedisUtils.getCacheObject(osskey);

            // 获取上传分段进度
            List<PartUploadResult> listParts = storage.listParts(multipartVo.getFilename(), multipartVo.getUploadId(), null, null);
            multipartVo.setPartUploadList(listParts.stream()
                .map(x -> new MultipartVo.PartUploadResult(x.getPartNumber(), x.getETag()))
                .collect(Collectors.toList()));
        } else {
            String originalName = multipartBo.getOriginalName();
            String suffix = StringUtils.substring(originalName, originalName.lastIndexOf("."), originalName.length());
            UploadResult uploadResult = storage.initiateMultipart(suffix);
            multipartVo.setFilename(uploadResult.getFilename());
            multipartVo.setUploadId(uploadResult.getUploadId());
            multipartVo.setMd5Digest(md5Digest);
            multipartVo.setOriginalName(originalName);
            multipartVo.setSuffix(suffix);
            RedisUtils.setCacheObject(osskey, multipartVo, Duration.ofMillis(60 * 60 * 72));
            RedisUtils.setCacheObject(GlobalConstants.OSS_MULTIPART + multipartVo.getUploadId(), multipartVo, Duration.ofMillis(60 * 60 * 72));
        }
        return multipartVo;
    }

    /**
     * 上传文件的分段（分片上传）
     *
     * @param multipartBo 分段上传的参数对象
     * @return 分片上传成功后的对象信息
     */
    @Override
    public MultipartVo uploadPart(MultipartBo multipartBo) {
        String uploadId = multipartBo.getUploadId();
        Integer partNumber = multipartBo.getPartNumber();
        MultipartVo multipartVo = RedisUtils.getCacheObject(GlobalConstants.OSS_MULTIPART + uploadId);
        if (ObjectUtil.isNull(multipartVo)) {
            throw new ServiceException("该分片任务不存在!");
        }
        OssClient storage = OssFactory.instance();
        String privateUrl = storage.uploadPartFutures(multipartVo.getFilename(), uploadId, partNumber, multipartBo.getMd5Digest(), 60 * 60 * 72);
        multipartVo.setPrivateUrl(privateUrl);
        multipartVo.setPartNumber(partNumber);
        return multipartVo;
    }

    /**
     * 获取上传分段进度
     *
     * @param multipartBo 分片上传对象信息
     * @return 分片上传对象信息
     */
    @Override
    public MultipartVo uploadPartList(MultipartBo multipartBo) {
        String uploadId = multipartBo.getUploadId();
        MultipartVo multipartVo = RedisUtils.getCacheObject(GlobalConstants.OSS_MULTIPART + uploadId);
        if (ObjectUtil.isNull(multipartVo)) {
            throw new ServiceException("该分片任务不存在!");
        }
        OssClient storage = OssFactory.instance();
        List<PartUploadResult> listParts = storage.listParts(multipartVo.getFilename(), uploadId, multipartBo.getMaxParts(), multipartBo.getPartNumberMarker());
        multipartVo.setPartUploadList(listParts.stream()
            .map(x -> new MultipartVo.PartUploadResult(x.getPartNumber(), x.getETag()))
            .collect(Collectors.toList()));
        return multipartVo;
    }

    /**
     * 合并分段
     *
     * @param multipartBo 分片上传对象信息
     * @return OSS对象存储视图对象
     */
    @Override
    public SysOssVo completeMultipartUpload(MultipartBo multipartBo) {
        String uploadId = multipartBo.getUploadId();
        String uploadIdKey = GlobalConstants.OSS_MULTIPART + uploadId;
        MultipartVo multipartVo = RedisUtils.getCacheObject(uploadIdKey);
        if (ObjectUtil.isNull(multipartVo)) {
            throw new ServiceException("该分片任务不存在!");
        }
        List<PartUploadResult> listParts = multipartBo.getPartUploadList().stream()
            .map(x -> PartUploadResult.builder()
                .partNumber(x.getPartNumber())
                .eTag(x.getETag())
                .build())
            .collect(Collectors.toList());
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult = storage.completeMultipartUpload(multipartVo.getFilename(), uploadId, listParts);
        // 保存文件信息
        SysOssVo sysOssVo = buildResultEntity(multipartVo.getOriginalName(), multipartVo.getSuffix(), storage.getConfigKey(), uploadResult);
        RedisUtils.deleteObject(uploadIdKey);
        RedisUtils.deleteObject(GlobalConstants.OSS_CONTINUATION + LoginHelper.getUserId() + multipartVo.getMd5Digest());
        return sysOssVo;
    }

    /**
     * 桶类型为 private 的URL 修改为临时URL时长为120s
     *
     * @param oss OSS对象
     * @return oss 匹配Url的OSS对象
     */
    private SysOssVo matchingUrl(SysOssVo oss) {
        OssClient storage = OssFactory.instance(oss.getService());
        // 仅修改桶类型为 private 的URL，临时URL时长为120s
        if (AccessPolicyType.PRIVATE == storage.getAccessPolicy()) {
            oss.setUrl(storage.getPrivateUrl(oss.getFileName(), 120));
        }
        return oss;
    }

}
