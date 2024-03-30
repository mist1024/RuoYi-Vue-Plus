package org.dromara.common.oss.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分片上传信息对象
 *
 * @author SunnyDeer0911
 */
@Data
public class PartUploadInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件上传ID（分片上传扩展字段，文件分片上传S3 OSS返回的唯一标识）
     */
    private String uploadId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原名
     */
    private String originalName;

    /**
     * 文件后缀名
     */
    private String fileSuffix;

    /**
     * URL地址
     */
    private String url;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 服务商
     */
    private String service;

    /**
     * 分片大小
     */
    private Long partSize;

    /**
     * 分片数量
     */
    private Long totalParts;

    /**
     * 合并完成
     */
    private boolean needMerge;

    /**
     * 已完成上传的分片列表
     */
    List<PartInfo> partInfoList;
}
