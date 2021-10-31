package com.ruoyi.isc.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 服务管控审核
 *
 * @author Wenchao Gong
 * @date 2021-09-09
 */

@Data
@ApiModel("服务管控审核业务对象")
public class IscAuditBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID集合
     */
    @ApiModelProperty(value = "ID集合")
    @NotEmpty(message = "请选择记录")
    private List<Long> ids;

    /**
     * 审核状态（0待审核 1审核通过 2驳回）
     */
    @ApiModelProperty(value = "审核状态（0待审核 1审核通过 2驳回）")
    @NotBlank(message = "请填写审核结果")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
