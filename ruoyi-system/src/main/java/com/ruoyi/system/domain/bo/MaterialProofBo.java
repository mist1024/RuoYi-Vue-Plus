package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 材料业务对象 material_proof
 *
 * @author ruoyi
 * @date 2023-03-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialProofBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 关联申请id
     */
    @NotBlank(message = "关联申请id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String houseId;

    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createTime;

    /**
     * 0待审核，1审核失败，2审核成功
     */
    @NotNull(message = "0待审核，1审核失败，2审核成功不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 所对应材料的id
     */
    @NotBlank(message = "所对应材料的id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String modulePathId;

    /**
     * 数据
     */
    @NotBlank(message = "数据不能为空", groups = { AddGroup.class, EditGroup.class })
    private String file;

    /**
     * 对应module_paht的字段
     */
    @NotBlank(message = "对应module_paht的字段不能为空", groups = { AddGroup.class, EditGroup.class })
    private String materialKey;

    /**
     * 对应module_path的描述
     */
    @NotBlank(message = "对应module_path的描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 审核人
     */
    @NotBlank(message = "审核人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String auditDept;

    /**
     * 审核人类型
     */
    @NotBlank(message = "审核人类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkType;

    private String materialName;

    /**
     * 次数
     */
    private Integer number;

}
