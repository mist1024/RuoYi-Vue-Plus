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
import org.dromara.common.oss.entity.*;
import org.dromara.common.oss.enumd.AccessPolicyType;
import org.dromara.common.oss.factory.OssFactory;
import org.dromara.common.oss.utils.PartUploadCacheHelper;
import org.dromara.system.domain.SysOss;
import org.dromara.system.domain.bo.SysOssBo;
import org.dromara.system.domain.bo.SysOssPartUploadBo;
import org.dromara.system.domain.vo.SysOssPartUploadVo;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    @Override
    public SysOssPartUploadVo partUpload(MultipartFile file, SysOssPartUploadBo bo) {
        try {
            String uploadId = bo.getUploadId();

            // 如果 uploadId 为空，则说明是新建上传分片
            if (StringUtils.isBlank(uploadId)) {
                return createPartUpload(file, bo);
            }

            // uploadId不为空，从缓存中获取分片上传信息
            PartUploadInfo partUploadInfo = PartUploadCacheHelper.getCache(uploadId);
            if (ObjectUtil.isNull(partUploadInfo)) {
                throw new ServiceException("未找到分片上传信息！");
            }
            // TODO 是否需要合并分片
            partUploadInfo.setNeedMerge(bo.getNeedMerge());
            OssClient storage = OssFactory.instance(partUploadInfo.getService());
            // 上传分片并返回结果
            return partUpload(storage, partUploadInfo, file, bo.getPartNumber());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 创建并上传分片
     *
     * @param file 需要进行上传的分片文件
     * @param bo   OSS分片上传业务对象
     * @return 分片上传对象信息VO
     */
    private SysOssPartUploadVo createPartUpload(MultipartFile file, SysOssPartUploadBo bo) throws IOException {
        // 文件大小
        Long fileSize = bo.getFileSize();
        // 分片大小
        Long partSize = bo.getPartSize();
        // 分片数量
        Long totalParts = bo.getTotalParts();
        // 文件名
        String originalFileName = bo.getFileName();
        // 从文件名中获取文件后缀
        String suffix = StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
        OssClient storage = OssFactory.instance();
        CreatePartUploadResult partUpload = storage.createPartUploadSuffix(suffix);
        // 构建分片上传信息对象
        PartUploadInfo partUploadInfo = new PartUploadInfo();
        partUploadInfo.setUploadId(partUpload.getUploadId());
        partUploadInfo.setFileName(partUpload.getFilename());
        partUploadInfo.setOriginalName(originalFileName);
        partUploadInfo.setFileSuffix(suffix);
        partUploadInfo.setUrl(partUpload.getUrl());
        partUploadInfo.setFileSize(fileSize);
        partUploadInfo.setService(storage.getConfigKey());
        partUploadInfo.setPartSize(partSize);
        partUploadInfo.setTotalParts(totalParts);
        partUploadInfo.setPartInfoList(new ArrayList<>());
        // TODO 是否需要合并分片
        partUploadInfo.setNeedMerge(bo.getNeedMerge());
        // 上传分片并返回结果
        return partUpload(storage, partUploadInfo, file, bo.getPartNumber());
    }

    /**
     * 上传分片
     *
     * @param storage        OSS客户端
     * @param partUploadInfo 分片上传信息对象
     * @param file           需要进行上传的分片文件
     * @param partNumber     分片序号
     * @return 分片上传对象信息VO
     */
    private SysOssPartUploadVo partUpload(OssClient storage, PartUploadInfo partUploadInfo, MultipartFile file, Integer partNumber) throws IOException {
        // 文件上传ID
        String uploadId = partUploadInfo.getUploadId();
        // 构建分片上传对象信息VO
        SysOssPartUploadVo sysOssPartUploadVo = new SysOssPartUploadVo();
        sysOssPartUploadVo.setUploadId(uploadId);
        sysOssPartUploadVo.setUrl(partUploadInfo.getUrl());
        sysOssPartUploadVo.setPartInfoList(partUploadInfo.getPartInfoList());
        sysOssPartUploadVo.setMergeCompleted(partUploadInfo.isNeedMerge());
        // 上传分片
        PartUploadResult partUploadResult = storage.partUpload(file.getInputStream(), partUploadInfo.getFileName(), uploadId, partNumber, file.getSize());
        // 将完成上传的分片信息放入集合中
        partUploadInfo.getPartInfoList().add(new PartInfo(partUploadResult.getPartNumber(), partUploadResult.getETag()));
        // 分片上传信息放入缓存
        PartUploadCacheHelper.putCache(partUploadInfo);
        // 检查是否需要合并 - 已经完成所有上传时操作
        if (partUploadInfo.isNeedMerge()) {
            // 如果已经完成最后一片的上传，则进行合并
            UploadResult uploadResult = storage.completePartUpload(uploadId, partUploadInfo.getFileName(), partUploadInfo.getPartInfoList());
            // 不报错即合并成功，删除缓存中的分片上传信息
            PartUploadCacheHelper.removeCache(uploadId);
            // 数据落库
            SysOss oss = new SysOss();
            oss.setFileSuffix(partUploadInfo.getFileSuffix());
            oss.setOriginalName(partUploadInfo.getOriginalName());
            oss.setService(storage.getConfigKey());
            oss.setFileName(partUploadInfo.getFileName());
            oss.setUrl(partUploadInfo.getUrl());
            baseMapper.insert(oss);
            // 回填对象存储ID
            sysOssPartUploadVo.setOssId(oss.getOssId());
        }
        return sysOssPartUploadVo;
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
