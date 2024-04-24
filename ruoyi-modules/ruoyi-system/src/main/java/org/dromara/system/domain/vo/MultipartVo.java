package org.dromara.system.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.dromara.system.domain.SysOss;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author AprilWind
 */
@Data
@AutoMapper(target = SysOss.class)
public class MultipartVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 用于分片上传任务的 Upload ID
     * 在初始化分片上传时获取，并在后续的分片上传和完成上传过程中使用
     */
    private String uploadId;

    /**
     * 分片编号（从1开始递增）
     */
    private Integer partNumber;

    /**
     * 用于私有预签名 URL
     * 用于上传文件或分片的预签名 URL，确保文件或分片在上传时具有私有访问权限
     */
    private String privateUrl;

    /**
     * 已上传列表（最大长度一千）
     * 必须是唯一且按照递增顺序排列，严格检查是否漏传
     */
    private List<PartUploadResult> partUploadList;

    @Data
    @AllArgsConstructor
    public static class PartUploadResult implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 分片编号（从1开始递增）
         */
        private Integer partNumber;

        /**
         * 从上传部分的内容生成的实体标签
         */
        private String eTag;
    }

}
