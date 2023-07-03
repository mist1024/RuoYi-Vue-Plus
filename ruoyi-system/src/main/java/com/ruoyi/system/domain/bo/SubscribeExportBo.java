package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 预约导出业务对象 subscribe_export
 *
 * @author ruoyi
 * @date 2023-04-20
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SubscribeExportBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 路径
     */
    @NotBlank(message = "路径不能为空", groups = { AddGroup.class, EditGroup.class })
    private String path;

    /**
     * 申请人id
     */
    @NotBlank(message = "申请人id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userId;

    private String description;

    private String processKey;

    private String exportStatus;


}
