package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 材料对象 material_proof
 *
 * @author ruoyi
 * @date 2023-03-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("material_proof")
public class MaterialProof extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 关联申请id
     */
    private String houseId;
    /**
     * 0待审核，1审核失败，2审核成功
     */
    private Long status;
    /**
     * 所对应材料的id
     */
    private String modulePathId;
    /**
     * 数据
     */
    private String file;
    /**
     * 对应module_paht的字段
     */
    private String materialKey;
    /**
     * 对应module_path的描述
     */
    private String description;
    /**
     * 审核人
     */
    private String auditDept;
    /**
     * 审核人类型
     */
    private String checkType;

    private String materialName;

    private String processKey;

    private Integer number;

}
