package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 推送日志业务对象 push_log
 *
 * @author ruoyi
 * @date 2023-07-20
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class PushLogBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 推送数据
     */
    @NotBlank(message = "推送数据不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pushData;

    /**
     * 返回结果
     */
    @NotBlank(message = "返回结果不能为空", groups = { AddGroup.class, EditGroup.class })
    private String resultData;


}
