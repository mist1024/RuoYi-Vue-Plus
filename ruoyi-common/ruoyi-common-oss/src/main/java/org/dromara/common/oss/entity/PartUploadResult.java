package org.dromara.common.oss.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 分片上传返回体
 *
 * @author SunnyDeer0911
 */
@Data
@Builder
public class PartUploadResult {

    /**
     * 分片上传ID
     */
    private String uploadId;

    /**
     * 分片序号
     */
    private Integer partNumber;

    /***
     * 分片大小
     */
    private Long partSize;

    /**
     * 已上传对象的实体标记（用来校验文件）
     */
    private String eTag;

}
