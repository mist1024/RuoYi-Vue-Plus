package org.dromara.liteflow.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

/**
 * 编排规则对象 liteflow_chain
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("liteflow_chain")
public class LiteflowChain extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 配置环境
     */
    private String applicationName;

    /**
     * 编排规则ID
     */
    private String chainName;

    /**
     * 状态（0未激活 1激活）
     */
    private Integer chainStatus;

    /**
     * EL表达式
     */
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
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remark;


}
