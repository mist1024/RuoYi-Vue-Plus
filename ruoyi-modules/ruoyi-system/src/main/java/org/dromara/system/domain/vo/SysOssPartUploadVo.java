package org.dromara.system.domain.vo;

import lombok.Data;
import org.dromara.common.oss.entity.PartInfo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分片上传对象信息VO
 *
 * @author SunnyDeer0911
 */
@Data
public class SysOssPartUploadVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分片上传ID
     */
    private String uploadId;

    /**
     * URL地址
     */
    private String url;

    /**
     * 已完成上传的分片列表
     */
    List<PartInfo> partInfoList;

    /**
     * 合并完成
     */
    private Boolean mergeCompleted;

    /**
     * 分片上传ID - 仅在文件成功合并，数据落库时才会生成
     * @see this#mergeCompleted 分片文件合并状态
     */
    private Long ossId;

}
