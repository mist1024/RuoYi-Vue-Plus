package org.dromara.liteflow.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.liteflow.domain.LiteflowScript;

/**
 * 脚本业务对象 liteflow_script
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LiteflowScript.class, reverseConvertGenerate = false)
public class LiteflowScriptBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 配置环境
     */
    @NotBlank(message = "配置环境不能为空", groups = {AddGroup.class})
    private String applicationName;

    /**
     * 脚本ID
     */
    @NotBlank(message = "脚本ID不能为空", groups = {AddGroup.class})
    private String scriptId;

    /**
     * 脚本名称
     */
    @NotBlank(message = "脚本名称不能为空", groups = {AddGroup.class})
    private String scriptName;

    /**
     * 脚本语言类型（groovy | qlexpress | js | python | lua | aviator | java | kotlin）
     */
    @NotBlank(message = "脚本语言类型不能为空", groups = {AddGroup.class})
    private String scriptLanguage;

    /**
     * 脚本类型(script普通脚本节点，脚本里无需返回 switch_script选择脚本节点，脚本里需要返回选择的节点Id boolean_script条件脚本节点，脚本里需要返回true/false for_script数量循环节点，脚本里需要返回数值类型，表示循环次数)
     */
    @NotBlank(message = "脚本类型不能为空", groups = {AddGroup.class})
    private String scriptType;

    /**
     * 状态（0未激活 1激活）
     */
    private Integer scriptStatus;

    /**
     * 脚本内容
     */
    @NotBlank(message = "脚本内容不能为空", groups = {AddGroup.class, EditGroup.class})
    private String scriptData;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
    private String remark;

}
