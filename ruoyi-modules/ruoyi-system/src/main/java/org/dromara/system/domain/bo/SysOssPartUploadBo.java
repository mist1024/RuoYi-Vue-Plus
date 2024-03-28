package org.dromara.system.domain.bo;

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
     * 文件上传ID（分片上传扩展字段，文件分片上传S3 OSS返回的唯一标识）
     * TODO 上传第一片分片时允许为空，后续需要从第一次分片上传的响应中获取回
     */
    private String uploadId;

    /**
     * 文件名
     * TODO 允许为空，如果为空，则从上传的文件中获取
     */
    private String fileName;

    /**
     * 文件大小
     * TODO 文件的总大小，上传第一片分片时不允许为空，后续请求用不到，可传可不传
     */
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;

    /**
     * 分片序号
     * TODO 分片序号不能为空，从1开始
     */
    @NotNull(message = "分片序号不能为空")
    private Integer partNumber;

    /**
     * 分片大小
     */
    private Long partSize;

    /**
     * 分片数量
     */
    private Long totalParts;

    /**
     * 完成状态
     * TODO 允许为空，为空默认
     */
    private Boolean needMerge;

}
