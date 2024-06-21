package org.dromara.liteflow.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.liteflow.domain.LiteflowChain;

/**
 * 编排规则业务对象 liteflow_chain
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LiteflowChain.class, reverseConvertGenerate = false)
public class LiteflowChainBo extends BaseEntity {

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
     * 编排规则ID
     */
    @NotBlank(message = "编排规则ID不能为空", groups = {AddGroup.class})
    private String chainName;

    /**
     * 状态（0未激活 1激活）
     */
    private Integer chainStatus;

    /**
     * EL表达式
     */
    @NotBlank(message = "EL表达式不能为空", groups = {AddGroup.class, EditGroup.class})
    private String elStr;

    /**
     * namespace（决策路由很多，想要判断某一组决策路由时在chain层面加入namespace参数）
     */
    private String nameSpace;

    /**
     * 决策路由EL
     */
    private String route;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
    private String remark;

}
