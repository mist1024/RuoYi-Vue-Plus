package org.dromara.system.domain.bo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * OSS分片上传业务对象
 *
 * @author SunnyDeer0911
 */
@Data
public class SysOssPartUploadBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件上传ID（创建分片上传任务时，S3 OSS返回的唯一标识）
     * TODO 上传第一片分片时允许为空，后续需要从第一次分片上传的响应中获取回填
     */
    private String uploadId;

    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    /**
     * 文件大小（文件的总大小）
     */
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;

    /**
     * 分片序号*（分片序号从1开始）
     */
    @NotNull(message = "分片序号不能为空")
    @Min(value = 1, message = "分片序号不能小于1")
    private Integer partNumber;

    /**
     * 分片大小
     */
//    @NotNull(message = "分片大小不能为空")
    private Long partSize;

    /**
     * 分片数量
     */
//    @NotNull(message = "分片数量不能为空")
    private Long totalParts;

    /**
     * 是否需要合并
     */
    @NotNull(message = "是否需要合并不能为空")
    private Boolean needMerge;
}
