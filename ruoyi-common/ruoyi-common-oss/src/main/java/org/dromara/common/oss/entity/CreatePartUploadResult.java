package org.dromara.common.oss.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 创建上传分片任务返回体
 *
 * @author SunnyDeer0911
 */
@Data
@Builder
public class CreatePartUploadResult {

    /**
     * 文件路径
     */
    private String url;

    /**
     * 分片上传ID
     */
    private String uploadId;

    /**
     * 文件名
     */
    private String filename;

}
